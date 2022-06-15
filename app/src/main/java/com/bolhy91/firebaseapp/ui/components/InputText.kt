package com.bolhy91.firebaseapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolhy91.firebaseapp.ui.theme.grayColor200
import com.bolhy91.firebaseapp.ui.theme.inputColor

@Composable
fun InputText(placeholder: String, onChangeText: (String) -> Unit) {
    var inputValue by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = inputValue,
        onValueChange = {
            inputValue = it
            onChangeText(inputValue)
        },
        modifier = Modifier
            .background(inputColor),
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(color = grayColor200, fontSize = 15.sp)
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
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
        InputText("Enter your email", {})
    }
}