package ink.girigiri.lib.proxy

import ink.girigiri.lib.entity.Jumper
import ink.girigiri.lib.inf.IJump

interface IJumpUpdaterProxy {
    fun <J:IJump> update(jump: J?, jumper: Jumper<J>)
}