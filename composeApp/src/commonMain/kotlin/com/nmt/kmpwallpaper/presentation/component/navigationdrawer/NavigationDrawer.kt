package com.nmt.kmpwallpaper.presentation.component.navigationdrawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmt.kmpcore.infrastructure.provider.Language
import com.nmt.kmpcore.infrastructure.provider.LanguageProvider
import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_back
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_forward
import com.nmt.kmpwallpaper.model.AppSetting
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun DrawerHeader(
    shouldShowIcon: Boolean = false,
    onBack: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (shouldShowIcon) {
            Icon(
                modifier = Modifier.scale(0.6f).clickable { onBack() }.align(Alignment.TopStart).padding(start = 16.dp)
                ,
                imageVector = vectorResource(Res.drawable.ic_back),
                contentDescription = "icon back settings",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(text = "Settings", style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun DrawerBody(
    items: List<AppSetting>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (AppSetting) -> Unit,
    viewingDetail: Boolean = false,
    onLanguageClick: (Language) -> Unit = {}
) {
    Surface(
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 10.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        var clickedSettings by remember {
            mutableStateOf<AppSetting?>(null)
        }
        when {
            (clickedSettings is AppSetting.Language ||
            clickedSettings is AppSetting.Rating ||
            clickedSettings is AppSetting.Privacy ||
            clickedSettings is  AppSetting.TermNCondition) && viewingDetail  -> {
                DrawerSettingDetail(
                    setting = clickedSettings ?: AppSetting.Language(),
                    onItemClick = onLanguageClick
                )
            }
            else -> {
                LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(items) { item ->
                        DrawerItem(
                            modifier = Modifier.clickable {
                                onItemClick(item)
                                clickedSettings = item
                            },
                            content = item.name,
                            iconVector = vectorResource(item.icon),
                            endContent = {
                                when (item) {
                                    is AppSetting.Language -> {
                                        Icon(
                                            vectorResource(LanguageProvider.getLocaleLanguage().icon),
                                            contentDescription = "locale language icon",
                                            tint = Color.Unspecified
                                        )
                                    }

                                    else -> {
                                        Icon(
                                            vectorResource(Res.drawable.ic_forward),
                                            contentDescription = "icon forward",
                                            tint = Color.Unspecified
                                        )
                                    }
                                }
                            }
                        )

                        items.lastOrNull()?.let { nonNullLastItem ->
                            if (nonNullLastItem != item) {
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                                    thickness = 0.5.dp,
                                    color = MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.25f
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerSettingDetail(
    setting: AppSetting,
    onItemClick: (Language) -> Unit
    ) {
    when(setting) {
        is AppSetting.Language -> {
            LazyColumn(modifier = Modifier.padding(vertical = 10.dp),verticalArrangement = Arrangement.spacedBy(10.dp)) {
                val list = LanguageProvider.getAllSupportLanguages()
                items(list) {
                    val name = it::class.simpleName ?: it.code
                    DrawerItem(
                        modifier = Modifier.padding(horizontal = 10.dp).clickable {
                            onItemClick(it)
                        },
                        content = name,
                        iconDrawable = it.icon
                    )
                    list.lastOrNull()?.let { nonNullLastItem ->
                        if (nonNullLastItem != it) {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                                thickness = 0.5.dp,
                                color = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.25f
                                )
                            )
                        }
                    }
                }
            }
        }
        else -> {}
    }
}

@Composable
private fun DrawerItem(
    modifier: Modifier = Modifier,
    content: String,
    endContent: @Composable () -> Unit = {},
    iconVector: ImageVector? = null,
    iconDrawable: DrawableResource? = null
) {
    Card(
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            iconDrawable?.let {
                Icon(
                    modifier = Modifier.padding(start = 10.dp),
                    imageVector = vectorResource(it),
                    contentDescription = "Drawer item icon $content",
                    tint = Color.Unspecified
                )
            }
            iconVector?.let {
                Icon(
                    modifier = Modifier.padding(start = 10.dp),
                    imageVector = iconVector,
                    contentDescription = "Drawer item icon $content",
                    tint = Color.Unspecified
                )
            }
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f).padding(start = 4.dp),
                fontWeight = FontWeight.Bold
            )
            endContent.invoke()
        }
    }
}