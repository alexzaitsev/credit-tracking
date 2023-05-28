package ui.screen.home.component

import androidx.compose.ui.graphics.Color
import ui.screen.home.model.AccountStatus
import ui.view.theme.Colors.AMBER_200
import ui.view.theme.Colors.AMBER_400
import ui.view.theme.Colors.BLACK
import ui.view.theme.Colors.DEEP_PURPLE_400
import ui.view.theme.Colors.GRAY_200
import ui.view.theme.Colors.INDIGO_A100
import ui.view.theme.Colors.ORANGE_A100
import ui.view.theme.Colors.PINK_200
import ui.view.theme.Colors.PINK_400
import ui.view.theme.Colors.PINK_600
import ui.view.theme.Colors.TEAL_200
import ui.view.theme.Colors.TEAL_400
import ui.view.theme.Colors.TEAL_600
import ui.view.theme.Colors.TEAL_A100
import ui.view.theme.Colors.WHITE

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
    open val txAmountNegativeText: Color,
    open val txAmountPositiveText: Color
) {

    object Ok : HomeColors(
        generalStatusBg = TEAL_400,
        generalStatusText = WHITE,
        accountStatus = TEAL_600,
        accountTitleText = BLACK,
        accountBalanceNegativeText = PINK_600,
        accountBalancePositiveText = TEAL_600,
        cardBg = AMBER_400,
        fabBg = DEEP_PURPLE_400,
        fabIconBg = AMBER_400,
        lastTxsText = DEEP_PURPLE_400,
        txBg = INDIGO_A100,
        txCommentText = GRAY_200,
        txAmountNegativeText = ORANGE_A100,
        txAmountPositiveText = TEAL_A100
    )

    object Issue : HomeColors(
        generalStatusBg = PINK_400,
        generalStatusText = WHITE,
        accountStatus = PINK_600,
        accountTitleText = WHITE,
        accountBalanceNegativeText = PINK_200,
        accountBalancePositiveText = TEAL_200,
        cardBg = DEEP_PURPLE_400,
        fabBg = AMBER_400,
        fabIconBg = DEEP_PURPLE_400,
        lastTxsText = AMBER_200,
        txBg = INDIGO_A100,
        txCommentText = GRAY_200,
        txAmountNegativeText = ORANGE_A100,
        txAmountPositiveText = TEAL_A100
    )
}

val AccountStatus.colors: HomeColors
    get() = when (this) {
        AccountStatus.Ok -> HomeColors.Ok
        is AccountStatus.Issue -> HomeColors.Issue
    }

fun Double.accountBalanceColor(colors: HomeColors): Color =
    if (this < 0) colors.accountBalanceNegativeText else colors.accountBalancePositiveText

fun Double.txAmountColor(colors: HomeColors): Color =
    if (this < 0) colors.txAmountNegativeText else colors.txAmountPositiveText
