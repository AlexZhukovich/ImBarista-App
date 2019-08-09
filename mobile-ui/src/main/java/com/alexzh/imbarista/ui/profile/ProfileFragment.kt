package com.alexzh.imbarista.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.alexzh.imbarista.R
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModel()

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
        when(item.itemId) {
            R.id.logout_action -> {
                viewModel.logOut()
                return true
            }
        }
        return false
    }

    override fun onStart() {
        viewModel.getLogOutInfo().observe(this, Observer<Resource<Any>> {
            it?.let { handleLogOut(it) }
        })
        super.onStart()
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
