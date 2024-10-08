package de.enderkatze.katzcrafttimer.api.framework.presenter.gui

interface GUIHandler {

    fun openMenu(menu: GUIMenu)

    fun getMenus(): List<GUIMenu>

    fun addMenu(menu: GUIMenu)

    fun removeMenu(menu: GUIMenu)
}