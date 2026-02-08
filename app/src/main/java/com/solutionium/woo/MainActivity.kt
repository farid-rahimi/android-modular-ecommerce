package com.solutionium.woo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.solutionium.core.designsystem.theme.WooTheme
import com.solutionium.data.local.AppPreferences
import com.solutionium.data.local.AppPreferencesImpl
import com.solutionium.feature.home.GRAPH_HOME_ROUTE
import com.solutionium.feature.home.navigateToHome
import com.solutionium.feature.product.detail.navigateProductDetail
import com.solutionium.woo.ui.WooApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale




data class DeepLinkData(val uri: Uri)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    //private lateinit var navController: NavController
    private val pendingDeepLink = mutableStateOf<DeepLinkData?>(null)

    // Declare the launcher at the top of your Activity
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted. FCM can post notifications.
        } else {
            // Inform the user that they will not receive notifications.
            // You can show a Snackbar or dialog explaining why it's useful.
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val localeToSet = Locale("fa")
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(localeToSet)
        config.setLayoutDirection(localeToSet) // Explicitly set layout direction

        val updatedContext = newBase.createConfigurationContext(config)
        super.attachBaseContext(updatedContext)
    }


    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseIntentForDeepLink(intent)

        //enableEdgeToEdge()
        // ========= SET APP LOCALE TO FARSI =========
        // 1. Create a LocaleListCompat for the desired language ("fa" for Farsi)
        //val appLocale = LocaleListCompat.forLanguageTags("fa-IR")
        // 2. Set the application's locale. This will persist and trigger a restart if needed.
        //AppCompatDelegate.setApplicationLocales(appLocale)
        // =============================================


        askNotificationPermission()


        // ... inside onCreate()
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            if (uiState.isLoading) {
                // Show a splash screen or just a blank screen
                Surface(modifier = Modifier.fillMaxSize()) { }
                return@setContent
            }

            // The layout direction is now automatically handled by the system and Compose.
            // We only need to provide it to the CompositionLocal.
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                WooTheme {
                    val navController = rememberNavController()
                    WooApp(navController = navController)

                    DeepLinkHandler(
                        navController = navController,
                        deepLinkData = pendingDeepLink.value,
                        onDeepLinkConsumed = {
                            pendingDeepLink.value = null
                        }
                    )
                }
            }
        }
        //...


        //intentState = intent

//        setContent {
//
//            val baseContext = this.baseContext
//
//            // 2. Create a new context with the updated configuration. This is key.
//            val updatedContext = baseContext.createConfigurationContext(baseContext.resources.configuration)
//
//            //CompositionLocalProvider(LocalContext provides updatedContext) {
//                WooTheme {
//                    val navController = rememberNavController()
//                    //CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
//
//
//                    WooApp(navController = navController)
//
//                    //}
//
//                    //LaunchedEffect(key1 = intentState) {
//                    //handleIntent(navController, intentState)
//                    //}
//                }
//            //}
//        }

    }

//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        intentState = intent
//    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Handle the new intent
        parseIntentForDeepLink(intent)
        //handleIntent(intent)
    }

    private fun parseIntentForDeepLink(intent: Intent?) {
        // We only care about VIEW actions with a data URI.
        if (intent?.action == Intent.ACTION_VIEW && intent.data != null) {
            // Store the URI in our state variable. This triggers the DeepLinkHandler.
            pendingDeepLink.value = DeepLinkData(uri = intent.data!!)
        }
    }

    /**
     * Helper function to parse the intent and navigate.
     */
    private fun handleIntent(intent: Intent?) {
        // Use the navController to handle the deep link.
        // This will automatically find the correct destination
        // in your NavHost graph (`checkoutScreen` in this case)
        // and navigate to it.
//        if (intent != null) {
            //navController.handleDeepLink(intent)
//            // Crucial: After handling the intent, set it to null.
//            // This prevents re-navigation if the screen rotates or recomposes for other reasons.
//            if (intentState == intent) {
//                intentState = null
//            }
//        }
    }
}

@Composable
fun DeepLinkHandler(
    navController: NavHostController,
    deepLinkData: DeepLinkData?,
    onDeepLinkConsumed: () -> Unit // Lambda to reset the state
) {
    // This LaunchedEffect will run whenever deepLinkData changes from null to a new value.
    LaunchedEffect(deepLinkData) {
        if (deepLinkData == null) return@LaunchedEffect

        val uri = deepLinkData.uri
        // Check if the link is a product link we care about.
        if (uri.scheme == "https" && uri.host == "qeshminora.com" && uri.pathSegments.firstOrNull() == "product") {
            // Get the slug from the URL (e.g., "some-product" from /product/some-product)
            val productSlug = uri.pathSegments.getOrNull(1)

            if (!productSlug.isNullOrBlank()) {
                // 1. Wait for 3 seconds
                delay(300)

                // 2. Navigate to the product detail screen
                navController.navigateToHome()
                delay(200)
                navController.navigateProductDetail(
                    rootRoute = GRAPH_HOME_ROUTE,
                    // You might need to provide a rootRoute object if your function requires it
                    productSlug = productSlug
                )
            }
        }
        onDeepLinkConsumed()
        // You could add else-if blocks here to handle other deep links like the payment-return one.
    }
}

// You will also need a navigation function that accepts only the slug
fun NavController.navigateProductDetailBySlug(productSlug: String) {
    // Assuming 'product_root' is the route name for the feature graph
    // The final route might look like: 'product_root/product?slug=some-slug'
    navigate("product_root/product?slug=$productSlug")
}