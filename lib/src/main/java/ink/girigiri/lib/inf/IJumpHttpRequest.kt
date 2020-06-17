package ink.girigiri.lib.inf

import ink.girigiri.lib.proxy.IJumpCheckerProxy

interface IJumpHttpRequest{
    fun request(url:String,params:Map<String,Any>,checker:IJumpCheckerProxy)
}