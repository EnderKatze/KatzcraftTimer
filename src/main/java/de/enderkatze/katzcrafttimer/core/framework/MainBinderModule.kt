package de.enderkatze.katzcrafttimer.core.framework

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import de.enderkatze.katzcrafttimer.Main
import de.enderkatze.katzcrafttimer.core.data.DataManager
import de.enderkatze.katzcrafttimer.core.data.DefaultDataManager
import de.enderkatze.katzcrafttimer.core.data.config.DefaultGlobalDataConfig
import de.enderkatze.katzcrafttimer.core.data.config.GlobalDataConfig
import de.enderkatze.katzcrafttimer.core.data.config.DefaultTimerConfig
import de.enderkatze.katzcrafttimer.core.data.config.TimerConfig
import de.enderkatze.katzcrafttimer.presenter.timer_display.DefaultTimerDisplay
import de.enderkatze.katzcrafttimer.presenter.timer_display.TimerDisplay
import de.enderkatze.katzcrafttimer.timer.DefaultTimerFactory
import de.enderkatze.katzcrafttimer.timer.DefaultTimerManager
import de.enderkatze.katzcrafttimer.timer.TimerFactory
import de.enderkatze.katzcrafttimer.timer.TimerManager


class MainBinderModule(private val plugin: Main): AbstractModule() {

    override fun configure() {
        // Instance Binds
        bind(Main::class.java).toInstance(this.plugin)

        // Interface Binds
        bind(TimerManager::class.java).to(DefaultTimerManager::class.java).`in`(Scopes.SINGLETON)
        bind(TimerFactory::class.java).to(DefaultTimerFactory::class.java).`in`(Scopes.SINGLETON)
        bind(TimerDisplay::class.java).to(DefaultTimerDisplay::class.java).`in`(Scopes.SINGLETON)

        bind(TimerConfig::class.java).to(DefaultTimerConfig::class.java).`in`(Scopes.SINGLETON)
        bind(GlobalDataConfig::class.java).to(DefaultGlobalDataConfig::class.java).`in`(Scopes.SINGLETON)
        bind(DataManager::class.java).to(DefaultDataManager::class.java).`in`(Scopes.SINGLETON)
    }
}