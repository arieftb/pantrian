package com.arieftb.pantrian

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity(), View.OnClickListener, MediaPlayer.OnCompletionListener {

    private lateinit var soundCall: IntArray
    private lateinit var numberCall: IntArray
    private lateinit var mediaPlayer: MediaPlayer
    private var current_index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_main_call.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            btn_main_call.id -> call()
        }
    }

    private fun call() {
        val number = et_main_antrian.text.toString()
        numberCall = IntArray(number.length)

        for (i in 0 until number.length) {
            numberCall[i] = number.toCharArray()[i].toString().toInt()
        }

        soundCall = IntArray(numberCall.size + 1)

        for (i in 0 until numberCall.size + 1) {
            if(i == 0) {
                soundCall[i] = R.raw.nomor_antrian
            } else {
                soundCall[i] = SoundHelper.getSound(numberCall[i - 1])!!
            }
        }

        mediaPlayer = MediaPlayer.create(this, soundCall[0])
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.start()

    }

    private fun callPlay() {
        current_index = (current_index + 1) % soundCall.size

        if (current_index != 0) {
            val assetFileDesc: AssetFileDescriptor = this.resources.openRawResourceFd(soundCall[current_index])

            try {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(assetFileDesc.fileDescriptor, assetFileDesc.startOffset, assetFileDesc.declaredLength)
                mediaPlayer.prepare()
                mediaPlayer.start()
                assetFileDesc.close()
            } catch (e: IllegalArgumentException) {
                Log.d("MainActivity", "callPlay: $e")
            } catch (e: IllegalStateException) {
                Log.d("MainActivity", "callPlay: $e")
            } catch (e: IOException) {
                Log.d("MainActivity", "callPlay: $e")
            }
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        callPlay()
    }
}
