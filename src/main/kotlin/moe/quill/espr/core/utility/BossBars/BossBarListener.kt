package moe.quill.espr.core.utility.BossBars

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class BossBarListener(val bossBarManager: BossBarManager): Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val player = event.player
        bossBarManager.bossbars.forEach { player.showBossBar(it) }
    }
}