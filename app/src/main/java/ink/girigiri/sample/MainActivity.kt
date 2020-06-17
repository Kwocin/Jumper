package ink.girigiri.sample

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import ink.girigiri.lib.JumperConfiger
import ink.girigiri.lib.callback.ReminderCallBack
import ink.girigiri.lib.entity.Jump
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.proxy.IJumpReminderProxy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        JumperConfiger.Builder()
            .init(application)
            .url("http://192.168.3.6:9090/version")
            .reminder(object : IJumpReminderProxy {
                override fun <J : IJump> showRemind(jump: J?, callBack: ReminderCallBack) {
                    AlertDialog.Builder(this@MainActivity)
                        .setMessage(jump?.updateContent)
                        .setTitle("Update")
                        .setPositiveButton("update",{ dialog, which ->
                            callBack.next()
                        })
                        .setNegativeButton("cancel",{d,w ->
                            callBack.cancel()
                        })
                        .show()
                }
            })
            .build()

        JumperConfiger
            .get(Jump().javaClass)?.onCheckStart {
                Log.i("Jumper","onCheckStart()")
            }?.onParseStart {
                Log.i("Jumper","onParseStart()")
            }?.onParseCompleted {
                Log.i("Jumper","onParseCompleted():"+it.toString())
            }?.onUpdateStart {
                Log.i("Jumper","onUpdateStart()")
            }?.onInstallStart {
                Log.i("Jumper","onInstallStart()")
            }?.onFailed { state, err ->
                Log.i("Jumper","onFailed():"+err)
            }?.jump()
    }
}