package ink.girigiri.lib.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.util.Log
import ink.girigiri.lib.JumperConfiger
import ink.girigiri.lib.inf.IJump

class DownLoadService : Service() {
    companion object{
        @JvmStatic
        fun bindService(conn:ServiceConnection){
            var intent=Intent(JumperConfiger.application,DownLoadService.javaClass)
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

    private fun <J:IJump>startDownload(jump:J){
        //下载
        Log.i("Jumper","startDownload()")
    }

    private fun stopDownload() {

    }
    private fun setUpNatification() {

    }

    inner class DownLoadBinder : Binder() {
        fun <J:IJump> start(jump:J){
            startDownload(jump)
        }
        fun stop(){
            stopDownload()
        }
        fun showNotification(){

        }
    }

}