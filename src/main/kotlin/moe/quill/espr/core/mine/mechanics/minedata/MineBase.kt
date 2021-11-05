package moe.quill.espr.core.mine.mechanics.minedata

import org.bukkit.configuration.serialization.ConfigurationSerializable

class MineBase(val selectionName: String, val startLevel: Int) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): MineBase {
            val selection = map["selection"] as String
            val level = map["level"] as Int

            return MineBase(selection, level)
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "selection" to selectionName,
            "level" to startLevel
        )
    }
}