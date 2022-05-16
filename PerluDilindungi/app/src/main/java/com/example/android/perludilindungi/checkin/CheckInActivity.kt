package com.example.android.perludilindungi.checkin

import GpsService
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.android.perludilindungi.R
import com.example.android.perludilindungi.databinding.ActivityCheckInBinding
//import com.example.android.perludilindungi.network.CheckInProperty
import com.example.android.perludilindungi.network.PerluDilindungiApi
import com.example.android.perludilindungi.network.PerluDilindungiApiService
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.zxing.BarcodeFormat
import kotlinx.coroutines.runBlocking
import java.lang.Error
import java.util.jar.Manifest
import kotlin.math.log

class CheckInActivity : AppCompatActivity(), SensorEventListener {
    lateinit var backButton: Button
    private lateinit var sensorManager: SensorManager
    private var sensorTemperatur: Sensor? = null
    lateinit var textTemprature : TextView
    lateinit var barcodeScanner : CodeScanner
    lateinit var viewBarcode : CodeScannerView
    lateinit var tv6: TextView
    lateinit var tv7: TextView
    var currentLocation = Array<Double>(2){0.0; 0.0}
    lateinit var gpsService: GpsService
    lateinit var viewModel: CheckInViewModel
    lateinit var imageStatus: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)
        supportActionBar?.hide()
        backButton = findViewById(R.id.backButton)
        textTemprature = findViewById(R.id.tempratureTextView)
        imageStatus = findViewById(R.id.imageView)
        imageStatus.visibility = View.INVISIBLE
        backButton.setOnClickListener {
            this.onBackPressed()
        }

        //Bind with datamodel
        viewModel = ViewModelProvider(this).get(CheckInViewModel::class.java)

        //Checking for permission
        permissionHandler()

        //Temperature Sensor
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorTemperatur = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        //Gps Service
        gpsService = GpsService(this)
        tv7 = findViewById(R.id.textView7)
        getLocation()

        //Binding for barcode
        tv6 = findViewById(R.id.textView6)
        viewBarcode = findViewById(R.id.barcodeLayoutView)
        barcodeHandler()

        //viewmodel observer
        viewModel.checkInResponse.observe(this, Observer {
            imageStatus.visibility = View.VISIBLE
            imageStatus.setImageResource(R.drawable.ic_broken_image)
            tv6.text = ""
            tv7.text = ""
            if(it.success && it.data.userStatus.equals("green")){
                tv6.text = "Berhasil CheckIn"
                imageStatus.setImageResource(R.drawable.cekin_woke)
            }else if(it.success){
                tv6.text = "Gagal CheckIn"
                tv7.text = it.data.reason
                imageStatus.setImageResource(R.drawable.cekin_err)
            }else{
                tv6.text = "Kesalahan ${it.code}"
                tv7.text = it.message
                imageStatus.setImageResource(R.drawable.cekin_err)
            }
        })
    }

    override fun onBackPressed() {
        finish()
        //super.onBackPressed()
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if(p0.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE){
                textTemprature.setText(String.format("%.1f",p0.values[0]) + "°C")
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume() {
        super.onResume()
        if(sensorTemperatur != null) {
            sensorManager.registerListener(this, sensorTemperatur, SensorManager.SENSOR_DELAY_NORMAL)
        }
        supportActionBar?.hide()
        barcodeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        if(sensorTemperatur != null) {
            sensorManager.unregisterListener(this)
        }
        supportActionBar?.hide()
        barcodeScanner.releaseResources()
    }

    fun barcodeHandler(){
        barcodeScanner = CodeScanner(this, viewBarcode)
        barcodeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = listOf(BarcodeFormat.QR_CODE)
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false
            decodeCallback = DecodeCallback {
                runOnUiThread {
                    viewModel.postCheckIn(it.text, currentLocation[0], currentLocation[1])
                }
            }
            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Kesalahan", "terjadi kesalahan dalam inisialisasi kamera : ${it.message}")
                }
            }
        }
    }

    fun permissionHandler(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                Array<String>(1){android.Manifest.permission.CAMERA},101)
        }
    }

    fun getLocation(){
        currentLocation = gpsService.getMyLocation()
    }
}