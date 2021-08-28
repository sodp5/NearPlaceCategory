package com.munny.nearplacecategory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MilestoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_milestone)

        findViewById<Button>(R.id.btn_map_activity).setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }
    }
}