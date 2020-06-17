package ink.girigiri.lib

import android.app.Application
import ink.girigiri.lib.entity.Jump
import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest
import ink.girigiri.lib.net.DefaultJumpHttpRequest
import ink.girigiri.lib.proxy.*
import ink.girigiri.lib.proxy.impl.*
import java.lang.Exception


/**
 * 运动员配置类
 * @Description 版本管理配置类
 * @author Pulis
 * @create 2020/6/12
 */

/**
 *
 * @property jumpCheckerProxy IJumpChecker 检查器
 * @property jumpParserProxy IJumpParser 解析器
 * @property jumpUpdaterProxy IJumpUpdater   更新器
 * @property jumpInstallerProxy IJumpInstaller   安装器
 * @property url String? url
 * @property isInit Boolean 初始化标识
 */
class JumperConfiger private constructor() {
    private lateinit var jumpCheckerProxy: IJumpCheckerProxy
    private lateinit var jumpParserProxy: IJumpParserProxy
    private lateinit var jumpUpdaterProxy: IJumpUpdaterProxy
    private lateinit var jumpInstallerProxy: IJumpInstallerProxy
    private lateinit var jumpReminderProxy: IJumpReminderProxy

    private lateinit var url: String
    private lateinit var params: HashMap<String, Any>

    companion object {
        private var isInit: Boolean = false
        var application: Application? = null
        private var instance: JumperConfiger = getInstance()
        private fun getInstance(): JumperConfiger {
            if (instance == null) {
                instance = JumperConfiger()
            }
            return instance
        }

        @JvmStatic
        fun <J : IJump> get(clazz: Class<J>): Jumper<J>? {
            if (!checkBuild()) {
                return null
            }
            return Jumper(
                instance.url,
                instance.params,
                clazz,
                instance.jumpCheckerProxy,
                instance.jumpParserProxy,
                instance.jumpReminderProxy,
                instance.jumpUpdaterProxy,
                instance.jumpInstallerProxy
            )
        }

        @JvmStatic
        fun get(): Jumper<Jump>? {
            if (!checkBuild()) {
                return null
            }

            return Jumper(
                instance.url,
                instance.params,
                Jump().javaClass,
                instance.jumpCheckerProxy,
                instance.jumpParserProxy,
                instance.jumpReminderProxy,
                instance.jumpUpdaterProxy,
                instance.jumpInstallerProxy
            )
        }

        private fun checkBuild(): Boolean {

            try {
                if (!isInit) {
                    throw Exception("please call build() first!")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return isInit
        }

    }


    class Builder {
        private var jumpCheckerProxy: IJumpCheckerProxy
        private var jumpParserProxy: IJumpParserProxy
        private var jumpUpdaterProxy: IJumpUpdaterProxy
        private var jumpInstallerProxy: IJumpInstallerProxy
        private var jumpHttpRequest: IJumpHttpRequest
        private var jumpReminderProxy: IJumpReminderProxy
        private var url: String?
        private var params: HashMap<String, Any>

        init {
            url = null
            params = hashMapOf()
            jumpHttpRequest = DefaultJumpHttpRequest()
            jumpCheckerProxy = BaseJumpChecker(jumpHttpRequest)
            jumpParserProxy = BaseJumpParser()
            jumpUpdaterProxy = BaseJumpUpdater()
            jumpInstallerProxy = BaseJumpInstaller()
            jumpReminderProxy = BaseJumpReminder()
        }

        fun init(application: Application): Builder {
            JumperConfiger.application = application
            return this
        }

        fun url(url: String): Builder {
            this.url = url
            return this
        }

        fun param(key: String, value: Any) {
            params.put(key, value)
        }

        fun param(map: Map<String, Any>) {
            params.plus(map)
        }

        fun request(request: IJumpHttpRequest) {
            this.jumpHttpRequest = request
        }

        fun parser(parserProxy: IJumpParserProxy): Builder {
            this.jumpParserProxy = parserProxy
            return this
        }

        fun checker(checkerProxy: IJumpCheckerProxy): Builder {
            this.jumpCheckerProxy = checkerProxy
            return this
        }

        fun reminder(reminderProxy: IJumpReminderProxy): Builder {
            this.jumpReminderProxy = reminderProxy
            return this
        }

        fun updater(updaterProxy: IJumpUpdaterProxy): Builder {
            this.jumpUpdaterProxy = updaterProxy
            return this
        }

        fun installer(installerProxy: IJumpInstallerProxy): Builder {
            this.jumpInstallerProxy = installerProxy
            return this
        }

        fun build(): JumperConfiger? {

            if (url == null) {
                throw Exception("url is not null!")
                return null
            } else {
                instance.url = url!!
            }
            isInit = true
            instance.params = params
            instance.jumpParserProxy = this.jumpParserProxy
            instance.jumpInstallerProxy = this.jumpInstallerProxy
            instance.jumpUpdaterProxy = this.jumpUpdaterProxy
            instance.jumpCheckerProxy = this.jumpCheckerProxy
            instance.jumpReminderProxy=this.jumpReminderProxy
            return instance
        }
    }


}