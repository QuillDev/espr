package moe.quill.espr.core.teams

class TeamManager {
    val teams = mutableListOf<ScoredTeam>()

    fun registerTeam(team: ScoredTeam){
        teams.add(team)
    }

    fun removeTeam(team: ScoredTeam){
        teams.remove(team)
    }

}