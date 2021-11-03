package moe.quill.espr.core.mine

import org.bukkit.configuration.serialization.ConfigurationSerializable

class MineData(val mines: List<Mine> = arrayListOf()) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): MineData {
            return MineData(map["mines"] as List<Mine>)
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf("mines" to mines)
    }
}