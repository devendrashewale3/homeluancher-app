package com.tesseract.sdk

import android.graphics.drawable.Drawable

data class AppData(
    var appName: String,
    var appPackageName: String,
    var appClassName: String?,
    var appVersionName: String,
    var appVersionCode: String,
    var appIconDrawable: Drawable

)