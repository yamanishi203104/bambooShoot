package com.example.githubtest

import android.app.*
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.File

class ForegroundService : Service(){
    var data: Intent? = null
    var code = Activity.RESULT_OK

    lateinit var mediaRecorder: MediaRecorder
    lateinit var projectionManager: MediaProjectionManager
    lateinit var projection: MediaProjection
    lateinit var virtualDisplay: VirtualDisplay

    var height = 2800
    var width = 1400
    var dpi = 1000

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        data = intent?.getParcelableExtra("data")
        code = intent?.getIntExtra("code",Activity.RESULT_OK)?:Activity.RESULT_OK

        dpi = intent?.getIntExtra("dpi",1000)?:1000

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelID = "rec_notify"

        if (notificationManager.getNotificationChannel(channelID) == null){
            val channel = NotificationChannel(channelID,"録画サービス起動中通知",NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = Notification.Builder(applicationContext,channelID)
            .setContentText("録画中です")
            .setContentTitle("画面録画")
            .setSmallIcon(R.drawable.ic_stop)
            .build()

        startForeground(1,notification)

        startRec()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRec()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startRec(){
        if (data != null){
            projectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            projection = projectionManager.getMediaProjection(code,data!!)

            mediaRecorder = MediaRecorder()
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder.setVideoEncodingBitRate(1080 * 10000)
            mediaRecorder.setVideoFrameRate(30)
            mediaRecorder.setVideoSize(width, height)
            mediaRecorder.setAudioSamplingRate(44100)
            mediaRecorder.setOutputFile(getFilePath())
            mediaRecorder.prepare()

            virtualDisplay = projection.createVirtualDisplay(
                "recode",
                width,
                height,
                dpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mediaRecorder.surface,
                null,
                null
            )
            mediaRecorder.start()
        }
    }

    fun stopRec(){
        mediaRecorder.stop()
        mediaRecorder.release()
        virtualDisplay.release()
        projection.stop()
    }
    fun getFilePath(): File {
        val scopedStoragePath = getExternalFilesDir(null)
        val file = File("${scopedStoragePath?.path}/${System.currentTimeMillis()}.mp4")
        return file
    }
}
