package moe.quill.espr.menu

import moe.quill.espr.util.ItemBuilder
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.plugin.Plugin

open class Menu(
    private val plugin: Plugin,
    private val rows: Int = 3,
    private val title: Component = Component.text("Menu")
) {

    protected val blockade = MenuItem(
        ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
            .displayName(Component.empty())
            .build()
    )

    val items = mutableMapOf<Int, MenuItem>()

    fun setItem(int: Int, item: MenuItem) {
        items[int] = item
    }

    fun setItem(range: IntRange, item: MenuItem) {
        for (idx in range) {
            setItem(idx, item)
        }
    }

    fun openMenu(viewer: HumanEntity) {
        viewer.openInventory(MenuInstance(plugin, this, rows, title).inventory)
    }
}