package ink.girigiri.lib.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File


class InstallUtils {



    companion object{
        init {
            System.loadLibrary("native-lib")
        }
        @JvmStatic
        fun installApk(context: Context,path:String){
            val intent = Intent(Intent.ACTION_VIEW)
            val file = File(path)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val apkUri: Uri = FileProvider.getUriForFile(
                    context,
                    context.packageName + ".updateFileProvider",
                    file
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            } else {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val uri: Uri = Uri.fromFile(file)
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
            }
            context.startActivity(intent)
        }
        external fun installApkForBspatch(oldApk:String,out:String,patch:String)
    }
}