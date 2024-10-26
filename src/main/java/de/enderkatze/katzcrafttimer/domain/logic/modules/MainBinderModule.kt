package de.enderkatze.katzcrafttimer.domain.logic.modules

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import com.google.inject.name.Names
import de.enderkatze.katzcrafttimer.KatzcraftTimer
import de.enderkatze.katzcrafttimer.domain.contracts.data.SettingsManager
import de.enderkatze.katzcrafttimer.api.framework.timer.Timer
import de.enderkatze.katzcrafttimer.infra.data.globaldata.GlobalDataConfigImpl
import de.enderkatze.katzcrafttimer.core.framework.data.GlobalDataConfig
import de.enderkatze.katzcrafttimer.infra.data.timerdata.TimerConfigImpl
import de.enderkatze.katzcrafttimer.core.framework.data.TimerConfig
import de.enderkatze.katzcrafttimer.core.presenter.display.TimerDisplayImpl
import de.enderkatze.katzcrafttimer.core.presenter.display.TimerDisplay
import de.enderkatze.katzcrafttimer.core.timer.TimerManagerImpl
import de.enderkatze.katzcrafttimer.api.framework.timer.TimerManager
import de.enderkatze.katzcrafttimer.infra.data.playerdata.SettingsManagerImpl
import de.enderkatze.katzcrafttimer.core.presenter.display.display_handlers.ActionbarDisplayHandler
import de.enderkatze.katzcrafttimer.core.presenter.display.display_handlers.BossbarDisplayHandler
import de.enderkatze.katzcrafttimer.core.timer.TimerNormal
import de.enderkatze.katzcrafttimer.external.placeholderapi.TimerExpansion


class MainBinderModule(private val plugin: KatzcraftTimer): AbstractModule() {

    override fun configure() {
        /* Instance Binds */

        bind(KatzcraftTimer::class.java).toInstance(this.plugin)

        bind(ActionbarDisplayHandler::class.java).`in`(Scopes.SINGLETON)
        bind(BossbarDisplayHandler::class.java).`in`(Scopes.SINGLETON)

        bind(Timer::class.java).annotatedWith(Names.named("timer.normal")).to(TimerNormal::class.java)

        /* Interface Binds */

        // Timer
        bind(TimerManager::class.java).to(TimerManagerImpl::class.java).`in`(Scopes.SINGLETON)
        bind(TimerDisplay::class.java).to(TimerDisplayImpl::class.java).`in`(Scopes.SINGLETON)

        // Data
        bind(TimerConfig::class.java).to(TimerConfigImpl::class.java).`in`(Scopes.SINGLETON)
        bind(GlobalDataConfig::class.java).to(GlobalDataConfigImpl::class.java).`in`(Scopes.SINGLETON)
        bind(SettingsManager::class.java).to(SettingsManagerImpl::class.java).`in`(Scopes.SINGLETON)

        // External
        bind(TimerExpansion::class.java).`in`(Scopes.SINGLETON)
    }
}