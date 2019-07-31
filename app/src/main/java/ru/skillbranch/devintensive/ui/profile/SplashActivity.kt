package ru.skillbranch.devintensive.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent



class SplashActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@SplashActivity, ProfileActivity::class.java))
        finish()
    }
}