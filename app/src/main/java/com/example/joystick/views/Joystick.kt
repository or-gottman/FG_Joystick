package com.example.androidapp.views

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.View.OnTouchListener
import com.example.joystick.utils.Point
import com.example.joystick.utils.intersects
import kotlin.math.pow
import kotlin.math.sqrt

class JoystickView : SurfaceView, SurfaceHolder.Callback, OnTouchListener {
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var backgroundRadius: Float = 0f
    private var radius: Float = 0f
    private var joystickCallback: JoystickListener? = null
    private val ratio = 5


    constructor(context: Context?) : super(context) {
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is JoystickListener) joystickCallback = context
        // Set background to be transparent
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true);
        holder.setFormat(PixelFormat.TRANSPARENT);
    }

    constructor(context: Context?, attributes: AttributeSet?) : super(context, attributes) {
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is JoystickListener) joystickCallback = context
        // Set background to be transparent
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true);
        holder.setFormat(PixelFormat.TRANSPARENT);
    }

    private fun drawJoystick(newX: Float, newY: Float) {
        if (holder.surface.isValid) {
            val myCanvas = this.holder.lockCanvas()
            val colors = Paint()
            // Clear canvas
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

            // Find sin and cos of the angle between the joystick's center and mouse
            val hypotenuse =
                sqrt(
                    (newX - centerX).toDouble().pow(2.0) + (newY - centerY).toDouble().pow(2.0)
                )
                    .toFloat()
            val sin = (newY - centerY) / hypotenuse //sin = o/h
            val cos = (newX - centerX) / hypotenuse //cos = a/h

            //Draw the base first before shading
//            colors.setARGB(255, 100, 100, 100)
            colors.setARGB(200, 255, 203, 0);
            myCanvas.drawCircle(centerX, centerY, backgroundRadius, colors)
            for (i in 1..(backgroundRadius / ratio).toInt()) {
                colors.setARGB(
                    50 ,
                    0,
                    0,
                    0
                ) //Gradually decrease the shade of black drawn to create a nice shading effect
                myCanvas.drawCircle(
                    newX - cos * hypotenuse * (ratio / backgroundRadius) * i,
                    newY - sin * hypotenuse * (ratio / backgroundRadius) * i,
                    (i * (radius * 4.5 / backgroundRadius)).toFloat(),
                    colors
                ) //Gradually increase the size of the shading effect
            }

            //Drawing the joystick hat
            for (i in 0..(radius / ratio).toInt()) {
                colors.setARGB(
                    255 - 2* i,
                    (i * (216 * ratio / radius)).toInt(),
                    (i * (30 * ratio / radius)).toInt(), (i * (91 * ratio / radius)).toInt()
                ) //Change the joystick color for shading purposes
//    colors.setARGB(255,216,30,91);

                myCanvas.drawCircle(
                    newX,
                    newY,
                    radius - i.toFloat() * ratio / 3,
                    colors
                ) //Draw the shading for the hat
            }
            holder.unlockCanvasAndPost(myCanvas) //Write the new drawing to the SurfaceView
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        centerX = (width / 2).toFloat()
        centerY = (height / 2).toFloat()
        backgroundRadius = (width.coerceAtMost(height) / 5).toFloat()
        radius = (width.coerceAtMost(height) / 6).toFloat()
        drawJoystick(centerX, centerY)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}
    override fun onTouch(v: View, e: MotionEvent): Boolean {
        if (v == this) {
            if (e.action != MotionEvent.ACTION_UP) {
                val len =
                    sqrt(((e.x - centerX) * (e.x - centerX)) + ((e.y - centerY) * (e.y - centerY)))

                // When in range
                if (len < backgroundRadius) {
                    joystickCallback?.onJoystickMoved(
                        (e.x - centerX) / backgroundRadius,
                        (e.y - centerY) / backgroundRadius
                    );
                    drawJoystick(e.x, e.y)
                }

                // When out of bounds
                else {

                    var centerOfCircle = Point(centerX.toDouble(), centerY.toDouble())
                    var radiusAsDouble = backgroundRadius.toDouble()
                    var userPoint = Point((e.x).toDouble(), (e.y).toDouble())
                    var intersections =
                        intersects(centerOfCircle, userPoint, centerOfCircle, radiusAsDouble, false)
                    var deltaOne = sqrt(
                        (e.y - (intersections[0].y).toFloat()) * (e.y - (intersections[0].y).toFloat()) +
                                (e.x - (intersections[0].x).toFloat()) * (e.x - (intersections[0].x).toFloat())
                    )
                    var deltaTwo = sqrt(
                        (e.y - (intersections[1].y).toFloat()) * (e.y - (intersections[1].y).toFloat()) +
                                (e.x - (intersections[1].x).toFloat()) * (e.x - (intersections[1].x).toFloat())
                    )

                    var constrainedX = 0f
                    var constrainedY = 0f

                    if (deltaOne < deltaTwo) {
                        constrainedX = (intersections[0].x).toFloat()
                        constrainedY = (intersections[0].y).toFloat()
                    } else {
                        constrainedX = (intersections[1].x).toFloat()
                        constrainedY = (intersections[1].y).toFloat()
                    }

                    joystickCallback?.onJoystickMoved(
                        (constrainedX - centerX) / (backgroundRadius),
                        (constrainedY - centerY) / (backgroundRadius)
                    );
                    drawJoystick(constrainedX, constrainedY)
                }
            } else {
                joystickCallback?.onJoystickMoved(0f, 0f);
                drawJoystick(centerX, centerY)
            }
        }

        return true;
    }

    interface JoystickListener {
        fun onJoystickMoved(xPercent: Float, yPercent: Float)
    }
}