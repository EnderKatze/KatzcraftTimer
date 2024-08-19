package de.enderkatze.katzcrafttimer.core.framework

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import de.enderkatze.katzcrafttimer.Main
import de.enderkatze.katzcrafttimer.timer.DefaultTimerFactory
import de.enderkatze.katzcrafttimer.timer.DefaultTimerManager
import de.enderkatze.katzcrafttimer.timer.TimerFactory
import de.enderkatze.katzcrafttimer.timer.TimerManager


class MainBinderModule(private val plugin: Main): AbstractModule() {

    override fun configure() {
        bind(Main::class.java).toInstance(this.plugin)
        bind(TimerManager::class.java).to(DefaultTimerManager::class.java)
        bind(TimerFactory::class.java).to(DefaultTimerFactory::class.java)
    }

    fun createInjector(): Injector {
        return Guice.createInjector(this)
    }
}