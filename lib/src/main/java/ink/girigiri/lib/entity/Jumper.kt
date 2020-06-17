package ink.girigiri.lib.entity

import android.util.Log
import ink.girigiri.lib.callback.ReminderCallBack
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.proxy.*

/**
 * 真正去做一系列更新操作的类
 * @param J:IJump 版本信息
 * @property clazz Class<J> 版本信息实例类
 * @property checkerProxy IJumpChecker 检查器
 * @property paser IJumpParser 解析器
 * @property updaterProxy IJumpUpdater 更新器
 * @property installerProxy IJumpInstaller 安装器
 * @constructor
 */
class Jumper<out J : IJump>(
    private var url: String,
    private var params: Map<String, Any>,
    private var clazz: Class<J>,
    private var checker: IJumpCheckerProxy,
    private var paser: IJumpParserProxy,
    private var reminder: IJumpReminderProxy,
    private var updater: IJumpUpdaterProxy,
    private var installer: IJumpInstallerProxy

): ReminderCallBack {
    /**
     * 更新状态标识
     */
    companion object {
        val JUMPER_STATE_CHECKE = 0
        val JUMPER_STATE_PARSER = 1
        val JUMPER_STATE_UPDATE = 2
        val JUMPER_STATE_INSTALL = 3
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
    /**
     * 版本信息类
     */
    private var jump: J?

    /**
     * 初始化
     */
    init {
        jump = null
        onCheckStart=null
        onParseStart=null
        onParseCompleted=null
        onUpdateStart=null
        onInstallStart=null
        onFailed=null
    }

    /**
     * 获取 版本信息类
     * @return J?
     */
    fun get(): J? = jump

    /**
     * 执行一系列的更新操作
     */
    fun jump() {
        checker.check(url, params, this)
        //执行回调
        onCheckStart?.invoke()
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
    internal fun parse(response: String) {
        onParseStart?.invoke()
        jump = paser.parser(response, this,clazz)
        //解析是否成功
        onParseCompleted?.invoke(jump)
        remind()
    }
    internal fun remind(){
        reminder.showRemind(jump,this)
    }
    /**
     * 下载更新包
     *
     */
    override fun next() {
        onUpdateStart?.invoke()
        updater.update(jump, this)
    }

    override fun cancel() {
        Log.i("Jumper","cancel()")
    }

    /**
     * 安装
     */
    private fun install() {
        onInstallStart?.invoke()
    }




}