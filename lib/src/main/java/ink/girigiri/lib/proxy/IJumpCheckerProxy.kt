package ink.girigiri.lib.proxy

import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump

interface IJumpCheckerProxy {
    fun  < J:IJump> check(url: String, param: Map<String, Any>, jumper: Jumper<J>)
    fun completed(response: String)
    fun failed(err:String)
}