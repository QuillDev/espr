package moe.quill.espr.core.utility.bars

import net.kyori.adventure.bossbar.BossBar

class BossBarManager {
    val bossbars = mutableListOf<BossBar>()

    fun registerBossBar(bossBar: BossBar){
        bossbars.forEach{removeBossBar(it)}
        bossbars.add(bossBar)
    }
    fun removeBossBar(bossBar: BossBar){
        bossbars.remove(bossBar)
    }
}