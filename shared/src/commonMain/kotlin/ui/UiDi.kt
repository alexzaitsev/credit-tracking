package ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools
import ui.screen.addTx.AddTxScreenModel
import ui.screen.home.HomeScreenModel


val uiModule = module {

    factory { HomeScreenModel(get()) }
    factory { parameters ->
        AddTxScreenModel(
            accountId = parameters[0],
            dataRepository = get()
        )
    }
}

@Composable
inline fun <reified T : ScreenModel> Screen.getScreenModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val koin = KoinPlatformTools.defaultContext().get()
    return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
}