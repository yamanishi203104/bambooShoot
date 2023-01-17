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
import com.example.githubtest.databinding.ActivityEdit2Binding

class EditActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityEdit2Binding

//    private val REQUEST_GALLERY_TAKE = 2
//    private val RECORD_REQUEST_CODE = 1000
//
//    private lateinit var storage_vv: VideoView
//    private lateinit var storage_btn: Button

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdit2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.hide()

//        val redPlayer01x : Float = binding.redPlayer01.x
//        val redPlayer01y : Float = binding.redPlayer01.y
//
//        binding.button4.setOnClickListener {
//            binding.redPlayer01.translationX = redPlayer01x
//            binding.redPlayer01.translationY = redPlayer01y
//        }


        /** color of red, pieces */
        val redPlayers = arrayOf(binding.redPlayer01, binding.redPlayer02,binding.redPlayer03,binding.redPlayer04,binding.redPlayer05,binding.redPlayer06,binding.redPlayer07,binding.redPlayer08,binding.redPlayer09,binding.redPlayer10,binding.redPlayer11)
        val redPlayers_x:Array<Float> = Array(11){0f}
        val redPlayers_y:Array<Float> = Array(11){0f}
        /** color ob blue pieces */
        val bluePlayers = arrayOf(binding.bluePlayer01, binding.bluePlayer02,binding.bluePlayer03,binding.bluePlayer04,binding.bluePlayer05,binding.bluePlayer06,binding.bluePlayer07,binding.bluePlayer08,binding.bluePlayer09,binding.bluePlayer10,binding.bluePlayer11)
        val bluePlayers_x:Array<Float> = Array(11){0f}
        val bluePlayers_y:Array<Float> = Array(11){0f}

        for (player in redPlayers){
            var i = 0
            redPlayers_x[i] = player.x
            redPlayers_y[i] = player.y
            i++
        }

        for (player in bluePlayers){
            var i = 0
            bluePlayers_x[i] = player.x
            bluePlayers_y[i] = player.y
            i++
        }

        val ballx:Float = binding.blackBall.x
        val bally:Float = binding.blackBall.y

        var listener = View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.y = motionEvent.rawY - view.height / 2
                view.x = motionEvent.rawX - view.width / 2
            }
            true
        }

        for (player in redPlayers){
            player.setOnTouchListener(listener)
        }
        for (player in bluePlayers){
            player.setOnTouchListener(listener)
        }
        binding.blackBall.setOnTouchListener(listener)

        binding.chooseBoardSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val text = parent?.selectedItem as String
                when(text){
                    "フリー" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_free)
                    "サッカー" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_soccer)
                    "バスケ" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_basketball)
                    "テニス" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_tennis)
                    "ハンドボール" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_handball)
                    "バレー" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_volleyball)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.pieceResetButton.setOnClickListener {
            binding.blackBall.translationX = ballx
            binding.blackBall.translationY = bally

            for (player in redPlayers){
                var i = 0
                player.translationX = redPlayers_x[i]
                player.translationY = redPlayers_y[i]
                i++
            }

            for (player in bluePlayers){
                var i = 0
                player.translationX = bluePlayers_x[i]
                player.translationY = bluePlayers_y[i]
                i++
            }
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

        binding.backButton.setOnClickListener { onbuttonTapped(it) }

//        storage_btn.setOnClickListener {
//            openGalleryForImage()
//
//            val customSurfaceView = CustomSurfaceView(this, binding.surfaceView)
//            binding.surfaceView.setOnTouchListener { v, event ->
//                customSurfaceView.onTouch(event)
//            }
//
//            binding.clearButton.setOnClickListener {
//                customSurfaceView.reset()
//            }
//
//            binding.blackButton.setOnClickListener {
//                customSurfaceView.changeColor("black")
//            }
//
//            binding.whiteButton.setOnClickListener {
//                customSurfaceView.changeColor("white")
//            }
//
//            binding.redButton.setOnClickListener {
//                customSurfaceView.changeColor("red")
//            }
//
//            binding.yellowButton.setOnClickListener {
//                customSurfaceView.changeColor("yellow")
//            }
//
//            binding.bigpenButton.setOnClickListener {
//                customSurfaceView.changePensize("big")
//            }
//
//            binding.midpenButton.setOnClickListener {
//                customSurfaceView.changePensize("mid")
//            }
//
//            binding.smallpenButton.setOnClickListener {
//                customSurfaceView.changePensize("small")
//            }
        }

    fun onbuttonTapped(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

//        private fun openGalleryForImage(){
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "video/*"
//            startActivityForResult(intent,REQUEST_GALLERY_TAKE)
//        }

//        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//            super.onActivityResult(requestCode, resultCode, data)
//
//            when(requestCode){
//                2->{
//                    if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE){
//                        storage_vv.setVideoURI(data?.data)
//                        binding.videoView.start()
//                        Log.d("★","error")
//                    }
//                }
//            }
//        }
    }