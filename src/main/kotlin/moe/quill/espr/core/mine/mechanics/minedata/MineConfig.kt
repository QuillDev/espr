package moe.quill.espr.core.mine.mechanics.minedata

import org.bukkit.Material

//TODO: Load the data from some config file
class MineConfig {
    val data = MineDataStore(
        mapOf(
            Material.DEAD_BRAIN_CORAL_BLOCK to MaterialData(1, 60),
            Material.RAW_COPPER_BLOCK to MaterialData(2, 50),
            Material.RAW_IRON_BLOCK to MaterialData(2, 50),
            Material.GLOWSTONE to MaterialData(3, 45),
            Material.RAW_GOLD_BLOCK to MaterialData(4, 40),
            Material.DIAMOND_BLOCK to MaterialData(5, 25),
            Material.AMETHYST_BLOCK to MaterialData(6, 15)
        )
    )
}