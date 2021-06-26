package com.example.joystick.model

class FGPlayer {

    private var throttle: Float = 0f
    private var elevator: Float = 0f
    private var rudder: Float = 0f
    private var aileron: Float = 0f

    var Throttle: Float
    get() = throttle
    set(value){
        throttle=value
    }

}