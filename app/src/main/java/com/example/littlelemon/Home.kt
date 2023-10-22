package com.example.littlelemon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun Home(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //Todo
    }
}

@Preview
@Composable
fun PreviewHome() {
    val context = LocalContext.current
    Home(navController = NavHostController(context))
}
