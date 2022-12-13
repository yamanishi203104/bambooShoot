package com.example.githubtest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.VideoView
import com.example.githubtest.databinding.ActivityEdit3Binding

class EditActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityEdit3Binding

    private val REQUEST_GALLERY_TAKE = 2
    private val RECORD_REQUEST_CODE = 1000

    private lateinit var storage_vv: VideoView
    private lateinit var storage_btn: Button

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdit3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.hide()

        var listener = View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.y = motionEvent.rawY - view.height / 2
                view.x = motionEvent.rawX - view.width / 2
            }
            true
        }

        val customSurfaceView = CustomSurfaceView(this, binding.surfaceView)
        binding.surfaceView.setOnTouchListener { v, event ->
            customSurfaceView.onTouch(event)
        }

        binding.blackButton.setOnClickListener {
            customSurfaceView.changeColor("black")
        }

        binding.whiteButton.setOnClickListener {
            customSurfaceView.changeColor("white")
        }

        binding.redButton.setOnClickListener {
            customSurfaceView.changeColor("red")
        }

        binding.yellowButton.setOnClickListener {
            customSurfaceView.changeColor("yellow")
        }

        binding.bigpenButton.setOnClickListener {
            customSurfaceView.changePensize("big")
        }

        binding.midpenButton.setOnClickListener {
            customSurfaceView.changePensize("mid")
        }

        binding.smallpenButton.setOnClickListener {
            customSurfaceView.changePensize("small")
        }

        binding.clearButton.setOnClickListener {
            customSurfaceView.reset()
        }

        storage_btn.setOnClickListener {
            openGalleryForImage()

            val customSurfaceView = CustomSurfaceView(this, binding.surfaceView)
            binding.surfaceView.setOnTouchListener { v, event ->
                customSurfaceView.onTouch(event)
            }

            binding.clearButton.setOnClickListener {
                customSurfaceView.reset()
            }

            binding.blackButton.setOnClickListener {
                customSurfaceView.changeColor("black")
            }

            binding.whiteButton.setOnClickListener {
                customSurfaceView.changeColor("white")
            }

            binding.redButton.setOnClickListener {
                customSurfaceView.changeColor("red")
            }

            binding.yellowButton.setOnClickListener {
                customSurfaceView.changeColor("yellow")
            }

            binding.bigpenButton.setOnClickListener {
                customSurfaceView.changePensize("big")
            }

            binding.midpenButton.setOnClickListener {
                customSurfaceView.changePensize("mid")
            }

            binding.smallpenButton.setOnClickListener {
                customSurfaceView.changePensize("small")
            }
        }
    }

    private fun openGalleryForImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*"
        startActivityForResult(intent,REQUEST_GALLERY_TAKE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            2 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE) {
                    storage_vv.setVideoURI(data?.data)
                    binding.videoView.start()
                    Log.d("★", "error")
                }
            }
        }
    }
}