package com.rakusd.artoolkitproject

import android.os.Bundle
import android.widget.FrameLayout
import org.artoolkit.ar6.base.ARActivity
import org.artoolkit.ar6.base.rendering.ARRenderer

class ARToolkitActivity:ARActivity() {
    val MY_PERMISSIONS_REQUEST_CAMERA:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),MY_PERMISSIONS_REQUEST_CAMERA)
        }*/
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
    }

    override fun supplyFrameLayout(): FrameLayout {

        return this.findViewById(R.id.mainLayout) as FrameLayout
    }

    override fun supplyRenderer(): ARRenderer {
        return ARTRenderer(this)
    }

}