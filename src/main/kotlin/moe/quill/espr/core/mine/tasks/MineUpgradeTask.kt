package moe.quill.espr.core.mine.tasks

import moe.quill.espr.core.mine.MineManager
import org.bukkit.scheduler.BukkitRunnable

class MineUpgradeTask(private val mineManager: MineManager, private val upgradeTime: Long) : BukkitRunnable() {

    private var nextUpgrade = System.currentTimeMillis()

    override fun run() {
        if (System.currentTimeMillis() < nextUpgrade) return
        mineManager.mines.forEach { it.level++ }
        nextUpgrade = System.currentTimeMillis() + upgradeTime
    }
}