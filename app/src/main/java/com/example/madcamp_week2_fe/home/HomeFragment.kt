package com.example.madcamp_week2_fe.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.example.madcamp_week2_fe.R

class HomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val items = listOf(
            HomeGridItem(R.drawable.image1, "재령이네 과일가게", "비타민 과일 박스 세트"),
            HomeGridItem(R.drawable.image2, "준서네 반찬가게", "매콤달콤 진미채 박스"),
            HomeGridItem(R.drawable.image3, "준서네 반찬가게", "명절 반찬 박스 세트"),
            HomeGridItem(R.drawable.image4, "재령이네 과일가게", "영양소 듬뿍 상큼 과일 박스"),
        )

        val gridView: GridView = view.findViewById(R.id.homeGridView)
        val adapter = HomeGridAdapter(requireContext(), items)
        gridView.adapter = adapter

        return view
    }
}