package moe.quill.espr.core.engine.scoreboard

import moe.quill.espr.core.engine.scoreboard.entries.Entry
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard

class ESPRBoard(private val title: Entry) {

    private val MAX_LINES = 15

    private val tools = ScoreboardTools()

    private val serializer = LegacyComponentSerializer.legacyAmpersand()
    private val entries = mutableListOf<Entry>()
    private val staleEntries = mutableListOf<Component>()

    private val bukkitBoard: Scoreboard = Bukkit.getScoreboardManager().newScoreboard
    private val objective: Objective = tools.registerObjective(bukkitBoard, "SB", "dummy", title.getComponent())

    fun addPlayer(player: Player) {
        player.scoreboard = bukkitBoard
    }

    fun addEntry(entry: Entry) {
        entries.add(entry)
    }

    fun update() {
        val freshTitle = title.getComponent()
        val staleTitle = objective.displayName()
        if (!equalValue(freshTitle, staleTitle)) {
            objective.displayName(freshTitle)
        }

        for (idx in 0..entries.size) {
            val entry = entries[idx]
            val freshLine = entry.getComponent()

            //Make sure we are only updating entries
            if (idx < staleEntries.size) {
                val staleLine = staleEntries[idx]
                if (equalValue(freshLine, staleLine)) continue
            }

            //set the line
            val entryPrefix = ChatColor.values()[idx]
            val team = tools.getTeam(bukkitBoard, idx.toString())
            val entryName = entryPrefix.toString() + "" + ChatColor.WHITE

            if (!team.hasEntry(entryName)) {
                team.entries.forEach(team::removeEntry) //TODO: Is this needed?
                team.addEntry(entryName)
            }

            team.prefix(freshLine)
            objective.getScore(entryName).score = MAX_LINES - idx
        }
    }

    private fun equalValue(first: Component, second: Component): Boolean {
        return serializer.serialize(first).equals(second)
    }
}