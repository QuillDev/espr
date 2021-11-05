package moe.quill.espr.core.teams

import net.kyori.adventure.text.Component

class ScoredTeam(name: Component) : Team(name) {
    var pointHistory = mutableListOf<Int>()
    var pointTotal = 0

    fun modifyScore(score: Int) {
        pointHistory.add(score)
        if(pointTotal <= 0) return
        pointTotal += score
    }

}