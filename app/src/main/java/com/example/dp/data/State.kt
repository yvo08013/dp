package com.example.dp.data

import android.util.Log
import com.example.dp.R
import com.example.dp.core.utils.ErrorCodes
import kotlinx.coroutines.flow.*


sealed class State<T>(
    val data: T? = null,
    val errorCode: Int? = null,
    val messageID: Int? = null,
    val throwable: Throwable? = null
) {
    /**
     * Means that loading has started. Must be emitted only once and immediately before any operations
     * to inform UI that loading has started as soon as possible.
     */
    class Initial<T> : State<T>()

    /**
     * Means that data was loaded successfully but it may be outdated and it's update expected. UI should
     * decide whether to use this data or wait until [Success] or [Error].
     */
    class Loading<T>(data: T) : State<T>(data)

    /**
     * Means that data was loaded successfully. This state is final meaning that there will be no subsequent
     * state emissions unless data source updates is expected outside of the flow that
     * provided this state.
     */
    class Success<T>(data: T) : State<T>(data)

    /**
     * Means that an error occurred while trying to load data. Contains error itself,
     * short message that describes error and error code (e.g. **4xx** for client errors, **5xx** for server
     * and **8xx** for app-specific operations such as data mapping). This state is final meaning that there
     * will be no subsequent emissions of states unless data source updates is expected outside of the
     * flow that provided this state.
     */
    class Failure<T>(errorCode: Int, messageID: Int, throwable: Throwable) : State<T>(
        errorCode = errorCode, messageID = messageID, throwable = throwable
    )


    companion object Utils {
        const val LOG_TAG = "state"

        /**
         * @param useLoadingData If true - return flow where all subsequent repetitions of the same value
         * are filtered out with [isEqualInsensitive], otherwise with [isEqual].
         */
        fun <T> Flow<State<T>>.filterState(useLoadingData: Boolean): Flow<State<T>> {
            return if (useLoadingData) {
                this.distinctUntilChanged(Utils::isEqualInsensitive)
            } else {
                this.distinctUntilChanged(Utils::isEqual)
            }
        }

        /**
         * Transforms flow of **State`<inT>`** into flow of **State`<outT>`** without losing State type,
         * applying provided [transform] if [data] **!= null**.
         *
         * * If an error occurs inside [transform] for [Success], [Failure] will be emitted into result flow
         * with [ErrorCodes.STATE_TRANSFORM_FAIL] error code.
         *
         * * If an error occurs inside [transform] for [Loading], the error will logged with [LOG_TAG] and
         * nothing will be emitted in order not to violate the contract "[Failure] is the final state".
         */
        inline fun <inT, outT> Flow<State<inT>>.transformData(
            crossinline transform: suspend (inT) -> outT
        ): Flow<State<outT>> = this.transform { state ->
            when (state) {
                is Initial -> emit(Initial())
                is Failure -> emit(Failure(state.errorCode!!, state.messageID!!, state.throwable!!))
                is Loading -> try {
                    emit(Loading(transform(state.data!!)))
                } catch (e: Exception) {
                    Log.d(LOG_TAG, "An error occurred during State transformation.", e)
                }
                is Success -> try {
                    emit(Success(transform(state.data!!)))
                } catch (e: Exception) {
                    emit(Failure(ErrorCodes.STATE_TRANSFORM_FAIL, R.string.error_corrupted_data, e))
                }
            }
        }

        /**
         * Immediately emits [Initial] and then re-emits everything from [provider]'s flow into result flow
         * using [emitAll]. [Initial] from [provider]'s flow will be filtered out to make sure that UI
         * will receive it only once.
         *
         * This function is useful when it's take some time for [provider] to produce flow.
         */
        inline fun <T> suspendFlowProvider(
            crossinline provider: suspend () -> Flow<State<T>>
        ): Flow<State<T>> = flow {
            emit(Initial())
            emitAll(provider().filterNot { it is Initial })
        }

        /**
         * - If both states are [Failure], it will compare them using [isEqualError].
         * - If both states are [Loading] or both are [Success], it will compare it's [data] using [isEqualData].
         * - If [old] is [Success] and [new] is [Loading], it will compare it's [data] too. It helps for
         * screens that use data from [Loading] to avoid redundant UI updates because in most cases if UI
         * already received data with state [Success], next data in [Loading] will be the same.
         */
        fun <T> isEqual(old: State<T>, new: State<T>): Boolean {
            return when {
                old is Failure && new is Failure   -> {
                    isEqualError(old, new)
                }
                (old is Loading && new is Loading) ||
                (old is Success && new is Success) ||
                (old is Success && new is Loading) -> {
                    isEqualData(old.data, new.data)
                }
                else                               -> {
                    old::class == new::class
                }
            }
        }

        /**
         * - If both states are [Failure], it will compare them using [isEqualError].
         * - If both states are [Loading] or [Success], it will compare it's [data] using [isEqualData].
         */
        fun <T> isEqualInsensitive(old: State<T>, new: State<T>): Boolean {
            return when {
                old is Failure && new is Failure   -> {
                    isEqualError(old, new)
                }
                (old is Loading || old is Success) &&
                (new is Loading || new is Success) -> {
                    isEqualData(old.data, new.data)
                }
                else                               -> {
                    old::class == new::class
                }
            }
        }

        /**
         * - If **T** is [Collection], data will be compared ignoring item's order.
         * - If **T** is not [Collection], data will be compared by [equals].
         *
         * Be careful using [isEqualData] without overriding [equals] as it may lead to unexpected behavior.
         * For example, removing and re-inserting same data into local DB with auto generated primary key
         * or having image URL with dynamic access token may lead to [isEqualData] returns false even if
         * logically data are same.
         */
        fun <T> isEqualData(old: T?, new: T?): Boolean {
            return if (old != null && new != null) {
                when {
                    old is Collection<*> && new is Collection<*>   -> {
                        old.size == new.size && old.toSet() == new.toSet()
                    }
                    old !is Collection<*> && new !is Collection<*> -> {
                        old == new
                    }
                    else                                           -> false
                }
            } else false
        }

        /**
         * Compare [Failure] by it's [errorCode], [messageID], [throwable] type and [throwable] message.
         */
        private fun <T> isEqualError(old: Failure<T>, new: Failure<T>) =
            old.errorCode == new.errorCode &&
            old.messageID == new.messageID &&
            old.throwable?.message == new.throwable?.message &&
            old.throwable?.javaClass == new.throwable?.javaClass

    }
}