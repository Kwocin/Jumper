package ink.girigiri.lib.proxy

import ink.girigiri.lib.callback.JumperAffairCallBack
import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest

interface IJumpUpdaterProxy {
    fun <J:IJump> update(jumpInfo: JumpInfo<J>, httpRequest: IJumpHttpRequest,callBack: JumperAffairCallBack)
}