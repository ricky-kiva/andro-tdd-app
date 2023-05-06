package com.dicoding.newsapp.ui.list

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.newsapp.R
import com.dicoding.newsapp.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.home, R.string.bookmark)
    }
}

// - TDD (Test Driven Development): a method that writes test code first before production code
// - TDD Workflow:
// - Start -> Write failing test -> Pass the test -> Refactor -> Back to start

// - Write failing test: Confirm the test must fail with reason, because you haven't wrote any production code
// - Write code that pass the test: Make production code to feed the test and pass
// - Refactor -> Clean, simplify, optimize the logic & make the code modular
// - Repeat: back write failing test when adding new functionality. Do commit as often as possible