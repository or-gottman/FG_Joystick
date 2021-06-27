package com.example.joystick.view_model

import com.example.joystick.model.Model

class ViewModel(model: Model) {

    private var model: Model = model

    fun VM_engine_start() {
        model.engine_start()
    }

    fun VM_turbo() {
        model.turbo()
    }


    fun connect(ip: String, port: Int) {
        model.connect(ip, port)
    }

    var VM_Throttle: Float
        get() = model.Throttle
        set(value) {
            model.Throttle = value
        }

    var VM_Elevator: Float
        get() = model.Elevator
        set(value) {
            model.Elevator = value
        }

    var VM_Rudder: Float
        get() = model.Rudder
        set(value) {
            model.Rudder = value
        }

    var VM_Aileron: Float
        get() = model.Aileron
        set(value) {
            model.Aileron = value
        }


}