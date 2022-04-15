package com.khue.joliecafejp.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.*
import com.google.accompanist.flowlayout.SizeMode.*
import com.google.accompanist.pager.*
import com.khue.joliecafejp.R
import com.khue.joliecafejp.navigation.nav_graph.AUTHENTICATION_ROUTE
import com.khue.joliecafejp.navigation.nav_screen.BottomBarScreen
import com.khue.joliecafejp.navigation.nav_screen.HomeSubScreen
import com.khue.joliecafejp.presentation.common.TextCustom
import com.khue.joliecafejp.presentation.components.CategoryButton
import com.khue.joliecafejp.presentation.components.HomeTopBar
import com.khue.joliecafejp.presentation.components.ImagePickerBottomSheetContent
import com.khue.joliecafejp.presentation.components.ProductItem
import com.khue.joliecafejp.presentation.screens.login.LoginViewModel
import com.khue.joliecafejp.presentation.viewmodels.HomeViewModel
import com.khue.joliecafejp.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val user by loginViewModel.user.collectAsState()
    val searchTextState by homeViewModel.searchTextState
    val pagerState = rememberPagerState()

    // Bottom Sheet
    val coroutineScope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    LaunchedEffect(user) {
        if (user == null) {
            navController.navigate(AUTHENTICATION_ROUTE) {
                popUpTo(BottomBarScreen.Home.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = state,
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            ImagePickerBottomSheetContent(
                coroutineScope = coroutineScope,
                modalBottomSheetState = state
            )
        }
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    userName = user!!.displayName,
                    userCoins = 300
                ) {
                    navController.navigate(HomeSubScreen.Notifications.route) {
                        launchSingleTop = true
                    }
                }
            },
            backgroundColor = MaterialTheme.colors.greyPrimary
        ) { paddingValues ->
            val padding = paddingValues.calculateBottomPadding()


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = EXTRA_LARGE_PADDING,
                        end = EXTRA_LARGE_PADDING,
                        bottom = padding
                    )
                    .verticalScroll(state = rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    text = searchTextState,
                    onCloseClicked = {
                        homeViewModel.updateSearchTextState(newValue = "")
                    },
                    onSearchClicked = { searchString ->

                    },
                    onTextChange = { newValue ->
                        homeViewModel.updateSearchTextState(newValue = newValue)
                    }
                )
                ImageSlider(pagerState = pagerState)
                Categories()
                TextCustom(
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(vertical = EXTRA_LARGE_PADDING),
                    content = stringResource(R.string.best_sellers),
                    color = MaterialTheme.colors.textColor2,
                    fontFamily = ralewayMedium
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                ) {
                    repeat(10) {
                        item {
                            ProductItem(
                                onAdd = {
                                    coroutineScope.launch { state.show() }
                                }
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))
                    }
                }

                Spacer(modifier = Modifier.height(58.dp))
            }
        }
    }
}

@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(SEARCH_CORNER_SHAPE)),
        color = MaterialTheme.colors.greyOpacity60Primary
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = MaterialTheme.colors.textColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colors.titleTextColor,
                disabledTextColor = MaterialTheme.colors.textColor,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.White,
                cursorColor = MaterialTheme.colors.textColor,
                textColor = MaterialTheme.colors.textColor,
                placeholderColor = MaterialTheme.colors.textColor
            ))
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(
    pagerState: PagerState
) {
    val images = listOf(
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
    )

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = EXTRA_LARGE_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        HorizontalPager(
            count = images.size,
            state = pagerState
        ) { page ->
            Image(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                    .height(139.dp)
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.6f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                painter = painterResource(id = images[page]),
                contentDescription = "Image slider",
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            inactiveColor = MaterialTheme.colors.textColor,
            activeColor = MaterialTheme.colors.textColor2
        )
    }
}

@Composable
fun Categories() {
    val categories = listOf(
        "All",
        "Coffee",
        "Tea",
        "Juice",
        "Pastry",
        "Coffee",
        "Coffee",
        "More"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextCustom(
            modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(vertical = EXTRA_LARGE_PADDING),
            content = stringResource(R.string.categories),
            color = MaterialTheme.colors.textColor2,
            fontFamily = ralewayMedium
        )
        FlowRow(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            mainAxisSize = Wrap,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
            mainAxisSpacing = CATEGORY_BUTTON_SPACING
        ) {
            categories.map {
                CategoryButton(title = it) {
                }
            }
        }
    }
}


