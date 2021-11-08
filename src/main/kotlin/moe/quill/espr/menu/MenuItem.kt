package moe.quill.espr.menu

import org.bukkit.entity.HumanEntity
import org.bukkit.inventory.ItemStack

class MenuItem(val icon: ItemStack, val action: (clicker: HumanEntity) -> Unit = {})