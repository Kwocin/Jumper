package ink.girigiri.lib.proxy.impl

import ink.girigiri.lib.callback.JumperAffairCallBack
import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest
import ink.girigiri.lib.proxy.IJumpCheckerProxy
import ink.girigiri.lib.proxy.IJumpErrorProxy
import ink.girigiri.lib.proxy.IJumpParserProxy

class BaseJumpChecker :IJumpCheckerProxy{
    private lateinit var callBack:JumperAffairCallBack
    override fun <J : IJump> check(jumpInfo: JumpInfo<J>,httpRequest: IJumpHttpRequest,callBack: JumperAffairCallBack) {
        this.callBack=callBack
        httpRequest.check(jumpInfo,this)

    }

    override fun completed(response: String) {
        callBack.next(response)
    }
    override fun failed(t: Throwable) {
        callBack.error(t)
    }
}
