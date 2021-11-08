package moe.quill.espr;

import moe.quill.espr.core.MessageListener
import moe.quill.espr.core.engine.GameManager
import moe.quill.espr.core.engine.commands.Gamestate
import moe.quill.espr.core.engine.scoreboard.ScoreboardManager
import moe.quill.espr.core.mine.ConfigManager
import moe.quill.espr.core.mine.mechanics.BlockPointSpawner
import moe.quill.espr.core.mine.mechanics.minedata.MaterialData
import moe.quill.espr.core.mine.mechanics.minedata.MineBase
import moe.quill.espr.core.mine.mechanics.minedata.GameConfig
import moe.quill.espr.core.mine.mechanics.minedata.MineDataStore
import moe.quill.espr.core.shops.ShopListener
import moe.quill.espr.core.teams.TeamManager
import moe.quill.espr.core.utility.BlockDecayListener
import moe.quill.espr.core.utility.bars.BossBarListener
import moe.quill.espr.core.utility.bars.BossBarManager
import moe.quill.espr.devtools.select.SelectModule
import moe.quill.espr.devtools.select.SelectionConfigData
import moe.quill.espr.devtools.select.data.NamedSelection
import org.bukkit.configuration.serialization.ConfigurationSerialization
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
        ConfigurationSerialization.registerClass(GameConfig::class.java, "GameConfig")
        ConfigurationSerialization.registerClass(MineDataStore::class.java)

        val selectModule = SelectModule(this)
        val config = ConfigManager(this, selectModule)

        val bossBarManager = BossBarManager()
        val boardManager = ScoreboardManager(this)
        val teamManager = TeamManager(boardManager)
        val gameManager = GameManager(this, bossBarManager, teamManager)

        registerListener(
            BossBarListener(bossBarManager),
            BlockPointSpawner(this, config.dataStore, teamManager),
            BlockDecayListener(),
            ShopListener(this, config, teamManager)
        )
        getCommand("gamestate")?.setExecutor(Gamestate(gameManager))

        server.messenger.registerIncomingPluginChannel(this, "BungeeCord", MessageListener(teamManager, gameManager))
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")
    }

    fun registerListener(vararg listener: Listener) {
        listener.forEach { server.pluginManager.registerEvents(it, this) }
    }
}
