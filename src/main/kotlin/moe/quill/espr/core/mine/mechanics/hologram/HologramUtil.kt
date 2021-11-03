package moe.quill.espr.core.mine.mechanics.hologram

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.AreaEffectCloud
import org.bukkit.entity.EntityType
import org.bukkit.plugin.Plugin
import org.bukkit.util.Vector

class HologramUtil {

    companion object {
        @JvmStatic
        fun spawnHologram(location: Location, name: Component): AreaEffectCloud {
            val hologram = location.world.spawnEntity(location, EntityType.AREA_EFFECT_CLOUD) as AreaEffectCloud
            hologram.customName(name)
            hologram.isCustomNameVisible = true
            hologram.ticksLived = Int.MAX_VALUE
            hologram.setParticle(Particle.BLOCK_CRACK, Bukkit.createBlockData(Material.AIR))
            return hologram
        }

        @JvmStatic
        fun spawnKamikazeHologram(
            plugin: Plugin,
            location: Location,
            name: Component
        ): AreaEffectCloud {
            val hologram = spawnHologram(location, name)
            KamiHolo(hologram).runTaskLater(plugin, 15)
            return hologram
        }
    }
}