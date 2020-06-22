package ink.girigiri.lib.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import ink.girigiri.lib.R
import ink.girigiri.lib.callback.JumperAffairCallBack
import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.inf.IJumpHttpRequest
import ink.girigiri.lib.proxy.IJumpErrorProxy
import ink.girigiri.lib.proxy.IJumpUpdaterProxy

class DefaultDialogFragment : DialogFragment() {
    private  var updateContent:TextView?=null
    private  var versionCode:TextView?=null
    private  var cancel:TextView?=null
    private  var updateBtn:Button?=null
    private lateinit var jumpInfo:JumpInfo<*>
    private lateinit var updaterProxy:IJumpUpdaterProxy
    private lateinit var httpRequest: IJumpHttpRequest
    private lateinit var callBack: JumperAffairCallBack
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.default_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initData()
        initListener()

    }
    private fun initView(view: View) {
        updateBtn = view?.findViewById(R.id.updateBtn)
        cancel = view?.findViewById(R.id.cancel)
        versionCode = view?.findViewById(R.id.versionCode)
        updateContent = view?.findViewById(R.id.updateContent)
    }
    private fun initData() {
        updateContent?.setText(jumpInfo.jump?.updateContent)
        versionCode?.setText(jumpInfo.jump?.versionCode)

    }

    private fun initListener() {
        updateBtn?.setOnClickListener {
            //显示进度条
            callBack.next()
        }
        cancel?.setOnClickListener {
            this.dismiss()

        }
    }



    fun <J:IJump> show(fragmentManager: FragmentManager,jumpInfo: JumpInfo<J>,updaterProxy: IJumpUpdaterProxy,httpRequest: IJumpHttpRequest,callBack: JumperAffairCallBack){
        this.jumpInfo=jumpInfo
        this.updaterProxy=updaterProxy
        this.httpRequest=httpRequest
        this.callBack=callBack
        this.show(fragmentManager,"updateDialog")

    }
}