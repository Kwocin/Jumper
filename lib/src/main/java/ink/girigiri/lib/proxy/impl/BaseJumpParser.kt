package ink.girigiri.lib.proxy.impl

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.proxy.IJumpParserProxy
import org.json.JSONObject

class BaseJumpParser() : IJumpParserProxy {

    /**
     * 解析服务器返回的更新信息
     * @param response String 请求返回
     * @return J IJump 实例
     */
    override fun <J : IJump> parser(
        response: String,
        jumper: Jumper<J>,
        jump:Class<J>
    ): J? {
        if (TextUtils.isEmpty(response)) {
            jumper.failed(Jumper.JUMPER_STATE_PARSER, "response is empty")
            return null
        }
        return Gson().fromJson(response,jump)
    }
}