package de.enderkatze.katzcrafttimer.core.framework.data

import org.bukkit.configuration.file.FileConfiguration

interface CustomConfig {

    fun getConfig(): FileConfiguration?
    fun saveConfig()
    fun reloadConfig()

    
}