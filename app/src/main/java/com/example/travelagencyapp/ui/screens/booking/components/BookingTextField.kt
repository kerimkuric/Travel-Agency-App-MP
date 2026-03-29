package com.example.travelagencyapp.ui.screens.booking.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BookingTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String?,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            singleLine = true,
            isError = errorText != null,
            modifier = Modifier.fillMaxWidth(),
        )

        if (errorText != null) {
            ValidationErrorText(text = errorText)
        }
    }
}

