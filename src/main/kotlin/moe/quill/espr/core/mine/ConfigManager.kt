package moe.quill.espr.core.mine

import moe.quill.espr.core.mine.mechanics.minedata.GameConfig
import moe.quill.espr.core.mine.mechanics.minedata.MineDataStore
import moe.quill.espr.core.mine.tasks.MineRestoreTask
import moe.quill.espr.devtools.config.YamlConfig
import moe.quill.espr.devtools.select.SelectModule
import moe.quill.espr.devtools.select.data.Selection
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files
import kotlin.random.Random

class ConfigManager(plugin: JavaPlugin, selectionModule: SelectModule) :
    YamlConfig<GameConfig>(plugin, "mines.yml") {

    val mines = mutableListOf<Mine>()
    private val config: GameConfig
    val dataStore: MineDataStore

    val pickaxeUpgradeZones = mutableListOf<Selection>()
    val drillUpgradeZones = mutableListOf<Selection>()
    val qualityUpgradeZones = mutableListOf<Selection>()


    init {
        if (!Files.exists(path)) {
            write(GameConfig.defaultInstance())
        }

        this.config = load()
        this.dataStore = config.datastore

        //Set up the mines
        config.mineBases.forEach {
            val selection = selectionModule.getSelection(it.selectionName) ?: return@forEach
            Bukkit.getLogger().info("Added mine from selection " + selection.name)
            mines.add(Mine(selection, config.datastore, it.startLevel))
        }


        //Add shop upgrade locations
        drillUpgradeZones.addAll(config.drillUpgradeZones.mapNotNull { selectionModule.getSelection(it) })
        qualityUpgradeZones.addAll(config.qualityUpgradeZones.mapNotNull { selectionModule.getSelection(it) })
        pickaxeUpgradeZones.addAll(config.pickaxeUpgradeZones.mapNotNull { selectionModule.getSelection(it) })

        MineRestoreTask(this).runTaskTimer(plugin, 0, 10)
    }

    override fun get(): GameConfig {
        return config
    }
}