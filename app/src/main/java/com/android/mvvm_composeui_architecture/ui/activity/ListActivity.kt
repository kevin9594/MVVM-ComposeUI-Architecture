package com.android.mvvm_composeui_architecture.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.mvvm_composeui_architecture.BaseActivity
import com.android.mvvm_composeui_architecture.network.TestData
import com.android.mvvm_composeui_architecture.route.ROUTE_DETAIL
import com.android.mvvm_composeui_architecture.route.ROUTE_LIST
import com.android.mvvm_composeui_architecture.ui.screen.Detail
import com.android.mvvm_composeui_architecture.ui.screen.TestItem
import com.android.mvvm_composeui_architecture.ui.theme.MVVMComposeUIArchitectureTheme
import com.android.mvvm_composeui_architecture.viewmodel.ListViewModel
import com.android.mvvm_composeui_architecture.ui.screen.List
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kevin
 * @create 2023/2/9
 * @description
 */
@AndroidEntryPoint
class ListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMComposeUIArchitectureTheme {
                Content()
            }
        }
    }

    @Composable
    fun Content(viewModel: ListViewModel = viewModel()) {
        viewModel.getData()
        val controller = rememberNavController()
        NavHost(
            navController = controller,
            startDestination = ROUTE_LIST
        ) {
            composable(ROUTE_LIST) {
                List(toDetail = { controller.navigate(ROUTE_DETAIL) }, viewModel)
            }
            composable(ROUTE_DETAIL) {
                Detail(viewModel)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MVVMComposeUIArchitectureTheme {
            val data = TestData(
                "Coco",
                "https://howtodoandroid.com/images/coco.jpg",
                "Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar",
                "Latest"
            )

            TestItem(
                modifier = Modifier,
                data = data
            )
        }
    }
}

