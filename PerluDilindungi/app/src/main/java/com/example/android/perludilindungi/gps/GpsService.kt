import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat


class GpsService(context: Context) : Service(), LocationListener {
    var phoneLocation : Array<Double> = Array(2){ 6.874404665445239; 107.60490985070649 }
    lateinit var locationManager: LocationManager
    lateinit var context: Context

    init {
        this.context = context
        _checkSecurity()
        _findLoc()
    }

    override fun onBind(p0: Intent?): IBinder? {return null}

    override fun onLocationChanged(p0: Location) {}

    fun getMyLocation() : Array<Double>{
        _findLoc()
        return phoneLocation
    }

    fun stopGPS(){
        if(locationManager!=null){
            locationManager.removeUpdates(this@GpsService)
        }
    }

    fun getDistance(lat: Double, long: Double) : Float{
        this._findLoc()
        val results = FloatArray(1)
        Location.distanceBetween(
            phoneLocation[0], phoneLocation[1],
            lat, long,
            results
        )
        return results[0]
    }

    private fun _findLoc(){
        this.locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        val minTimeUpdate : Long = 1000 * 60 * 1
        val minDistance : Float = 5.0F
        val isGpsNyala : Boolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isInternetNyala : Boolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        _checkSecurity()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        if(isInternetNyala){
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTimeUpdate,
                minDistance,
                this@GpsService
            )
            val lokasi : Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (lokasi != null) {
                this.phoneLocation[0] = lokasi.latitude
                this.phoneLocation[1] = lokasi.longitude
            }


        }else if(isGpsNyala){
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTimeUpdate,
                minDistance,
                this@GpsService
            )
            val lokasi : Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lokasi != null) {
                this.phoneLocation[0] = lokasi.latitude
                this.phoneLocation[1] = lokasi.longitude
            }

        }

    }

    private fun _checkSecurity(){
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (this as Activity?)!!,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ),
                101
            )
        }

        if(ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                (this as Activity?)!!,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
        }
    }
}