package com.teachbaseTestProject.app.fragments.filter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.teachbaseTestProject.app.core.BaseFragment
import com.teachbaseTestProject.app.fragments.filter.utils.onTabSelectedListener
import com.teachbaseTestProject.app.utils.launchWith
import com.teachbaseTestProject.app.utils.onEachChanged
import com.teachbaseTestProject.filter.MovieType
import com.teachbaseTestProject.filter.SortType
import com.teachbaseTestProject.filter.presentation.FilterViewModel
import com.teachbasetestproject.app.databinding.FilterFragmentBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class FiltersFragment : BaseFragment<FilterFragmentBinding>(FilterFragmentBinding::inflate) {

    private val viewModel: FilterViewModel by viewModel()
    private val yearRequestKey = "yearRequestKey"
    private var movieTypeSelectedTab: TabLayout.Tab? = null
    private var sortTypeSelectedTab: TabLayout.Tab? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(yearRequestKey) { _, bundle ->
            val sinceYear: Int = bundle.getInt("sinceYear")
            val toYear: Int = bundle.getInt("toYear")
            viewModel.setDateRange(sinceYear to toYear)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initViews()

        viewModel.movieFilterStateFlow.onEachChanged {
            binding.year.text = it.dateRange!!.toListFormat()
        }.launchWith(viewLifecycleOwner)

        viewModel.movieFilterStateFlow.map {
            it.rate
        }.onEachChanged {
            binding.rating.text = it!!.toRatingFormat()
            binding.ratingSlider.setValues(it.first.toFloat(), it.second.toFloat())
        }.launchWith(viewLifecycleOwner)
    }

    private fun initViews() {
        binding.backButton.setOnClickListener { findNavController().popBackStack() }
        binding.clearFiltersTextview.setOnClickListener { viewModel.clearFilters() }
        binding.showButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val currentFilter = viewModel.movieFilterStateFlow.first()
                //findNavController().safeActionNavigate(FiltersFragmentDirections.actionFilterFragmentToFilterResultFragment())
            }
        }

        binding.movieTypeTablayout.selectTab(movieTypeSelectedTab)
        binding.movieTypeTablayout.onTabSelectedListener {
            val type = when (it.position) {
                0 -> MovieType.ALL
                1 -> MovieType.FILMS
                2 -> MovieType.SERIES
                3 -> MovieType.ANIME
                4 -> MovieType.CARTOON
                else -> throw IllegalStateException()
            }
            movieTypeSelectedTab = it
            viewModel.setMovieType(type)
        }

        binding.sortType.selectTab(sortTypeSelectedTab)
        binding.sortType.onTabSelectedListener {
            val type = when (it.position) {
                0 -> SortType.RATE
                1 -> SortType.POPULARITY
                2 -> SortType.YEAR
                else -> throw IllegalStateException()
            }
            sortTypeSelectedTab = it
            viewModel.setSortType(type)
        }

        binding.ratingSlider.addOnChangeListener { slider, _, _ ->
            viewModel.setRate(slider.values[0].toInt() to slider.values[1].toInt())
        }

        val yearPickerDialogFragment = YearPickerDialogFragment()
        binding.yearsContainer.setOnClickListener {
            yearPickerDialogFragment.show(childFragmentManager, "")
        }
    }
}

private fun Pair<Int, Int>.toListFormat() = "$first, $second"
private fun Pair<Int, Int>.toRatingFormat() = "от $first до $second"