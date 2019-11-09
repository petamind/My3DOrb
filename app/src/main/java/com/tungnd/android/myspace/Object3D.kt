package com.tungnd.android.myspace

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback
import android.view.SurfaceView
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class Object3D: SurfaceView, Runnable, Callback{
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {}

    override fun surfaceCreated(holder: SurfaceHolder?) {
        center.x = width.toFloat()/2
        center.y = height.toFloat()/2
        radius = width.toFloat()/5
        generatePoint3D()

    }


    var radius = 100.0f
    var indices = arrayListOf<Float>()
    var points = arrayListOf<Point3D>()
    var center = Point3D(0f, 0f, 0f)
    var numberOfPoint3d: Int = 4
    lateinit var canvas: Canvas
    var paint: Paint
    init {
        paint = Paint()
        paint.isAntiAlias = true
        paint.strokeWidth = 10f
        paint.style = Paint.Style.STROKE
        paint.strokeMiter = 10f
        holder.addCallback(this)
     }
    constructor(ctx: Context, radius: Float = 30.0f, numberOfPoint3D: Int = 4): super(ctx){
        this.radius = radius
        this.numberOfPoint3d = numberOfPoint3D
        Thread(this).start()
    }

    constructor(ctx: Context, attributeSet: AttributeSet): super(ctx, attributeSet){
        generatePoint3D()
    }

    constructor(ctx: Context, attributeSet: AttributeSet, radius: Float =50.0f, numberOfPoint3D: Int = 4):this(ctx, attributeSet){
        this.radius = radius
        this.numberOfPoint3d = numberOfPoint3D
        generatePoint3D()
    }

    fun generatePoint3D(){
        for( i in 0 until numberOfPoint3d){
            indices.add(i + 0.5f)

            val phi = acos(1.0 - 2.0*indices.last()/numberOfPoint3d)
            val theta = Math.PI *(1.0 + 5.0.pow(0.5))* indices.last()

            val x = radius * cos(theta) * sin(phi) + center.x
            val y = radius * sin(theta) * sin(phi) + center.y
            val z = radius * cos(phi) + center.z

            points.add(Point3D(x.toFloat(), y.toFloat(), z.toFloat()))
        }
        Log.d("indexes",indices.toString())
        Log.d("indexes",points.toString())
        Log.d("indexes radius",radius.toString())
    }

    fun draw(){
        if(holder.surface.isValid){
            canvas = holder.lockCanvas()
            canvas.drawColor(Color.WHITE)
            for(p in points){
                canvas.drawPoint(p.x, p.y, paint)
            }

            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun run() {
        while(true){
            draw()
            Thread.sleep(16)
        }
    }
}