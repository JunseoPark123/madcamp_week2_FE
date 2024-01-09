package com.example.madcamp_week2_fe
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.madcamp_week2_fe.home.HomeFragment
import com.example.madcamp_week2_fe.dibs.DibsFragment
import com.example.madcamp_week2_fe.databinding.ActivityMainBinding
import com.example.madcamp_week2_fe.orderinfo.OrderInfoFragment


public const val TAG_ORDERINFO = "orderinfo_fragment"
public const val TAG_HOME = "home_fragment"
public const val TAG_DIBS = "dibs_fragment"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        accessToken = sharedPreferences.getString("access_token", null)

        setFragment(TAG_HOME, HomeFragment())
        binding.navigationView.selectedItemId = R.id.home

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.orderInfo -> setFragment(TAG_ORDERINFO, OrderInfoFragment())
                R.id.home -> setFragment(TAG_HOME, HomeFragment())
                R.id.dibs -> setFragment(TAG_DIBS, DibsFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        // 기존에 있는 프래그먼트를 찾거나 새로운 프래그먼트를 생성합니다.
        val currentFragment = manager.findFragmentByTag(tag)
        val newFragment = if (currentFragment != null) {
                currentFragment
        } else {
            when (tag) {
                TAG_HOME -> HomeFragment()
                TAG_ORDERINFO -> OrderInfoFragment()
                TAG_DIBS -> DibsFragment()
                else -> HomeFragment()
            }
        }

        // 다른 프래그먼트를 숨깁니다.
        hideAllFragments(fragTransaction)

        // 새 프래그먼트를 추가하거나 기존 프래그먼트를 보여줍니다.
        if (currentFragment == null) {
            fragTransaction.add(R.id.mainFrameLayout, newFragment, tag)
        } else {
            fragTransaction.show(newFragment)
        }

        fragTransaction.commitAllowingStateLoss()
    }

    private fun hideAllFragments(transaction: FragmentTransaction) {
        val orderInfo = supportFragmentManager.findFragmentByTag(TAG_ORDERINFO)
        val home = supportFragmentManager.findFragmentByTag(TAG_HOME)
        val dibs = supportFragmentManager.findFragmentByTag(TAG_DIBS)

        orderInfo?.let { transaction.hide(it) }
        home?.let { transaction.hide(it) }
        dibs?.let { transaction.hide(it) }
    }

    fun navigateToFragment(tag: String) {
        setFragment(tag, when (tag) {
            TAG_HOME -> HomeFragment()
            TAG_ORDERINFO -> OrderInfoFragment()
            TAG_DIBS -> DibsFragment()
            else -> HomeFragment()
        })
        binding.navigationView.selectedItemId = when (tag) {
            TAG_HOME -> R.id.home
            TAG_ORDERINFO -> R.id.orderInfo
            TAG_DIBS -> R.id.dibs
            else -> R.id.home
        }
    }
}