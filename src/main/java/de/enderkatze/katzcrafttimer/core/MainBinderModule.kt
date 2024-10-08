package de.enderkatze.katzcrafttimer.core

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import de.enderkatze.katzcrafttimer.KatzcraftTimer
import de.enderkatze.katzcrafttimer.api.framework.data.PlayerTimerSettings
import de.enderkatze.katzcrafttimer.core.data.globaldata.GlobalDataConfigImpl
import de.enderkatze.katzcrafttimer.core.data.playerdata.PlayerTimerSettingsImpl
import de.enderkatze.katzcrafttimer.core.framework.data.GlobalDataConfig
import de.enderkatze.katzcrafttimer.core.data.timerdata.TimerConfigImpl
import de.enderkatze.katzcrafttimer.core.framework.data.TimerConfig
import de.enderkatze.katzcrafttimer.core.presenter.timer_display.TimerDisplayImpl
import de.enderkatze.katzcrafttimer.core.presenter.timer_display.TimerDisplay
import de.enderkatze.katzcrafttimer.core.timer.TimerManagerImpl
import de.enderkatze.katzcrafttimer.api.framework.timer.TimerManager
import de.enderkatze.katzcrafttimer.api.framework.timer.TimerRegistry
import de.enderkatze.katzcrafttimer.core.timer.TimerRegistryImpl


class MainBinderModule(private val plugin: KatzcraftTimer): AbstractModule() {

    override fun configure() {
        /* Instance Binds */

        bind(KatzcraftTimer::class.java).toInstance(this.plugin)

        /* Interface Binds */

        // Timer
        bind(TimerManager::class.java).to(TimerManagerImpl::class.java).`in`(Scopes.SINGLETON)
        bind(TimerRegistry::class.java).to(TimerRegistryImpl::class.java).`in`(Scopes.SINGLETON)
        bind(TimerDisplay::class.java).to(TimerDisplayImpl::class.java).`in`(Scopes.SINGLETON)

        // Data
        bind(TimerConfig::class.java).to(TimerConfigImpl::class.java).`in`(Scopes.SINGLETON)
        bind(GlobalDataConfig::class.java).to(GlobalDataConfigImpl::class.java).`in`(Scopes.SINGLETON)
        bind(PlayerTimerSettings::class.java).to(PlayerTimerSettingsImpl::class.java).`in`(Scopes.SINGLETON)
    }
}