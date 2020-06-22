package ink.girigiri.lib.proxy

import ink.girigiri.lib.callback.JumperAffairCallBack

import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest

interface IJumpCheckerProxy {
    fun  < J:IJump> check(jumpInfo: JumpInfo<J>,httpRequest: IJumpHttpRequest,callBack: JumperAffairCallBack)
    fun completed(response: String)
    fun failed(throwable: Throwable)
}