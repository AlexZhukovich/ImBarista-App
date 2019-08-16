package com.alexzh.imbarista.domain

import com.alexzh.imbarista.domain.model.Map

interface MapProvider {

    fun getMap(): Map
}