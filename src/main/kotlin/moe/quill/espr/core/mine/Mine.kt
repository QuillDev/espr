package moe.quill.espr.core.mine

import moe.quill.espr.core.mine.mechanics.minedata.MineDataStore
import moe.quill.espr.devtools.select.data.NamedSelection
import org.bukkit.Material

class Mine(
    val selection: NamedSelection,
    private val data: MineDataStore,
    var level: Int = 1
) {

    init {
        selection.getBlocks().forEach { it.type = getRandomType() }
    }

    fun getRandomType(): Material {
        return data.getRandomBlockForLevel(level)
    }
}