package com.example.birdhunt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class StartGame : AppCompatActivity() {
    private lateinit var gameView: GameView

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        gameView = GameView(this)
        setContentView(gameView)
    }
}
