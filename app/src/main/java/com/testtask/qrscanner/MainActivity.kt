package com.testtask.qrscanner

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.webkit.URLUtil
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                title_text.setText(R.string.home_title)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                title_text.setText(R.string.title_scan)
                openScanner()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                title_text.setText(R.string.title_more)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    // Обработка результата сканирования
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result == null || result.contents == null) {
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            if (!URLUtil.isValidUrl(result.contents)) {
                showError()
            } else {
                openResultScreen(result.contents)
            }
        }
    }

    // открываает сканер qr кодов
    private fun openScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt(getString(R.string.scan_message))
        integrator.setBarcodeImageEnabled(true)
        integrator.setOrientationLocked(false)
        integrator.setBeepEnabled(false)
        integrator.initiateScan()
    }

    // Открывает экран с результатами сканирования
    private fun openResultScreen(url: String) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.KEY_URL, url)
        startActivity(intent)
    }

    // Показывает диалог с сообщением об ошибке
    private fun showError() {
        val errorMessage = StringBuilder()
            .append(getString(R.string.error_message_1)).append("\n")
            .append(getString(R.string.error_message_2)).append("\n")
            .append(getString(R.string.error_message_3)).append("\n")
            .append(getString(R.string.error_message_4))

        AlertDialog.Builder(this)
            .setTitle(R.string.error_title)
            .setMessage(errorMessage)
            .setCancelable(false)
            .setNegativeButton(R.string.close) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

}
