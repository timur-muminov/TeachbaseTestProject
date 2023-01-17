package com.teachbaseTestProject.app.fragments.main.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.teachbaseTestProject.app.utils.epicadapter.EpicAdapter
import com.teachbaseTestProject.app.utils.epicadapter.bind
import com.teachbasetestproject.app.R
import com.teachbasetestproject.app.databinding.YearItemBinding
import com.teachbasetestproject.app.databinding.YearPickerDialogFragmentBinding

class YearPickerDialogFragment : DialogFragment(R.layout.year_picker_dialog_fragment) {

    private var sinceYear: Int = 1900
    private var toYear: Int = 2023

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = YearPickerDialogFragmentBinding.inflate(inflater, container, false)
        val years = (sinceYear..toYear).toList()

        binding.sinceRecyclerview.adapter = buildSinceAdapter().apply { loadItems(years) }
        binding.toRecyclerview.adapter = buildToAdapter().apply { loadItems(years) }

        binding.clearTextview.setOnClickListener {
            setResult(1900, 2023)
        }
        binding.submitTextview.setOnClickListener {
            setResult(sinceYear, toYear)
        }

        return binding.root
    }

    private fun setResult(sinceYear: Int, toYear: Int) {
        setFragmentResult("yearPickerKey", bundleOf("sinceYear" to sinceYear, "toYear" to toYear))
        dismiss()
    }

    private fun buildSinceAdapter() = EpicAdapter {
        setup<Int, YearItemBinding>(YearItemBinding::inflate) {
            bind { item ->
                yearTextview.text = item.toString()
                sinceYear = item
            }
        }
    }

    private fun buildToAdapter() = EpicAdapter {
        setup<Int, YearItemBinding>(YearItemBinding::inflate) {
            bind { item ->
                yearTextview.text = item.toString()
                toYear = item
            }
        }
    }
}