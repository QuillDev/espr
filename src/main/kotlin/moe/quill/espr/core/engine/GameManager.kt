package moe.quill.espr.core.engine

import moe.quill.espr.core.teams.TeamManager
import moe.quill.espr.core.utility.bars.BossBarManager
import moe.quill.espr.core.utility.bars.MatchTimer
import moe.quill.espr.core.utility.Countdown
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
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
                val countdown = Countdown(
                    10000,
                    Component.text("Game Starting soon!").color(NamedTextColor.YELLOW)
                )
                { changeState(GameState.ACTIVE) }.runTaskTimer(plugin, 0, 5)
            }
            GameState.ACTIVE -> {
                val matchTimer = MatchTimer(
                    bossBarManager,
                    600000
                ).runTaskTimer(plugin, 0, 20)
                startTime = System.currentTimeMillis()
                TODO("teleport teams to their spawns")
            }
            GameState.ENDING -> {
                teamManager.teams.sortByDescending { it.pointTotal }
                val winningTeam = teamManager.teams[0]
                val losingTeam = teamManager.teams[1]
                winningTeam.uuids.forEach{
                    winningTeam.playerMap[it]?.showTitle(
                        Title.title(Component.text("YOU WIN!").color(NamedTextColor.GREEN), Component.empty())
                    )
                }
                losingTeam.uuids.forEach{
                    winningTeam.playerMap[it]?.showTitle(
                        Title.title(Component.text("YOU LOSE!").color(NamedTextColor.RED), Component.empty())
                    )
                }
                val countdown = Countdown(
                    10000,
                    Component.text("Returning to lobby.").color(NamedTextColor.YELLOW)
                ) {
                    changeState(GameState.WAITING)
                    teamManager.teams.forEach(teamManager::removeTeam)
                }.runTaskTimer(plugin, 0, 5)
                TODO("remove the players from the server")

            }
        }
    }

}