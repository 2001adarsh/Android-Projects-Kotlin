package com.adarsh.soundpool

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var soundPool:SoundPool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soundPool = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //works only for 21 and above.
            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            SoundPool.Builder()
                    .setMaxStreams(4) //maximum sounds to play
                    .setAudioAttributes(audioAttributes)
                    .build()
        }else{ //for lower phones
            SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        }

        val intSound1 = soundPool.load(this, R.raw.sound1, 0)
        val intSound2 = soundPool.load(this, R.raw.sound2, 0)
        val intSound3 = soundPool.load(this, R.raw.sound3, 0)
        val intSound4 = soundPool.load(this, R.raw.sound4, 0)

        var forpausing:Int=0

        sound1.setOnClickListener {
            soundPool.play(intSound1, 1F, 1F, 0, 0, 1F)  //In loop we can pass -1 for repeating
            // indefinitely
            //soundPool.pause(forpausing) //for pausing sound3
            soundPool.autoPause() //for pausing whenever paused
        }
        sound2.setOnClickListener {
            soundPool.play(intSound2, 1F, 1F, 0, 0, 1F)
            soundPool.autoPause()
        }
        sound3.setOnClickListener {
            forpausing = soundPool.play(intSound3, 1F, 1F, 0, 0, 1F)
            soundPool.autoPause()
        }
        sound4.setOnClickListener {
            soundPool.play(intSound4, 1F, 1F, 0, 0, 1F)
            soundPool.autoPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

}