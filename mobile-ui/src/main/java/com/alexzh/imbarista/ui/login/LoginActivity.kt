package com.alexzh.imbarista.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.SessionView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.ui.createaccount.CreateAccountActivity
import com.alexzh.imbarista.ui.home.HomeActivity
import com.alexzh.imbarista.viewmodel.LogInViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.UnknownHostException

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val INVALID_RESOURCE_VALUE = -1

        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    private val viewModel: LogInViewModel by viewModel()

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

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // not used
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.changeEmailText(s.toString())
            }
        })

        emailEditText.setOnFocusChangeListener {
                _, hasFocus -> if (hasFocus) viewModel.changeEmailText(emailEditText.text.toString())
        }

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // not used
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.changePasswordText(s.toString())
            }
        })

        passwordEditText.setOnFocusChangeListener {
                _, hasFocus -> if (hasFocus) viewModel.changePasswordText(passwordEditText.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getLogInInfo().observe(this, Observer<Resource<SessionView>> {
            it?.let { handleState(it) }
        })
        viewModel.getEmailError().observe(this, Observer<Int> {
            it?.let { emailInputLayout.error = if (it != INVALID_RESOURCE_VALUE) getString(it) else "" }
        })
        viewModel.getPasswordError().observe(this, Observer<Int> {
            it?.let { passwordInputLayout.error = if (it != INVALID_RESOURCE_VALUE) getString(it) else "" }
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
                finish()
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
