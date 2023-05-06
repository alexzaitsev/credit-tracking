package ui

import org.koin.dsl.module
import ui.screen.home.HomeScreenModel

val uiModule = module {

    single { HomeScreenModel(get()) }
}
