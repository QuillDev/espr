package moe.quill.espr.core.mine.mechanics.minedata

import moe.quill.espr.core.mine.Mine
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable

//TODO: Load the data from some config file
class MineConfig(val datastore: MineDataStore = MineDataStore(), val mineBases: List<MineBase> = listOf()) :
    ConfigurationSerializable {

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "datastore" to datastore,
            "mines" to mineBases
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): MineConfig {
            return MineConfig(
                map["datastore"] as MineDataStore,
                (map["mines"] as List<*>).filterIsInstance<MineBase>()
            )
        }

        fun defaultInstance(): MineConfig {
            return MineConfig(
                //Data Store
                MineDataStore(
                    mapOf(
                        Material.COAL_BLOCK to MaterialData(Material.COAL_BLOCK, 1, 10),
                        Material.IRON_BLOCK to MaterialData(Material.IRON_BLOCK, 1, 10)
                    )
                ),
                //Mine Bases
                listOf(
                    MineBase("mine-team-1", 1),
                    MineBase("mine-team-2", 1),
                    MineBase("mine-middle", 10),
                )
            )
        }
    }
}