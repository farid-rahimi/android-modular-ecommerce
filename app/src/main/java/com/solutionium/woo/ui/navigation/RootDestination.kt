package com.solutionium.woo.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.solutionium.woo.R


enum class RootDestination(
    val rootScreen: RootScreen,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: Int,
) {
    Home(
        rootScreen = RootScreen.Home,
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home,
        title = R.string.home,
    ),
    Category(
        rootScreen = RootScreen.Category,
        selectedIcon = Icons.Rounded.Search,
        unselectedIcon = Icons.Outlined.Search,
        title = R.string.category,
    ),
    Cart(
        rootScreen = RootScreen.Cart,
        selectedIcon = Icons.Rounded.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
        title = R.string.cart,
    ),
    Account(
        rootScreen = RootScreen.Account,
        selectedIcon = Icons.Rounded.Person,
        unselectedIcon = Icons.Outlined.Person,
        title = R.string.account,
    ),
}
