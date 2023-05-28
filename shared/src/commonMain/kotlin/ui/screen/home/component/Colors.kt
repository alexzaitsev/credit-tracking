package ui.screen.home.component

import androidx.compose.ui.graphics.Color
import ui.screen.home.model.AccountStatus
import ui.view.theme.Colors
import ui.view.theme.Colors.PINK_200
import ui.view.theme.Colors.PINK_600
import ui.view.theme.Colors.TEAL_200
import ui.view.theme.Colors.TEAL_600

sealed class HomeColors(
    open val generalStatusBg: Color,
    open val generalStatusText: Color,
    open val accountStatus: Color,
    open val accountTitleText: Color,
    open val accountBalanceNegativeText: Color,
    open val accountBalancePositiveText: Color,
    open val cardBg: Color,
    open val fabBg: Color,
    open val fabIconBg: Color,
    open val lastTxsText: Color,
    open val txBg: Color,
    open val txCommentText: Color,
) {

    object Ok : HomeColors(
        generalStatusBg = Colors.TEAL_400,
        generalStatusText = Colors.WHITE,
        accountStatus = Colors.TEAL_600,
        accountTitleText = Colors.BLACK,
        accountBalanceNegativeText = PINK_600,
        accountBalancePositiveText = TEAL_600,
        cardBg = Colors.AMBER_400,
        fabBg = Colors.DEEP_PURPLE_400,
        fabIconBg = Colors.AMBER_400,
        lastTxsText = Colors.DEEP_PURPLE_400,
        txBg = Colors.BLUE_300,
        txCommentText = Colors.GRAY_200
    )

    object Issue : HomeColors(
        generalStatusBg = Colors.PINK_400,
        generalStatusText = Colors.WHITE,
        accountStatus = Colors.PINK_600,
        accountTitleText = Colors.WHITE,
        accountBalanceNegativeText = PINK_200,
        accountBalancePositiveText = TEAL_200,
        cardBg = Colors.DEEP_PURPLE_400,
        fabBg = Colors.AMBER_400,
        fabIconBg = Colors.DEEP_PURPLE_400,
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

fun Double.zeroBasedColor(colors: HomeColors): Color =
    if (this < 0) colors.accountBalanceNegativeText else colors.accountBalancePositiveText
