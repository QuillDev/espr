package moe.quill.espr.core

import com.google.common.io.ByteStreams
import moe.quill.espr.core.engine.GameManager
import moe.quill.espr.core.engine.GameState
import moe.quill.espr.core.teams.Team
import moe.quill.espr.core.teams.TeamManager
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import java.util.*

class MessageListener(private val teamManager: TeamManager, private val gameManager: GameManager) :
    PluginMessageListener {

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (channel != "BungeeCord") return

        try {
            val input = ByteStreams.newDataInput(message)
            val subChannel = input.readUTF()
            if (subChannel != "ESPR") return

            when (input.readUTF()) {
                "STATE" -> {
                    gameManager.changeState(GameState.STARTING)
                }
                "CREATE_TEAM" -> {
                    val uuids = mutableListOf<UUID>()
                    try {
                        uuids.add(UUID.fromString(input.readUTF()))
                        val team = Team()
                        teamManager.registerTeam(team)
                    } catch (ignored: Exception) {
                    }
                }
            }

        } catch (ignored: Exception) {
        }

    }
}

