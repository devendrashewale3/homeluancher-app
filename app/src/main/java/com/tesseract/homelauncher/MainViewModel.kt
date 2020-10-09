package com.tesseract.homelauncher

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tesseract.sdk.AppData
import com.tesseract.sdk.LauncherList


class MainViewModel : ViewModel() {

     var articleLiveData: MutableLiveData<MutableList<AppData>?> = MutableLiveData()
    var launcherListObj = LauncherList()


    fun fetchAppLauncherList(context:Context): MutableLiveData<MutableList<AppData>?> {
        var appLauncherList: MutableList<AppData>? = launcherListObj.getLauncherList(context)

       articleLiveData.value = appLauncherList

        return articleLiveData
    }

    fun launchApplication(context: Context?,packageName: String?) {
        val launchIntent: Intent? = packageName?.let {
            context?.getPackageManager()?.getLaunchIntentForPackage(
                it
            )
        }
        context?.startActivity(launchIntent)
    }
}