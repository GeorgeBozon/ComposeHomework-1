package ru.otus.marketsample.promo.feature.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.PromoState

@Composable
fun PromoContent(viewModel: PromoListViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initViewModel()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            state.hasError -> Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = state.errorProvider(context)
                )

            state.promoListState.isNotEmpty() -> LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.promoListState) {
                    PromoCard(modifier = Modifier.padding(10.dp), promo = it)
                }
            }
        }
    }
}

@Composable
private fun PromoCard(modifier: Modifier = Modifier, promo: PromoState) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = promo.image,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomStart)) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = promo.name,
                color = Color.White,
                fontSize = TextUnit(
                    25f,
                    TextUnitType.Sp
                )
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = promo.description,
                color = Color.White,
                fontSize = TextUnit(
                    14f,
                    TextUnitType.Sp
                )
            )
        }
    }
}