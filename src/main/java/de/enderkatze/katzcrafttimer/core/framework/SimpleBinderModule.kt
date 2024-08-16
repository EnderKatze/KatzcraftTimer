package de.enderkatze.katzcrafttimer.core.framework

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import de.enderkatze.katzcrafttimer.Main


class SimpleBinderModule(private val plugin: Main): AbstractModule() {

    override fun configure() {
        bind(Main::class.java).toInstance(this.plugin)
    }

    fun createInjector(): Injector {
        return Guice.createInjector(this)
    }
}