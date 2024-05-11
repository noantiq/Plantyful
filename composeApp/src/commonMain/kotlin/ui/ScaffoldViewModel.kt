package ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ScaffoldViewModel: ViewModel() {
    var title: String by mutableStateOf("")
    var actions: @Composable (RowScope.() -> Unit) by mutableStateOf({})
    var floatingActionButton: @Composable () -> Unit by mutableStateOf({})
    var snackBarMessage: String? by mutableStateOf(null)

    fun reset(title: String) {
        this.title = title
        actions = {}
        floatingActionButton = {}
    }
}