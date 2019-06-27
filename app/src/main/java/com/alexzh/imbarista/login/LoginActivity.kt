package com.alexzh.imbarista.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexzh.imbarista.R
import com.alexzh.imbarista.home.HomeActivity
import com.alexzh.imbarista.createaccount.CreateAccountActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolbar)

        loginButton.setOnClickListener {
            HomeActivity.start(this@LoginActivity)
        }

        createAccountButton.setOnClickListener {
            CreateAccountActivity.start(this@LoginActivity)
        }
    }
}
