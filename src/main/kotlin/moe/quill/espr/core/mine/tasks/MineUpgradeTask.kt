package moe.quill.espr.core.mine.tasks

import moe.quill.espr.core.mine.ConfigManager
import org.bukkit.scheduler.BukkitRunnable

class MineUpgradeTask(private val configManager: ConfigManager, private val upgradeTime: Long) : BukkitRunnable() {

    private var nextUpgrade = System.currentTimeMillis()

    override fun run() {
        if (System.currentTimeMillis() < nextUpgrade) return
        configManager.mines.forEach { it.level++ }
        nextUpgrade = System.currentTimeMillis() + upgradeTime
    }
}