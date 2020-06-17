package ink.girigiri.lib.net

import androidx.annotation.MainThread
import ink.girigiri.lib.inf.IJumpHttpRequest
import ink.girigiri.lib.proxy.IJumpCheckerProxy
import ink.girigiri.lib.utils.ThreadChanger
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ThreadPoolExecutor


class DefaultJumpHttpRequest() : IJumpHttpRequest {
    val timeOut = 5000
    val requestMethod = "GET"

    override fun request(urlStr: String, params: Map<String, Any>,checker: IJumpCheckerProxy){
        //子线程
        ThreadChanger.io {
            var result = StringBuilder()
            var input:InputStream?=null
            var reader: BufferedReader?=null
            try {
                var url = URL(getUrl(urlStr, params))
                var conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = timeOut
                conn.readTimeout = timeOut
                conn.requestMethod = requestMethod
                //返回输入流
                input = conn.getInputStream()
                //读取输入流

                reader = BufferedReader(InputStreamReader(input))
                var line:String?

                while (reader.readLine().also { line=it }!=null) {
                    result.append(line)
                }
            }catch (e:Exception){
                e.printStackTrace()
                ThreadChanger.main {
                    checker.failed(e.message?:"")
                }
            }finally {
                input?.close()
                reader?.close()
            }
            ThreadChanger.main {
                checker.completed(result.toString())
            }
        }


    }

    fun getUrl(urlStr: String, params: Map<String, Any>): String {
        var sb = urlStr
        sb.plus("?")
        var iterator = params.iterator()
        while (iterator.hasNext()) {
            sb.plus(iterator.next().key)
                .plus("=")
                .plus(iterator.next().value)
                .plus("&")
        }
        sb = sb.slice(0..sb.length - 1)
        sb.plus(urlStr)
        return sb
    }
}