package ink.girigiri.lib.net

import android.os.FileUtils
import android.text.TextUtils
import androidx.annotation.MainThread
import ink.girigiri.lib.callback.DownLoadCallBack
import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest
import ink.girigiri.lib.proxy.IJumpCheckerProxy
import ink.girigiri.lib.utils.ThreadChanger
import java.io.*
import java.lang.Exception
import java.lang.NullPointerException
import java.net.HttpURLConnection
import java.net.URL


class DefaultJumpHttpRequest() : IJumpHttpRequest {
    val timeOut = 5000
    val requestMethod = "GET"
    var fileName:String=""
    override fun <J : IJump> check(jumpInfo: JumpInfo<J>, checker: IJumpCheckerProxy) {
        //子线程
        ThreadChanger.io {
            var result = StringBuilder()
            var input: InputStream? = null
            var reader: BufferedReader? = null
            try {
                var url = URL(getUrl(jumpInfo.url, jumpInfo.params))
                var conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = timeOut
                conn.readTimeout = timeOut
                conn.requestMethod = requestMethod
                if (conn.responseCode == 200) {
                    //返回输入流
                    input = conn.getInputStream()
                    //读取输入流

                    reader = BufferedReader(InputStreamReader(input))
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        result.append(line)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                ThreadChanger.main {
                    checker.failed(e)
                }
            } finally {
                input?.close()
                reader?.close()
            }
            if (!TextUtils.isEmpty(result)) {
                ThreadChanger.main {
                    checker.completed(result.toString())
                }
            }

        }
    }


    override fun <J : IJump> download(jumpInfo: JumpInfo<J>,callback: DownLoadCallBack) {
        ThreadChanger.io {
            var input: InputStream? = null
            var output: FileOutputStream? = null
            var url = URL(jumpInfo.jump?.downloadUrl)
            var fileName=jumpInfo.jump?.apkName
            try {
                var conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = timeOut
                conn.readTimeout = timeOut
                conn.setRequestProperty("Accept-Encoding", "identity");
                conn.requestMethod = requestMethod
                if (conn.responseCode == 200) {
                    input = conn.inputStream
                    if (input != null) {
                        var file = File(jumpInfo.path)
                        if (!file.exists()) {
                            file.mkdirs()
                        }
                        output = FileOutputStream(file.absolutePath + "/"+fileName)
                        var total = conn.contentLength

                        var buff = ByteArray(1024)
                        var sum: Long = 0
                        var len = 0
                        while (input.read(buff).apply { len = this } > 0) {
                            output.write(buff, 0, len)
                            sum += len.toLong()

                            val progress = ((sum * 1.0f / total) * 100).toInt()
                            ThreadChanger.main {
                                //回调进度
                                callback.progress(progress)
                            }
                        }
                        ThreadChanger.main {
                            //完成回调
                            callback.completed()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ThreadChanger.main {
                    //异常回调
                    callback.failed(e)
                }
            } finally {

                input.use {
                    it?.close()
                }
                output.use {
                    it?.close()
                }
            }
        }
    }

    private fun getFileName(downloadUrl: String?): Any? {
        if (downloadUrl!=null){
            if (downloadUrl.contains('?')){
                return  downloadUrl.substring(downloadUrl.lastIndexOf('/'),downloadUrl.lastIndexOf('?'))
            }else{
                return  downloadUrl.substring(downloadUrl.lastIndexOf('/'))
            }
        }
        return "cache.app"
    }


    fun getUrl(urlStr: String?, params: Map<String, Any>): String? {
        var sb = urlStr
        sb.plus("?")
        var iterator = params.iterator()
        while (iterator.hasNext()) {
            sb.plus(iterator.next().key)
                .plus("=")
                .plus(iterator.next().value)
                .plus("&")
        }
        sb = sb?.slice(0..sb.length - 1)
        sb.plus(urlStr)
        return sb
    }




}