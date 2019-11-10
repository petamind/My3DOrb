package com.tungnd.android.myspace

import android.graphics.Color

/**
 * @author Tung Nguyen
 */
class Point3D(var x: Float, var y: Float, var z: Float, var maxColorScale: Float) : Comparable<Point3D> {
    override fun compareTo(other: Point3D): Int {
        return z.compareTo(other.z)
    }

    var color: Int = 0

    init {
        updateColor()
    }

    fun set(x: Float, y:Float, z: Float){
        this.x = x
        this.y = y
        this.z = z
        updateColor()
    }

    fun translate(to: Point3D){
        x += to.x
        y += to.y
        z += to.z
        updateColor()
    }

    private fun updateColor(){
            color = Color.argb(((z + maxColorScale) *255 /maxColorScale / 2).toInt(), 0, 0, 0)
    }

    override fun toString(): String {
        return arrayOf(x, y, z).contentToString()
    }
}