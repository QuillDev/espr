package moe.quill.espr.core.mine

import moe.quill.espr.core.mine.commands.MineCreate
import moe.quill.espr.core.mine.tasks.MineRestoreTask
import moe.quill.espr.core.mine.tasks.MineUpgradeTask
import moe.quill.espr.devtools.config.YamlConfig
import moe.quill.espr.devtools.select.SelectModule
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files

class MineManager(plugin: JavaPlugin, selectionModule: SelectModule) :
    YamlConfig<MineData>(plugin, "mines.yml") {

    val mineMap = HashMap<String, Mine>()

    init {

        if (!Files.exists(path)) {
            write(MineData())
        }
        //Setup the mines
        load().mines.forEach {
            mineMap[it.name] = it
            it.setup()
        }

        plugin.getCommand("minecreate")?.setExecutor(MineCreate(selectionModule, this))

        MineRestoreTask(this).runTaskTimer(plugin, 0, 10)
        MineUpgradeTask(this, 10000).runTaskTimer(plugin, 0, 10)

    }

    fun addMine(mine: Mine) {
        mineMap[mine.name] = mine
        update()
    }

    fun removeMine(name: String) {
        mineMap.remove(name)
        update()
    }

    private fun update() {
        write(get())
    }

    override fun get(): MineData {
        return MineData(mineMap.values.toList())
    }
}