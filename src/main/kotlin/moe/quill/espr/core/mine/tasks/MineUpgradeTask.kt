package moe.quill.espr.core.mine.tasks

import moe.quill.espr.core.mine.MineManager
import org.bukkit.Material
import org.bukkit.scheduler.BukkitRunnable

class MineUpgradeTask(private val mineManager: MineManager, private val upgradeTime: Long) : BukkitRunnable() {

    private var nextUpgrade = System.currentTimeMillis()

    private var upgradeIndex = 0
    private val upgrades = arrayListOf(Material.IRON_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE)


    override fun run() {
        if (System.currentTimeMillis() < nextUpgrade) return
        if (upgradeIndex >= upgrades.size) return

        mineManager.mineMap.values.forEach { mine -> mine.types.add(upgrades[upgradeIndex]) }
        upgradeIndex++
        nextUpgrade = System.currentTimeMillis() + upgradeTime
    }
}