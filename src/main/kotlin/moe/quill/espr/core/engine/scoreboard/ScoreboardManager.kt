package moe.quill.espr.core.engine.scoreboard

import moe.quill.espr.core.engine.lambda.BukkitLambda
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin

class ScoreboardManager(plugin: Plugin) : Listener {

    val boards = mutableListOf<MGScoreboard>()

    init {
        BukkitLambda { boards.forEach { it.update() } }.runTaskTimer(plugin, 0, 5)
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onJoin(event: PlayerJoinEvent) {
        boards.forEach {
            if (!it.viewers.contains(event.player.uniqueId)) return@forEach
            it.addViewer(event.player.uniqueId)
        }
    }
}