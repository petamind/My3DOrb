package com.tungnd.android.myspace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    lateinit var object3D: Object3D

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
