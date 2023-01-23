package com.teachbaseTestProject.app.fragments.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.teachbaseTestProject.app.core.BaseFragment
import com.teachbaseTestProject.app.fragments.main.home.HomeFragment
import com.teachbaseTestProject.app.fragments.main.search.SearchContainer
import com.teachbasetestproject.app.R
import com.teachbasetestproject.app.databinding.ContainerFragmentBinding

class ContainerFragment : BaseFragment<ContainerFragmentBinding>(ContainerFragmentBinding::inflate) {

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)


        binding.mainViewpager.adapter = buildFragmentStateAdapter()
        binding.mainViewpager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.mainViewpager) { tab, position ->
            val icon = if (position == 0) R.drawable.home_icon else R.drawable.search_icon
            val text = if (position == 0) R.string.home else R.string.search
            tab.setIcon(icon)
            tab.setText(text)
        }.attach()
    }

    private fun buildFragmentStateAdapter() = object : FragmentStateAdapter(childFragmentManager, lifecycle) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment =
            if (position == 0) HomeFragment() else SearchContainer()
    }
}
