package moe.quill.espr.core.engine.commands

import moe.quill.espr.core.engine.GameManager
import moe.quill.espr.core.engine.GameState
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class Gamestate(val gameManager: GameManager) : CommandExecutor{


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player) return true
        if(args.isEmpty()) {
            //run default command
            sender.sendMessage(Component.text("Game state is currently: %s".format(gameManager.state)))
        }
        try {
            gameManager.changeState(GameState.valueOf(args[0].uppercase()))
        }catch(e : IllegalArgumentException){
            sender.sendMessage(Component.text("Not a valid Game State!").color(NamedTextColor.RED))
        }
        return true
    }

}