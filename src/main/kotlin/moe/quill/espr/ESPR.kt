package moe.quill.espr;

import moe.quill.espr.core.mine.MineManager
import moe.quill.espr.core.mine.mechanics.BlockPointSpawner
import moe.quill.espr.core.mine.mechanics.minedata.MaterialData
import moe.quill.espr.core.mine.mechanics.minedata.MineBase
import moe.quill.espr.core.mine.mechanics.minedata.MineConfig
import moe.quill.espr.core.mine.mechanics.minedata.MineDataStore
import moe.quill.espr.devtools.select.SelectModule
import moe.quill.espr.devtools.select.SelectionConfigData
import moe.quill.espr.devtools.select.data.NamedSelection
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class ESPR : JavaPlugin() {

    override fun onEnable() {
        logger.info("Starting $name!")

        ConfigurationSerialization.registerClass(SelectionConfigData::class.java)
        ConfigurationSerialization.registerClass(NamedSelection::class.java)
        ConfigurationSerialization.registerClass(MineBase::class.java)
        ConfigurationSerialization.registerClass(MaterialData::class.java)
        ConfigurationSerialization.registerClass(MineBase::class.java)
        ConfigurationSerialization.registerClass(MineConfig::class.java)
        ConfigurationSerialization.registerClass(MineDataStore::class.java)

        val selectModule = SelectModule(this)
        val mines = MineManager(this, selectModule)

        registerListener(BlockPointSpawner(this, mines.dataStore))
    }

    fun registerListener(vararg listener: Listener) {
        listener.forEach { server.pluginManager.registerEvents(it, this) }
    }

    fun unregisterListener(vararg listener: Listener) {
        listener.forEach { HandlerList.unregisterAll(it) }
    }
}
