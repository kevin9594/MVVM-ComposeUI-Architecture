package com.android.mvvm_composeui_architecture.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.mvvm_composeui_architecture.ui.theme.MVVMComposeUIArchitectureTheme
import com.android.mvvm_composeui_architecture.viewmodel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.mvvm_composeui_architecture.BaseActivity
import com.android.mvvm_composeui_architecture.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMComposeUIArchitectureTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Count()
                }
            }
        }
    }
}

@Composable
fun Count(viewModel: MainViewModel = viewModel()) {

    var number by remember { mutableStateOf(0) }

    LaunchedEffect(true) {
        viewModel.result.collect {
            number = it
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = number.toString(),
            fontSize = 64.sp,
            color = colorResource(id = R.color.purple_200)
        )
        Text(
            text = "CLICK",
            fontSize = 32.sp,
            color = Color.Black,
            modifier = Modifier.clickable {
                viewModel.plusOne()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        val context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                context.startActivity(Intent(context, ListActivity::class.java))
            }
        ) {
            Text(
                text = "Next Page",
                fontSize = 48.sp,
                color = Color(0x995A647C),
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_gray_right),
                contentDescription = "next page",
                modifier = Modifier.size(48.dp, 48.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MVVMComposeUIArchitectureTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Count()
        }
    }
}