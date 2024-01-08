package com.example.madcamp_week2_fe.dibs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.MainActivity
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.TAG_HOME

class DibsFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dibs, container, false)
        recyclerView = view.findViewById(R.id.recyclerView) // RecyclerView ID를 찾아 설정합니다.
        initializeRecyclerView()
        val leftArrow: ImageView = view.findViewById(R.id.left_arrow)

        leftArrow.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(TAG_HOME)
        }
        return view
    }

    private fun initializeRecyclerView() {
        val adapter = DibsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
}