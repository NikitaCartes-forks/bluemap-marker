package de.miraculixx.bmm

import de.bluecolored.bluemap.api.BlueMapAPI
import de.miraculixx.bmm.api.APIConnector
import de.miraculixx.bmm.map.MarkerManager
import de.miraculixx.bmm.utils.Settings
import de.miraculixx.bmm.utils.message.*
import de.miraculixx.bmm.utils.settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import java.io.File
import java.util.function.Consumer

class BlueMap(sourceFolder: File, version: Int) {
    private val onEnable = Consumer<BlueMapAPI> {
        consoleAudience.sendMessage(prefix + cmp("Connect to BlueMap API..."))
        val configFile = File(sourceFolder, "settings.json")
        settings.apply {
            val s = json.decodeFromString<Settings>(configFile.takeIf { f -> f.exists() }?.readText()?.ifBlank { "{}" } ?: "{}")
            language = s.language
        }
//        Localization(File(sourceFolder, "language"), settings.language, listOf(), prefix)
        MarkerManager.loadAllMarker(it, sourceFolder)
        consoleAudience.sendMessage(prefix + cmp("Successfully enabled Marker Command addition!"))
        CoroutineScope(Dispatchers.Default).launch {
            APIConnector.checkVersion(version)
        }
    }

    private val onDisable = Consumer<BlueMapAPI> {
        consoleAudience.sendMessage(prefix + cmp("Disconnecting from BlueMap API..."))
        MarkerManager.saveAllMarker(sourceFolder)
        consoleAudience.sendMessage(prefix + cmp("Successfully saved all data. Waiting for BlueMap to reload..."))
    }

    fun disable() {
        BlueMapAPI.unregisterListener(onDisable)
        BlueMapAPI.unregisterListener(onEnable)
    }

    init {
        BlueMapAPI.onEnable(onEnable)
        BlueMapAPI.onDisable(onDisable)
    }
}