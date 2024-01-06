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
            HomeGridItem(R.drawable.image1, "Text 1"),
            HomeGridItem(R.drawable.image2, "Text 2"),
            HomeGridItem(R.drawable.image3, "Text 3"),
            HomeGridItem(R.drawable.image4, "Text 4"),
        )

        val gridView: GridView = view.findViewById(R.id.homeGridView)
        val adapter = HomeGridAdapter(requireContext(), items)
        gridView.adapter = adapter

        return view
    }
}