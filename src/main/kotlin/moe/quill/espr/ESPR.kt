package moe.quill.espr;

import moe.quill.espr.core.engine.GameManager
import moe.quill.espr.core.engine.commands.Gamestate
import moe.quill.espr.core.mine.MineManager
import moe.quill.espr.core.mine.mechanics.BlockPointSpawner
import moe.quill.espr.core.mine.mechanics.minedata.MaterialData
import moe.quill.espr.core.mine.mechanics.minedata.MineBase
import moe.quill.espr.core.mine.mechanics.minedata.MineConfig
import moe.quill.espr.core.mine.mechanics.minedata.MineDataStore
import moe.quill.espr.core.teams.TeamManager
import moe.quill.espr.core.utility.BlockDecayListener
import moe.quill.espr.core.utility.BossBars.BossBarListener
import moe.quill.espr.core.utility.BossBars.BossBarManager
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

        val teamManager = TeamManager()
        val bossBarManager = BossBarManager()
        val gameManager = GameManager(this, bossBarManager, teamManager)

        registerListener(
            BossBarListener(bossBarManager),
            BlockPointSpawner(this, mines.dataStore),
            BlockDecayListener()
        )
        getCommand("gamestate")?.setExecutor(Gamestate(gameManager))

    }

    fun registerListener(vararg listener: Listener) {
        listener.forEach { server.pluginManager.registerEvents(it, this) }
    }

    fun unregisterListener(vararg listener: Listener) {
        listener.forEach { HandlerList.unregisterAll(it) }
    }
}
