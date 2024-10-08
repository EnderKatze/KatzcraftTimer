package de.enderkatze.katzcrafttimer.core.framework.presenter.gui

import de.enderkatze.katzcrafttimer.api.framework.presenter.gui.GUIMenu

interface GUIBuilder {

    fun build(menu: GUIMenu)
}