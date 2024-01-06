package com.example.madcamp_week2_fe
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.madcamp_week2_fe.home.HomeFragment
import com.example.madcamp_week2_fe.dibs.DibsFragment
import com.example.madcamp_week2_fe.databinding.ActivityMainBinding
import com.example.madcamp_week2_fe.orderinfo.OrderInfoFragment


private const val TAG_ORDERINFO = "orderinfo_fragment"
private const val TAG_HOME = "home_fragment"
private const val TAG_MYPAGE = "mypage_fragment"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_HOME, HomeFragment())
        binding.navigationView.selectedItemId = R.id.home

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.orderInfo -> setFragment(TAG_ORDERINFO, OrderInfoFragment())
                R.id.home -> setFragment(TAG_HOME, HomeFragment())
                R.id.myPage -> setFragment(TAG_MYPAGE, DibsFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val orderInfo = manager.findFragmentByTag(TAG_ORDERINFO)
        val home = manager.findFragmentByTag(TAG_HOME)
        val myPage = manager.findFragmentByTag(TAG_MYPAGE)

        if (orderInfo != null){
            fragTransaction.hide(orderInfo)
        }

        if (home != null){
            fragTransaction.hide(home)
        }

        if (myPage != null) {
            fragTransaction.hide(myPage)
        }

        if (tag == TAG_ORDERINFO) {
            if (orderInfo!=null){
                fragTransaction.show(orderInfo)
            }
        }
        else if (tag == TAG_HOME) {
            if (home != null) {
                fragTransaction.show(home)
            }
        }

        else if (tag == TAG_MYPAGE){
            if (myPage != null){
                fragTransaction.show(myPage)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}