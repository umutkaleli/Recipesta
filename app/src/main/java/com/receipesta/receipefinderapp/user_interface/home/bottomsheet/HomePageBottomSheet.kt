package com.receipesta.receipefinderapp.user_interface.home.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.receipesta.receipefinderapp.R
import com.receipesta.receipefinderapp.user_interface.home.HomePage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*



class HomePageBottomSheet : BottomSheetDialogFragment() {

    private lateinit var meal_type :String
    private var id_meal_type : Int = 0

    companion object{

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView =  inflater.inflate(R.layout.fragment_home_page_bottom_sheet, container, false)

        val mealTypechipGroup = mView.findViewById<ChipGroup>(R.id.mealTypeChipGroup)

        mealTypechipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds[0])
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            meal_type = selectedMealType
            id_meal_type = checkedIds[0]
        }

        val applyButton = mView.findViewById<Button>(R.id.applyButton)
        applyButton.setOnClickListener{
            HomePage.MealAndDietType.mealType.value = meal_type
            updateChip(id_meal_type,mealTypechipGroup)

        }

        return mView
    }

    private fun updateChip(chipId : Int, chipGroup : ChipGroup){
        if(chipId != 0){
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch(e : java.lang.Exception) {
                Log.d("HomeBottomSheet", e.message.toString())
            }
        }

    }


}