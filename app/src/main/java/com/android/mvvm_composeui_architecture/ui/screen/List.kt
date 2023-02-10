package com.android.mvvm_composeui_architecture.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.android.mvvm_composeui_architecture.network.TestData
import com.android.mvvm_composeui_architecture.viewmodel.ListViewModel

/**
 * @author kevin
 * @create 2023/2/9
 * @description
 */
@Composable
fun List(toDetail: () -> Unit = {}, viewModel: ListViewModel = viewModel()) {
    val list: List<TestData> by viewModel.list.observeAsState(initial = emptyList())

    if (list.isEmpty()) return

    LazyColumn {
        itemsIndexed(items = list) { _, data ->
            TestItem(
                modifier = Modifier.clickable {
                    viewModel.sendData(data)
                    toDetail()
                },
                data = data
            )
        }
    }
}

@Composable
fun TestItem(modifier: Modifier, data: TestData) {
    Surface(
        modifier =
        modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = rememberImagePainter(
                    data = data.imageUrl,
                    builder = {
                        scale(coil.size.Scale.FILL)
                        placeholder(coil.compose.base.R.drawable.notification_action_background)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = data.desc,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.2f)
            )
            Spacer(
                modifier = Modifier.width(4.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .weight(0.8f)
            ) {
                Text(
                    text = data.name,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = data.category,
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(4.dp)
                )
                Text(
                    text = data.desc,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}