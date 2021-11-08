package moe.quill.espr.core.teams

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class Team : ForwardingAudience {

    //Members
    val members = hashSetOf<UUID>()

    //Scoring
    var score = 0
    var points = 0

    //Upgrade Tiers
    var pickTier = 1
    var qualityTier = 1
    var drillTier = 1

    fun onlineMembers(): List<Player> {
        return members.mapNotNull { Bukkit.getPlayer(it) }
    }

    fun modifyPoints(amount: Int) {
        points += amount

        if (amount > 0) {
            score += amount
        }
    }

    override fun audiences(): MutableIterable<Audience> {
        return onlineMembers().toMutableList()
    }
}