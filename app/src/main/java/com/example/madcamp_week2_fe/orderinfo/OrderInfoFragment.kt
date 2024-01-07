package com.example.madcamp_week2_fe.orderinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week2_fe.R
import com.example.madcamp_week2_fe.home.HomeFragment

class OrderInfoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderInfoAdapter: OrderInfoAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_orderinfo, container, false)
        recyclerView = view.findViewById(R.id.recyclerView) // RecyclerView ID 설정
        initializeRecyclerView()

        val leftArrow: ImageView = view.findViewById(R.id.left_arrow)

        leftArrow.setOnClickListener {
            navigateToHomeFragment()
        }

        return view
    }

    private fun initializeRecyclerView() {
        orderInfoAdapter = OrderInfoAdapter(getOrderInfoList())
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = orderInfoAdapter
    }

    private fun getOrderInfoList(): List<OrderInfo> {
        // 여기에 이미지 리소스 ID를 사용하여 OrderInfo 데이터 리스트 생성
        return listOf(
            OrderInfo(R.drawable.image1),
            OrderInfo(R.drawable.image2),
            OrderInfo(R.drawable.image3),
            OrderInfo(R.drawable.image4)
        )
    }

    private fun navigateToHomeFragment() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, HomeFragment())
            .addToBackStack(null)  // 옵션: 뒤로 가기 스택에 추가
            .commit()
    }
}