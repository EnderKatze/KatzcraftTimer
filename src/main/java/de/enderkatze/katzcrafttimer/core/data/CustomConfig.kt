package de.enderkatze.katzcrafttimer.core.data

import org.bukkit.configuration.file.FileConfiguration

interface CustomConfig {

    fun getConfig(): FileConfiguration?
    fun saveConfig()
    fun reloadConfig()

    
}