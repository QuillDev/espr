package moe.quill.espr.core.mine.commands

import moe.quill.espr.core.mine.Mine
import moe.quill.espr.core.mine.MineManager
import moe.quill.espr.devtools.select.SelectModule
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MineCreate(private val selectModule: SelectModule, private val mineManager: MineManager) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {

        if (sender !is Player) return true

        if (args.size < 3) {
            sender.sendMessage(Component.text("Proper syntax [mineName] [selectionName] [...materials]"))
            return true
        }

        val mineName = args[0]
        val selection = selectModule.getSelection(args[1]) ?: return true

        var materials = mutableListOf<Material>()

        args.copyOfRange(2, args.size).forEach { query ->
            try {
                materials.add(Material.valueOf(query.uppercase()))
            } catch (e: Exception) {
            }
        }

        materials = materials.filter { it.isBlock }.toMutableList()

        if (materials.isEmpty()) {
            sender.sendMessage("Could not create mine. supplied 0 valid materials!")
            return true
        }

        val newMine = Mine(mineName, selection, materials)
        mineManager.addMine(newMine)
        newMine.setup()

        sender.sendMessage(Component.text("Created mine with $mineName @ ${args[1]} of ${materials.joinToString(" ")}"))

        return true
    }
}