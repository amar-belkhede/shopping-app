package com.example.shoppingappv2.fragment.home.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.shoppingappv2.R
import com.example.shoppingappv2.utli.Constants.Companion.DEFAULT_SIZE_TYPE
import com.example.shoppingappv2.utli.Constants.Companion.DEFAULT_PRICE_RANGE_TYPE
import com.example.shoppingappv2.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.product_bottom_sheet.view.*
import java.lang.Exception
import java.util.*

class ProductBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mainViewModel: MainViewModel

    private var sizeTypeChip = DEFAULT_SIZE_TYPE
    private var sizeTypeChipId = 0
    private var priceRangeTypeChip = DEFAULT_PRICE_RANGE_TYPE
    private var priceRangeTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView =  inflater.inflate(R.layout.product_bottom_sheet, container, false)

        mainViewModel.readSizeAndPriceRange.asLiveData().observe(viewLifecycleOwner,{ value ->
            sizeTypeChip = value.sizeType
            priceRangeTypeChip = value.priceRangeType
            updateChip(value.sizeTypeId, mView.sizeType_chipGroup)
            updateChip(value.priceRangeTypeId, mView.priceRange_chipGroup)
        })

        mView.sizeType_chipGroup.setOnCheckedChangeListener { group, selectedSizeChipId ->
            val chip = group.findViewById<Chip>(selectedSizeChipId)
            val selectedSizeType = chip.text.toString().lowercase(Locale.ROOT)
            sizeTypeChip = selectedSizeType
            sizeTypeChipId = selectedSizeChipId
        }

        mView.priceRange_chipGroup.setOnCheckedChangeListener { group, selectedPriceRangeChipId ->
            val chip = group.findViewById<Chip>(selectedPriceRangeChipId)
            val selectedPriceRangeType = chip.text.toString().lowercase(Locale.ROOT)
            priceRangeTypeChip = selectedPriceRangeType
            priceRangeTypeChipId = selectedPriceRangeChipId
        }

        mView.apply_btn.setOnClickListener {
            mainViewModel.saveSizeAndPriceRange(
                sizeTypeChip,
                sizeTypeChipId,
                priceRangeTypeChip,
                priceRangeTypeChipId
            )

            val action =
                ProductBottomSheetFragmentDirections.actionProductBottomSheetFragmentToHomeFragment()
            findNavController().navigate(action)
        }


        return mView
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }


}