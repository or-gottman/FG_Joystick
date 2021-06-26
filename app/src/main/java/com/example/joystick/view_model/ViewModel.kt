package com.example.joystick.view_model

import com.example.joystick.model.Model

class ViewModel(model: Model) {

    private var my_model: Model = model

    fun VM_engine_start() {
        my_model.engine_start()
    }

    fun VM_turbo() {
        my_model.turbo()
    }


    fun connect(ip: String, port: Int) {
        my_model.connect(ip, port)
    }

    var VM_Throttle: Float
        get() = my_model.Throttle
        set(value) {
            my_model.Throttle = value
        }

    var VM_Elevator: Float
        get() = my_model.Elevator
        set(value) {
            my_model.Elevator = value
        }

    var VM_Rudder: Float
        get() = my_model.Rudder
        set(value) {
            my_model.Rudder = value
        }

    var VM_Aileron: Float
        get() = my_model.Aileron
        set(value) {
            my_model.Aileron = value
        }


}