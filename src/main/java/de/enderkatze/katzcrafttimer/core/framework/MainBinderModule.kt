package de.enderkatze.katzcrafttimer.core.framework

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import de.enderkatze.katzcrafttimer.Main
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
        bind(TimerManager::class.java).to(DefaultTimerManager::class.java)
        bind(TimerFactory::class.java).to(DefaultTimerFactory::class.java)
        bind(TimerDisplay::class.java).to(DefaultTimerDisplay::class.java)
    }

    fun createInjector(): Injector {
        return Guice.createInjector(this)
    }
}