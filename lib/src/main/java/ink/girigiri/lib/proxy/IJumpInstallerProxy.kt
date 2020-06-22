package ink.girigiri.lib.proxy

import ink.girigiri.lib.callback.JumperAffairCallBack
import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.inf.IJump

interface IJumpInstallerProxy {
    fun <J:IJump> install(jumpInfo: JumpInfo<J>,callBack: JumperAffairCallBack)
}