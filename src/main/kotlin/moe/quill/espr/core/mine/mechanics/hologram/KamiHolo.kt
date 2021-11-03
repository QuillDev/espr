package moe.quill.espr.core.mine.mechanics.hologram

import org.bukkit.entity.AreaEffectCloud
import org.bukkit.scheduler.BukkitRunnable

class KamiHolo(private val entity: AreaEffectCloud) : BukkitRunnable() {

    override fun run() {
        entity.remove()
        cancel()
    }
}