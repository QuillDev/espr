package moe.quill.espr.core.mine

import moe.quill.espr.core.mine.mechanics.minedata.MineConfig
import moe.quill.espr.core.mine.mechanics.minedata.MineDataStore
import moe.quill.espr.core.mine.tasks.MineRestoreTask
import moe.quill.espr.devtools.config.YamlConfig
import moe.quill.espr.devtools.select.SelectModule
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files

class MineManager(plugin: JavaPlugin, selectionModule: SelectModule) :
    YamlConfig<MineConfig>(plugin, "mines.yml") {

    val mines = mutableListOf<Mine>()
    private val config: MineConfig
    val dataStore: MineDataStore

    init {
        if (!Files.exists(path)) {
            write(MineConfig.defaultInstance())
        }

        this.config = load()
        this.dataStore = config.datastore

        //Set up the mines
        config.mineBases.forEach {
            val selection = selectionModule.getSelection(it.selectionName) ?: return@forEach
            Bukkit.getLogger().info("Added mine from selection " + selection.name)
            mines.add(Mine(selection, config.datastore, it.startLevel))
        }

        MineRestoreTask(this).runTaskTimer(plugin, 0, 10)
    }

    private fun update() {
        write(get())
    }

    override fun get(): MineConfig {
        return config
    }
}