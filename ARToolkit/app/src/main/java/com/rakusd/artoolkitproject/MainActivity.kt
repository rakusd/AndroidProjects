package com.rakusd.artoolkitproject

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchButton.setOnClickListener(this::goToArtoolkit)
        creatorButton.setOnClickListener(this::showCreatorInfo)
        markerButton.setOnClickListener(this::showMarker)
        detailsButton.setOnClickListener(this::showDetails)
    }

    fun goToArtoolkit(view: View):Unit {
        val intent= Intent(this,ARToolkitActivity::class.java)
        if(packageManager.queryIntentActivities(intent,0).isNotEmpty()) {
            startActivity(intent)
        } else {
            Toast.makeText(this,"Something went wrong. Try again.",Toast.LENGTH_SHORT)
        }
    }

    fun showCreatorInfo(view: View):Unit {
        val builder=AlertDialog.Builder(view.context)
            .setView(layoutInflater.inflate(R.layout.creator_layout,null))
            .setTitle("About me")
            .setNeutralButton("Hide",null)
            .show()
    }

    fun showMarker(view: View):Unit {
        val builder=AlertDialog.Builder(view.context)
            .setView(layoutInflater.inflate(R.layout.marker_layout,null))
            .setNeutralButton("Hide",null)
            .setTitle("Marker")
            .show()

    }

    fun showDetails(view: View):Unit {
        AlertDialog.Builder(view.context)
            .setTitle("Details")
            .setView(layoutInflater.inflate((R.layout.details_layout),null))
            .setNeutralButton("Hide",null)
            .show()
    }
}
