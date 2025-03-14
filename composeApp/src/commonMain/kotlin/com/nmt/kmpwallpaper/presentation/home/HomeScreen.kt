package com.nmt.kmpwallpaper.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_wallpaper
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun HomeScreen(
    component: HomeComponent
) {
    val uiState by component._uiState.subscribeAsState()
    Column(
        modifier = Modifier.fillMaxSize().background(
            color = MaterialTheme.colorScheme.background
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
        ) {
            Column(modifier = Modifier.fillMaxSize()
                , horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                Image(
                    imageVector = vectorResource(Res.drawable.ic_wallpaper),
                    contentDescription = "flash screen icon",
                    modifier = Modifier.padding(bottom = 20.dp),
                )

                Text(
                    "WallyArt",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 20.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    uiState.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 20.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                LinearProgressIndicator(
                    modifier = Modifier.height(5.dp).width(100.dp),
                    color = ProgressIndicatorDefaults.linearColor,
                    trackColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}