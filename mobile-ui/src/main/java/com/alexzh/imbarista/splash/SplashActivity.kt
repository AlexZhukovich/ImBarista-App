package com.alexzh.imbarista.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexzh.imbarista.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActivity.start(this@SplashActivity)
        finish()
    }
}
