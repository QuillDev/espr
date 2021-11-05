package moe.quill.espr.core.utility

import moe.quill.feather.lib.lambda.FeatherRealtimeRunnable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.ceil

class Countdown(val time: Long,val subtext: Component ,var lambda: ()-> Unit = {}) : BukkitRunnable() {
    private val endTime = System.currentTimeMillis() + time
    private val lastTime = -1

    override fun run() {
        val now = System.currentTimeMillis()
        val remainingTime = ceil((endTime - now) / 1000.0).toInt()
        if(remainingTime == lastTime) return
        if (now >= endTime) {
            cancel()
            lambda()
            return
        }
        Bukkit.getServer().showTitle(Title.title(
            Component.text(remainingTime).color(
                if(remainingTime > time/2 ) NamedTextColor.GREEN else NamedTextColor.RED
            ),
            subtext))
    }

}