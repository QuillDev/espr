package moe.quill.espr.core.mine.mechanics.minedata

import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable

class MaterialData(val type: Material, val value: Int, val weight: Int) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): MaterialData {
            val type = Material.valueOf((map["type"] as String).uppercase())
            val value = map["value"] as Int
            val weight = map["weight"] as Int
            return MaterialData(type, value, weight)
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "type" to type.name,
            "value" to value,
            "weight" to weight
        )
    }

}