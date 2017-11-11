package com.exsilicium.unsubscriber

import android.app.Activity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat

class WebViewActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomTabsIntent.Builder().setToolbarColor(
                ContextCompat.getColor(this@WebViewActivity, android.R.color.holo_orange_dark)
        ).build().apply { intent.`package` = "com.android.chrome" }.launchUrl(this@WebViewActivity, intent.data)
        finish()
    }
}
