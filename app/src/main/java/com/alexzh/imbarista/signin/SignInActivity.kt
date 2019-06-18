package com.alexzh.imbarista.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexzh.imbarista.R
import com.alexzh.imbarista.coffees.CoffeesActivity
import com.alexzh.imbarista.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signIn.setOnClickListener {
            CoffeesActivity.start(this@SignInActivity)
        }

        signUp.setOnClickListener {
            SignUpActivity.start(this@SignInActivity)
        }
    }
}
