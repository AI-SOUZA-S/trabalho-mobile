package com.example.aplicativodemonstracao

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView

class CustomAdapter (private val context: Context, private val dataSource: ArrayList<Int>) : BaseAdapter() {
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

        val itemText: TextView = view.findViewById(R.id.textView)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)

        itemText.text = dataSource[position].toString()

        deleteButton.setOnClickListener {
            dataSource.removeAt(position)
            notifyDataSetChanged()
        }

        return view
    }

}