package moe.quill.espr.core.mine.mechanics

import moe.quill.espr.core.mine.mechanics.hologram.HologramUtil
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.Plugin

class BlockPointSpawner(private val plugin: Plugin) : Listener {

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {

        event.player.playSound(Sound.sound(Key.key("block.amethyst_block.break"), Sound.Source.AMBIENT, .6f, 1f))
        HologramUtil.spawnKamikazeHologram(
            plugin,
            event.block.location.clone().add(0.0, 1.0, 0.0),
            Component.text("+1").color(NamedTextColor.GREEN)
        )

    }
}