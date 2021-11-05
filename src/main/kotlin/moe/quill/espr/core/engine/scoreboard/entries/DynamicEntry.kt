package moe.quill.espr.core.engine.scoreboard.entries

import net.kyori.adventure.text.Component

class DynamicEntry(private val provider: () -> Component) : Entry {

    override fun getComponent(): Component {
        return provider()
    }
}