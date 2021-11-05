package moe.quill.espr.core.teams

class TeamManager {
    val teams = mutableListOf<Team>()

    fun registerTeam(team: Team){
        teams.add(team)
    }

    fun removeTeam(team: Team){
        teams.remove(team)
    }
}