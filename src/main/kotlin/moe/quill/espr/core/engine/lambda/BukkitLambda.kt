package moe.quill.espr.core.engine.lambda

import org.bukkit.scheduler.BukkitRunnable

class BukkitLambda(private val action: () -> Unit) : BukkitRunnable() {
    override fun run() {
        action()
    }
}