package com.alexzh.imbarista.helpers.snackbar

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import org.junit.Assert.assertTrue
import org.junit.Assert.fail

/**
 * Helper for verification text in [com.google.android.material.snackbar.Snackbar]" on the screen.
 */
object SnackbarVerification {

    /**
     * Asserts that a snackbar contains text.
     *
     * @param text to be checked
     * @param timeoutInMilliseconds timeout for verification
     * @param iterations number of iterations for verification
     */
    fun assertSnackbarText(text: String, timeoutInMilliseconds: Long = 5_000, iterations: Int = 10) {
        val timeoutPerIteration = timeoutInMilliseconds / iterations
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        repeat(iterations) {
            val message = uiDevice.findObject(By.text(text))
            if (message != null) {
                assertTrue(true)
                return
            }
            uiDevice.waitForIdle(timeoutPerIteration)
        }
        fail("\"$text\" not found")
    }
}