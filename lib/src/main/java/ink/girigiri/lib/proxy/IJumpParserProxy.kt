package ink.girigiri.lib.proxy

import ink.girigiri.lib.callback.JumperAffairCallBack
import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump

/**
 * 版本解析接口
 * @param J:IJump IJump对象实例
 */
interface IJumpParserProxy {
    /**
     * 解析
     * @param response String 请求返回的数据
     * @return J 返回IJump对象实例
     */
    fun < J:IJump> parser(response: String,jump:Class<J>,callBack: JumperAffairCallBack)
}