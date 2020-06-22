package ink.girigiri.lib.entity

import android.app.Activity
import android.content.Context
import ink.girigiri.lib.callback.JumperAffairCallBack
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest
import ink.girigiri.lib.proxy.*
import java.lang.Exception
import java.util.*

/**
 * 真正去做一系列更新操作的类
 * @param J:IJump 版本信息
 * @property clazz Class<J> 版本信息实例类
 * @property checker IJumpChecker 检查器
 * @property paser IJumpParser 解析器
 * @property updater IJumpUpdater 更新器
 * @property installer IJumpInstaller 安装器
 * @constructor
 */
class Jumper< J : IJump>(
    private var context: Context,
    private var jumpInfo: JumpInfo<J>,
    private var checker: IJumpCheckerProxy,
    private var paser: IJumpParserProxy,
    private var reminder: IJumpReminderProxy,
    private var updater: IJumpUpdaterProxy,
    private var installer: IJumpInstallerProxy,
    private var httpRequest: IJumpHttpRequest,
    private var errorProcessor: IJumpErrorProxy
){


    /**
     * 更新状态标识
     */
    companion object {
        const val JUMPER_STATE_CHECK = 0
        const val JUMPER_STATE_PARSE = 1
        const val JUMPER_STATE_REMIND = 2
        const val JUMPER_STATE_UPDATE = 3
        const val JUMPER_STATE_INSTALL = 4
    }

    /**
     * 各种监听器
     */
    private var onCheckStart: (() -> Unit)?
    private var onParseStart: (() -> Unit)?
    private var onParseCompleted:((jump:J?)->Unit)?
    private var onUpdateStart: (() -> Unit)?
    private var onInstallStart: (() -> Unit)?
    private var onFailed: ((state: Int, err: String) -> Unit)?
    private var callback:OnJumperCallBack
    private var affairQueue:Queue<Int>

    /**
     * 初始化
     */
    init {
        callback=OnJumperCallBack()
        affairQueue=LinkedList()
        onCheckStart=null
        onParseStart=null
        onParseCompleted=null
        onUpdateStart=null
        onInstallStart=null
        onFailed=null
        initAffariQueue()
    }

    private fun initAffariQueue() {
        //事务加入队列
        affairQueue.offer(JUMPER_STATE_CHECK)
        affairQueue.offer(JUMPER_STATE_PARSE)
        affairQueue.offer(JUMPER_STATE_REMIND)
        affairQueue.offer(JUMPER_STATE_UPDATE)
        affairQueue.offer(JUMPER_STATE_INSTALL)
    }

    /**
     * 获取 版本信息类
     * @return J?
     */
    fun get(): J? = jumpInfo.jump

    /**
     * 执行一系列的更新操作
     */
    fun jump() {
        callback.next()
    }

    private fun check() {
        //执行回调
        onCheckStart?.invoke()
        checker.check(jumpInfo,httpRequest,callback)
    }

    internal fun failed(state: Int,err: String) {
        onFailed?.invoke(state,err)
    }

    /**===================================================================

    @Description 状态监听器

    ===================================================================**/
    fun onCheckStart(function: () -> Unit): Jumper<J> {
        onCheckStart = function
        return this
    }

    fun onParseStart(function: () -> Unit): Jumper<J> {
        onParseStart = function
        return this
    }
    fun onParseCompleted(function: (jump:J?) -> Unit): Jumper<J> {
        onParseCompleted = function
        return this
    }
    fun onUpdateStart(function: () -> Unit): Jumper<J> {
        onUpdateStart = function
        return this
    }

    fun onInstallStart(function: () -> Unit): Jumper<J> {
        onInstallStart = function
        return this
    }

    fun onFailed(function: (state: Int, err: String) -> Unit): Jumper<J> {
        onFailed = function
        return this
    }


    /**===================================================================

    @Description 私有方法区

    ===================================================================**/
    /**
     * 执行解析操作
     */
    private fun parse(response: String) {
        onParseStart?.invoke()

        //解析是否成功
        paser.parser(response,jumpInfo.clazz,callback)
        onParseCompleted?.invoke(jumpInfo.jump)

    }
    private fun update() {
        updater.update(jumpInfo,httpRequest,callback)
    }
    private fun remind(any: J) {
        jumpInfo.jump=any
        if (context==null){
            throw Exception("Context not null")
        }
        if (context is Activity) {
            reminder.showRemind(context,jumpInfo,updater,httpRequest,callback)
        }else{
            throw Exception("Context not Activity Context")
        }
    }


    /**
     * 安装
     */
    private fun install() {
        onInstallStart?.invoke()
        installer.install(jumpInfo,callback)
    }



    inner class OnJumperCallBack: JumperAffairCallBack {
        override fun next(any:Any?) {
            //下一个队列
            //弹出
            when (affairQueue.poll()){
                JUMPER_STATE_CHECK -> check()
                JUMPER_STATE_PARSE -> parse(any?.toString()?:"")
                JUMPER_STATE_REMIND -> remind(any!! as J)
                JUMPER_STATE_UPDATE-> update()
                JUMPER_STATE_INSTALL -> install()
            }
        }

        override fun error(throwable: Throwable) {
            errorProcessor.process(throwable)

        }

    }


}