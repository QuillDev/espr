package moe.quill.espr.devtools.select

import moe.quill.espr.ESPR
import moe.quill.espr.devtools.config.YamlConfig
import moe.quill.espr.devtools.select.commands.SelectionRemove
import moe.quill.espr.devtools.select.commands.SelectionSave
import moe.quill.espr.devtools.select.commands.SelectionTool
import moe.quill.espr.devtools.select.data.NamedSelection
import moe.quill.espr.devtools.select.data.Selection
import moe.quill.espr.devtools.select.listener.SelectListener
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.nio.file.Files
import java.util.*

class SelectModule(plugin: ESPR) : YamlConfig<SelectionConfigData>(plugin, "tools", "selections.yml") {

    val tool: ItemStack
    val toolKey = NamespacedKey(plugin, "select-tool-key")

    val selectMap = HashMap<UUID, Selection>()
    val leftMap = HashMap<UUID, Location>()
    val rightMap = HashMap<UUID, Location>()

    val selections = HashMap<String, NamedSelection>()

    init {
        plugin.registerListener(SelectListener(this))

        if (!Files.exists(path)) {
            write(SelectionConfigData())
        }
        load().selections.forEach { this.selections[it.name] = it }


        plugin.getCommand("seladd")?.setExecutor(SelectionSave(this))
        plugin.getCommand("selrem")?.also {
            val removeCommand = SelectionRemove(this)
            it.setExecutor(removeCommand)
            it.tabCompleter = removeCommand
        }
        plugin.getCommand("seltool")?.setExecutor(SelectionTool(this))

        //Setup the tool object
        tool = ItemStack(Material.STICK)
        val tempMeta = tool.itemMeta
        tempMeta.persistentDataContainer.set(toolKey, PersistentDataType.STRING, "")
        tool.itemMeta = tempMeta

    }

    fun getPlayerSelection(uuid: UUID): Selection? {
        return selectMap[uuid]
    }

    fun getSelection(name: String): NamedSelection? {
        return selections[name]
    }

    fun addSelection(selection: NamedSelection) {
        selections[selection.name] = selection
        update()
    }

    fun removeSelection(name: String) {
        selections.remove(name)
        update()
    }

    private fun update() {
        write(SelectionConfigData(selections.values.toList()))
    }

    override fun get(): SelectionConfigData {
        TODO("Not yet implemented")
    }
}