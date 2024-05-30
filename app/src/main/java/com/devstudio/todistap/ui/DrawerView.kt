package com.devstudio.todistap.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devstudio.todistap.MainActivity
import com.devstudio.todistap.R
import com.devstudio.todistap.Utils.Prefs
import androidx.compose.ui.platform.LocalContext


const val receipt = "zunaidsultan786@gmail.com"

@Composable
fun DrawerView(modifier: Modifier = Modifier) {

    val context = LocalContext.current



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Spacer(modifier = Modifier.height(40.dp))
        Image(
            modifier = Modifier
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                    shape = CircleShape
                )
                .size(150.dp)
                .graphicsLayer {
                    shape = CircleShape
                    clip = true
                },
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = "app icon"
        )
        Spacer(modifier = Modifier.height(20.dp))

        ThemeSwitchOption()

        DrawerItems(
            itemText = "Report bug",
            itemIcon = R.drawable.ic_bug_l,
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(receipt))
                    putExtra(Intent.EXTRA_SUBJECT, "Found a bug in Tod ist App")
                    putExtra(Intent.EXTRA_TEXT, "Aslamualikum")
                }

                context.startActivity(intent)
            }
        )

    }
}

@Composable
fun DrawerItems(
    modifier: Modifier = Modifier,
    itemText: String,
    itemIcon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            }
            .padding(start = 6.dp, end = 6.dp, top = 12.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = itemText, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)

        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = itemIcon),
            contentDescription = itemText,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
fun ThemeSwitchOption(modifier: Modifier = Modifier) {

    var checked by remember { mutableStateOf(Prefs.isDarkTheme) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                Prefs.isDarkTheme = !Prefs.isDarkTheme
                MainActivity.viewModel.setTheme(Prefs.isDarkTheme)
                checked = MainActivity.viewModel.theme.value
            }
            .padding(start = 6.dp, end = 6.dp, top = 12.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Switch Theme",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )


        ThemeSwitch(
            isChecked = checked,
            width = 38.dp,
            height = 20.dp,
            cornerRadius = 25.dp,
            borderColor = MaterialTheme.colorScheme.onBackground,
            checkedTrackColor = Color.Transparent,
            uncheckedTrackColor = Color.Transparent,
            checkedIcon = R.color.white,
            uncheckedIcon = R.color.black
        ) {
            checked = it
            Prefs.isDarkTheme = checked
            MainActivity.viewModel.setTheme(checked)
        }

    }
}
