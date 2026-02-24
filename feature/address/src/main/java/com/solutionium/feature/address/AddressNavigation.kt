package com.solutionium.feature.address

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.solutionium.sharedui.common.DestinationRoute

private const val ROUTE_ADDRESS_SCREEN = "address_route"
private const val ROUTE_ADDRESS_LIST_SCREEN = "address_list_route"
fun NavGraphBuilder.addressScreen(
    rootRoute: DestinationRoute,
    onConfirm: () -> Unit,
    onBack: () -> Unit,
    onAddEditAddress: (addressId: Int?) -> Unit
) {
    composable(
        route = "${rootRoute.route}/${ROUTE_ADDRESS_LIST_SCREEN}",
    ) {
        AddressListScreen(
            onNavigateToEditAddress = onAddEditAddress,
            onBackNavigation = onBack,
            viewModel =koinViewModel()
        )
    }

    dialog(
        route = "${rootRoute.route}/${ROUTE_ADDRESS_SCREEN}/{address_id_or_new}",
        arguments = listOf(navArgument("address_id_or_new") { type = NavType.IntType }),
        dialogProperties = DialogProperties(
            dismissOnBackPress = true, // Default: true
            dismissOnClickOutside = true, // Default: true
            usePlatformDefaultWidth = false // IMPORTANT: Set to false to control width
            // securePolicy = SecureFlagPolicy.Inherit // Default
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f) // Example: 90% of screen width
                .fillMaxHeight(0.85f) // Example: 85% of screen height
                .padding(vertical = 24.dp), // Some vertical padding so it doesn't touch screen edges
            shape = RoundedCornerShape(16.dp), // Modern rounded corners for the dialog
            tonalElevation = 8.dp, // Or shadowElevation
            shadowElevation = 8.dp
        ) {
            AddEditAddressScreen(
                // ViewModel will get address_id_or_new from its SavedStateHandle
                onSaved = onConfirm,
                onBack = onBack,
                viewModel =koinViewModel()
            )
        }
    }
}

fun NavController.navigateAddressList(
    rootRoute: DestinationRoute,
) {
    navigate("${rootRoute.route}/${ROUTE_ADDRESS_LIST_SCREEN}")
}

fun NavController.navigateAddress(
    rootRoute: DestinationRoute,
    addressId: Int? = null,
) {
    navigate("${rootRoute.route}/${ROUTE_ADDRESS_SCREEN}/${addressId ?: "-1"}")
}
