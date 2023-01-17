package com.teachbaseTestProject.app.fragments.main.filter

import android.os.Bundle
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.teachbaseTestProject.app.core.BaseFragment
import com.teachbaseTestProject.app.fragments.main.filter.utils.onSeekBarChanged
import com.teachbaseTestProject.app.fragments.main.filter.utils.onTabSelectedListener
import com.teachbaseTestProject.app.utils.launchWith
import com.teachbaseTestProject.app.utils.onEachChanged
import com.teachbaseTestProject.app.utils.safeActionNavigate
import com.teachbaseTestProject.filter.MovieType
import com.teachbaseTestProject.filter.SortType
import com.teachbaseTestProject.filter.presentation.FilterViewModel
import com.teachbasetestproject.app.databinding.FilterFragmentBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FiltersFragment : BaseFragment<FilterFragmentBinding>(FilterFragmentBinding::inflate) {

    private val viewModel: FilterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("yearPickerKey") { key, bundle ->
            val sinceYear: Int = bundle.getInt("sinceYear")
            val toYear: Int = bundle.getInt("toYear")
            viewModel.setDateRange(sinceYear to toYear)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initViews()

        viewModel.movieFilterStateFlow.map { it.dateRange }.onEachChanged {
            binding.year.text = it!!.toListFormat()
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
        binding.movieTypeTablayout.onTabSelectedListener {
            val type = when (it.position) {
                0 -> MovieType.ALL
                1 -> MovieType.FILMS
                2 -> MovieType.SERIES
                3 -> MovieType.ANIME
                4 -> MovieType.CARTOON
                else -> throw IllegalStateException()
            }
            viewModel.setMovieType(type)
        }
        binding.sortType.onTabSelectedListener {
            val type = when (it.position) {
                0 -> SortType.RATE
                1 -> SortType.POPULARITY
                2 -> SortType.YEAR
                else -> throw IllegalStateException()
            }
            viewModel.setSortType(type)
        }

        binding.ratingSeekbar.onSeekBarChanged {

        }

        val yearPickerDialogFragment = YearPickerDialogFragment()
        binding.yearsContainer.setOnClickListener {
            yearPickerDialogFragment.show(childFragmentManager, "")
        }
    }
}

private fun Pair<Int, Int>.toListFormat() = "$first, $second"