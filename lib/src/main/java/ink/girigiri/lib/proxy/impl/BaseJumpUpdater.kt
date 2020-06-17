package ink.girigiri.lib.proxy.impl

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.proxy.IJumpUpdaterProxy
import ink.girigiri.lib.service.DownLoadService

class BaseJumpUpdater :IJumpUpdaterProxy{

    private lateinit var binder: DownLoadService.DownLoadBinder
    private lateinit var jump:IJump
    override fun <J:IJump> update(jump: J?, jumper: Jumper<J>) {
        //启动服务
        DownLoadService.bindService(JumpServiceConnection())
        this.jump=jump!!
    }

    inner class JumpServiceConnection :ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            TODO("Not yet implemented")
            var binder=service as DownLoadService.DownLoadBinder
            binder.start(jump);
        }

    }
}