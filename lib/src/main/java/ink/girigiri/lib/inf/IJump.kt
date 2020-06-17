package ink.girigiri.lib.inf

import ink.girigiri.lib.entity.VersionPack

/**
 * 版本信息实例类
 * @property versionCode String?    版本号
 * @property updateContent String?  更新内容
 * @property versionPack VersionPack?   更新包信息类
 * @property updateDate String?     更新日期
 * @property isLastVersion Boolean? 是否是最新版本
 * @property isDiffPack Boolean?    是否是差分包

 */
interface IJump{
    var versionCode:String?
    var updateContent:String?
    var versionPack: VersionPack?
    var updateDate:String?
    var isLastVersion:Boolean?
    var isDiffPack:Boolean?

}