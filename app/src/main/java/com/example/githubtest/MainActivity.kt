package com.example.githubtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.githubtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeImage.setOnClickListener{onhomeImageTapped(it)}
    }

    fun onhomeImageTapped(view: View?){
        val intent = Intent(this, activity_layouts::class.java)
        startActivity(intent)
    }
}