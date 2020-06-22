package ink.girigiri.lib.callback

interface DownLoadCallBack {

    fun progress(progress: Int)
    fun completed()
    fun failed(t:Throwable)
}