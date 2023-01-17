package com.teachbaseTestProject.app.fragments.main.home

import android.os.Bundle
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.teachbaseTestProject.app.core.BaseFragment
import com.teachbaseTestProject.app.utils.epicadapter.EpicAdapter
import com.teachbaseTestProject.app.utils.epicadapter.bind
import com.teachbaseTestProject.app.utils.epicadapter.requireEpicAdapter
import com.teachbaseTestProject.app.utils.launchWith
import com.teachbaseTestProject.app.utils.onEachChanged
import com.teachbaseTestProject.home.presentation.HomeViewModel
import com.teachbaseTestProject.movie.Movie
import com.teachbasetestproject.app.R
import com.teachbasetestproject.app.databinding.CategoryItemBinding
import com.teachbasetestproject.app.databinding.HomeFragmentBinding
import com.teachbasetestproject.app.databinding.MovieItemBinding
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initViews()

        viewModel.state.filterIsInstance<HomeViewModel.ViewLoadState.Loaded>().map {
            it.items
        }.onEachChanged {
            binding.homeRecyclerview.requireEpicAdapter().loadItems(it)
        }.launchWith(viewLifecycleOwner)

        viewModel.state.filterIsInstance<HomeViewModel.ViewLoadState.LoadingError>().onEachChanged {
            binding.includedExceptionDialog.errorText.text = it.error?.toString(requireContext()) ?: "Ошибка"
        }.launchWith(viewLifecycleOwner)

        viewModel.state.onEachChanged {
            binding.includedProgressBar.progressBarLayout.isVisible = it is HomeViewModel.ViewLoadState.Loading
            binding.includedExceptionDialog.exceptionDialog.isVisible = it is HomeViewModel.ViewLoadState.LoadingError
        }.launchWith(viewLifecycleOwner)
    }

    private fun initViews() {
        binding.homeRecyclerview.adapter = buildMovieCategoriesAdapter()

        binding.includedExceptionDialog.retryButton.setOnClickListener {
            viewModel.load()
        }
    }

    private fun buildMovieCategoriesAdapter() = EpicAdapter {
        setup<HomeViewModel.CategoryItem, CategoryItemBinding>(CategoryItemBinding::inflate) {
            init { moviesRecyclerview.adapter = buildMoviesAdapter() }
            bind { item ->
                categoryTextview.text = item.category.name
                moviesRecyclerview.requireEpicAdapter().loadItems(item.movies)
            }
        }
    }

    private fun buildMoviesAdapter() = EpicAdapter {
        setup<Movie, MovieItemBinding>(MovieItemBinding::inflate) {
            init { movieImage.clipToOutline = true }
            bind { item ->
                Glide.with(requireContext()).load(item.imageUrl ?: R.drawable.placeholder_movie)
                    .placeholder(R.drawable.placeholder_movie).into(movieImage)
                val rate = if (!item.rate.isNullOrEmpty()) item.rate!!.formatToRateValue() else ""
                movieRateTextview.text = rate
                movieNameTextview.text = item.name ?: ""
                movieGenreTextview.text = item.genre ?: ""

                root.setOnClickListener {
                    //findNavController().safeActionNavigate(HomeFragmentDirections.actionDashboardFragmentToDetailFragment())
                }
            }
        }
    }

    private fun String.formatToRateValue() = String.format("%.1f", this.toFloat()).replace(",", ".")
}
