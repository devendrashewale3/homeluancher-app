package com.tesseract.sdk

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.util.Log


class LauncherList {

    fun getLauncherList(context: Context): MutableList<AppData> {

        var appDataList: MutableList<AppData> = mutableListOf()
        val pm: PackageManager = context.packageManager
        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        val lst = pm.queryIntentActivities(i, 0)
        for (resolveInfo in lst) {
            Log.d("Test", "New Launcher Found: " + resolveInfo.activityInfo.packageName)
            val packageInfo = context.packageManager.getPackageInfo(
                resolveInfo.activityInfo.packageName,
                PackageManager.GET_ACTIVITIES
            )
            val mainActivity =
                context.packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName)

            var versionName = packageInfo.versionName
            var versionCode = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionCode = "Version Code " + packageInfo.longVersionCode.toString()
            }

            var appData = AppData(
                resolveInfo.loadLabel(context.packageManager).toString(),
                resolveInfo.activityInfo.packageName,
                mainActivity?.component?.className,
                versionName,
                versionCode,
                resolveInfo.loadIcon(context.packageManager)

            )
            appDataList.add(appData)
        }

        appDataList.sortBy { it.appName }
        return appDataList
    }
}