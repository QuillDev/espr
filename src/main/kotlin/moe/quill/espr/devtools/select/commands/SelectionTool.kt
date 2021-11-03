package moe.quill.espr.devtools.select.commands

import moe.quill.espr.devtools.select.SelectModule
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SelectionTool(private val selectionModule: SelectModule) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true
        sender.inventory.addItem(selectionModule.tool)
        return true
    }
}