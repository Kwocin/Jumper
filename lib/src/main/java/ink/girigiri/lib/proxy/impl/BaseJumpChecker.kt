package ink.girigiri.lib.proxy.impl

import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest
import ink.girigiri.lib.proxy.IJumpCheckerProxy

class BaseJumpChecker(
    private var jumpRequest: IJumpHttpRequest
) :IJumpCheckerProxy{
    private lateinit var jumper:Jumper<IJump>
    /**
     * 检查版本
     */
    override fun <J:IJump> check(url: String, params: Map<String, Any>, jumper: Jumper<J>) {
        this.jumper=jumper
        jumpRequest.request(url,params,this)
    }

    override fun completed(response: String) {
        jumper.parse(response)
    }

    override fun failed(err: String) {
        jumper.failed(Jumper.JUMPER_STATE_CHECKE,err)
    }
}
