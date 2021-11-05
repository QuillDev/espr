package moe.quill.espr.core.utility

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFadeEvent

class BlockDecayListener : Listener {

    @EventHandler
    fun onBlockDecay(event: BlockFadeEvent){
        event.isCancelled = true
    }
}