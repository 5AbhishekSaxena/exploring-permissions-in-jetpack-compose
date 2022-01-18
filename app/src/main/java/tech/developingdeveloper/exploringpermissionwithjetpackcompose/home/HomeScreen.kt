package tech.developingdeveloper.exploringpermissionwithjetpackcompose.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@Composable
@ExperimentalPermissionsApi
fun HomeScreen(
    viewModel: HomeViewModel = HomeViewModel()
) {
    val exportState = viewModel.exportDealsState.collectAsState()

    Log.e("HomeScreen", "recomposing HomeScreen")

    HomeContent(
        exportState = exportState.value,
        onExportClick = {
            Log.e("DealListContent", "DealListScreen, onExportDeals clicked")
            viewModel.onExportFlowInitialed()
        },
        export = viewModel::exportData
    )
}