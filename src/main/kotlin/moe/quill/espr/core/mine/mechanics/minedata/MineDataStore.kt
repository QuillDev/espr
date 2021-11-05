package moe.quill.espr.core.mine.mechanics.minedata

import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import kotlin.random.Random

class MineDataStore(val blockMappings: Map<Material, MaterialData> = mapOf()) : ConfigurationSerializable {


    private fun getAvailableForLevel(level: Int): List<MaterialData> {
        return blockMappings
            .filterValues { level.coerceAtLeast(1) >= it.value }
            .map { it.value }
            .sortedBy { it.value }
    }

    fun getRandomBlockForLevel(level: Int): Material {
        val available = getAvailableForLevel(level)
        var roll = Random.nextInt(available.sumOf { it.weight })

        for (item in available) {
            if (roll <= item.weight) {
                return item.type
            }

            roll -= item.weight
        }

        return Material.AIR
    }

    //Serialization
    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): MineDataStore {
            val mappings = mutableMapOf<Material, MaterialData>()

            (map["data"] as Collection<*>).filterIsInstance<MaterialData>()
                .forEach { mappings[it.type] = it }

            return MineDataStore(mappings)
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "data" to blockMappings.values.toList()
        )
    }
}