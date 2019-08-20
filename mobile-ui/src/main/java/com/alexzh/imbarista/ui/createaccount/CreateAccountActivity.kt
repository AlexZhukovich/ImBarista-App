package com.alexzh.imbarista.ui.createaccount

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.UserAlreadyExistViewException
import com.alexzh.imbarista.model.UserView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.ui.login.LoginActivity
import com.alexzh.imbarista.viewmodel.CreateAccountViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_create_account.createAccountButton
import kotlinx.android.synthetic.main.activity_create_account.emailEditText
import kotlinx.android.synthetic.main.activity_create_account.emailInputLayout
import kotlinx.android.synthetic.main.activity_create_account.passwordEditText
import kotlinx.android.synthetic.main.activity_create_account.toolbar
import java.net.UnknownHostException

class CreateAccountActivity : AppCompatActivity() {

    companion object {
        private const val INVALID_RESOURCE_VALUE = -1

        fun start(context: Context) {
            context.startActivity(Intent(context, CreateAccountActivity::class.java))
        }
    }

    private val viewModel: CreateAccountViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        setSupportActionBar(toolbar)

        createAccountButton.setOnClickListener {
            viewModel.createAccount(
                nameEditText.text.toString(),
                emailEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        nameEditText.setOnFocusChangeListener {
                _, hasFocus -> if (hasFocus) viewModel.changeNameText(nameEditText.text.toString())
        }

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // not used
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.changeNameText(s.toString())
            }
        })

        emailEditText.setOnFocusChangeListener {
                _, hasFocus -> if (hasFocus) viewModel.changeEmailText(emailEditText.text.toString())
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

        passwordEditText.setOnFocusChangeListener {
                _, hasFocus -> if (hasFocus) viewModel.changePasswordText(passwordEditText.text.toString())
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
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUserInfo().observe(this, Observer<Resource<UserView>> {
            it?.let { handleCreateAccount(it) }
        })
        viewModel.getNameError().observe(this, Observer<Int> {
            it?.let { nameInputLayout.error = if (it != INVALID_RESOURCE_VALUE) getString(it) else "" }
        })
        viewModel.getEmailError().observe(this, Observer<Int> {
            it?.let { emailInputLayout.error = if (it != INVALID_RESOURCE_VALUE) getString(it) else "" }
        })
        viewModel.getPasswordError().observe(this, Observer<Int> {
            it?.let { passwordInputLayout.error = if (it != INVALID_RESOURCE_VALUE) getString(it) else "" }
        })
    }

    private fun handleCreateAccount(resource: Resource<UserView>) {
        when (resource.status) {
            ResourceState.LOADING -> {
                progressBar.visibility = View.VISIBLE
                nameEditText.isEnabled = false
                emailEditText.isEnabled = false
                passwordEditText.isEnabled = false
                createAccountButton.isEnabled = false
            }
            ResourceState.SUCCESS -> {
                progressBar.visibility = View.GONE
                nameEditText.isEnabled = true
                emailEditText.isEnabled = true
                passwordEditText.isEnabled = true
                createAccountButton.isEnabled = true
                displaySuccessMessage()
            }
            ResourceState.ERROR -> {
                progressBar.visibility = View.GONE
                nameEditText.isEnabled = true
                emailEditText.isEnabled = true
                passwordEditText.isEnabled = true
                createAccountButton.isEnabled = true
                displayErrorMessage(resource.error)
            }
        }
    }

    private fun displaySuccessMessage() {
        Snackbar.make(root, R.string.user_was_created, Snackbar.LENGTH_LONG)
            .setAction(R.string.login_btn_text) { LoginActivity.start(this@CreateAccountActivity) }
            .show()
    }

    private fun displayErrorMessage(error: Throwable?) {
        val text: String = when (error) {
            is UnknownHostException -> getString(R.string.error_internet_connection)
            is UserAlreadyExistViewException -> getString(R.string.error_user_already_exist)
            else -> getString(R.string.error_name_email_password_validation)
        }
        Snackbar.make(root, text, Snackbar.LENGTH_LONG).show()
    }
}
