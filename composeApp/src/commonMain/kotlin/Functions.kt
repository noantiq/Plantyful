import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

fun composeNavigation(
    lifecycleOwner: LifecycleOwner,
    navigationFunction: () -> Unit
) {
    if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
        navigationFunction()
    }
}