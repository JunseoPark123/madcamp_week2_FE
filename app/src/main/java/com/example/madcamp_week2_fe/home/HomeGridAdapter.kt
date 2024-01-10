package com.example.madcamp_week2_fe.home

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.madcamp_week2_fe.R

class HomeGridAdapter(private val context: Context, private val items: List<HomeGridItem>) : BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.home_grid_item, parent, false)

        val item = items[position]
        val imageView = view.findViewById<ImageView>(R.id.image)
        val store = view.findViewById<TextView>(R.id.store)
        val menuName = view.findViewById<TextView>(R.id.menuName)
        val detail1 = view.findViewById<TextView>(R.id.detail1)
        val detail2 = view.findViewById<TextView>(R.id.detail2)
        val price = view.findViewById<TextView>(R.id.price)
        val amount = view.findViewById<TextView>(R.id.amount)

        imageView.setImageResource(item.imageResource)
        store.text = item.store
        menuName.text = item.menuName
        detail1.text = item.detailName1
        detail2.text = item.detailName2
        price.text = "${item.price}원"
        amount.text = "${item.amount}개"


        return view
    }

    override fun notifyDataSetChanged() {
        // 여기에 필요한 추가 로직을 구현할 수 있습니다.
        super.notifyDataSetChanged() // 상위 클래스의 notifyDataSetChanged 호출
    }
}