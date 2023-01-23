package com.teachbaseTestProject.app.fragments.filter_result

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.teachbaseTestProject.app.core.BaseFragment
import com.teachbaseTestProject.app.core.decorator.DecorConfig
import com.teachbaseTestProject.app.core.decorator.LinearDividerDrawer
import com.teachbaseTestProject.app.core.decorator.base.Decorator
import com.teachbaseTestProject.app.core.utils.formatToRateValue
import com.teachbaseTestProject.app.core.utils.getColorFromRate
import com.teachbaseTestProject.app.fragments.movie_detail.MovieDetailFragment
import com.teachbaseTestProject.app.utils.epicadapter.EpicAdapter
import com.teachbaseTestProject.app.utils.epicadapter.bind
import com.teachbaseTestProject.app.utils.epicadapter.requireEpicAdapter
import com.teachbaseTestProject.app.utils.launchWith
import com.teachbaseTestProject.app.utils.onEachChanged
import com.teachbaseTestProject.entities.movie.Movie
import com.teachbaseTestProject.filter.presentation.FilterResultViewModel
import com.teachbasetestproject.app.R
import com.teachbasetestproject.app.databinding.FilterResultFragmentBinding
import com.teachbasetestproject.app.databinding.SearchMovieItemBinding
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FilterResultFragment : BaseFragment<FilterResultFragmentBinding>(FilterResultFragmentBinding::inflate) {

    private val viewModel: FilterResultViewModel by viewModel {
        parametersOf(
            FilterResultFragmentArgs.fromBundle(requireArguments()).movieFilter
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initViews()

        viewModel.state.filterIsInstance<FilterResultViewModel.ViewLoadState.Loaded>().map {
            it.movies
        }.onEachChanged {
            binding.moviesRecyclerview.requireEpicAdapter().loadItems(it)
        }.launchWith(viewLifecycleOwner)

        viewModel.state.filterIsInstance<FilterResultViewModel.ViewLoadState.LoadingError>().onEachChanged {
            binding.includedExceptionDialog.errorText.text = it.error?.toString(requireContext()) ?: "Ошибка"
        }.launchWith(viewLifecycleOwner)

        viewModel.state.onEachChanged {
            binding.includedProgressBar.progressBarLayout.isVisible = it is FilterResultViewModel.ViewLoadState.Loading
            binding.includedExceptionDialog.exceptionDialog.isVisible =
                it is FilterResultViewModel.ViewLoadState.LoadingError
        }.launchWith(viewLifecycleOwner)
    }

    private fun initViews() {
        binding.includedExceptionDialog.retryButton.setOnClickListener {
            viewModel.loadData()
        }
        val decorator = Decorator.Builder().overlay(LinearDividerDrawer(getFragmentResultDecorConfig())).build()
        binding.moviesRecyclerview.addItemDecoration(decorator)
        binding.moviesRecyclerview.adapter = buildMoviesAdapter()
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun buildMoviesAdapter() = EpicAdapter {
        setup<Movie, SearchMovieItemBinding>(SearchMovieItemBinding::inflate) {
            init { movieImage.clipToOutline = true }
            bind { item ->
                Glide.with(requireContext()).load(item.imageUrl).placeholder(R.drawable.placeholder_movie)
                    .into(movieImage)
                movieNameTextview.text = item.name ?: ""
                movieSecondNameTextview.text = item.secondName ?: ""
                movieGenreTextview.text = item.genre ?: ""
                movieVotesTextview.text = item.votes ?: ""
                movieRateTextview.text = item.rate.formatToRateValue()
                movieRateTextview.setTextColor(requireContext().getColorFromRate(item.rate?.toFloat()))

                root.setOnClickListener {
                    requireActivity().supportFragmentManager.commit {
                        addToBackStack(null)
                        replace(
                            R.id.nav_host_fragment,
                            MovieDetailFragment::class.java,
                            bundleOf("movieId" to item.id)
                        )
                    }
                }
            }
        }
    }


    private fun getFragmentResultDecorConfig() = DecorConfig(
        color = ContextCompat.getColor(requireContext(), R.color.gray_8),
        height = 1,
        paddingStart = 106
    )
}