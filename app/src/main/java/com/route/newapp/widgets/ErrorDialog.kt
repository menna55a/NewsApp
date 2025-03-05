package com.route.newsappc41gsunwed.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.route.newapp.R


@Composable
fun ErrorDialog(errorState:String, modifier: Modifier = Modifier, onDismiss: () -> Unit ) {
    AlertDialog(onDismissRequest = { onDismiss() }, confirmButton = {
        TextButton(onClick = { onDismiss()}) {
            Text(text = stringResource(R.string.ok))
        }
    }, containerColor = Color.White, text = {
        Text(text = errorState, color = Color.Black, fontSize = 14.sp)
    }
    )
}
