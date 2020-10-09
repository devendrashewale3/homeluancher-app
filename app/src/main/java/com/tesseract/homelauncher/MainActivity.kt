package com.tesseract.homelauncher

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tesseract.sdk.AppData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),ItemViewClickListener {

    var appLauncherList: MutableList<AppData>? = mutableListOf()

    var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reclyerView.layoutManager = LinearLayoutManager(this@MainActivity)

        reclyerView.adapter = AppListAdapter(appLauncherList,this)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel?.fetchAppLauncherList(applicationContext)
            ?.observe(this, object : Observer<MutableList<AppData>?> {
                override fun onChanged(t: MutableList<AppData>?) {
                    Log.d("app data", t.toString())
                    appLauncherList = t

                    reclyerView.adapter = AppListAdapter(appLauncherList,this@MainActivity)
                }

            })


        app_searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (reclyerView.adapter as AppListAdapter).filter.filter(newText)
                return false
            }

        })


    }

    override fun onItemViewClick(packageName: String) {
        mainViewModel?.launchApplication(applicationContext,packageName)
    }


}