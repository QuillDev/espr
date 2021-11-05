package moe.quill.espr.core.mine.tasks

import moe.quill.espr.core.mine.MineManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class MineRestoreTask(private val mineManager: MineManager) : BukkitRunnable() {
    override fun run() {

        mineManager.mines.forEach { mine ->
            mine.selection.getBlocks().forEach block@{ block ->
                if (block.type != Material.AIR) return@block
                if (Random.nextDouble() < .8) return@block

                for (player in Bukkit.getOnlinePlayers()) {
                    if (player.location.distance(block.location) < 2.1) return@block
                }
                block.type = mine.getRandomType()
            }
        }
    }
}