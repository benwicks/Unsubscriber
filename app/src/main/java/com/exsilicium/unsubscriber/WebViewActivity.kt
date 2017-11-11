package com.exsilicium.unsubscriber

import android.annotation.SuppressLint
import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.TextView

class WebViewActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast") override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        window.attributes = window.attributes.apply {
            flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS and WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        }
        val intentDataUri = intent.data
        val host = intentDataUri.host

        findViewById<TextView>(R.id.tv_text).text = resources.getString(R.string.unsubscribe_from_format_prompt, host)
        findViewById<CheckBox>(R.id.checked_text_view).text = resources.getString(R.string.do_every_time_format, host)

        findViewById<View>(R.id.button_yes).setOnClickListener {
            launchUri(intentDataUri)
            finish()
        }

        findViewById<View>(R.id.button_never).setOnClickListener { finish() }
    }

    companion object {
        const val CHROME_PACKAGE_NAME = "com.android.chrome"
    }

    private fun launchUri(intentDataUri: Uri?) {
        val connection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(componentName: ComponentName, client: CustomTabsClient) {
                val builder = CustomTabsIntent.Builder()
                val intent = builder.build()
                client.warmup(0L)
                intent.intent.`package` = CHROME_PACKAGE_NAME
                intent.launchUrl(this@WebViewActivity, intentDataUri)
            }

            override fun onServiceDisconnected(name: ComponentName) {}
        }
        CustomTabsClient.bindCustomTabsService(this, CHROME_PACKAGE_NAME, connection)
    }
}
