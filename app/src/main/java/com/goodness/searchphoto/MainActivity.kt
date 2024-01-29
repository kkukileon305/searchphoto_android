package com.goodness.searchphoto

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.goodness.searchphoto.adapters.MainViewPagerAdapter
import com.goodness.searchphoto.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	private val photosViewModel by lazy { ViewModelProvider(this)[PhotosViewModel::class.java] }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		initViewModel()
		initSwiper()
		initHandler()
	}

	private fun initSwiper() {
		val pager = binding.pager
		val tabLayout = binding.tabLayout

		pager.adapter = MainViewPagerAdapter(this)

		TabLayoutMediator(tabLayout, pager) { tab, position ->
			tab.text = when (position) {
				0 -> getString(R.string.fragment_1)
				1 -> getString(R.string.fragment_2)
				else -> ""
			}
		}.attach()
	}

	private fun initViewModel() {
		photosViewModel.isLoading.observe(this) { isLoading ->
			binding.btnSearch.isEnabled = !isLoading
			binding.btnSearch.setBackgroundColor(
				getColor(
					if (isLoading) R.color.non_active else R.color.active
				)
			)
		}
	}

	private fun initHandler() {
		val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
		val lastKeyword = sharedPref.getString(LASTKEYWORD, "")
		binding.etSearch.setText(lastKeyword)

		binding.btnSearch.setOnClickListener {
			val keyword = binding.etSearch.text.toString()

			if (keyword.isNotEmpty()) {
				photosViewModel.fetchPhotosByKeyword(keyword)
			} else {
				Toast.makeText(this, getString(R.string.empty_warning), Toast.LENGTH_SHORT).show()
			}

			saveKeyword(binding.etSearch.text.toString())
			hideKeyboard()
			binding.etSearch.clearFocus()
		}
	}

	private fun saveKeyword(keyword: String) {
		val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
		with(sharedPref.edit()) {
			putString(LASTKEYWORD, keyword)
			apply()
		}
	}

	private fun hideKeyboard() {
		val inputMethodManager =
			getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
	}

	companion object {
		const val LASTKEYWORD = "last_keyword"
	}
}