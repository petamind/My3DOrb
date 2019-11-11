package com.tungnd.android.myspace

import android.content.pm.FeatureGroupInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    lateinit var object3D: Object3D

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onResume() {
        object3D = Object3D(this, numberOfPoint3D = 100)
        setContentView(object3D)
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        object3D.isRunning = false
    }
}
