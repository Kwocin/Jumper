package ink.girigiri.lib.proxy.impl

import android.os.Build
import ink.girigiri.lib.JumperConfiger
import ink.girigiri.lib.callback.JumperAffairCallBack
import ink.girigiri.lib.entity.JumpInfo
import ink.girigiri.lib.inf.IJump
import ink.girigiri.lib.proxy.IJumpInstallerProxy
import ink.girigiri.lib.utils.InstallUtils
import java.io.File

class BaseJumpInstaller : IJumpInstallerProxy {
    override fun <J : IJump> install(jumpInfo: JumpInfo<J>, callBack: JumperAffairCallBack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //是否有安装位置来源的权限

            //是否有安装位置来源的权限
            val haveInstallPermission: Boolean =
                JumperConfiger.application!!.packageManager.canRequestPackageInstalls()
            if (haveInstallPermission) {
                if (jumpInfo.jump?.isDiffPack == true) {
                    var oldApk = JumperConfiger.application!!.applicationInfo.sourceDir
                    var patch = jumpInfo.path + File.separator + jumpInfo.jump?.apkName
                    var out = jumpInfo.path + File.separator + "cache.apk"
                    InstallUtils.installApkForBspatch(oldApk,out,patch)
                    InstallUtils.installApk(JumperConfiger.application!!, out)
                } else {
                    InstallUtils.installApk(
                        JumperConfiger.application!!,
                        jumpInfo.path + File.separator + jumpInfo.jump?.apkName
                    )
                }
            } else {
                //提醒需要安装未知来源应用

            }
        } else {
            InstallUtils.installApk(JumperConfiger.application!!, jumpInfo.path)
        }
    }

}