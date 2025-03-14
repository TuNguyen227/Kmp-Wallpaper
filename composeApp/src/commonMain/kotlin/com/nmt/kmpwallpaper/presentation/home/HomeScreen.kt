package com.nmt.kmpwallpaper.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_wallpaper
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun HomeScreen(
    component: HomeComponent
) {
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)
    ) {
        Image(
            imageVector = vectorResource(Res.drawable.ic_wallpaper),
            contentDescription = "flash screen icon",
            modifier = Modifier.padding(top = 20.dp).align(Alignment.BottomCenter),
        )
    }
}