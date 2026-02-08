package com.solutionium.core.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ProductImageCarousel(images: List<String>, modifier: Modifier = Modifier) {
    if (images.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = { images.size })
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            HorizontalPager(state = pagerState) { page ->
                AsyncImage(
                    model = images[page],
                    contentDescription = "Product image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(images.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(8.dp)
                            .background(
                                color = if (pagerState.currentPage == index) Color.White else Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text("No image available", style = MaterialTheme.typography.bodyMedium)
        }
    }
}