package moe.quill.espr.core.utility.bars

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.text.SimpleDateFormat


class MatchTimer(
    private val bossBarManager: BossBarManager,
    private var time: Long
) : BukkitRunnable() {


    private var formatter = SimpleDateFormat("mm:ss")
    private var bossBar = BossBar.bossBar(Component.text(), 1f, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS)

    init {
        Bukkit.getServer().showBossBar(bossBar)
    }

    private val endTime = System.currentTimeMillis() + time

    override fun run() {
        val now = System.currentTimeMillis()
        val delta = endTime - now
        val progress = delta / time.toFloat()
        if (progress > 1) {
            cancel()
            return
        }
        bossBar.progress(progress)
        bossBar.name(Component.text("Remaining time: %s".format(formatter.format(delta))))
        bossBarManager.registerBossBar(bossBar)
    }
}