import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.stringResource
import plantyful.composeapp.generated.resources.Res
import plantyful.composeapp.generated.resources.back
import ui.ScaffoldViewModel
import ui.overview.mediumPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantyScaffold(
    viewModel: ScaffoldViewModel,
    navController: NavController,
    content: @Composable (PaddingValues, Dp) -> Unit
){
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(viewModel.snackBarMessage) {
        viewModel.snackBarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.snackBarMessage = null
        }
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(viewModel.topAppBarState)
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = viewModel.title,
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    if (navController.currentBackStack.collectAsState().value.size >= 3){
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(Res.string.back)
                            )
                        }
                    }
                },
                actions = viewModel.actions,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = viewModel.floatingActionButton,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        content(paddingValues, mediumPadding)
    }
}