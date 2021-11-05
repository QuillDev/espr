package moe.quill.espr.core.mine.mechanics.hologram

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.AreaEffectCloud
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.plugin.Plugin

class HologramUtil {

    companion object {
        @JvmStatic
        fun spawnHologram(location: Location, name: Component): AreaEffectCloud {
            //Spawn the entity
            return location.world.spawnEntity(
                location,
                EntityType.AREA_EFFECT_CLOUD,
                CreatureSpawnEvent.SpawnReason.COMMAND
            ) {
                //Apply the attributes we want
                val cloud = it as AreaEffectCloud
                cloud.customName(name)
                cloud.isCustomNameVisible = true
                cloud.ticksLived = Int.MAX_VALUE
                cloud.setParticle(Particle.BLOCK_CRACK, Bukkit.createBlockData(Material.AIR))
            } as AreaEffectCloud
        }

        @JvmStatic
        fun spawnEpemeralHologram(
            plugin: Plugin,
            location: Location,
            name: Component
        ): AreaEffectCloud {
            val hologram = spawnHologram(location, name)
            EphemeralHologram(hologram).runTaskLater(plugin, 15)
            return hologram
        }
    }
}