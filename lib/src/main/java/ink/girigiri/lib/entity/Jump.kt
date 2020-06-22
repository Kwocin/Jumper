package ink.girigiri.lib.entity

import ink.girigiri.lib.inf.IJump

/**
 * 版本信息实例类
 * @property versionCode String?    版本号
 * @property updateContent String?  更新内容
 * @property versionPack VersionPack?   更新包信息类
 * @property updateDate String?     更新日期
 * @property isLastVersion Boolean? 是否是最新版本
 * @property isDiffPack Boolean?    是否是差分包

 */
data class Jump(
    override var code:String?=null,
    override var msg:String?=null,
    override var apkName: String?=null,
    override var versionCode: String?=null,
    override var updateContent: String?=null,
    override var updateDate: String?=null,
    override var isLastVersion: Boolean?=null,
    override var isDiffPack: Boolean?=null,
    override var downloadUrl: String?=null
) :IJump{


}