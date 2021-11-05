package moe.quill.espr.core.engine.scoreboard.entries

import net.kyori.adventure.text.Component


class StaticEntry(private val component: Component) : Entry {

    override fun getComponent(): Component {
        return component
    }
}