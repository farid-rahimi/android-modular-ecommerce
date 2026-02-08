package com.solutionium.woo.ui.navigation


import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy

@Composable
fun WooNavigationBar(
    destinations: List<RootDestination>,
    currentDestination: NavDestination?,
    cartCount: Int,
    onNavigationSelected: (RootScreen) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isRootDestination(destination)

            NavigationBarItem(
                selected = selected,
                onClick = {
                    onNavigationSelected(destination.rootScreen)
                          },
//                icon = {
//                    Icon(
//                        imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
//                        contentDescription = "${destination.title} icon",
//                    )
//                },
                label = {
                    Text(
                        text = stringResource(destination.title),
                    )
                },
                icon = {
                    if (destination == RootDestination.Cart && cartCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text(text = cartCount.toString())
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                                contentDescription = "${destination.title} icon",
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                            contentDescription = "${destination.title} icon",
                        )
                    }
                }
            )
        }
    }
}

private fun NavDestination?.isRootDestination(destination: RootDestination) =
    this?.hierarchy?.any { it.route == destination.rootScreen.destinationRoute.route } ?: false
