package com.example.madcamp_week2_fe.dibs;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.R

class DibsAdapter : RecyclerView.Adapter<DibsAdapter.DibsViewHolder>() {

    private val itemCount = 10 // 표시할 아이템 개수

    class DibsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // 뷰 홀더 초기화. 여기에서 dibs_item.xml의 뷰를 바인딩합니다.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DibsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dibs_item, parent, false)
        return DibsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DibsViewHolder, position: Int) {
        // 여기에서 각 아이템 뷰에 데이터를 바인딩합니다.
        // 데이터가 필요 없으므로, 이 부분은 비워둡니다.
    }

    override fun getItemCount(): Int {
        return itemCount
    }
}