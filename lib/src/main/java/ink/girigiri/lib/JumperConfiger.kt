package ink.girigiri.lib

import android.app.Application
import android.content.Context
import ink.girigiri.lib.entity.Jump
import ink.girigiri.lib.entity.JumpInfo
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
    private lateinit var jumpHttpRequest: IJumpHttpRequest
    private lateinit var errorProcessorProxy: IJumpErrorProxy
    private lateinit var url: String
    private lateinit var path: String
    private lateinit var params: HashMap<String, Any>
    private var autoInstall=true
    private var downloadOnBackground=true
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
        fun <J : IJump> get(context: Context, clazz: Class<J>): Jumper<J>? {
            if (!checkBuild()) {
                return null
            }
            var jumpInfo=JumpInfo<J>(clazz, instance.url,
                instance.params, instance.path
                ,instance.autoInstall,
                instance.downloadOnBackground)

            return Jumper(
                context,
                jumpInfo,
                instance.jumpCheckerProxy,
                instance.jumpParserProxy,
                instance.jumpReminderProxy,
                instance.jumpUpdaterProxy!!,
                instance.jumpInstallerProxy,
                instance.jumpHttpRequest,
                instance.errorProcessorProxy
            )
        }

        @JvmStatic
        fun get(context: Context): Jumper<Jump>? {
            if (!checkBuild()) {
                return null
            }
            var jumpInfo=JumpInfo(Jump().javaClass,
                instance.url, instance.params,
                instance.path,instance.autoInstall,
                instance.downloadOnBackground)

            return Jumper(
                context,
                jumpInfo,
                instance.jumpCheckerProxy,
                instance.jumpParserProxy,
                instance.jumpReminderProxy,
                instance.jumpUpdaterProxy!!,
                instance.jumpInstallerProxy,
                instance.jumpHttpRequest,
                instance.errorProcessorProxy
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
        private var errorProcessorProxy: IJumpErrorProxy
        private var url: String?
        private var params: HashMap<String, Any>
        private var path:String?
        private var autoInstall=true
        private var downloadOnBackground=true
        init {
            url = null
            params = hashMapOf()
            path=null
            jumpHttpRequest = DefaultJumpHttpRequest()
            jumpCheckerProxy = BaseJumpChecker()
            jumpParserProxy = BaseJumpParser()
            jumpUpdaterProxy=BaseJumpUpdater()
            jumpInstallerProxy = BaseJumpInstaller()
            jumpReminderProxy = BaseJumpReminder()
            errorProcessorProxy=BaseErrorProcessor()
        }

        fun init(application: Application): Builder {
            JumperConfiger.application = application
            return this
        }

        fun url(url: String): Builder {
            this.url = url
            return this
        }

        fun param(key: String, value: Any):Builder {
            params.put(key, value)
            return this
        }

        fun param(map: Map<String, Any>):Builder {
            params.plus(map)
            return this
        }
        fun path(path:String):Builder{
            this.path
            return this
        }
        fun errorProcessor(errorProcessor: IJumpErrorProxy):Builder {
            this.errorProcessorProxy = errorProcessor
            return this
        }
        fun request(request: IJumpHttpRequest):Builder {
            this.jumpHttpRequest = request
            return this
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
        fun autoInstall(auto:Boolean):Builder{
            this.autoInstall=auto
            return this
        }
        fun downloadOnBackground(onBackground:Boolean):Builder{
            this.downloadOnBackground=onBackground
            return this
        }
        fun build(): JumperConfiger? {

            if (url == null) {
                throw Exception("url is not null!")
                return null
            } else {
                instance.url = url!!
            }
            if (path==null){
                this.path= application?.cacheDir?.absolutePath
            }
            isInit = true
            instance.params = params
            instance.path=this.path.toString()
            instance.jumpParserProxy = this.jumpParserProxy
            instance.jumpInstallerProxy = this.jumpInstallerProxy
            instance.jumpUpdaterProxy = this.jumpUpdaterProxy
            instance.jumpCheckerProxy = this.jumpCheckerProxy
            instance.jumpReminderProxy=this.jumpReminderProxy
            instance.jumpHttpRequest=this.jumpHttpRequest
            instance.autoInstall=this.autoInstall
            instance.downloadOnBackground=this.downloadOnBackground
            instance.errorProcessorProxy=this.errorProcessorProxy
            return instance
        }
    }


}