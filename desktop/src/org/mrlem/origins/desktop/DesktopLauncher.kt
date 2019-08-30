package org.mrlem.origins.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.mrlem.origins.OriginsGame

object DesktopLauncher {

    @JvmStatic
    fun main(arg: Array<String>) {
        LwjglApplication(OriginsGame(), LwjglApplicationConfiguration())
    }

}
