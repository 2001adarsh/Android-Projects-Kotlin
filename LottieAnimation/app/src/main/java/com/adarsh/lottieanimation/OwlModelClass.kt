package com.adarsh.lottieanimation

import android.util.Log
import com.airbnb.lottie.LottieAnimationView

class OwlModelClass (var owlView: LottieAnimationView ) {

    init {

    }

    fun begin():Boolean{
        owlView.progress = 17f
        owlView.speed= 0.5f
        owlView.setMinAndMaxFrame(5, 100)
        owlView.playAnimation()
        Log.d("BaseActivity", " PlayAnimation started and ended.")
        return true
    }

    fun reading(){
        owlView.progress = 127f
        owlView.speed = 0.5f
        owlView.setMinAndMaxFrame(127, 209)
        owlView.playAnimation()
        Log.d("BaseActivity", " ReadingAnimation started and ended.")
    }

    fun pause(){
        owlView.pauseAnimation()
    }
}