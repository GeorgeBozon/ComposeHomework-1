package ru.otus.marketsample.products.feature.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.otus.common.ui.R
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.ProductState

@Composable
fun ProductsContent(viewModel: ProductListViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initViewModel()
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            state.hasError -> Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = state.errorProvider(context)
                )

            state.productListState.isNotEmpty() -> LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.productListState) {
                    ProductCard(modifier = Modifier.padding(16.dp), product = it)
                }
            }
        }
    }
}

@Composable
private fun ProductCard(modifier: Modifier = Modifier, product: ProductState) {
    Row(
        modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top
    ) {
        Box(modifier = Modifier.weight(1f)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                model = product.image,
                contentDescription = null
            )
            if (product.discount.isNotBlank()) {
                DiscountBadge(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 8.dp, top = 8.dp),
                    text = product.discount
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 8.dp)

        ) {
            val rubStr = stringResource(ru.otus.marketsample.R.string.price_rub)
            Text(
                modifier = Modifier.align(Alignment.TopStart),
                fontFamily = FontFamily.SansSerif,
                text = product.name,
                fontSize = TextUnit(
                    18f,
                    TextUnitType.Sp
                )
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(
                        color = Color(0xFFFFF3E0),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                color = colorResource(R.color.purple_500),
                text = "${product.price} $rubStr",
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(
                    16f,
                    TextUnitType.Sp
                )
            )
        }
    }
}

@Composable
private fun DiscountBadge(modifier: Modifier, text: String) {
    val shape = remember {
        RoundedCornerShape(
            topStart = 20.dp,
            bottomEnd = 10.dp,
            bottomStart = 20.dp,
            topEnd = 3.dp
        )
    }

    val gradientColors =
        listOf(colorResource(R.color.purple_200), colorResource(R.color.purple_500))
    Box(
        modifier = modifier
            .border(width = 2.dp, color = Color.White, shape = shape)
            .background(
                brush = Brush.horizontalGradient(colors = gradientColors),
                shape = shape
            )

    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = TextUnit(14f, TextUnitType.Sp),
            color = Color.White,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp)
        )
    }
}
