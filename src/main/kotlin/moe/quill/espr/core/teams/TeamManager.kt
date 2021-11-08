package moe.quill.espr.core.teams

import moe.quill.espr.core.engine.scoreboard.MGScoreboard
import moe.quill.espr.core.engine.scoreboard.ScoreboardManager
import moe.quill.espr.core.engine.scoreboard.entries.DynamicEntry
import moe.quill.espr.core.engine.scoreboard.entries.StaticEntry
import net.kyori.adventure.text.Component
import java.util.*

class TeamManager(private val scoreboardManager: ScoreboardManager) {

    val teams = hashSetOf<Team>()
    val boards = hashMapOf<Team, MGScoreboard>()

    init {
        val testTeam = Team()
        testTeam.members += UUID.fromString("959e2a47-f4ee-4492-9a4f-387a2d9340c3")
        registerTeam(testTeam)
    }

    fun registerTeam(team: Team) {
        teams += team

        val board = MGScoreboard(StaticEntry(Component.text("Place Holder")))
        board.entries += DynamicEntry { Component.text("Score: ").append(Component.text(team.score)) }
        board.entries += DynamicEntry { Component.text("Currency: ").append(Component.text(team.points)) }

        //Show the board to all the players
        team.members.forEach { board.addViewer(it) }

        //Register it with the board manager
        boards[team] = board
        scoreboardManager.boards += board
    }

    fun getTeam(uuid: UUID): Team? {
        return teams.firstOrNull { it.members.contains(uuid) }
    }
}