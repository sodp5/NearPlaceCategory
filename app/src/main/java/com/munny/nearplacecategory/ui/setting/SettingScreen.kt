package com.munny.nearplacecategory.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.munny.nearplacecategory.values.Colors.Lilac900
import com.munny.nearplacecategory.values.Colors.TextBlack
import com.munny.nearplacecategory.values.Colors.TextGray

@Composable
fun SettingScreen(
    meter: Int,
    timeCost: Int,
    onComplete: () -> Unit,
    onTextChanged: (String) -> Unit
) {
    Scaffold(
        topBar = {
            SettingTopBar(
                onComplete = onComplete
            )
        }
    ) { innerPadding ->
        SettingContent(
            meter = meter,
            timeCost = timeCost,
            onTextChanged = onTextChanged,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun SettingTopBar(
    onComplete: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "거리설정",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextBlack
            )
        },
        backgroundColor = Color.White,
        actions = {
            Text(
                text = "완료",
                fontSize = 18.sp,
                color = Lilac900,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickable(onClick = onComplete)
            )
        }
    )
}

@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
    meter: Int,
    timeCost: Int,
    onTextChanged: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 12.dp)
    ) {
        Text(
            text = "내 주변 반경",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = TextBlack
        )
        Text(
            text = "천천히 걸을 때 시속 4km의 속도로 걷는다고 합니다.",
            fontSize = 14.sp,
            color = TextGray
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = meter.toString(),
                onValueChange = onTextChanged,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = TextBlack
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.None
                )
            )
            Text(
                text = "m",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextGray
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = "약 ${timeCost}분",
                fontSize = 16.sp,
                color = TextBlack
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen(
        meter = 500,
        timeCost = 6,
        onComplete = {},
        onTextChanged = {}
    )
}