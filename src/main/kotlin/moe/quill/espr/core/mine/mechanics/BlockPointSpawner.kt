package moe.quill.espr.core.mine.mechanics

import moe.quill.espr.core.mine.mechanics.hologram.HologramUtil
import moe.quill.espr.core.mine.mechanics.minedata.MineDataStore
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.Plugin

class BlockPointSpawner(private val plugin: Plugin, private val dataStore: MineDataStore) : Listener {

    private val colors = listOf(
        NamedTextColor.GRAY,
        NamedTextColor.GREEN,
        NamedTextColor.AQUA,
        NamedTextColor.LIGHT_PURPLE,
        NamedTextColor.DARK_PURPLE,
        NamedTextColor.GOLD,
        NamedTextColor.RED
    )

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val data = dataStore.blockMappings[event.block.type] ?: return
        val points = data.value
        val color = if (points >= colors.size) colors[colors.size - 1] else colors[points]

        event.player.playSound(Sound.sound(Key.key("block.amethyst_block.break"), Sound.Source.AMBIENT, .6f, 1f))
        HologramUtil.spawnEpemeralHologram(
            plugin,
            event.block.location.clone().add(0.0, 1.0, 0.0),
            Component.text("+")
                .append(Component.text(points))
                .color(color)
        )

    }
}