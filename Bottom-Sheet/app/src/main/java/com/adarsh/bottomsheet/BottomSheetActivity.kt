package com.adarsh.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet_2.*


class BottomSheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)

        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet2)

        arrow.setOnClickListener {
            if(bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                arrow.rotation = slideOffset*180
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        button2.text = "Hidden"
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        button2.text= "Expanded"
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        button2.text = "Collapsed"
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        button2.text = "Dragging"
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        button2.text = "Settling"
                    }
                }
            }
        })

    }
}