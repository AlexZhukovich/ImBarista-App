package com.alexzh.imbarista.ui.splash

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alexzh.imbarista.model.SessionView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.ui.home.HomeActivity
import com.alexzh.imbarista.ui.login.LoginActivity
import com.alexzh.imbarista.viewmodel.CheckExistingSessionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel: CheckExistingSessionViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        viewModel.getExistingSessionInfo().observe(this, Observer<Resource<SessionView>>{
            it?.let { handleExistingSession(it) }
        })
        viewModel.checkExistingSession()
    }

    private fun handleExistingSession(resource: Resource<SessionView>) {
        when (resource.status) {
            ResourceState.LOADING -> {
                // not used
            }
            ResourceState.SUCCESS -> {
                if (resource.data != null && resource.data.isExistingSessionValid()) {
                    HomeActivity.start(this@SplashActivity)
                    finish()
                } else {
                    LoginActivity.start(this@SplashActivity)
                    finish()
                }
            }
            ResourceState.ERROR -> {
                LoginActivity.start(this@SplashActivity)
                finish()
            }
        }
    }
}
