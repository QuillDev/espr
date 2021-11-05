package moe.quill.espr.core.utility.BossBars

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class MatchTimer(
    val bossBarManager: BossBarManager,
    var time: Long
) : BukkitRunnable() {

    var bossBar = BossBar.bossBar(Component.text(), 1f, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS)

    init {
        Bukkit.getServer().showBossBar(bossBar)
    }

    val endTime = System.currentTimeMillis() + time

    override fun run() {
        var now = System.currentTimeMillis()
        var delta = endTime - now
        var progress = delta / time.toFloat()
        if (progress > 1) {
            cancel()
            return
        }
        bossBar.progress(progress)
        bossBar.name(Component.text("Remaining time: %s".format(delta)))
        bossBarManager.registerBossBar(bossBar)
    }
}