package moe.quill.espr.devtools.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.Plugin
import java.nio.file.Path
import kotlin.io.path.writeText

abstract class YamlConfig<T : ConfigurationSerializable>(plugin: Plugin, vararg chunks: String) {

    val path = Path.of(plugin.dataFolder.path.toString(), *chunks)

    fun load(): T {
        return getYaml().get("root") as T
    }

    abstract fun get(): T

    fun write(content: T) {

        val config = YamlConfiguration()
        config.set("root", content)
        path.parent.toFile().mkdirs()
        path.writeText(config.saveToString())
    }

    protected fun getYaml(): YamlConfiguration {
        return YamlConfiguration.loadConfiguration(path.toFile())
    }
}