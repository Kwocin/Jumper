package ink.girigiri.lib.entity

import ink.girigiri.lib.inf.IJump
import kotlin.collections.HashMap

data class JumpInfo<J:IJump>(
    var clazz: Class<J>,
    var url: String,
    var params: HashMap<String, Any>,
    var path:String,
    var autoInstall:Boolean,
    var downloadOnBackground:Boolean
) {
     var jump:J? = null

}