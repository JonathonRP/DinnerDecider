package com.company.thecreater.dinnerdecider

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class FragmentStateAdaptor(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val fragmentList: ArrayList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }
}