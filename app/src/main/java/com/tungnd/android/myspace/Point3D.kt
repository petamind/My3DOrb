package com.tungnd.android.myspace

import java.util.*

class Point3D {
    var x: Float = 0f
    var y: Float = 0f
    var z: Float = 0f
    constructor(x: Float, y: Float, z: Float){
        this.x = x
        this.y = y
        this.z = z
    }

    fun translate(to: Point3D){
        x += to.x
        y += to.y
        z += to.z
    }

    override fun toString(): String {
        return arrayOf(x, y, z).contentToString()
    }
}