package com.alexzh.imbarista.idlingresources

import androidx.test.espresso.IdlingResource

class ViewVisibilityIdlingResource(
    private val viewId: Int,
    private val expectedVisibility: Int
) : IdlingResource {

    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        // TODO: implement it
        return ""
    }

    override fun isIdleNow(): Boolean {
        // TODO: implement it
        return true
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        // TODO: implement it
    }
}