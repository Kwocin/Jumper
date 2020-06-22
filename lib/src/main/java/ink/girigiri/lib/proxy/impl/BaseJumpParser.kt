package ink.girigiri.lib.proxy.impl

import com.google.gson.Gson
import ink.girigiri.lib.callback.JumperAffairCallBack
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.proxy.IJumpParserProxy

import java.lang.Exception

class BaseJumpParser() : IJumpParserProxy {

    /**
     * 解析服务器返回的更新信息
     * @param response String 请求返回
     * @return J IJump 实例
     */
    override fun <J : IJump> parser(
        response: String,
        jump: Class<J>,
        callBack: JumperAffairCallBack
    ) {
        try {
            callBack.next( Gson().fromJson(response, jump))

        } catch (e: Exception) {
            e.printStackTrace()
            callBack.error(e)
        }

    }
}