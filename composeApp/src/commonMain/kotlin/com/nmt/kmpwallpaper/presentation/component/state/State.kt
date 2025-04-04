//package com.nmt.kmpwallpaper.presentation.component.state
//
//import androidx.compose.foundation.lazy.grid.LazyGridState
//import androidx.compose.runtime.Composable
//
//@Composable
//fun <T : Any> List<T>.rememberLazyGridState(): LazyGridState {
//    val state = androidx.compose.foundation.lazy.grid.rememberLazyGridState()
//
//    return when (itemCount) {
//        // Return a different LazyListState instance.
//        0 -> remember(this) {
//            LazyGridState(
//                firstVisibleItemIndex = state.firstVisibleItemIndex,
//                firstVisibleItemScrollOffset = state.firstVisibleItemScrollOffset
//            )
//        }
//        // Return rememberLazyListState (normal case).
//        else -> state
//    }
//}