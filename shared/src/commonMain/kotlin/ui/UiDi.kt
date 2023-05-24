package ui

import org.koin.dsl.module
import ui.screen.addTx.AddTxScreenModel
import ui.screen.home.HomeScreenModel


val uiModule = module {

    single { HomeScreenModel(get()) }
    single { parameters ->
        AddTxScreenModel(
            accountId = parameters[0],
            dataRepository = get()
        )
    }
}
