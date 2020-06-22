package ink.girigiri.lib.proxy

import android.content.Context
import ink.girigiri.lib.callback.JumperAffairCallBack
import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest

interface IJumpReminderProxy {
    fun <J:IJump>showRemind(context: Context, jumpInfo: JumpInfo<J>,updaterProxy: IJumpUpdaterProxy,httpRequest: IJumpHttpRequest,callBack: JumperAffairCallBack)


}