package ink.girigiri.lib.inf

import ink.girigiri.lib.callback.DownLoadCallBack
import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.proxy.IJumpCheckerProxy
import javax.security.auth.callback.Callback

interface IJumpHttpRequest{
    fun <J:IJump>check(jumpInfo: JumpInfo<J>, checker:IJumpCheckerProxy)
    fun <J:IJump> download(jumpInfo: JumpInfo<J>,callBack: DownLoadCallBack)
}