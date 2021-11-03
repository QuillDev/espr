package moe.quill.espr.core.mine

import moe.quill.espr.devtools.select.data.NamedSelection
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import kotlin.random.Random

class Mine(val name: String, val selection: NamedSelection, val types: MutableList<Material>) :
    ConfigurationSerializable {

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): Mine {
            return Mine(
                map["name"] as String,
                map["selection"] as NamedSelection,
                toMaterials((map["materials"] as List<String>))
            )
        }

        @JvmStatic
        fun toMaterials(queries: List<String>): MutableList<Material> {
            val materials = arrayListOf<Material>()
            queries.forEach {
                try {
                    materials.add(Material.valueOf(it))
                } catch (e: Exception) {
                }
            }
            return materials
        }
    }

    fun setup() {
        if (types.isEmpty()) return
        selection.getBlocks().forEach { block ->
            block.type = randomType()
        }
    }

    fun randomType(): Material {
        return types[Random.nextInt(types.size)]
    }


    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "name" to name,
            "selection" to selection,
            "materials" to types.map { it.name }.toList()
        )
    }

}