package com.example.birdhunt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.Random

class Duck2(context: Context) : Duck1(context) {

    private var duck = arrayOfNulls<Bitmap>(9)

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

    override fun getBitmap(): Bitmap {
        return duck[duckFrame] ?: duck[0]!!
    }

    override fun getWidth(): Int {
        return duck[0]?.width ?: 0
    }

    override fun getHeight(): Int {
        return duck[0]?.height ?: 0
    }

    override fun resetPosition() {
        duckX = GameView.getDWidth() + Random().nextInt(1500)
        duckY = Random().nextInt(400)
        velocity = 15 + Random().nextInt(19)
    }
}
