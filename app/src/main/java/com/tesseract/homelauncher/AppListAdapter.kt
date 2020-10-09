package com.tesseract.homelauncher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.tesseract.sdk.AppData
import java.util.*

class AppListAdapter(var appList: MutableList<AppData>?, itemViewClickListener: ItemViewClickListener) : RecyclerView.Adapter<ViewHolder>(),Filterable {

    var appFilterList : MutableList<AppData>? = null
    var itemViewClickListener: ItemViewClickListener? = null

    init {
        appFilterList = appList
        this.itemViewClickListener = itemViewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.appinfo_rowitem_layout, parent, false)


        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {


        var size: Int? = appFilterList?.let { it ->
            it.size
        }

        return size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.appFilterList?.get(position)?.let { holder.bind(it)
            holder.itemView.setOnClickListener {itemView ->
                itemViewClickListener?.onItemViewClick(it.appPackageName)
            }
        }


    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    appFilterList = appList
                } else {
                    val resultList : MutableList<AppData>? = mutableListOf()
                    appList?.forEach { row ->
                        if (row.appName.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList?.add(row)
                        }
                    }
                    appFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = appFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                appFilterList = results?.values as MutableList<AppData>?
                notifyDataSetChanged()
            }

        }
    }

}



class ViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private var textView2: TextView? = null
    private var textView3: TextView? = null
    private var textView4: TextView? = null
    private var textView5: TextView? = null
    private var textView1: TextView? = null
    var appIconImageView: AppCompatImageView? = null


    init {
        textView3 = itemView.findViewById(R.id.textView3)
        textView2 = itemView.findViewById(R.id.textView2)
        textView4 = itemView.findViewById(R.id.textView4)
        textView5 = itemView.findViewById(R.id.textView5)
        textView1 = itemView.findViewById(R.id.textView1)
        appIconImageView = itemView.findViewById(R.id.appIconImageView)


    }


    fun bind(appData: AppData) {
        textView2?.text = appData.appName
        textView3?.text = appData.appPackageName

        textView4?.text = "Version Name " + appData.appVersionName
        textView5?.text = "Version Code " + appData.appVersionCode
        textView1?.text = appData.appClassName
        appIconImageView?.setImageDrawable(appData.appIconDrawable)
    }

}
