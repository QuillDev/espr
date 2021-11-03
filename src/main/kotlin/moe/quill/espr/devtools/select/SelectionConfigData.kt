package moe.quill.espr.devtools.select

import moe.quill.espr.devtools.select.data.NamedSelection
import org.bukkit.configuration.serialization.ConfigurationSerializable

class SelectionConfigData(val selections: List<NamedSelection> = arrayListOf()) : ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): SelectionConfigData {
            return SelectionConfigData(map["selections"] as ArrayList<NamedSelection>)
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "selections" to selections
        )
    }

    fun toMap(): Map<String, NamedSelection> {
        val map = mutableMapOf<String, NamedSelection>()
        selections.forEach { map[it.name] = it }
        return map
    }
}