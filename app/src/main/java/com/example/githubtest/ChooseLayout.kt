package com.example.githubtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.githubtest.databinding.ActivityChooseLayoutBinding

class ChooseLayout : AppCompatActivity() {
    private lateinit var binding: ActivityChooseLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.hide()

        binding.button.setOnClickListener { onbuttonTapped(it) }

        binding.button2.setOnClickListener { onbutton2Tapped(it) }

        binding.button3.setOnClickListener { onbutton3Tapped(it) }
    }

    fun onbuttonTapped(view: View){
        val intent = Intent(this,EditActivity3::class.java)
        startActivity(intent)
    }

    fun onbutton2Tapped(view: View){
        val intent = Intent(this,EditActivity1::class.java)
        startActivity(intent)
    }

    fun onbutton3Tapped(view: View){
        val intent = Intent(this,EditActivity2::class.java)
        startActivity(intent)
    }
}