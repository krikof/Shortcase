package com.example.shortcaserc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.shortcaserc.databinding.FragmentExplainationBinding

class ExplainationFragment: Fragment() {
    private var _binding: FragmentExplainationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var closeButton: Button
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExplainationBinding.inflate(inflater, container, false)

        val root: View = binding.root

        closeButton = binding.closeButton
        webView = binding.explainationWebView

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        explainationWebViewSetup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners() {
        closeButton.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }
    }

    private fun explainationWebViewSetup() {
        webView.webViewClient = WebViewClient()
        webView.apply {
            loadUrl("https://relevantxkcd.appspot.com/")
            settings.javaScriptEnabled = true
        }
    }
}