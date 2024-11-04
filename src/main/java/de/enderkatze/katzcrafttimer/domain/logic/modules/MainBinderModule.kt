package de.enderkatze.katzcrafttimer.domain.logic.modules

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import com.google.inject.name.Names
import de.enderkatze.katzcrafttimer.KatzcraftTimer
import de.enderkatze.katzcrafttimer.domain.contracts.data.PlayerSettingsManager
import de.enderkatze.katzcrafttimer.domain.contracts.timer.Timer
import de.enderkatze.katzcrafttimer.infra.data.globaldata.GlobalDataConfigImpl
import de.enderkatze.katzcrafttimer.domain.contracts.data.GlobalDataConfig
import de.enderkatze.katzcrafttimer.infra.data.timerdata.TimerConfigImpl
import de.enderkatze.katzcrafttimer.domain.contracts.data.TimerConfig
import de.enderkatze.katzcrafttimer.presenter.display.TimerDisplayImpl
import de.enderkatze.katzcrafttimer.presenter.display.TimerDisplay
import de.enderkatze.katzcrafttimer.infra.timer.TimerManagerImpl
import de.enderkatze.katzcrafttimer.domain.contracts.timer.TimerManager
import de.enderkatze.katzcrafttimer.infra.data.playerdata.PlayerSettingsManagerImpl
import de.enderkatze.katzcrafttimer.presenter.display.display_handlers.ActionbarDisplayHandler
import de.enderkatze.katzcrafttimer.presenter.display.display_handlers.BossbarDisplayHandler
import de.enderkatze.katzcrafttimer.infra.timer.TimerNormal
import de.enderkatze.katzcrafttimer.domain.contracts.timer.TimerFactory
import de.enderkatze.katzcrafttimer.external.placeholderapi.TimerExpansion
import de.enderkatze.katzcrafttimer.infra.timer.TimerFactoryImpl
import de.enderkatze.katzcrafttimer.presenter.user_interface.commands.TimerCommand


class MainBinderModule(private val plugin: KatzcraftTimer): AbstractModule() {

    override fun configure() {
        /* Instance Binds */

        bind(KatzcraftTimer::class.java).toInstance(this.plugin)

        bind(ActionbarDisplayHandler::class.java).`in`(Scopes.SINGLETON)
        bind(BossbarDisplayHandler::class.java).`in`(Scopes.SINGLETON)

        bind(Timer::class.java).annotatedWith(Names.named("timer.normal")).to(TimerNormal::class.java)

        bind(TimerCommand::class.java).`in`(Scopes.SINGLETON)

        /* Interface Binds */

        // Timer
        bind(TimerManager::class.java).to(TimerManagerImpl::class.java).`in`(Scopes.SINGLETON)
        bind(TimerDisplay::class.java).to(TimerDisplayImpl::class.java).`in`(Scopes.SINGLETON)
        bind(TimerFactory::class.java).to(TimerFactoryImpl::class.java).`in`(Scopes.SINGLETON)

        // Data
        bind(TimerConfig::class.java).to(TimerConfigImpl::class.java).`in`(Scopes.SINGLETON)
        bind(GlobalDataConfig::class.java).to(GlobalDataConfigImpl::class.java).`in`(Scopes.SINGLETON)
        bind(PlayerSettingsManager::class.java).to(PlayerSettingsManagerImpl::class.java).`in`(Scopes.SINGLETON)

        // External
        bind(TimerExpansion::class.java).`in`(Scopes.SINGLETON)
    }
}