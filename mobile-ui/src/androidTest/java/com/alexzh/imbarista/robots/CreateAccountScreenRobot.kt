package com.alexzh.imbarista.robots

fun createAccountScreen(func: CreateAccountRobot.() -> Unit) = CreateAccountRobot().apply { func() }

class CreateAccountRobot : BaseTestRobot() {

}