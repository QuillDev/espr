package moe.quill.espr.core.teams

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import java.util.*

open class Team(name: Component) : ForwardingAudience{
    val uuids = listOf<UUID>()
    val playerMap = mutableMapOf<UUID, Player>()

    fun registerPlayer(player: Player) {
        playerMap[player.uniqueId] = player
    }

    fun removePlayer(player: Player) {
        playerMap.remove(player.uniqueId)
    }

    override fun audiences(): MutableIterable<Audience> {
        return playerMap.values
    }

}