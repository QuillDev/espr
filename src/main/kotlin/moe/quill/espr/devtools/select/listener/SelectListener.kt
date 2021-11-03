package moe.quill.espr.devtools.select.listener

import moe.quill.espr.devtools.select.SelectModule
import moe.quill.espr.devtools.select.data.Selection
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.persistence.PersistentDataType
import java.util.*

class SelectListener(private val selectModule: SelectModule) : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.hand != EquipmentSlot.HAND) return

        val player = event.player
        val uuid = player.uniqueId
        val heldItem = player.inventory.itemInMainHand
        val meta = heldItem.itemMeta ?: return

        if (!meta.persistentDataContainer.has(selectModule.toolKey, PersistentDataType.STRING)) return

        val location = event.clickedBlock?.location ?: return


        event.isCancelled = true

        when (event.action) {
            Action.LEFT_CLICK_BLOCK, Action.LEFT_CLICK_AIR -> {
                player.sendMessage(
                    Component.text("Set left selection to [${location.blockX}, ${location.blockY}, ${location.blockZ} ]")
                )
                selectModule.leftMap[uuid] = location
                checkSelectionUpdate(uuid)
                return
            }
            Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR -> {
                player.sendMessage(
                    Component.text("Set right selection to [${location.blockX}, ${location.blockY}, ${location.blockZ} ]")
                )
                selectModule.rightMap[uuid] = location
                checkSelectionUpdate(uuid)
                return
            }
            else -> return
        }
    }

    private fun checkSelectionUpdate(uuid: UUID) {
        val left = selectModule.leftMap[uuid] ?: return
        val right = selectModule.rightMap[uuid] ?: return
        selectModule.selectMap[uuid] = Selection(left, right)
    }
}