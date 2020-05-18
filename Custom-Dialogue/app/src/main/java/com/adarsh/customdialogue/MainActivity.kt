package com.adarsh.customdialogue

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialogue_layout.*

class MainActivity : AppCompatActivity() {
    private lateinit var mDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDialog = Dialog(this)
        ShowDialog()

        button.setOnClickListener {
            ShowDialog()
        }
    }

    private fun ShowDialog(){
        mDialog.setContentView(R.layout.dialogue_layout)
        var window = mDialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.show()
        mDialog.save_btn.setOnClickListener {
            mDialog.dismiss()
        }
    }
}

