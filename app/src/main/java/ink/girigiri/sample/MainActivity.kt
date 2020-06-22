package ink.girigiri.sample

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import ink.girigiri.lib.JumperConfiger

import ink.girigiri.lib.entity.Jump

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        JumperConfiger.Builder()
            .init(application)
            .url("http://192.168.3.50:9090/version")
            .build()

        JumperConfiger
            .get(this,Jump().javaClass)?.onCheckStart {
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