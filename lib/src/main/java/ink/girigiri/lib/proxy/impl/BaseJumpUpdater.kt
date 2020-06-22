package ink.girigiri.lib.proxy.impl

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import ink.girigiri.lib.callback.JumperAffairCallBack

import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest
import ink.girigiri.lib.proxy.IJumpErrorProxy
import ink.girigiri.lib.proxy.IJumpUpdaterProxy
import ink.girigiri.lib.service.DownLoadService

class BaseJumpUpdater : IJumpUpdaterProxy {

    private lateinit var binder: DownLoadService.DownLoadBinder
    private lateinit var jumpInfo: JumpInfo<*>
    private lateinit var httpRequest: IJumpHttpRequest
    private lateinit var callBack: JumperAffairCallBack


    inner class JumpServiceConnection : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            var binder = service as DownLoadService.DownLoadBinder
            binder.start(jumpInfo, httpRequest, callBack)
        }

    }

    override fun <J : IJump> update(
        jumpInfo: JumpInfo<J>,
        httpRequest: IJumpHttpRequest,
        callBack: JumperAffairCallBack
    ) {
        this.httpRequest = httpRequest
        this.callBack = callBack
        this.jumpInfo = jumpInfo
        //启动服务
        DownLoadService.bindService(JumpServiceConnection())
    }


}