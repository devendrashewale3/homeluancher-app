package com.tesseract.homelauncher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AppPackageReceiver: BroadcastReceiver() {
    @Suppress("DEPRECATION")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if (intent?.action.equals(Intent.ACTION_PACKAGE_ADDED) || intent?.action.equals(Intent.ACTION_PACKAGE_INSTALL)) {
                val packageName = intent?.data?.encodedSchemeSpecificPart
                Toast.makeText(
                    context,
                    context?.getString(R.string.app_install) + packageName,
                    Toast.LENGTH_SHORT
                ).show();
            }

            if (intent?.action.equals(Intent.ACTION_PACKAGE_REMOVED) ) {
                val packageName = intent?.data?.encodedSchemeSpecificPart
                Toast.makeText(
                    context,
                    context?.getString(R.string.app_uninstall) + packageName,
                    Toast.LENGTH_SHORT
                ).show();
            }

        }
    }
}