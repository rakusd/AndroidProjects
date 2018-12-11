package com.rakusd.artoolkitproject

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout.*
import org.artoolkit.ar6.base.ARActivity
import org.artoolkit.ar6.base.rendering.ARRenderer

class MainActivity:ARActivity() {
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
        return ARTRenderer()
    }
}