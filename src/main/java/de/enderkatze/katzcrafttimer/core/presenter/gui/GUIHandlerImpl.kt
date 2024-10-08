package de.enderkatze.katzcrafttimer.core.presenter.gui

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.api.framework.presenter.gui.GUIMenu
import de.enderkatze.katzcrafttimer.api.framework.presenter.gui.GUIHandler
import de.enderkatze.katzcrafttimer.core.framework.presenter.gui.GUIBuilder

class GUIHandlerImpl @Inject constructor(
    val guiBuilder: GUIBuilder,
): GUIHandler {

    private var menus: MutableList<GUIMenu> = mutableListOf()

    override fun openMenu(menu: GUIMenu) {
        guiBuilder.build(menu)
    }

    override fun getMenus(): List<GUIMenu> {
        return menus
    }

    override fun addMenu(menu: GUIMenu) {
        menus.add(menu)
    }

    override fun removeMenu(menu: GUIMenu) {
        menus.remove(menu)
    }


}