package moe.quill.espr.core.teams

import net.kyori.adventure.text.Component

class ScoredTeam(name: Component) : Team(name) {
    var pointHistory = mutableListOf<Int>()
    var pointTotal = 0

    fun addScore(score: Int) {
        pointHistory.add(score)
        pointTotal += score
    }

}