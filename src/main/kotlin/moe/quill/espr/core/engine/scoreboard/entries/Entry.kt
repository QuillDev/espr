package moe.quill.espr.core.engine.scoreboard.entries

import net.kyori.adventure.text.Component

interface Entry {

    fun getComponent(): Component
}