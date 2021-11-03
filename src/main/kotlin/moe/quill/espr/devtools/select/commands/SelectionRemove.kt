package moe.quill.espr.devtools.select.commands

import moe.quill.espr.devtools.select.SelectModule
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class SelectionRemove(private val selectionModule: SelectModule) : CommandExecutor, TabCompleter {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) return true

        if (args.size < 1) {
            sender.sendMessage(Component.text("You must specify a name"))
            return true
        }

        selectionModule.getPlayerSelection(sender.uniqueId)?.let {
            selectionModule.removeSelection(args[0])
            sender.sendMessage("Removed selection with name ${args[0]}")
        } ?: run { sender.sendMessage("Could not find a zone with the name ${args[0]}") }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (args.size == 1) {
            return selectionModule.selections.keys.filter { it.contains(args[0]) }.toMutableList()
        }

        return null
    }
}