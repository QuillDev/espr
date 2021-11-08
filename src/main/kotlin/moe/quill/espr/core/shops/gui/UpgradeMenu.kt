package moe.quill.espr.core.shops.gui

import moe.quill.espr.core.teams.Team
import moe.quill.espr.menu.Menu
import moe.quill.espr.menu.MenuItem
import moe.quill.espr.util.ItemBuilder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.HumanEntity
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.pow

class UpgradeMenu(
    plugin: Plugin,
    private val team: Team,
    title: Component,
    private val icon: ItemStack,
    private val tierSupplier: (Team) -> Int,
    private val onUpgrade: (Team, Int) -> Unit,
    private val maxTier: Int
) : Menu(plugin, 1, title) {

    init {
        setItem(0..3, blockade)
        setItem(5..8, blockade)
        updateMenu()
    }

    private fun updateMenu() {

        val stack = ItemBuilder(icon.clone())
        var action: (HumanEntity) -> Unit = {}

        val tier = tierSupplier.invoke(team)

        if (tier >= maxTier) {
            stack.displayName(Component.text("MAX TIER").color(NamedTextColor.RED).decorate(TextDecoration.BOLD))
        } else {

            val cost = ceil(((tier + 1) * 500.0).pow(1 + (.1 * (tier + 1)))).toInt()
            stack.displayName(Component.text("Upgrade to tier ${tier + 1}?").color(NamedTextColor.GREEN))
            stack.lore(listOf(Component.text("Price: $cost").color(NamedTextColor.YELLOW)))
            action = {
                if (team.points < cost) {
                    it.sendMessage(
                        Component.text("You do not have enough points to upgrade!").color(NamedTextColor.RED)
                    )
                } else {
                    onUpgrade.invoke(team, tier + 1)
                    updateMenu()
                    team.points -= cost
                }
            }
        }

        setItem(4, MenuItem(stack.build(), action))
    }
}