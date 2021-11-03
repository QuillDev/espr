package moe.quill.espr.devtools.select.commands

import moe.quill.espr.devtools.select.SelectModule
import moe.quill.espr.devtools.select.data.NamedSelection
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SelectionSave(private val selectionModule: SelectModule) : CommandExecutor {

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

        selectionModule.getPlayerSelection(sender.uniqueId)?.let { selection ->
            selectionModule.addSelection(NamedSelection(args[0], selection.minLoc, selection.maxLoc))
            sender.sendMessage("Added zone with name ${args[0]}")
        } ?: run { sender.sendMessage("You do not have a zone selected!") }

        return true
    }
}