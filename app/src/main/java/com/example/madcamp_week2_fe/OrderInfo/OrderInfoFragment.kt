package com.example.madcamp_week2_fe.OrderInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.madcamp_week2_fe.R

class OrderInfoFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val frag = inflater.inflate(R.layout.fragment_orderinfo, container, false)

        return frag
    }
}