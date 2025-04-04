package com.nmt.kmpwallpaper.presentation.viewmodel

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

expect class ViewModel() : InstanceKeeper.Instance, CoroutineScope {
    override val coroutineContext: CoroutineContext
}