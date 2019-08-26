package com.alexzh.imbarista.robots

fun homeScreen(func: HomeScreenRobot.() -> Unit) = HomeScreenRobot().apply { func() }

open class HomeScreenRobot : BaseTestRobot() {

}