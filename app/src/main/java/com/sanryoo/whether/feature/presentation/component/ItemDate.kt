package com.sanryoo.whether.feature.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sanryoo.whether.feature.util.dayAndDateMonthFormat
import java.util.Date

@Composable
fun ItemDate(
    modifier: Modifier = Modifier,
    date: Date,
    isSelected: Boolean,
    backgroundColor: Color,
    contentColor: Color,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(backgroundColor)
            .then(
                if (isSelected)
                    Modifier.border(
                        width = 2.dp,
                        color = contentColor,
                        shape = RoundedCornerShape(15.dp)
                    )
                else
                    Modifier
            )
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = dayAndDateMonthFormat.format(date),
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
        )
    }
}