package ink.girigiri.lib.proxy.impl

import android.content.Context
import androidx.fragment.app.FragmentActivity
import ink.girigiri.lib.callback.JumperAffairCallBack
import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest
import ink.girigiri.lib.proxy.IJumpErrorProxy
import ink.girigiri.lib.proxy.IJumpReminderProxy
import ink.girigiri.lib.proxy.IJumpUpdaterProxy
import ink.girigiri.lib.widget.DefaultDialogFragment

class BaseJumpReminder :IJumpReminderProxy{


    override fun <J : IJump> showRemind(
        context: Context,
        jumpInfo: JumpInfo<J>,
        updaterProxy: IJumpUpdaterProxy,
        httpRequest: IJumpHttpRequest,
        callBack: JumperAffairCallBack
    ) {
        if (context is FragmentActivity){
            DefaultDialogFragment().show(context.supportFragmentManager,jumpInfo,updaterProxy,httpRequest,callBack)
        }else{

        }
    }


}