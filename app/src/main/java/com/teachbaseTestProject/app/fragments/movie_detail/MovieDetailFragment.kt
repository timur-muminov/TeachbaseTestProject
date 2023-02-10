package com.teachbaseTestProject.app.fragments.movie_detail

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.bumptech.glide.Glide
import com.teachbaseTestProject.app.core.BaseFragment
import com.teachbaseTestProject.app.core.utils.formatToRateValue
import com.teachbaseTestProject.app.core.utils.getColorFromRate
import com.teachbaseTestProject.app.utils.epicadapter.EpicAdapter
import com.teachbaseTestProject.app.utils.epicadapter.bind
import com.teachbaseTestProject.app.utils.epicadapter.requireEpicAdapter
import com.teachbaseTestProject.app.utils.launchWith
import com.teachbaseTestProject.app.utils.onEachChanged
import com.teachbaseTestProject.entities.movie.Person
import com.teachbaseTestProject.movie_detail.presentation.MovieDetailViewModel
import com.teachbasetestproject.app.R
import com.teachbasetestproject.app.databinding.MovieDetailFragmentBinding
import com.teachbasetestproject.app.databinding.PersonItemBinding
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailFragment : BaseFragment<MovieDetailFragmentBinding>(MovieDetailFragmentBinding::inflate) {

    private val viewModel: MovieDetailViewModel by viewModel {
        parametersOf(requireArguments().getInt("movieId"))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initViews()

        viewModel.state.filterIsInstance<MovieDetailViewModel.ViewLoadState.LoadingError>().onEachChanged {
            binding.includedExceptionDialog.errorText.text = it.error?.toString(requireContext()) ?: "Ошибка"
        }.launchWith(viewLifecycleOwner)

        viewModel.state.onEachChanged {
            binding.includedProgressBar.progressBarLayout.isVisible = it is MovieDetailViewModel.ViewLoadState.Loading
            binding.includedExceptionDialog.exceptionDialog.isVisible =
                it is MovieDetailViewModel.ViewLoadState.LoadingError
        }.launchWith(viewLifecycleOwner)


        viewModel.state.filterIsInstance<MovieDetailViewModel.ViewLoadState.Loaded>().map {
            it.movieDetail
        }.filterNotNull().onEachChanged {
            Glide.with(requireContext()).load(it.imageUrl).placeholder(R.drawable.placeholder_movie)
                .into(binding.movieImage)
            if (it.logo == null) binding.logoImageview.isVisible = false
            else Glide.with(requireContext()).load(it.logo).into(binding.logoImageview)

            binding.movieRateTextview.text = it.rate.formatToRateValue()
            binding.movieRateTextview.setTextColor(requireContext().getColorFromRate(it.rate?.toFloat()))
            binding.movieVotesTextview.text = it.votes ?: ""
            binding.movieYearsTextview.text = it.year ?: ""
            binding.movieGenreTextview.text = it.genre ?: ""

            if (it.spokenLanguages.isNullOrEmpty()) {
                binding.soundIcon.isVisible = false
            } else binding.spokenLanguageTextview.text = it.spokenLanguages?.joinToString() ?: ""

            binding.personsTextview.text = it.persons.formatToPersons()
            binding.personsRecyclerview.requireEpicAdapter().loadItems(it.persons ?: emptyList())

            binding.descriptionTextview.text = it.description ?: ""
        }.launchWith(viewLifecycleOwner)
    }

    private fun buildPersonsAdapter() = EpicAdapter {
        setup<Person, PersonItemBinding>(PersonItemBinding::inflate) {
            init { personImage.clipToOutline = true }
            bind { item ->
                Glide.with(requireContext()).load(item.photo).placeholder(R.drawable.placeholder_movie)
                    .into(personImage)
                personNameTextview.text = item.rusName ?: item.enName ?: ""
                professionTextview.text = item.enProfession ?: ""
            }
        }
    }

    private fun List<Person>?.formatToPersons(): String {
        if (this.isNullOrEmpty()) return ""
        return getString(R.string.roles_played) + take(7).map {
            it.rusName ?: it.enName
        }.joinToString() + getString(R.string.etc)
    }

    private fun initViews() {
        binding.backButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.includedExceptionDialog.retryButton.setOnClickListener { viewModel.loadData() }
        binding.personsRecyclerview.adapter = buildPersonsAdapter()
        binding.personsRecyclerview.layoutManager = GridLayoutManager(requireContext(), 4, HORIZONTAL, false)
    }
}
