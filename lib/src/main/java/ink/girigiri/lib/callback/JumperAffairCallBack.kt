package ink.girigiri.lib.callback

interface JumperAffairCallBack {
    fun next(any:Any?=null)
    fun error(throwable: Throwable)
}