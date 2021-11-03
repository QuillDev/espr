package moe.quill.espr.devtools.select.data

import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable

class NamedSelection(
    val name: String,
    loc1: Location,
    loc2: Location
) : Selection(loc1, loc2),
    ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): NamedSelection {
            val minLoc = map["min-loc"] as Location
            val maxLoc = map["max-loc"] as Location
            val name = map["name"] as String
            return NamedSelection(name, minLoc, maxLoc)
        }

    }

    override fun serialize(): MutableMap<String, Any> {
        val original = super.serialize()
        original["name"] = name
        return original
    }
}