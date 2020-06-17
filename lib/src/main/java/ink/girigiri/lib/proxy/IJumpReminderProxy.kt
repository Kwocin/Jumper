package ink.girigiri.lib.proxy

import ink.girigiri.lib.callback.ReminderCallBack
import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump

interface IJumpReminderProxy {
    fun <J:IJump>showRemind(jump: J?, callBack:ReminderCallBack)


}