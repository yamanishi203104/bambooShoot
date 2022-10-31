package com.example.githubtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.githubtest.databinding.ActivityLayoutsBinding

class activity_layouts : AppCompatActivity() {
    private lateinit var binding: ActivityLayoutsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layouts)
        binding = ActivityLayoutsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.movieButton.setOnClickListener { onMovieButtonTapped(it) }
        binding.movieboardButton.setOnClickListener { onMovieBoardButtonTapped(it) }
        binding.boardBotton.setOnClickListener { onBoardButtonTapped(it) }
    }

    fun onMovieButtonTapped(view: View?){
        val intent = Intent(this, /* 動画画面名 */::class.java)
        startActivity(intent)
    }

    fun onMovieBoardButtonTapped(view: View?){
        val intent = Intent(this, /* 動画+戦術ボード画面名 */::class.java)
        startActivity(intent)
    }

    fun onBoardButtonTapped(view: View?){
        val intent = Intent(this, /* 戦術ボード画面名 */::class.java)
        startActivity(intent)
    }
}