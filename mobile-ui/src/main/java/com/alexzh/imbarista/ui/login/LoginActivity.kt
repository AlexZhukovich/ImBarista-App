package com.alexzh.imbarista.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.SessionView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.ui.createaccount.CreateAccountActivity
import com.alexzh.imbarista.ui.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.UnknownHostException

class LoginActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolbar)

        loginButton.setOnClickListener {
            viewModel.logIn(
                emailEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        createAccountButton.setOnClickListener {
            CreateAccountActivity.start(this@LoginActivity)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getLogInInfo().observe(this, Observer<Resource<SessionView>>{
            it?.let {
                handleState(it)
            }
        })
    }

    private fun handleState(resource: Resource<SessionView>) {
        when (resource.status) {
            ResourceState.LOADING -> {
                progressBar.visibility = View.VISIBLE
                emailEditText.isEnabled = false
                passwordEditText.isEnabled = false
                loginButton.isEnabled = false
                createAccountButton.isEnabled = false
            }
            ResourceState.SUCCESS -> {
                progressBar.visibility = View.GONE
                emailEditText.isEnabled = true
                passwordEditText.isEnabled = true
                loginButton.isEnabled = true
                createAccountButton.isEnabled = true
                HomeActivity.start(this@LoginActivity)
            }
            ResourceState.ERROR -> {
                progressBar.visibility = View.GONE
                emailEditText.isEnabled = true
                passwordEditText.isEnabled = true
                loginButton.isEnabled = true
                createAccountButton.isEnabled = true
                displayError(resource.error)
            }
        }
    }

    private fun displayError(error: Throwable?) {
        val text: String = when (error) {
            is UnknownHostException -> getString(R.string.error_internet_connection)
            else -> getString(R.string.error_email_password_validation)
        }
        Snackbar.make(root, text, Snackbar.LENGTH_LONG).show()
    }
}
