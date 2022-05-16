package com.example.android.perludilindungi.webViewNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.perludilindungi.databinding.FragmentWebViewNewsBinding

class WebViewNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentWebViewNewsBinding.inflate(inflater, container, false)

        // get args from other fragment
        val args = WebViewNewsFragmentArgs.fromBundle(requireArguments())
        binding.webview.loadUrl(args.link)

        return binding.root
    }
}