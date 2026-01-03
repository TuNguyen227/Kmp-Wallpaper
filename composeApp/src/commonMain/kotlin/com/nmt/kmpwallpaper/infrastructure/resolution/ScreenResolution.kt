package com.nmt.kmpwallpaper.infrastructure.resolution

import io.github.tunguyen227.PlatformContext

object Size {
    var width: Int = 0
    var height: Int = 0
}

expect fun getDeviceSize(context: PlatformContext): Size
