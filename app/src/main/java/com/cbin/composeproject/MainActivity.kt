package com.cbin.composeproject

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.cbin.composeproject.theme.*
import com.cbin.composeproject.ui.utils.RotateIcon
import com.cbin.composeproject.ui.utils.TestTags
import com.guru.fontawesomecomposelib.FaIcon

/**
 * @author Cbin
 * @CreateDate 2021/8/12
 * @describe
 */
class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //MobileAds.initialize(this)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainAppContent(appTheme)
            }
        }
    }

}


@Composable
fun BaseView(
    appThemeState: AppThemeState,
    systemUiController: SystemUiController?,
    content: @Composable () -> Unit
) {
    val color = when (appThemeState.pallet) {
        ColorPallet.GREEN -> green700
        ColorPallet.BLUE -> blue700
        ColorPallet.ORANGE -> orange700
        ColorPallet.PURPLE -> purple700
    }
    systemUiController?.setStatusBarColor(color = color, darkIcons = appThemeState.darkTheme)
    ComposeProjectTheme(darkTheme = appThemeState.darkTheme, colorPallet = appThemeState.pallet) {
        content()
    }
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainAppContent(appThemeState: MutableState<AppThemeState>) {
    //Default home screen state is always HOME
    val homeScreenState = rememberSaveable { mutableStateOf(BottomNavType.HOME) }
    val bottomNavBarContentDescription = stringResource(id = R.string.a11y_bottom_navigation_bar)

    Column {
        HomeScreenContent(
            homeScreen = homeScreenState.value,
            appThemeState = appThemeState,
            modifier = Modifier.weight(1f)
        )
        BottomNavigationContent(
            modifier = Modifier
                .semantics { contentDescription = bottomNavBarContentDescription }
                .testTag(TestTags.BOTTOM_NAV_TEST_TAG),
            homeScreenState = homeScreenState
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreenContent(
    homeScreen: BottomNavType,
    appThemeState: MutableState<AppThemeState>,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Crossfade(homeScreen) { screen ->
            Surface(color = MaterialTheme.colors.background) {
                when (screen) {
                    BottomNavType.HOME -> ToastUtil(BottomNavType.HOME.name)
                    BottomNavType.WIDGETS -> ToastUtil(BottomNavType.WIDGETS.name)
                    BottomNavType.ANIMATION -> ToastUtil(BottomNavType.ANIMATION.name)
                    BottomNavType.DEMOUI -> ToastUtil(BottomNavType.DEMOUI.name)
                    BottomNavType.TEMPLATE -> ToastUtil(BottomNavType.TEMPLATE.name)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationContent(
    modifier: Modifier = Modifier,
    homeScreenState: MutableState<BottomNavType>
) {
    var animate by remember { mutableStateOf(false) }
    BottomNavigation(modifier = modifier) {
        BottomNavigationItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Home,
                    tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                )
            },
            selected = homeScreenState.value == BottomNavType.HOME,
            onClick = {
                homeScreenState.value = BottomNavType.HOME
                animate = false
            },
            label = { Text(text = stringResource(id = R.string.navigation_item_home)) },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_HOME_TEST_TAG)
        )
        BottomNavigationItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Tools, tint = LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.WIDGETS,
            onClick = {
                homeScreenState.value = BottomNavType.WIDGETS
                animate = false
            },
            label = { Text(text = stringResource(id = R.string.navigation_item_widgets)) },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_WIDGETS_TEST_TAG)
        )
        BottomNavigationItem(
            icon = {
                RotateIcon(
                    state = animate,
                    asset = Icons.Default.PlayArrow,
                    angle = 720f,
                    duration = 2000
                )
            },
            selected = homeScreenState.value == BottomNavType.ANIMATION,
            onClick = {
                homeScreenState.value = BottomNavType.ANIMATION
                animate = true
            },
            label = { Text(text = stringResource(id = R.string.navigation_item_animation)) },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_ANIM_TEST_TAG)

        )
        BottomNavigationItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.LaptopCode, tint = LocalContentColor.current.copy(
                        alpha =
                        LocalContentAlpha.current
                    )
                )
            },
            selected = homeScreenState.value == BottomNavType.DEMOUI,
            onClick = {
                homeScreenState.value = BottomNavType.DEMOUI
                animate = false
            },
            label = { Text(text = stringResource(id = R.string.navigation_item_demoui)) },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_DEMO_UI_TEST_TAG)
        )
        BottomNavigationItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.LayerGroup, tint = LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.TEMPLATE,
            onClick = {
                homeScreenState.value = BottomNavType.TEMPLATE
                animate = false
            },
            label = { Text(text = stringResource(id = R.string.navigation_item_profile)) },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_TEMPLATE_TEST_TAG)

        )
    }
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val appThemeState = remember { mutableStateOf(AppThemeState(false, ColorPallet.GREEN)) }
    BaseView(appThemeState.value, null) {
        MainAppContent(appThemeState)
    }
}

fun ToastUtil(text: String) {
    Toast.makeText(
        App.applicationContext(),
        text,
        Toast.LENGTH_SHORT
    ).show()
}