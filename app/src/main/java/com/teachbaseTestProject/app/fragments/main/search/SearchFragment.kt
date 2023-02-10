package com.teachbaseTestProject.app.fragments.main.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.teachbaseTestProject.app.core.BaseFragment
import com.teachbaseTestProject.app.core.utils.formatToRateValue
import com.teachbaseTestProject.app.core.utils.getColorFromRate
import com.teachbaseTestProject.app.fragments.movie_detail.MovieDetailFragment
import com.teachbaseTestProject.app.utils.epicadapter.EpicAdapter
import com.teachbaseTestProject.app.utils.epicadapter.bind
import com.teachbaseTestProject.app.utils.epicadapter.requireEpicAdapter
import com.teachbaseTestProject.app.utils.hideKeyboard
import com.teachbaseTestProject.app.utils.launchWith
import com.teachbaseTestProject.app.utils.onEachChanged
import com.teachbaseTestProject.app.utils.safeActionNavigate
import com.teachbaseTestProject.entities.movie.Movie
import com.teachbaseTestProject.search.presentation.SearchViewModel
import com.teachbasetestproject.app.R
import com.teachbasetestproject.app.databinding.SearchFragmentBinding
import com.teachbasetestproject.app.databinding.SearchMovieItemBinding
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate) {

    private val viewModel: SearchViewModel by viewModel()

    private val callback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            binding.root.transitionToStart()
            binding.searchEdittext.text?.clear()
            binding.searchEdittext.clearFocus()
            isEnabled = false
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initViews()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        viewModel.state.filterIsInstance<SearchViewModel.ViewLoadState.Loaded>().map {
            it.movies
        }.onEachChanged {
            binding.searchRecyclerview.requireEpicAdapter().loadItems(it)
        }.launchWith(viewLifecycleOwner)

        viewModel.state.filterIsInstance<SearchViewModel.ViewLoadState.LoadingError>().onEachChanged {
            binding.includedExceptionDialog.errorText.text = it.error?.toString(requireContext()) ?: "Ошибка"
        }.launchWith(viewLifecycleOwner)

        viewModel.state.onEachChanged {
            binding.includedProgressBar.progressBarLayout.isVisible = it is SearchViewModel.ViewLoadState.Loading
            binding.includedExceptionDialog.exceptionDialog.isVisible = it is SearchViewModel.ViewLoadState.LoadingError
        }.launchWith(viewLifecycleOwner)
    }

    private fun initViews() {
        binding.searchRecyclerview.adapter = buildMovieAdapter()

        binding.searchEdittext.setOnEditorActionListener { _, actionId, _ ->
            if (actionId != EditorInfo.IME_ACTION_SEARCH) return@setOnEditorActionListener false
            binding.searchEdittext.clearFocus()
            hideKeyboard()
            viewModel.saveSearchText(binding.searchEdittext.text.toString())
            viewModel.searchMovie()
            return@setOnEditorActionListener true
        }

        binding.searchEdittext.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) return@setOnFocusChangeListener
            binding.root.transitionToEnd()
            callback.isEnabled = true
        }

        binding.arrowBackIcon.setOnClickListener {
            binding.root.transitionToStart()
            binding.searchEdittext.clearFocus()
            binding.searchEdittext.text?.clear()
            hideKeyboard()
            callback.isEnabled = false
        }

        binding.filterIcon.setOnClickListener {
            findNavController().safeActionNavigate(SearchFragmentDirections.actionSearchFragmentToFilterFragment())
        }

        binding.includedExceptionDialog.retryButton.setOnClickListener {
            viewModel.searchMovie()
        }
    }

    private fun buildMovieAdapter() = EpicAdapter {
        setup<Movie, SearchMovieItemBinding>(SearchMovieItemBinding::inflate) {
            init { movieImage.clipToOutline = true }
            bind { item ->
                Glide.with(requireContext()).load(item.imageUrl ?: R.drawable.placeholder_movie).into(movieImage)
                movieRateTextview.text = item.rate.formatToRateValue()
                movieRateTextview.setTextColor(requireContext().getColorFromRate(item.rate?.toFloat()))
                movieNameTextview.text = item.name ?: ""
                movieSecondNameTextview.text = item.secondName ?: ""
                movieGenreTextview.text = item.genre ?: ""
                movieVotesTextview.text = item.votes ?: ""

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
}
