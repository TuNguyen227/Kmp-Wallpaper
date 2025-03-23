package com.nmt.kmpwallpaper.presentation.component.navigationdrawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmt.kmpcore.infrastructure.provider.LanguageProvider
import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_forward
import org.jetbrains.compose.resources.vectorResource

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun DrawerBody(
    items: List<Settings>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (Settings) -> Unit
) {
    Surface(
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 10.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { item ->
                DrawerItem(
                    content = item.title,
                    icon = vectorResource(item.icon),
                    endContent = {
                        when(item) {
                            Settings.LANGUAGE -> {
                                Icon(
                                    vectorResource(LanguageProvider.getLocaleLanguage().icon),
                                    contentDescription = "locale language icon",
                                    tint = Color.Unspecified
                                )
                            }
                            Settings.NOTIFICATIONS -> {
                                var isChecked by rememberSaveable {
                                    mutableStateOf(true)
                                }
                                Switch(
                                    checked = isChecked,
                                    onCheckedChange = {
                                        isChecked = !isChecked
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.background,
                                        uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                                        uncheckedTrackColor = Color.Gray.copy(alpha = 0.25f),
                                        checkedTrackColor = Color.Gray
                                    )
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

@Composable
private fun DrawerItem(
    content : String,
    endContent: @Composable () -> Unit = {},
    icon: ImageVector
) {
    Card(
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = "Drawer item icon $content"
            )
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