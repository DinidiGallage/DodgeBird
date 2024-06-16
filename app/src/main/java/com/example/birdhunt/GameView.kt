package com.example.birdhunt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.os.Handler
import android.view.MotionEvent
import android.view.View

class GameView(context: Context) : View(context) {

    private var background: Bitmap? = null
    private lateinit var rect: Rect
    private var duckFrame = 0
    private val handler = Handler()
    private val UPDATE_MILLIS: Long = 30
    private val duck1 = ArrayList<Duck1>()
    private val duck2 = ArrayList<Duck2>()
    private lateinit var ball: Bitmap
    private lateinit var target: Bitmap
    private var ballX = 0f
    private var ballY = 0f
    private var sX = 0f
    private var sY = 0f
    private var fX = 0f
    private var fY = 0f
    private var dX = 0f
    private var dY = 0f
    private var tempX = 0f
    private var tempY = 0f
    private val borderPaint = Paint()
    private var score = 0
    private var life = 10
    private var gameState = true

    private var highScore = 0
    private val sharedPreferences = context.getSharedPreferences("HighScores", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    init {
        // Load the background bitmap
        background = BitmapFactory.decodeResource(resources, R.drawable.background)

        // Get the display dimensions
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        setDWidth(size.x)
        val dHeight = size.y

        // Initialize the rectangle
        rect = Rect(0, 0, getDWidth(), dHeight)

        for (i in 0 until 2) {
            duck1.add(Duck1(context))
            duck2.add(Duck2(context))
        }

        ball = BitmapFactory.decodeResource(resources, R.drawable.football_removebg_preview)
        target = BitmapFactory.decodeResource(resources, R.drawable.shoottarget_removebg_preview)
        ballX = 0f
        ballY = 0f
        sX = 0f
        sY = 0f
        fX = 0f
        fY = 0f
        dX = 0f
        dY = 0f
        tempX = 0f
        tempY = 0f
        borderPaint.color = Color.RED
        borderPaint.strokeWidth = 5f

        // Retrieve high score from SharedPreferences
        highScore = sharedPreferences.getInt("HighScore", 0)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (life < 1) {
            gameState = false
            val intent = Intent(context, GameOver::class.java)
            intent.putExtra("score", score)

            // Update high score if the current score is higher
            if (score > highScore) {
                highScore = score
                editor.putInt("HighScore", highScore)
                editor.apply()
            }

            (context as Activity).startActivity(intent)
            (context as Activity).finish()
        }

        // Draw the background bitmap if it's not null
        background?.let {
            canvas.drawBitmap(it, null, rect, null)
            for (duck in duck1) {
                canvas.drawBitmap(duck.getBitmap(), duck.duckX.toFloat(), duck.duckY.toFloat(), null)
                duck.duckFrame++
                if (duck.duckFrame > 8) {
                    duck.duckFrame = 0
                }
                duck.duckX -= duck.velocity
                if (duck.duckX < -duck.getWidth()) {
                    duck.resetPosition()
                    life--
                }
            }

            for (duck in duck2) {
                canvas.drawBitmap(duck.getBitmap(), duck.duckX.toFloat(), duck.duckY.toFloat(), null)
                duck.duckFrame++
                if (duck.duckFrame > 8) {
                    duck.duckFrame = 0
                }

                duck.duckX -= duck.velocity
                if (duck.duckX < -duck.getWidth()) {
                    duck.resetPosition()
                    life--
                }
            }

            for (i in duck1.indices) {
                if (ballX <= duck1[i].duckX + duck1[i].getWidth() &&
                    ballX + ball.width >= duck1[i].duckX &&
                    ballY <= duck1[i].duckY + duck1[i].getHeight() &&
                    ballY >= duck1[i].duckY) {
                    duck1[i].resetPosition()
                    score++
                }
            }

            for (i in duck2.indices) {
                if (ballX <= duck2[i].duckX + duck2[i].getWidth() &&
                    ballX + ball.width >= duck2[i].duckX &&
                    ballY <= duck2[i].duckY + duck2[i].getHeight() &&
                    ballY >= duck2[i].duckY) {
                    duck2[i].resetPosition()
                    score++
                }
            }
        }

        if (sX > 0 && sY > rect.bottom * 0.75f) {
            canvas.drawBitmap(target, sX - target.width / 2f, sY - target.height / 2f, null)
        }

        if ((Math.abs(fX - sX) > 0 || Math.abs(fY - sY) > 0) && fY > 0 && fY > rect.bottom * 0.75f) {
            canvas.drawBitmap(target, fX - target.width / 2f, fY - target.height / 2f, null)
        }

        if ((Math.abs(dX) > 10 || Math.abs(dY) > 10) && sY > rect.bottom * 0.75f && fY > rect.bottom * 0.75f) {
            ballX = fX - ball.width / 2f - tempX
            ballY = fY - ball.width / 2f - tempY
            canvas.drawBitmap(ball, ballX, ballY, null)
            tempX += dX
            tempY += dY
        }

        canvas.drawLine(0f, rect.bottom * 0.75f, getDWidth().toFloat(), rect.bottom * 0.75f, borderPaint)

        // Draw the high score on the game screen
        val highScoreText = "High Score: $highScore"
        val paint = Paint().apply {
            color = Color.WHITE
            textSize = 48f // Set the font size
        }
        val textWidth = paint.measureText(highScoreText)
        val xPosition = (getDWidth() - textWidth) / 2 // Center horizontally
        val yPosition = 40f // Position from the top
        canvas.drawText(highScoreText, xPosition, yPosition, paint)

        if (gameState) {
            handler.postDelayed(runnable, UPDATE_MILLIS)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dX = 0f
                dY = 0f
                fX = 0f
                fY = 0f
                tempX = 0f
                tempY = 0f
                sX = event.x
                sY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                fX = event.x
                fY = event.y
            }
            MotionEvent.ACTION_UP -> {
                fX = event.x
                fY = event.y
                ballX = event.x
                ballY = event.y
                dX = fX - sX
                dY = fY - sY
            }
        }
        return true
    }

    private val runnable = Runnable {
        invalidate()
    }

    companion object {
        private var dWidth = 0

        fun getDWidth(): Int {
            return dWidth
        }

        fun setDWidth(width: Int) {
            dWidth = width
        }
    }
}
