package com.example.birdhunt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOver : AppCompatActivity() {
    private lateinit var tvScore: TextView
    private lateinit var tvHighScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)

        tvScore = findViewById(R.id.tvScore)
        tvHighScore = findViewById(R.id.tvHighScore)

        val score = intent.extras?.getInt("score", 0) ?: 0
        val sharedPreferences = getSharedPreferences("HighScores", Context.MODE_PRIVATE)
        val highScore = sharedPreferences.getInt("HighScore", 0)

        tvScore.text = score.toString()
        tvHighScore.text = highScore.toString()
    }

    fun restart(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
