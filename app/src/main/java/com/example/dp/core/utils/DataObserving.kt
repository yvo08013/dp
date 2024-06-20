package com.example.dp.core.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dp.R
import com.example.dp.data.State
import com.example.dp.data.State.Utils.filterState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


/**
 * Collect provided [dataFlow] inside [observe] with [Lifecycle.State.STARTED], applying
 * [filterState] to filter out all states with the same content and invokes the appropriate
 * delegate depending on the collected state.
 *
 * @param dataFlow Flow of data to observe.
 * @param useLoadingData Determines whether to use data from [State.Loading] or not.
 * @param onStart Called when [State.Initial] was collected. Usage example: hide UI and show
 * loading spinner/placeholders.
 * @param onFinish Called after first [onSuccess] (or [State.Loading] if [useLoadingData] is **true**).
 * Usage example: hide loading spinner/placeholders and start animation for initialized UI.
 * @param onFailure Called if [State.Failure] was collected. Default is [logStateError]. Usage example:
 * show error placeholder if UI was not yet initialized or show snackbar if UI already initialized.
 * @param onSuccess Called each time when [State.Success] (or [State.Loading] if [useLoadingData] is **true**)
 * was collected. Usage example: initialize UI.
 */
inline fun <T> Fragment.observeState(
    dataFlow: Flow<State<T>>,
    useLoadingData: Boolean,
    crossinline onStart: () -> Unit = {},
    crossinline onFinish: () -> Unit = {},
    crossinline onSuccess: suspend (T) -> Unit,
    crossinline onFailure: (Int, Int, Throwable) -> Unit = ::logStateError,
) {
    var initializationCompleted = true
    observe(Lifecycle.State.STARTED) {
        dataFlow.filterState(useLoadingData).collect { state ->
            when {
                state is State.Initial -> {
                    onStart()
                    initializationCompleted = false
                }
                state is State.Failure -> {
                    onFailure(state.errorCode!!, state.messageID!!, state.throwable!!)
                }
                state.data != null && (state is State.Success || useLoadingData) -> {
                    onSuccess(state.data)
                    if (!initializationCompleted) {
                        onFinish()
                        initializationCompleted = true
                    }
                }
            }
        }
    }
}

/**
 * Shorthand for
 * ~~~
 * viewLifecycleOwner.lifecycleScope.launch {
 *     viewLifecycleOwner.lifecycle.repeatOnLifecycle(state) {
 *     ...
 *     }
 * }
 * ~~~
 */
inline fun Fragment.observe(
    state: Lifecycle.State,
    crossinline runnable: suspend () -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(state) {
            runnable()
        }
    }
}

/**
 * Logs error code, throwable and error message with [State.LOG_TAG] and shows SnackBar with error code
 */
fun Fragment.logStateError(errorCode: Int, messageID: Int, throwable: Throwable) {
    Log.d(State.LOG_TAG, "code: $errorCode message: ${getString(messageID)}", throwable)
    view?.let { view ->
        Snackbar.make(view, errorCode.toString(), Snackbar.LENGTH_SHORT).show()
    }
}

sealed interface DataSource<T> {
    data class Local<T>(val data: T) : DataSource<T>
    data class LocalFlow<T>(val flow: Flow<T>) : DataSource<T>
}

inline fun <localInT, modelT> fetchLocal(
    crossinline dataProvider: suspend () -> DataSource<localInT>,
    crossinline mapDelegate: (localInT) -> modelT
) = flow {
    emit(State.Initial())

    try {
        when (val source = dataProvider()) {
            is DataSource.Local     -> {
                emit(mapData(source.data, ErrorCodes.STATE_LOCAL_MAPPING, mapDelegate))
            }
            is DataSource.LocalFlow -> {
                source.flow.collect { data ->
                    emit(mapData(data, ErrorCodes.STATE_LOCAL_MAPPING, mapDelegate))
                }
            }
        }
    } catch (e: Exception) {
        emit(State.Failure(ErrorCodes.STATE_LOCAL_UNKNOWN, R.string.error_unknown, e))
    }
}

suspend inline fun <inT, outT> mapData(
    data: inT?,
    code: Int,
    crossinline mapDelegate: suspend (inT) -> outT
): State<outT> {
    return if (data == null || data is Unit || data is Collection<*> && data.isEmpty()) {
        State.Failure(ErrorCodes.STATE_NO_DATA, R.string.error_no_data, NoDataException())
    } else {
        try {
            State.Success(mapDelegate(data))
        } catch (e: Exception) {
            State.Failure(code, R.string.error_corrupted_data, e)
        }
    }
}
