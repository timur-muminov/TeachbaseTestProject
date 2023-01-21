package com.teachbaseTestProject.app.fragments.filter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teachbaseTestProject.app.core.BaseFragment
import com.teachbaseTestProject.app.fragments.filter.utils.onTabSelectedListener
import com.teachbaseTestProject.app.utils.launchWith
import com.teachbaseTestProject.app.utils.onEachChanged
import com.teachbaseTestProject.app.utils.safeActionNavigate
import com.teachbaseTestProject.entities.filter.MovieType
import com.teachbaseTestProject.entities.filter.SortType
import com.teachbaseTestProject.filter.presentation.FilterViewModel
import com.teachbasetestproject.app.databinding.FilterFragmentBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FiltersFragment : BaseFragment<FilterFragmentBinding>(FilterFragmentBinding::inflate) {

    private val viewModel: FilterViewModel by viewModel()
    private val yearRequestKey = "yearRequestKey"

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
            binding.year.text = it.dateRange.toListFormat()
            binding.movieTypeTablayout.getTabAt(it.movieType.ordinal)
            binding.sortTypeTablayout.getTabAt(it.sortType.ordinal)
        }.launchWith(viewLifecycleOwner)

        viewModel.movieFilterStateFlow.map {
            it.rate
        }.onEachChanged {
            binding.rating.text = it.toRatingFormat()
            binding.ratingSlider.setValues(it.first.toFloat(), it.second.toFloat())
        }.launchWith(viewLifecycleOwner)
    }

    private fun initViews() {
        binding.backButton.setOnClickListener { findNavController().popBackStack() }
        binding.clearFiltersTextview.setOnClickListener { viewModel.clearFilters() }
        binding.showButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val currentFilter = viewModel.movieFilterStateFlow.first()
                findNavController().safeActionNavigate(FiltersFragmentDirections.actionFilterFragmentToFilterResultFragment(currentFilter))
            }
        }

        binding.movieTypeTablayout.onTabSelectedListener {
            viewModel.setMovieType(MovieType.values()[it.position])
        }

        binding.sortTypeTablayout.onTabSelectedListener {
            viewModel.setSortType(SortType.values()[it.position])
        }

        binding.ratingSlider.addOnChangeListener { slider, _, _ ->
            viewModel.setRate(slider.values[0].toInt() to slider.values[1].toInt())
        }

        binding.yearsContainer.setOnClickListener {
            findNavController().safeActionNavigate(
                FiltersFragmentDirections.actionFilterFragmentToYearPickerFragment(
                    yearRequestKey
                )
            )
        }
    }
}

private fun Pair<Int, Int>.toListFormat() = "$first, $second"
private fun Pair<Int, Int>.toRatingFormat() = "от $first до $second"