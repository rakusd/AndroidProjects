package com.rakusd.artoolkitproject

import android.app.Application
import org.artoolkit.ar6.base.assets.AssetHelper

class ARToolApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        val assetHelper = AssetHelper(assets)
        assetHelper.cacheAssetFolder(this,"Data")
    }
}