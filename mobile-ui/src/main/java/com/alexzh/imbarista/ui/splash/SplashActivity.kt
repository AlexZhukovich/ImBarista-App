package com.alexzh.imbarista.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexzh.imbarista.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginActivity.start(this@SplashActivity)
        finish()
    }
}
