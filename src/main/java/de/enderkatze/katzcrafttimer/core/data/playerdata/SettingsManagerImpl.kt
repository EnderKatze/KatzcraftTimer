package de.enderkatze.katzcrafttimer.core.data.playerdata

import de.enderkatze.katzcrafttimer.api.framework.data.playerdata.SettingsManager
import de.enderkatze.katzcrafttimer.core.framework.data.GlobalDataConfig
import javax.inject.Inject

class SettingsManagerImpl @Inject constructor(
    val dataConfig: GlobalDataConfig
): SettingsManager {


}