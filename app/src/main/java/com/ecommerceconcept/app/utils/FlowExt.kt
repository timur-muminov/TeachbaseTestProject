package com.ecommerceconcept.app.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.*

fun <T> Flow<T>.onEachChanged(action: suspend (T) -> Unit) = distinctUntilChanged().onEach(action)

fun <T, R> Flow<T>.mapChanged(transform: suspend (value: T) -> R) =
    map(transform).distinctUntilChanged()

fun <T> Flow<T>.launchWith(
    lifecycleOwner: LifecycleOwner, minActiveState: Lifecycle.State = Lifecycle.State.STARTED
) = flowWithLifecycle(
    lifecycleOwner.lifecycle, minActiveState
).launchIn(lifecycleOwner.lifecycleScope)

fun <T> Flow<T>.onEachLinkChanged(action: suspend (T) -> Unit) = distinctUntilChanged { old, new ->
    old === new
}.onEach(action)