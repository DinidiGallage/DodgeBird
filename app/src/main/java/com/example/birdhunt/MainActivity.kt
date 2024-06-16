package com.example.birdhunt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun StartGame(view: View) {
        val intent = Intent(this, StartGame::class.java)
        startActivity(intent)
    }
}

