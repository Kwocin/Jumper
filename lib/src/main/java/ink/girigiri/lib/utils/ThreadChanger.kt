package ink.girigiri.lib.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors


class ThreadChanger {

    companion object{
        private var ioThreadPool=Executors.newSingleThreadExecutor()
        private var handler=Handler(Looper.getMainLooper())
        fun io(function:()->Unit){
            ioThreadPool.execute(function)
        }
        fun main(function:()->Unit){
            handler.post(function)
        }
    }

}