package ink.girigiri.lib.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import ink.girigiri.lib.JumperConfiger
import ink.girigiri.lib.R
import ink.girigiri.lib.callback.DownLoadCallBack
import ink.girigiri.lib.callback.JumperAffairCallBack
import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest

class DownLoadService() : Service() {

    private lateinit var builder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager
    private lateinit var  affairCallback: JumperAffairCallBack
    companion object{
        private val NOTIFY_ID_DOWNLOAD=0x01
        private val CHANNEL_ID="JUMPER_CHNANNEL_ID"
        private val CHANNEL_NAME="JUMPER_CHNANNEL_NAME"
        @JvmStatic
        fun bindService(conn:ServiceConnection){
            var intent=Intent(JumperConfiger.application,DownLoadService::class.java)

            JumperConfiger.application?.startService(intent)
            JumperConfiger.application?.bindService(intent,conn, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Jumper","onStartCommand()")
        setUpNatification()
        return super.onStartCommand(intent, flags, startId)
    }



    override fun onBind(intent: Intent?): IBinder? {
        return DownLoadBinder()
    }

    private fun <J:IJump>startDownload(jumpInfo: JumpInfo<J>,httpRequest: IJumpHttpRequest,callback:JumperAffairCallBack){
        //下载
        Log.i("Jumper","startDownload()")
        this.affairCallback=callback
        httpRequest.download(jumpInfo,DownloaderCallBackImpl())
    }

    private fun stopDownload() {

    }

    /**
     * 初始化通知
     */
    private fun setUpNatification() {
        //安卓8.0以上
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            //关闭呼吸灯提醒
            channel.enableLights(false)
            //关闭震动
            channel.enableVibration(false)
            notificationManager.createNotificationChannel(channel)
        }

        builder = getNotificationBuilder()
        notificationManager.notify(NOTIFY_ID_DOWNLOAD,builder.build())
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText(getString(R.string.notification_connecting))
            .setContentTitle(getString(R.string.notification_title))
            .setSmallIcon(R.drawable.baseline_arrow_downward_pink_a200_24dp)
            .setOngoing(true)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
    }


    inner class DownLoadBinder : Binder() {
        fun <J:IJump> start(jumpInfo: JumpInfo<J>,httpRequest: IJumpHttpRequest,callback:JumperAffairCallBack){
            startDownload(jumpInfo,httpRequest,callback)
        }
        fun stop(){
            stopDownload()
        }
        fun showNotification(){

        }
    }

    inner class DownloaderCallBackImpl():DownLoadCallBack{
        override fun progress(progress: Int) {
            Log.i("Jumper","progress:"+progress)
        }

        override fun completed() {
            Log.i("Jumper","complete()")
            affairCallback.next()
        }

        override fun failed(t: Throwable) {
            Log.i("Jumper","failed()::"+t.message)

        }

    }

}