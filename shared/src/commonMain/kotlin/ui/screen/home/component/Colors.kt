package ui.screen.home.component

import androidx.compose.ui.graphics.Color
import ui.screen.home.model.AccountStatus
import ui.view.theme.Colors

sealed class HomeColors(
    open val generalStatusBg: Color,
    open val generalStatusText: Color,
    open val accountStatus: Color,
    open val cardBg: Color,
    open val fabBg: Color,
    open val fabText: Color,
    open val lastTxsText: Color,
    open val txBg: Color,
    open val txCommentText: Color,
) {

    object Ok : HomeColors(
        generalStatusBg = Colors.TEAL_400,
        generalStatusText = Colors.WHITE,
        accountStatus = Colors.TEAL_600,
        cardBg = Colors.AMBER_400,
        fabBg = Colors.DEEP_PURPLE_400,
        fabText = Colors.WHITE,
        lastTxsText = Colors.DEEP_PURPLE_400,
        txBg = Colors.BLUE_300,
        txCommentText = Colors.GRAY_200
    )

    object Issue : HomeColors(
        generalStatusBg = Colors.PINK_400,
        generalStatusText = Colors.WHITE,
        accountStatus = Colors.PINK_600,
        cardBg = Colors.DEEP_PURPLE_400,
        fabBg = Colors.AMBER_400,
        fabText = Colors.WHITE,
        lastTxsText = Colors.AMBER_200,
        txBg = Colors.BLUE_300,
        txCommentText = Colors.GRAY_200
    )
}

val AccountStatus.colors: HomeColors
    get() = when (this) {
        AccountStatus.Ok -> HomeColors.Ok
        is AccountStatus.Issue -> HomeColors.Issue
    }
