package moe.quill.espr.core.engine.scoreboard

import net.kyori.adventure.text.Component
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

class ScoreboardTools {
    /**
     * Register a new objective to the given scoreboard
     *
     * @param scoreboard to register the objective to
     * @param name       of the objective
     * @param criteria   for the objective
     * @param title      component to be shown for this objective
     */
    fun registerObjective(scoreboard: Scoreboard, name: String, criteria: String, title: Component?): Objective {
        val objective = scoreboard.getObjective(name)
        objective?.unregister()
        return scoreboard.registerNewObjective(name, criteria, title)
    }

    private fun registerTeam(scoreboard: Scoreboard, identifier: String): Team {
        val team = scoreboard.getTeam(identifier)
        team?.unregister()
        return scoreboard.registerNewTeam(identifier)
    }

    fun getTeam(scoreboard: Scoreboard, identifier: String): Team {
        return scoreboard.getTeam(identifier) ?: return registerTeam(scoreboard, identifier)
    }
}