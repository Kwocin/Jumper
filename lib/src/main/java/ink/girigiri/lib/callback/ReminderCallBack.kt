package ink.girigiri.lib.callback

interface ReminderCallBack{
    //更新
    fun next()
    //不更新
    fun cancel()
}