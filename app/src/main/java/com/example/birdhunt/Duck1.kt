package com.example.birdhunt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.Random

open class Duck1(context: Context) {
    private var duck = arrayOfNulls<Bitmap>(9)
    var duckX: Int = 0
    var duckY: Int = 0
    var velocity: Int = 0
    var duckFrame: Int = 0 // Moved duckFrame declaration here

    private val random = Random()

    init {
        duck[0] = BitmapFactory.decodeResource(context.resources, R.drawable.duck0)
        duck[1] = BitmapFactory.decodeResource(context.resources, R.drawable.duck1)
        duck[2] = BitmapFactory.decodeResource(context.resources, R.drawable.duck2)
        duck[3] = BitmapFactory.decodeResource(context.resources, R.drawable.duck3)
        duck[4] = BitmapFactory.decodeResource(context.resources, R.drawable.duck4)
        duck[5] = BitmapFactory.decodeResource(context.resources, R.drawable.duck5)
        duck[6] = BitmapFactory.decodeResource(context.resources, R.drawable.duck6)
        duck[7] = BitmapFactory.decodeResource(context.resources, R.drawable.duck7)
        duck[8] = BitmapFactory.decodeResource(context.resources, R.drawable.duck8)

        resetPosition()
    }

    open fun getBitmap(): Bitmap {
        return duck[duckFrame] ?: duck[0]!! // Return current frame's bitmap or the first one if null
    }

    open fun getWidth(): Int {
        return duck[duckFrame]?.width ?: 0 // Return current frame's width or 0 if null
    }

    open fun getHeight(): Int {
        return duck[duckFrame]?.height ?: 0 // Return current frame's height or 0 if null
    }

    open fun resetPosition() {
        duckX = GameView.getDWidth() + random.nextInt(1200)
        duckY = random.nextInt(300)
        velocity = 14 + random.nextInt(17)
        duckFrame = 0 // Reset duckFrame here
    }
}
