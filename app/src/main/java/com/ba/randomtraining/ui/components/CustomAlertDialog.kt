package com.ba.randomtraining.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
    title: String = "Notification",
    message: String,
    confirmButtonText: String = "OK",
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


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MyScreen() {
    var showDialog by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showDialog = true }) {
            Text("Show Dialog")
        }

        if (showDialog) {
            CustomAlertDialog(
                title = "Notification",
                message = "Some information here",
                confirmButtonText = "Got it",
                onConfirm = { showDialog = false },
                onDismiss = { showDialog = false }
            )
        }
    }
}
