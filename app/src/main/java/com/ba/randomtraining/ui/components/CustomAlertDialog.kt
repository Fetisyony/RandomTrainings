package com.ba.randomtraining.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ba.randomtraining.R
import com.ba.randomtraining.ui.theme.ErrorScreenColor
import com.ba.randomtraining.ui.theme.typography

@Composable
fun DialogCustomCard(content: @Composable (ColumnScope.() -> Unit)) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardColors(
            contentColor = ErrorScreenColor,
            containerColor = Color.White,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
fun CustomAlertDialog(
    title: String = stringResource(R.string.default_dialog_title_notification),
    message: String,
    confirmButtonText: String = stringResource(R.string.dialog_confirm_button_default_text),
    onConfirm: () -> Unit,
    onDismiss: (() -> Unit)? = null
) {
        Dialog(
            onDismissRequest = { onDismiss?.invoke() },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            DialogCustomCard {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = title,
                        style = typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(text = message, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ErrorButton(text = confirmButtonText) { onConfirm() }
                    }
                }
            }
        }
}
