package moe.quill.espr.core.engine

import moe.quill.espr.core.teams.TeamManager
import moe.quill.espr.core.utility.bars.BossBarManager
import moe.quill.espr.core.utility.bars.MatchTimer
import moe.quill.espr.core.utility.Countdown
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.Plugin

class GameManager(
    private val plugin: Plugin,
    private val bossBarManager: BossBarManager,
    private val teamManager: TeamManager
) {
    var state = GameState.WAITING
    var startTime = 0L

    fun changeState(state: GameState) {
        when (state) {
            GameState.WAITING -> TODO()
            GameState.STARTING -> {

                teamManager.teams.clear()

                Countdown(
                    10000,
                    Component.text("Game Starting soon!").color(NamedTextColor.YELLOW)
                )
                { changeState(GameState.ACTIVE) }.runTaskTimer(plugin, 0, 5)
            }
            GameState.ACTIVE -> {
                MatchTimer(
                    bossBarManager,
                    600000
                ).runTaskTimer(plugin, 0, 20)
                startTime = System.currentTimeMillis()
                TODO("teleport teams to their spawns")
            }
            GameState.ENDING -> {
                teamManager.teams.forEach { teamManager.teams -= it }
                TODO("calculate and show the winning team")
                TODO("remove the players from the server")

            }
        }
    }

}