package com.nmt.kmpwallpaper.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ButtonIcon(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    content: String,
    color: CardColors = CardDefaults.cardColors(),
    iconColor: Color,
    onClick: (String) -> Unit = {},
    icon: DrawableResource,
) {
    var isClicked by rememberSaveable {
        mutableStateOf(false)
    }
    Card(
        modifier = modifier,
        onClick = {
            isClicked = !isClicked
            onClick(content)
        },
        shape = shape,
        colors = color,
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.padding(5.dp)) {
                Icon(
                    imageVector = vectorResource(icon),
                    contentDescription = "icon button",
                    modifier = Modifier.align(Alignment.CenterHorizontally).size(24.dp),
                    tint = iconColor,
                )
                Text(
                    text = content,
                    modifier =
                        Modifier.semantics {
                            this.contentDescription = "button content"
                        },
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
