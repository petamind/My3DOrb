package com.tungnd.android.myspace

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback
import android.view.SurfaceView
import android.view.View
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

/**
 * @author Tung Nguyen
 */
class Object3D: SurfaceView, Runnable, Callback, View.OnClickListener {
    override fun onClick(v: View?) {
        mode = when(mode){
            AnimationMode.NORMAL -> AnimationMode.AWESOME
            else -> AnimationMode.NORMAL
        }
    }

    var mode= AnimationMode.NORMAL
    var radius = 100.0f
    var points = arrayListOf<Point3D>()
    var center = Point3D(0f, 0f, 0f, radius)
    var numberOfPoint3d: Int = 4
    var deltaPhi = 0.01f
    var isRunning = true
    lateinit var canvas: Canvas
    private var paint: Paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.strokeWidth = 20f
        paint.style = Paint.Style.STROKE
        paint.strokeMiter = 10f
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        holder.addCallback(this)
        setOnClickListener(this)
     }

    constructor(ctx: Context, radius: Float = 100.0f, numberOfPoint3D: Int = 4): super(ctx){
        this.radius = radius
        this.numberOfPoint3d = numberOfPoint3D
        Thread(this).start()
    }

    fun updatePoint3D(){

        val modIdx = if(mode == AnimationMode.NORMAL) deltaPhi else 0f
        val modTheta = if(mode == AnimationMode.AWESOME) deltaPhi else 0f

        for( i in 0 until numberOfPoint3d){
            val idx = (i + 0.5f + modIdx )% numberOfPoint3d

            val phi = acos(1.0 - 2.0*idx/numberOfPoint3d)
            val theta = Math.PI *(1.0  + 5.0.pow(0.5 ) + modTheta)* idx

            val x = radius * cos(theta) * sin(phi) + center.x
            val z = radius * sin(theta) * sin(phi) + center.z
            val y = radius * cos(phi) + center.y
            if(i == points.size)
            {
                points.add(Point3D(x.toFloat(), y.toFloat(), z.toFloat(), radius))
            } else {
                points[i].set(x.toFloat(), y.toFloat(), z.toFloat())
            }
        }
        points.sort()
    }

    fun draw(){
        if(holder.surface.isValid){
            canvas = holder.lockCanvas()
            canvas.drawColor(Color.WHITE)

            updatePoint3D()
            for(p in points){
                paint.setColor(p.color)
                canvas.drawPoint(p.x, p.y, paint)
            }

            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun run() {
        while(isRunning){
            deltaPhi += 0.001f
            draw()
            Thread.sleep(16)
        }
    }



    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {}

    override fun surfaceCreated(holder: SurfaceHolder?) {
        center.x = width.toFloat()/2
        center.y = height.toFloat()/2
        radius = width.toFloat()/3
        updatePoint3D()
    }
}