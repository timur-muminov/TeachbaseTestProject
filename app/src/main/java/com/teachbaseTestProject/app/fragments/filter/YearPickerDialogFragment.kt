package com.teachbaseTestProject.app.fragments.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.teachbaseTestProject.app.utils.epicadapter.EpicAdapter
import com.teachbaseTestProject.app.utils.epicadapter.bind
import com.teachbasetestproject.app.R
import com.teachbasetestproject.app.databinding.YearItemBinding
import com.teachbasetestproject.app.databinding.YearPickerDialogFragmentBinding
import kotlin.properties.Delegates

class YearPickerDialogFragment : DialogFragment(R.layout.year_picker_dialog_fragment) {

    private var requestKey: String by Delegates.notNull()

    private var sinceLayoutManager: LayoutManager by Delegates.notNull()
    private var toLayoutManager: LayoutManager by Delegates.notNull()

    private var sinceYear: Int = 1900
    private var toYear: Int = 2023

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = YearPickerDialogFragmentBinding.inflate(inflater, container, false)

        requestKey = YearPickerDialogFragmentArgs.fromBundle(requireArguments()).requestKey

        val yearsList = (sinceYear..toYear).toList<Any>()

        val sinceList = yearsList.toMutableList().addEmptyFieldsAndReverse(getString(R.string.from))
        val toList = yearsList.toMutableList().addEmptyFieldsAndReverse(getString(R.string.to))

        sinceLayoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        toLayoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)

        binding.sinceRecyclerview.layoutManager = sinceLayoutManager
        binding.toRecyclerview.layoutManager = toLayoutManager

        val sincePagerSnapHelper = PagerSnapHelper()
        val toPagerSnapHelper = PagerSnapHelper()

        sincePagerSnapHelper.attachToRecyclerView(binding.sinceRecyclerview)
        toPagerSnapHelper.attachToRecyclerView(binding.toRecyclerview)

        binding.sinceRecyclerview.adapter = buildSinceAdapter(sinceList)
        binding.toRecyclerview.adapter = buildToAdapter(toList)

        binding.clearTextview.setOnClickListener {
            setResult(sinceYear, toYear)
        }

        binding.submitTextview.setOnClickListener {
            val sincePosition = sincePagerSnapHelper.findTargetSnapPosition(sinceLayoutManager, 0, 0)
            val toPosition = toPagerSnapHelper.findTargetSnapPosition(toLayoutManager, 0, 0)

            val currentSinceYear: Int = if (sincePosition <= 1) sinceYear else sinceList[sincePosition] as Int
            val currentToYear = if (toPosition <= 1) toYear else toList[toPosition] as Int
            setResult(currentSinceYear, currentToYear)
        }

        return binding.root
    }

    private fun MutableList<Any>.addEmptyFieldsAndReverse(extraText: String): List<Any> {
        add(extraText)
        add("")
        add(0, "")
        reverse()
        return toList()
    }

    private fun setResult(since: Int, to: Int) {
        setFragmentResult(requestKey, bundleOf("sinceYear" to since, "toYear" to to))
        dismiss()
    }

    private fun buildSinceAdapter(years: List<Any>) = EpicAdapter {
        setup<Int, YearItemBinding>(YearItemBinding::inflate) {
            bind { item ->
                yearTextview.text = item.toString()
            }
        }
        setup<String, YearItemBinding>(YearItemBinding::inflate) { bind { item -> yearTextview.text = item } }
    }.apply { loadItems(years) }

    private fun buildToAdapter(years: List<Any>) = EpicAdapter {
        setup<Int, YearItemBinding>(YearItemBinding::inflate) {
            bind { item ->
                yearTextview.text = item.toString()
            }
        }
        setup<String, YearItemBinding>(YearItemBinding::inflate) { bind { item -> yearTextview.text = item } }
    }.apply { loadItems(years) }
}