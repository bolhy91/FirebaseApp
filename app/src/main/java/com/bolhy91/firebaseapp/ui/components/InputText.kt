package com.bolhy91.firebaseapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolhy91.firebaseapp.ui.theme.grayColor200
import com.bolhy91.firebaseapp.ui.theme.inputColor

@Composable
fun InputText(
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    valueText: MutableState<String>,
    onChangeText: (String) -> Unit
) {
    var isError by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = valueText.value,
        onValueChange = {
            valueText.value = it
            onChangeText(valueText.value)
            isError = valueText.value.isEmpty() || valueText.value.isBlank()
        },
        textStyle = TextStyle(fontSize = 18.sp),
        modifier = Modifier
            .fillMaxWidth()
            .background(inputColor),
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(color = grayColor200, fontSize = 18.sp)
            )
        },
        visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (keyboardType == KeyboardType.Password) {
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Visibility, contentDescription = "visibility")
                }
            }
        },
        singleLine = true,
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    )
}

@Preview(showBackground = true)
@Composable
fun InputTextPreview() {
    Surface(
        modifier = Modifier
            .background(Color.White)
            .padding(20.dp)
    ) {
//        InputText("Enter your email","", {})
    }
}