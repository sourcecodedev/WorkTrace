package com.upc.worktrace.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.upc.worktrace.R

class CustomSpinnerAdapter<T>(
    context: Context,
    private val items: List<SpinnerItem<T>>
) : ArrayAdapter<SpinnerItem<T>>(context, R.layout.spinner_item, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val view = recycledView ?: LayoutInflater.from(context)
            .inflate(R.layout.spinner_item, parent, false)

        val item = getItem(position)
        val textView = view.findViewById<TextView>(R.id.spinnerText)
        textView.text = item?.nombre

        return view
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getSelectedItemId(position: Int): T? {
        return items.getOrNull(position)?.id
    }
} 