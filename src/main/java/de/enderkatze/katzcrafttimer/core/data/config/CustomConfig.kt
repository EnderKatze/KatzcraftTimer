package de.enderkatze.katzcrafttimer.core.data.config

import org.bukkit.configuration.file.FileConfiguration

interface CustomConfig {

    fun getConfig(): FileConfiguration?
    fun saveConfig()
    fun reloadConfig()

    
}