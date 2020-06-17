package ink.girigiri.lib.proxy.impl

import android.app.AlertDialog
import android.content.DialogInterface
import ink.girigiri.lib.JumperConfiger
import ink.girigiri.lib.callback.ReminderCallBack
import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.proxy.IJumpReminderProxy

class BaseJumpReminder :IJumpReminderProxy{
    override fun <J : IJump> showRemind(jump: J?, callBack: ReminderCallBack) {

    }


}