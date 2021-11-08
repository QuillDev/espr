package moe.quill.espr.core.mine.mechanics

import moe.quill.espr.core.mine.mechanics.hologram.HologramUtil
import moe.quill.espr.core.mine.mechanics.minedata.MineDataStore
import moe.quill.espr.core.teams.TeamManager
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.Plugin

class BlockPointSpawner(
    private val plugin: Plugin,
    private val dataStore: MineDataStore,
    private val teamManager: TeamManager
) : Listener {

    private val colors = listOf(
        NamedTextColor.GRAY,
        NamedTextColor.GREEN,
        NamedTextColor.AQUA,
        NamedTextColor.LIGHT_PURPLE,
        NamedTextColor.DARK_PURPLE,
        NamedTextColor.GOLD,
        NamedTextColor.RED
    )

    private val checkFaces = listOf(
        BlockFace.UP,
        BlockFace.DOWN,
        BlockFace.NORTH,
        BlockFace.SOUTH,
        BlockFace.EAST,
        BlockFace.WEST,
        BlockFace.SELF
    )

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {

        val team = teamManager.getTeam(event.player.uniqueId) ?: return
        val data = dataStore.blockMappings[event.block.type] ?: return
        val points = data.value
        val color = if (points >= colors.size) colors[colors.size - 1] else colors[points]


        var totalPoints = 0
        val validBlocks = getVein(event.block, mutableSetOf())
        validBlocks.forEach {
            it.type = Material.AIR
            event.player.playSound(Sound.sound(Key.key("block.amethyst_block.break"), Sound.Source.AMBIENT, .6f, 1f))
            HologramUtil.spawnEpemeralHologram(
                plugin,
                it.location.clone().subtract(-0.5, 0.0, -0.5),
                Component.text("+")
                    .append(Component.text(points))
                    .color(color)
            )
            totalPoints += points
            //TODO: Remove
            totalPoints += 5000
        }

        team.modifyPoints(totalPoints)

        val message = Component.text()
            .append(Component.text("Broke a chain of ").color(NamedTextColor.YELLOW))
            .append(
                Component.text("x").append(Component.text(validBlocks.size)).color(NamedTextColor.GOLD)
                    .decorate(TextDecoration.BOLD)
            )
            .append(Component.text(" for ").color(NamedTextColor.YELLOW))
            .append(
                Component.text(totalPoints).append(Component.text("pts")).color(NamedTextColor.RED)
                    .decorate(TextDecoration.BOLD)
            ).build()

        event.player.sendActionBar(message)
        if (validBlocks.size > 50) {
            Bukkit.getServer().sendMessage(
                event.player.displayName().color(NamedTextColor.RED)
                    .append(Component.space())
                    .append(message)
            )
        }
    }

    fun getVein(origin: Block, accumulator: MutableSet<Block>): Set<Block> {
        val validNodes = getValidFaces(origin, accumulator).toMutableSet()

        while (validNodes.isNotEmpty()) {
            validNodes.toSet().forEach {
                validNodes.remove(it)
                validNodes.addAll(getValidFaces(it, accumulator))
            }
        }

        return accumulator
    }

    fun getValidFaces(origin: Block, accumulator: MutableSet<Block>): Set<Block> {
        val faces = checkFaces.map { origin.getRelative(it) }
            .filter { it.type == origin.type }
            .filter { !accumulator.contains(it) }
            .toSet()

        accumulator.addAll(faces)

        return faces
    }
}