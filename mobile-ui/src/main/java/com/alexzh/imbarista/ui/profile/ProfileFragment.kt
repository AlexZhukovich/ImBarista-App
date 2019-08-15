package com.alexzh.imbarista.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.UserView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.ui.login.LoginActivity
import com.alexzh.imbarista.viewmodel.GetCurrentUserViewModel
import com.alexzh.imbarista.viewmodel.LogOutViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val logOutViewModel: LogOutViewModel by viewModel()
    private val getCurrentUserViewModel: GetCurrentUserViewModel by viewModel()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_action -> {
                logOutViewModel.logOut()
                return true
            }
        }
        return false
    }

    override fun onStart() {
        logOutViewModel.getLogOutInfo().observe(this, Observer<Resource<Any>> {
            it?.let { handleLogOut(it) }
        })
        getCurrentUserViewModel.getUserInfo().observe(this, Observer<Resource<UserView>> {
            it?.let { handleProfileInfo(it) }
        })
        getCurrentUserViewModel.fetchUserInfo()
        super.onStart()
    }

    private fun handleProfileInfo(resource: Resource<UserView>) {
        when (resource.status) {
            ResourceState.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            ResourceState.SUCCESS -> {
                nameTextView.text = resource.data?.name
                emailTextView.text = resource.data?.email
                progressBar.visibility = View.GONE
            }
            ResourceState.ERROR -> {
                progressBar.visibility = View.GONE
                Snackbar.make(root, R.string.error_loading_user_info, Snackbar.LENGTH_LONG)
                    .setAction(R.string.try_again_action) { getCurrentUserViewModel.fetchUserInfo() }
                    .show()
            }
        }
    }

    private fun handleLogOut(resource: Resource<Any>) {
        when (resource.status) {
            ResourceState.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            ResourceState.SUCCESS -> {
                progressBar.visibility = View.GONE
                this@ProfileFragment.activity?.let { LoginActivity.start(it) }
            }
            ResourceState.ERROR -> {
                progressBar.visibility = View.GONE
            }
        }
    }
}
