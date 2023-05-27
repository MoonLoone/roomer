package com.example.roomer.presentation.screens.navbar_screens.profile_screen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.AccountScreenDestination
import com.example.roomer.presentation.screens.destinations.FollowsScreenDestination
import com.example.roomer.presentation.screens.destinations.SplashScreenDestination
import com.example.roomer.presentation.ui_components.BasicConfirmDialog
import com.example.roomer.presentation.ui_components.ProfileContentLine
import com.example.roomer.presentation.ui_components.SimpleAlertDialog
import com.example.roomer.utils.NavbarManagement
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scafState = rememberScaffoldState()

    NavbarManagement.showNavbar()
    Scaffold(scaffoldState = scafState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    top = dimensionResource(id = R.dimen.screen_top_margin),
                    start = dimensionResource(id = R.dimen.screen_start_margin),
                    end = dimensionResource(id = R.dimen.screen_end_margin)
                )
        ) {
            Text(
                text = stringResource(R.string.profile_title),
                textAlign = TextAlign.Start,
                fontSize = integerResource(id = R.integer.label_text).sp
            )
            AvatarProfile(
                urlValue = viewModel.avatarUrl,
                onBitmapValueChange = { it?.let { viewModel.changeAvatar(it) } }
            )
            ProfileContentLine(
                stringResource(R.string.account_label),
                R.drawable.account_icon,
                onClick = {
                    navigator.navigate(AccountScreenDestination)
                }
            )
            ProfileContentLine(
                text = stringResource(id = R.string.follows_profile),
                iconId = R.drawable.follow_fill,
                onClick = { navigator.navigate(FollowsScreenDestination) }
            )
            ProfileContentLine(
                stringResource(R.string.rating_label),
                R.drawable.rating_icon,
                onClick = {
                }
            )
            ProfileContentLine(
                stringResource(R.string.settings_label),
                R.drawable.settings_icon,
                onClick = {
                }
            )
            ProfileContentLine(
                stringResource(R.string.logout_label),
                R.drawable.logout_icon,
                onClick = {
                    viewModel.markStateLogout()
                }
            )
            if (state.isLogout) {
                BasicConfirmDialog(
                    text = stringResource(R.string.logout_confirm),
                    confirmOnClick = { viewModel.logout() },
                    dismissOnClick = { viewModel.clearState() }
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.primary_dark)
                )
            }
            if (state.error.isNotEmpty()) {
                SimpleAlertDialog(
                    title = stringResource(R.string.login_alert_dialog_text),
                    text = state.error
                ) {
                    viewModel.clearState()
                }
            }
        }
    }
    if (state.isLogout && state.success) navigator.navigate(SplashScreenDestination)
    if (!state.isLogout && state.success) viewModel.clearState()
}

@Composable
private fun AvatarProfile(
    enabled: Boolean = true,
    urlValue: String,
    onBitmapValueChange: (Bitmap?) -> Unit
) {
    val imageUri = rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
        imageUri.value?.let {
            if (Build.VERSION.SDK_INT < 28) {
                onBitmapValueChange(
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                )
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                onBitmapValueChange(ImageDecoder.decodeBitmap(source))
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageModifier = Modifier
            .align(Alignment.CenterHorizontally)
            .width(152.dp)
            .height(152.dp)
            .clip(CircleShape)
            .border(
                dimensionResource(id = R.dimen.ordinary_border),
                colorResource(id = R.color.primary_dark),
                CircleShape
            )
            .clickable {
                if (enabled) {
                    launcher.launch("image/*")
                    imageUri.value?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            onBitmapValueChange(
                                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                            )
                        } else {
                            val source = ImageDecoder.createSource(context.contentResolver, it)
                            onBitmapValueChange(ImageDecoder.decodeBitmap(source))
                        }
                    }
                }
            }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(urlValue)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.usual_client),
            contentDescription = stringResource(id = R.string.user_avatar_content_description),
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
        Icon(
            Icons.Filled.PhotoCamera,
            contentDescription = stringResource(R.string.upload_photo_content_description),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.ordinary_icon))
                .offset(50.dp, (-25).dp)
                .background(Color.White, shape = CircleShape)
        )
    }
}
