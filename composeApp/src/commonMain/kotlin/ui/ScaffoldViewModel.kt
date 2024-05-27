package ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ScaffoldViewModel: ViewModel() {
    var title: String by mutableStateOf("")
    var actions: @Composable (RowScope.() -> Unit) by mutableStateOf({})
    var floatingActionButton: @Composable () -> Unit by mutableStateOf({})
    var undoAction: (() -> Unit) ? by mutableStateOf(null)
    var snackBarMessage: String? by mutableStateOf(null)
    @OptIn(ExperimentalMaterial3Api::class)
    var topAppBarState by mutableStateOf(TopAppBarState(-Float.MAX_VALUE, 0f, 0f))

    @OptIn(ExperimentalMaterial3Api::class)
    fun reset(title: String) {
        this.title = title
        actions = {}
        floatingActionButton = {}
        topAppBarState = TopAppBarState(-Float.MAX_VALUE, 0f, 0f)
    }
}