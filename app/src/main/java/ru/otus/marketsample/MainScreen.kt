package ru.otus.marketsample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import ru.otus.marketsample.details.feature.compose.DetailsContent
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.compose.ProductsContent
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.compose.PromoContent
import ru.otus.common.ui.R

private const val PRODUCTS_NAV_INDEX = 0
private const val PROMO_NAV_INDEX = 1

private const val UNKNOWN_INDEX = 2

private const val NAV_ICON_SIZE = 24

@Composable
fun MainScreen(viewModelsFactory: ViewModelProvider.Factory) {
    val backStack = remember { mutableStateListOf<ScreenKey>(ProductsScreen) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        bottomBar = {
            NavBar(
                selectedItem = when (backStack.lastOrNull()) {
                    ProductsScreen -> PRODUCTS_NAV_INDEX
                    PromoScreen -> PROMO_NAV_INDEX
                    else -> UNKNOWN_INDEX
                },
                onPromoListClick = {
                    if (backStack.lastOrNull() != PromoScreen) backStack.add(PromoScreen)
                },
                onProductListClick = {
                    if (backStack.lastOrNull() != ProductsScreen) backStack.add(ProductsScreen)
                }
            )
        }) { paddingValues ->

        NavDisplay(
            modifier = Modifier.padding(paddingValues),
            onBack = { backStack.removeLastOrNull() },
            backStack = backStack,
            entryProvider = entryProvider {
                entry<ProductsScreen> {
                    ProductsContent(
                        viewModelsFactory.create(ProductListViewModel::class.java)
                    )
                }
                entry<PromoScreen> {
                    PromoContent(
                        viewModelsFactory.create(PromoListViewModel::class.java)
                    )
                }
                entry<DetailsScreen> { DetailsContent(it.id) }
            }
        )
    }
}

@Composable
private fun NavBar(
    selectedItem: Int,
    onProductListClick: () -> Unit,
    onPromoListClick: () -> Unit
) {

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        NavigationBarItem(
            selected = selectedItem == PRODUCTS_NAV_INDEX,
            onClick = {
                onProductListClick()
            },
            label = {
                Text(
                    text = stringResource(ru.otus.marketsample.R.string.title_products),
                    color = colorResource(R.color.purple_500)
                )
            },
            icon = {
                Icon(
                    modifier = Modifier.size(NAV_ICON_SIZE.dp),
                    painter = painterResource(R.drawable.ic_list),
                    tint = colorResource(R.color.purple_500),
                    contentDescription = null
                )
            })
        NavigationBarItem(
            selected = selectedItem == PROMO_NAV_INDEX,
            onClick = {
                onPromoListClick()

            },
            label = {
                Text(
                    text = stringResource(ru.otus.marketsample.R.string.title_promo),
                    color = colorResource(R.color.purple_500)
                )
            },
            icon = {
                Icon(
                    modifier = Modifier.size(NAV_ICON_SIZE.dp),
                    painter = painterResource(R.drawable.ic_discount),
                    tint = colorResource(R.color.purple_500),
                    contentDescription = null
                )
            })
    }
}

sealed interface ScreenKey

data object PromoScreen : ScreenKey

data object ProductsScreen : ScreenKey

data class DetailsScreen(val id: String) : ScreenKey


