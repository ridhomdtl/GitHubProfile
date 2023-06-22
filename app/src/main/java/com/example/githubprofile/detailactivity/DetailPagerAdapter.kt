@file:Suppress("DEPRECATION")

package com.example.githubprofile.detailactivity

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubprofile.R
import com.example.githubprofile.detailactivity.fragment.FragmentFollowers
import com.example.githubprofile.detailactivity.fragment.FragmentFollowing

class DetailPagerAdapter(private val context: Context, manage: FragmentManager, data: Bundle) : FragmentPagerAdapter(manage, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentBundle: Bundle
    init {
        fragmentBundle = data
    }

    @StringRes
    private val TAB_TITLE = intArrayOf(R.string.followers, R.string.following)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FragmentFollowers()
            1 -> fragment = FragmentFollowing()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLE[position])
    }
}
