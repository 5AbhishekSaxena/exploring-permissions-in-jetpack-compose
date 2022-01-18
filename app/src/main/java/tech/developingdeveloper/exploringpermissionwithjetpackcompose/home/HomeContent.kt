package tech.developingdeveloper.exploringpermissionwithjetpackcompose.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import tech.developingdeveloper.exploringpermissionwithjetpackcompose.ui.theme.ExploringPermissionWithJetpackComposeTheme


@Composable
@ExperimentalPermissionsApi
fun HomeContent(exportState: Boolean = false, onExportClick: () -> Unit, export: () -> Unit) {

    Log.e("HomeContent", "recomposing HomeContent")

    Scaffold(
        topBar = {
            HomeTopBar(onExportClick = onExportClick)


        }) { innerPadding ->

        HomeBody(
            modifier = Modifier
                .padding(innerPadding)
        )

        if (exportState) {
            ShareContent(export)
        }
    }
}

@Composable
private fun HomeTopBar(onExportClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Home")
        },
        actions = {
            IconButton(onClick = onExportClick) {
                Icon(
                    Icons.Default.Share,
                    contentDescription = "Export"
                )
            }
        }
    )
}

@Composable
fun HomeBody(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(text = "Some Home Content")
    }
}

@Composable
@ExperimentalPermissionsApi
fun ShareContent(exportDeals: () -> Unit, navigateToSettingsScreen: () -> Unit = {}) {

    // Track if the user doesn't want to see the rationale any more.
    val doNotShowRationale = rememberSaveable { mutableStateOf(false) }

    val writeExternalStoragePermissionState =
        rememberPermissionState(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    // permission denied rationale can be shown with snackbar as well,
    // without adding any composable to the layout.

    PermissionRequired(
        permissionState = writeExternalStoragePermissionState,
        permissionNotGrantedContent = {
            if (doNotShowRationale.value) {
                Text("Feature not available")
            } else {
                Rationale(
                    onDoNotShowRationale = { doNotShowRationale.value = true },
                    onRequestPermission = { writeExternalStoragePermissionState.launchPermissionRequest() }
                )
            }
        },
        permissionNotAvailableContent = {
            PermissionDenied(navigateToSettingsScreen)
        }
    ) {
        Text("Write external storage permission Granted")
        exportDeals()
    }
}

@Composable
private fun Rationale(
    onDoNotShowRationale: () -> Unit,
    onRequestPermission: () -> Unit
) {
    Column {
        Text("The write external storage permission is important for this app. Please grant the permission.")
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = onRequestPermission) {
                Text("Request permission")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = onDoNotShowRationale) {
                Text("Don't show rationale again")
            }
        }
    }
}

@Composable
private fun PermissionDenied(
    navigateToSettingsScreen: () -> Unit
) {
    Column {
        Text(
            "Write external storage is permission denied. See this FAQ with information about why we " +
                    "need this permission. Please, grant us access on the Settings screen."
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = navigateToSettingsScreen) {
            Text("Open Settings")
        }
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@ExperimentalPermissionsApi
@Suppress("UnusedPrivateMember")
private fun HomeContentPreview() {
    ExploringPermissionWithJetpackComposeTheme {
        Surface {
            HomeContent(
                exportState = false,
                onExportClick = {},
                export = {}
            )
        }
    }
}