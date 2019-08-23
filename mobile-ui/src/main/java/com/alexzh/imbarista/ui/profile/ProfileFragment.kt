package com.alexzh.imbarista.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
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
                changeUserDataVisibility(isNameVisible = false, isEmailVisible = false)
                progressBar.visibility = View.VISIBLE
            }
            ResourceState.SUCCESS -> {
                showUserData(
                    resource.data?.name ?: "",
                    resource.data?.email ?: ""
                )
                progressBar.visibility = View.GONE
            }
            ResourceState.ERROR -> {
                progressBar.visibility = View.GONE
                changeUserDataVisibility(isNameVisible = false, isEmailVisible = false)
                showErrorMessage(
                    getString(R.string.error_loading_user_info),
                    getString(R.string.try_again_action)
                ) { getCurrentUserViewModel.fetchUserInfo() }
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

    private fun showUserData(
        name: String,
        email: String
    ) {
        changeUserDataVisibility(name.isNotBlank(), email.isNotEmpty())
        nameTextView.text = name
        emailTextView.text = email
    }

    private fun changeUserDataVisibility(
        isNameVisible: Boolean,
        isEmailVisible: Boolean
    ) {
        if (isNameVisible) {
            nameImageView.visibility = View.VISIBLE
            nameTextView.visibility = View.VISIBLE
        } else {
            nameImageView.visibility = View.GONE
            nameTextView.visibility = View.GONE
        }

        if (isEmailVisible) {
            emailImageView.visibility = View.VISIBLE
            emailTextView.visibility = View.VISIBLE
        } else {
            emailImageView.visibility = View.GONE
            emailTextView.visibility = View.GONE
        }
    }

    @SuppressLint("PrivateResource")
    private fun showErrorMessage(
        messageText: String,
        actionText: String,
        action: () -> Unit
    ) {
        Snackbar.make(root, messageText, Snackbar.LENGTH_INDEFINITE)
            .setAction(actionText) { action() }
            .apply {
                val commonMargin = resources.getDimension(R.dimen.common_margin)
                val navigationHeight = resources.getDimension(R.dimen.design_bottom_navigation_height)
                view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams)
                    .apply {
                        setMargins(leftMargin, topMargin, rightMargin, (navigationHeight + commonMargin).toInt())
                    }
            }
            .show()
    }
}
