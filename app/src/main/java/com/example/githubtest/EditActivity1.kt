package com.example.githubtest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.util.SparseIntArray
import android.view.*
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.PathUtils
import com.example.githubtest.databinding.ActivityEdit1Binding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.lang.Exception

class EditActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityEdit1Binding

    private val REQUEST_GALLERY_TAKE = 2
    private val RECORD_REQUEST_CODE = 1000

    private lateinit var storage_vv: VideoView
    private lateinit var storage_btn: Button

//    private val REQUEST_CODE = 1000
//    private val REQUEST_PERMISSON = 1001
//    private lateinit var mediaProjectionManager: MediaProjectionManager
//    private var mediaProjection: MediaProjection? = null
//    private var virtualDisplay: VirtualDisplay? = null
//    private lateinit var mediaProjectionCallBack: MediaProjectionCallBack
//
//    private var mScreenDensity: Int? = null
//    private var DISPLAY_WIDTH = 720
//    private var DISPLAY_HEIGHT = 1280
//
//    private var mediaRecoeder: MediaRecorder? = null
//    private lateinit var toggleButton: FloatingActionButton
//
//    var isChecked = false
//
//    private lateinit var videoView: VideoView
//    private var videoUri: String = ""
//    private val ORIENTATIONS = SparseIntArray()
//
//    init {
//        ORIENTATIONS.append(Surface.ROTATION_0,90)
//        ORIENTATIONS.append(Surface.ROTATION_90,0)
//        ORIENTATIONS.append(Surface.ROTATION_180,270)
//        ORIENTATIONS.append(Surface.ROTATION_270,180)
//    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdit1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.hide()

        var check:Boolean = true

        /*
        @see https://medium.com/@NumberShapes/kotlin-draggable-imageview-480c1573cde5
         */
        /** color of red, pieces */
        val redPlayers = arrayOf(binding.redPlayer01, binding.redPlayer02,binding.redPlayer03,binding.redPlayer04,binding.redPlayer05,binding.redPlayer06,binding.redPlayer07,binding.redPlayer08,binding.redPlayer09,binding.redPlayer10,binding.redPlayer11)
        /** color ob blue pieces */
        val bluePlayers = arrayOf(binding.bluePlayer01, binding.bluePlayer02,binding.bluePlayer03,binding.bluePlayer04,binding.bluePlayer05,binding.bluePlayer06,binding.bluePlayer07,binding.bluePlayer08,binding.bluePlayer09,binding.bluePlayer10,binding.bluePlayer11)

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

        binding.chooseBoardSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val text = parent?.selectedItem as String
                when(text){
                    "?????????" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_freetate)
                    "????????????" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_soccertate)
                    "?????????" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_basketballtate)
                    "?????????" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_tennistate)
                    "??????????????????" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_handballtate)
                    "?????????" -> binding.tacticalBoard.setImageResource(R.drawable.tacticsboad_volleyballtate)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        storage_vv = binding.videoView
        storage_btn = binding.readVideoButton
        setupPermissions()

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

        binding.playButton.setOnClickListener {
            if(check == true){
                binding.videoView.pause()
                binding.playButton.setText("??????")
                check = false
            }else if (check == false){
                binding.videoView.start()
                binding.playButton.setText("????????????")
                check = true
            }
        }

        binding.readVideoButton.setOnClickListener {
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

        binding.backButton.setOnClickListener { onbuttonTapped(it) }

//        val metrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(metrics)
//        mScreenDensity = metrics.densityDpi
//        mediaRecoeder = MediaRecorder()
//        mediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

//        videoView = findViewById(R.id.videoView)
//        toggleButton = findViewById(R.id.toggleButton)

//        binding.toggleButton.setOnClickListener {
//            if (
//                ContextCompat.checkSelfPermission(
//                    this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) + ContextCompat.checkSelfPermission(
//                    this,android.Manifest.permission.RECORD_AUDIO
//                ) != PackageManager.PERMISSION_GRANTED
//            ){
//                isChecked = false
//                ActivityCompat.requestPermissions(
//                    this, arrayOf(
//                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        android.Manifest.permission.RECORD_AUDIO
//                    ),REQUEST_PERMISSON
//                )
//            }else{
//                binding.toggleButton.setOnClickListener{
//                    val serviceIntent = Intent(this,ForegroundService::class.java)
//                    startService(serviceIntent)
//                    toggleScreenShare(toggleButton)
//                }
//            }
//        }

        Log.d("???","error")
    }

    private fun openGalleryForImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*"
        startActivityForResult(intent,REQUEST_GALLERY_TAKE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            2->{
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE){
                    storage_vv.setVideoURI(data?.data)
                    binding.videoView.start()
                    Log.d("???","error")
                }
            }
        }

