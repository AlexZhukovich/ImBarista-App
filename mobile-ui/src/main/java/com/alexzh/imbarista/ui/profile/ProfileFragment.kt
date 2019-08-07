package com.alexzh.imbarista.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.alexzh.imbarista.R

class ProfileFragment : Fragment() {

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.general_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
