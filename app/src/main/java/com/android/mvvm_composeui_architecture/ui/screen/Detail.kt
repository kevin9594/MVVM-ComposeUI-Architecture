@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.android.mvvm_composeui_architecture.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.android.mvvm_composeui_architecture.network.TestData
import com.android.mvvm_composeui_architecture.viewmodel.ListViewModel

/**
 * @author kevin
 * @create 2023/2/9
 * @description
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun Detail(viewModel: ListViewModel = viewModel()) {
    val argData: TestData? by viewModel.arg.observeAsState(initial = null)
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberImagePainter(
                data = argData?.imageUrl,
                builder = {
                    scale(Scale.FILL)
                }
            ),
            contentDescription = argData?.desc,
            modifier = Modifier.size(200.dp, 200.dp)
        )
        Text(
            text = argData?.name ?: ""
        )
    }
}