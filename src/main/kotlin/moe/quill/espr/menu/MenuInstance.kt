package moe.quill.espr.menu

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.Plugin

class MenuInstance(plugin: Plugin, private val parent: Menu, size: Int, title: Component) : Listener {

    val inventory = Bukkit.createInventory(null, size * 9, title)

    init {
        parent.items.forEach { (idx, item) -> inventory.setItem(idx, item.icon) }
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.inventory != inventory) return
        event.isCancelled = true

        //Run the method if possible
        parent.items[event.slot]?.action?.invoke(event.whoClicked)
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        if (event.inventory != inventory) return

        if (inventory.viewers.isEmpty()) {
            HandlerList.unregisterAll(this)
        }
    }

}