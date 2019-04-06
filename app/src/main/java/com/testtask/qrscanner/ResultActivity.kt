package com.testtask.qrscanner

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val url = intent.getStringExtra(KEY_URL) ?: ""
        url.isNotBlank().let {
            web.visibility = View.VISIBLE
            web.settings.javaScriptEnabled = true
            web.loadUrl(url)
        }
    }

    companion object {
        const val KEY_URL = "key_url"
    }
}
