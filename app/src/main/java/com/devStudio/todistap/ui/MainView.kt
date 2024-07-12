package com.devStudio.todistap.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView
import com.devStudio.todistap.MainActivity
import com.devStudio.todistap.R
import com.devStudio.todistap.ShowInterstitialAd
import com.devStudio.todistap.data.model.Todo
import com.devStudio.todistap.data.model.enums.TodoStatus
import com.devStudio.todistap.data.viewModel.MainViewModel
import com.devStudio.todistap.ui.theme.accentD
import com.devStudio.todistap.ui.theme.poppins
import com.devStudio.todistap.ui.theme.primaryD
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.launch


private lateinit var getAllData: State<List<Todo>>

const val AdmobBannerId =
    "ca-app-pub-3967320234242948/7774774897" // ca-app-pub-3940256099942544/6300978111

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView() {

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    val viewModel = MainActivity.viewModel
    getAllData = viewModel.getAll.collectAsState(initial = listOf())

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        shape =
                            RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)
                        clip = true
                    },
                navigationIcon = {
                    IconButton(
                        onClick = { scope.launch { scaffoldState.drawerState.open() } },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = stringResource(id = R.string.menu),
                                tint = Color.White
                            )
                        })
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppins
                    )
                },
                backgroundColor = primaryD,
            )
        },
        drawerContent = {
            DrawerView()
        },
        drawerShape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
        drawerBackgroundColor = MaterialTheme.colorScheme.background,
        backgroundColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            val colors = listOf(primaryD, accentD)
            val gradient =
                Brush.linearGradient(colors, Offset.Zero, Offset.Infinite, TileMode.Mirror)

            ShowInterstitialAd()

            Column()
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .border(width = 1.dp, brush = gradient, shape = RoundedCornerShape(10.dp))
                        .background(Color.Transparent, RoundedCornerShape(10.dp))
                ) {

                    // dummy message to the user 
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        text = "This app is in under development phase if your found some error or bug please report",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = poppins
                    )
                }

                // original content of the user

                UserInputItemUi(viewModel = MainActivity.viewModel)


                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                ) {
                    items(getAllData.value) { item ->


                        val dismissState = rememberDismissState(confirmStateChange = { it ->
                            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                scope.launch {
                                    viewModel.delete(item)
                                }
                            }
                            true
                        })

                        SwipeToDismiss(
                            state = dismissState,
                            background = {
                                val color = animateColorAsState(
                                    if (dismissState.dismissDirection == DismissDirection.StartToEnd) Color.Red else Color.Red,
//                                    Color.Red,
                                    label = ""
                                )
                                val alignment =
                                    if (dismissState.dismissDirection == DismissDirection.StartToEnd) Alignment.CenterStart else Alignment.CenterEnd
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 0.dp, start = 12.dp, end = 12.dp)
                                        .background(
                                            color = color.value,
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    contentAlignment = alignment,
                                ) {
                                    Row {
                                        Spacer(modifier = Modifier.width(20.dp))
                                        androidx.compose.material.Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                    }

                                }
                            },
                            directions = setOf(
                                DismissDirection.StartToEnd,
                                DismissDirection.EndToStart
                            ),
                            // it matters on distance from the corners while deleting item
                            dismissThresholds = { FractionalThreshold(0.25f) },
                            dismissContent = {
                                UserDataTodosUi(todo = item, viewModel = viewModel)
                            })


                    }
                }

                AdmobBanner()

            }

        }
    }

}

@Composable
fun AdmobBanner(modifier: Modifier = Modifier) {
    Box(modifier = Modifier, contentAlignment = Alignment.BottomCenter) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                // on below line specifying ad view.
                AdView(context).apply {
                    // on below line specifying ad size
                    //adSize = AdSize.BANNER
                    // on below line specifying ad unit id
                    // currently added a test ad unit id.
                    setAdSize(AdSize.BANNER)
                    adUnitId = AdmobBannerId
                    // calling load ad to load our ad.
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }

}

@Composable
fun UserInputItemUi(viewModel: MainViewModel) {

    val userInput = remember { mutableStateOf("") }

    var isHintDisplayed by remember { mutableStateOf(true) }

    var showAd by remember { mutableStateOf(false) }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.unchecked_box),
                    contentDescription = "item status",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }*/

            Spacer(modifier = Modifier.width(8.dp))


            Box(
                modifier = Modifier.height(24.dp),
                contentAlignment = Alignment.CenterStart
            ) {

                if (showAd) {
                    ShowInterstitialAd()
                }

                BasicTextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    value = userInput.value,
                    onValueChange = {
                        userInput.value = it
                        isHintDisplayed = it.isEmpty()
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = poppins,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                    ),
                    cursorBrush = SolidColor(primaryD)

                )

                if (isHintDisplayed) {
                    Text(
                        text = stringResource(id = R.string.input_hint),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        fontFamily = poppins
                    )
                }
            }
        }



        if (userInput.value.isNotEmpty()) {
            IconButton(
                onClick = {
                    showAd = true
                    viewModel.insert(Todo(userInput.value))
                    userInput.value = ""
                    isHintDisplayed = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Done",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


@Composable
fun UserDataTodosUi(todo: Todo, viewModel: MainViewModel) {

    val icon =
        if (todo.status == TodoStatus.Done) R.drawable.checked_box else R.drawable.unchecked_box


    val newStatus = if (todo.status == TodoStatus.NotDone) TodoStatus.Done else TodoStatus.NotDone


    val updated = todo.copy(status = newStatus)



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 0.dp, start = 12.dp, end = 12.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(11.dp)
            )
            .graphicsLayer {
                shape = RoundedCornerShape(11.dp)
                clip = true
            }
            .clipToBounds()
            .clickable { viewModel.update(updated) },
//            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            // performing status changes
            viewModel.update(updated)

        }) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "item status",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .padding(start = 2.dp, end = 2.dp),
                text = todo.text,
                color = if (todo.status == TodoStatus.Done) MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.7f
                ) else MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Start,
                fontFamily = poppins
            )


            if (todo.status == TodoStatus.Done) {
                Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxWidth()) {
                    Divider(
                        modifier = Modifier
                            .width(todo.text.length * 15.dp)
                            .align(Alignment.CenterStart),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }
            }

            /*IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = {
                // delete the clicked item
                viewModel.delete
            }) {
                Icon(imageVector = Icons.TwoTone.Delete, contentDescription = "delete", tint = MaterialTheme.colorScheme.onBackground)
            }*/

        }
        Spacer(modifier = Modifier.width(2.dp))


        Spacer(modifier = Modifier.width(4.dp))

    }
}


