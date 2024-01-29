package com.goodness.searchphoto.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.goodness.searchphoto.MainActivity
import com.goodness.searchphoto.fragments.LikesFragment
import com.goodness.searchphoto.fragments.SearchListFragment

class MainViewPagerAdapter(fragment: MainActivity) : FragmentStateAdapter(fragment) {
	private val fragments by lazy {
		listOf(
			SearchListFragment(),
			LikesFragment()
		)
	}

	override fun getItemCount(): Int = 2

	override fun createFragment(position: Int): Fragment {
		return fragments[position]
	}
}