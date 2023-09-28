package com.example.vahanassignment.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vahanassignment.Database.UniversityItem
import com.example.vahanassignment.ui.theme.Purple40
import com.example.vahanassignment.ui.theme.Purple80
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun UniversityListingComposable(viewModel: UniversityViewwmodel, onclick: (String?) -> Unit) {
    val UniversityList = viewModel.UniversityList.collectAsStateWithLifecycle()
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
    if (isLoading.value) {
    Box(contentAlignment = Alignment.Center,modifier=Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
    }
    else {
        LazyColumn {

            items(UniversityList.value.size) { university ->

                UniversityItem(
                    UniversityList.value[university],
                    onclick
                )
            }
        }
    }
}


@Composable
fun UniversityItem(item: UniversityItem, onclick: (String?) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Purple80, Purple40)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)

        ) {
            Text(
                text = item.name,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.country, color = Color.White, fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Normal
                )
                Text(
                    text = item.web_pages,
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Normal,
                    modifier = Modifier.clickable {
                        val encodedUrl =
                            URLEncoder.encode(item.web_pages, StandardCharsets.UTF_8.toString())
                        onclick(encodedUrl)
                    }
                )

            }
        }

    }
}
