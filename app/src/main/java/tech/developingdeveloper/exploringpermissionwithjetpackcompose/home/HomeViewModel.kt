package tech.developingdeveloper.exploringpermissionwithjetpackcompose.home

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _exportState = MutableStateFlow(false)
    val exportDealsState: StateFlow<Boolean> = _exportState

    fun onExportFlowInitialed() {
        Log.e(javaClass.name, "onExportFlowInitialed, initiating export data flow.")
        _exportState.value = true
    }

    fun exportData() {
        Log.e(javaClass.name, "onExportClick, Exporting data")
        _exportState.value = false
    }
}