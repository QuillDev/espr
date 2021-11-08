package moe.quill.espr.core.shops

import moe.quill.espr.core.mine.ConfigManager
import moe.quill.espr.core.shops.gui.UpgradeMenu
import moe.quill.espr.core.teams.TeamManager
import moe.quill.espr.util.ItemBuilder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.plugin.Plugin

class ShopListener(
    private val plugin: Plugin,
    private val config: ConfigManager,
    private val teamManager: TeamManager
) : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.hand != EquipmentSlot.HAND) return
        val location = event.clickedBlock?.location ?: return

        val player = event.player
        val team = teamManager.getTeam(player.uniqueId) ?: return

        event.isCancelled = true
        config.pickaxeUpgradeZones.forEach {
            if (!it.isInBounds(location)) return@forEach
            UpgradeMenu(
                plugin,
                team,
                Component.text("Pickaxe Upgrades"),
                ItemBuilder(Material.GOLDEN_PICKAXE).build(),
                { team.pickTier },
                { team, int ->
                    team.pickTier = int
                    team.sendMessage(
                        Component.text("Upgraded Mining to tier $int").color(NamedTextColor.GREEN)
                    )
                    player.closeInventory()

                    //TODO: Process the pickaxe upgrade
                },
                4
            ).openMenu(player)
            return
        }

        config.qualityUpgradeZones.forEach {
            if (!it.isInBounds(location)) return@forEach
            UpgradeMenu(
                plugin,
                team,
                Component.text("Quality Upgrades"),
                ItemBuilder(Material.AMETHYST_SHARD).build(),
                { team.qualityTier },
                { team, int ->
                    team.qualityTier = int
                    team.sendMessage(
                        Component.text("Upgraded Quality to tier $int").color(NamedTextColor.GREEN)
                    )
                    player.closeInventory()
                    //TODO: Process the quality
                },
                4
            ).openMenu(player)
            return
        }

        config.drillUpgradeZones.forEach {
            if (!it.isInBounds(location)) return@forEach
            UpgradeMenu(
                plugin,
                team,
                Component.text("Drill Upgrades"),
                ItemBuilder(Material.NETHERITE_SCRAP).build(),
                { team.drillTier },
                { team, int ->
                    team.drillTier = int
                    team.sendMessage(
                        Component.text("Upgraded Drill to tier $int").color(NamedTextColor.GREEN)
                    )
                    player.closeInventory()

                    //TODO: Process the drill upgrade
                },
                4
            ).openMenu(player)
            return
        }

        event.isCancelled = false

    }
}