package com.example.csgo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.csgo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Csgo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main)

    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}