//        if (requestCode != REQUEST_CODE){
//            Toast.makeText(this,"Unk Error",Toast.LENGTH_LONG).show()
//            return
//        }
//        if (resultCode != RESULT_OK){
//            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
//            isChecked = false
//            return
//        }
//
//        mediaProjectionCallBack = MediaProjectionCallBack(
//            mediaRecoeder!!,mediaProjection
//        )
//        mediaProjection = mediaProjectionManager.getMediaProjection(
//            resultCode,data!!
//        )
//
//        mediaProjection!!.registerCallback(mediaProjectionCallBack,null)
//        virtualDisplay = createVirtualDisplay()
//        try {
//            mediaRecoeder!!.start()
//        }catch (e: Exception){
//            e.printStackTrace()
//        }
    }

    private fun setupPermissions(){
        val permission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),RECORD_REQUEST_CODE)
    }

    fun onbuttonTapped(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

//    private fun setupPermissions(){
//        val permission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
//
//        if(permission != PackageManager.PERMISSION_GRANTED){
//            makeRequest()
//        }
//    }
//
//    private fun makeRequest(){
//        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),RECORD_REQUEST_CODE)
//    }
//
//    private fun toggleScreenShare(v: FloatingActionButton?){
//        if(!isChecked){
//            initRecorder()
//            recordScreen()
//            isChecked = true
//            toggleButton.setImageResource(R.drawable.ic_stop)
//        }else{
//            try{
//                mediaRecoeder!!.stop()
//                mediaRecoeder!!.reset()
//                stopRecordingScreen()
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//
//            isChecked = false
//            toggleButton.setImageResource(R.drawable.ic_video)
//        }
//    }
//
//    private fun initRecorder(){
//        try{
//            var recordingFile = ("ScreenRec${System.currentTimeMillis()}.mp4")
//            mediaRecoeder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
//            mediaRecoeder!!.setVideoSource(MediaRecorder.VideoSource.SURFACE)
//            mediaRecoeder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//
//            val newPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//            val folder = File(newPath,"MyScreenREC/")
//            if (!folder.exists()){
//                folder.mkdirs()
//            }
//            val file1 = File(folder,recordingFile)
//            videoUri = file1.absolutePath
//
//            mediaRecoeder!!.setOutputFile(videoUri)
//            mediaRecoeder!!.setVideoSize(DISPLAY_WIDTH,DISPLAY_HEIGHT)
//            mediaRecoeder!!.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
//
//            mediaRecoeder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//            mediaRecoeder!!.setVideoEncodingBitRate(512*1000)
//            mediaRecoeder!!.setVideoFrameRate(30)
//
//            var rotation = windowManager.defaultDisplay.rotation
//            var orientation = ORIENTATIONS.get(rotation + 90)
//
//            mediaRecoeder!!.setOrientationHint(orientation)
//            mediaRecoeder!!.prepare()
//        }catch (e: Exception){
//            e.printStackTrace()
//        }
//    }
//
//    private fun recordScreen(){
//        if(mediaProjection == null){
//            startActivityForResult(
//                mediaProjectionManager.createScreenCaptureIntent(),REQUEST_CODE
//            )
//        }
//        virtualDisplay = createVirtualDisplay()
//        try{
//            mediaRecoeder!!.start()
//        }catch (e: Exception){
//            e.printStackTrace()
//        }
//    }
//
//    private fun createVirtualDisplay(): VirtualDisplay?{
//        return mediaProjection?.createVirtualDisplay(
//            "MainActivity",DISPLAY_WIDTH,DISPLAY_HEIGHT,mScreenDensity!!,
//            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
//            mediaRecoeder!!.surface,null,null
//        )
//    }
//
//    private fun stopRecordingScreen(){
//        if(virtualDisplay == null)
//            return
//        virtualDisplay!!.release()
//        destroyMediaProjecrion()
//    }
//
//    private fun destroyMediaProjecrion(){
//        if (mediaProjection != null){
//            mediaProjection!!.unregisterCallback(mediaProjectionCallBack)
//            mediaProjection!!.stop()
//            mediaProjection = null
//        }
//    }
//
//    inner class MediaProjectionCallBack(
//        var mediaRecorder: MediaRecorder,
//        var mediaProjection: MediaProjection?
//    ) : MediaProjection.Callback(){
//        override fun onStop() {
//            if (isChecked){
//                isChecked = false
//                mediaRecorder!!.stop()
//                mediaRecorder!!.reset()
//            }
//            mediaProjection = null
//            stopRecordingScreen()
//            super.onStop()
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when(requestCode){
//            REQUEST_PERMISSON -> {
//                if (grantResults.size > 0 && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED){
//                    toggleScreenShare(toggleButton)
//                }else{
//                    isChecked = false
//                    ActivityCompat.requestPermissions(
//                        this, arrayOf(
//                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            android.Manifest.permission.RECORD_AUDIO
//                        ),REQUEST_PERMISSON
//                    )
//                }
//            }
//        }
//    }
}


        //val myView :MyView = findViewById(R.id.myView)
        //val myView :MyView = binding.myView

        /*
        binding.choosePenColorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinner = parent as? Spinner
                val item = spinner?.selectedItem as? String
                item?.let {
                    if (it.isNotEmpty()){
                        MyView.changeColor(it)
                    }
                }
            }
        }*/