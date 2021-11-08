package moe.quill.espr.devtools.select.data

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.configuration.serialization.ConfigurationSerializable
import kotlin.math.max

open class Selection(loc1: Location, loc2: Location) : ConfigurationSerializable {

    val minLoc = Location(
        loc1.world,
        loc1.blockX.coerceAtMost(loc2.blockX).toDouble(),
        loc1.blockY.coerceAtMost(loc2.blockY).toDouble(),
        loc1.blockZ.coerceAtMost(loc2.blockZ).toDouble()
    )
    val maxLoc = Location(
        loc1.world,
        loc1.blockX.coerceAtLeast(loc2.blockX).toDouble(),
        loc1.blockY.coerceAtLeast(loc2.blockY).toDouble(),
        loc1.blockZ.coerceAtLeast(loc2.blockZ).toDouble()
    )

    /**
     * Check if the given location is within these bounds
     * @param location to check if it's within these bounds
     */
    fun isInBounds(location: Location): Boolean {
        return ((minLoc.x <= location.x && location.x <= maxLoc.x)
                && (minLoc.y <= location.y && location.y <= maxLoc.y)
                && (minLoc.z <= location.z && location.z <= maxLoc.z))
    }

    fun getBlocks(): List<Block> {
        val blocks = arrayListOf<Block>()
        for (x in minLoc.blockX..maxLoc.blockX) {
            for (y in minLoc.blockY..maxLoc.blockY) {
                for (z in minLoc.blockZ..maxLoc.blockZ) {
                    blocks.add(Location(minLoc.world, x.toDouble(), y.toDouble(), z.toDouble()).block)
                }
            }
        }

        return blocks
    }

    companion object {
        @JvmStatic
        fun deserialize(map: Map<String, Any>): Selection {
            val minLocation = map["min-loc"] as Location
            val maxLocation = map["max-loc"] as Location
            return Selection(minLocation, maxLocation)
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        return mutableMapOf(
            "min-loc" to minLoc,
            "max-loc" to maxLoc
        )
    }
}