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

// Best practice is to test code that is really needed for the functionality
// - Example in this case:
// --- ViewModel -> Repository -> Remote / Local Datasource
// - It could also be called as:
// --- System Under Test (SUT) -> Collaborator -> Datasource
// - We only need to test the ViewModel, because the main function is within there

// Test Double is imitation object to replace the real object when testing. There are types of Test Double:
// - Fake: imitation object that has similar implementation as the real object. No need `mocking framework` for this. Example: use ArrayList when the real object is a database
// - Dummy: imitation object that only being needed only for parameter. It could be any data or even empty data
// - Mock: imitation object with `behavior` like the