package com.example.joystick.model

import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class Model {
    private var throttle: Float = 0f
    private var elevator: Float = 0f
    private var rudder: Float = 0f
    private var aileron: Float = 0f
    private var client: Socket? = null
    private var out: PrintWriter? = null
    private val dispatchQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()


    constructor() {
        Thread {
            while (true) {
                try {
                    dispatchQueue.take().run()
                } catch (e: InterruptedException) {
                    //
                }
            }
        }.start()
    }

    var Throttle: Float
        get() = throttle
        set(value) {
            throttle = value
            if (out != null) {
                dispatchQueue.put(Runnable {
                    val throttleAsString = throttle.toString()
                    out?.print("set /controls/engines/current-engine/throttle $throttleAsString \r\n")
                    out?.flush()
                })
            }
        }

    var Rudder: Float
        get() = rudder
        set(value) {
            rudder = value

            if (out != null) {
                dispatchQueue.put(Runnable {
                    var rudderAsString = rudder.toString()
                    out?.print("set /controls/flight/rudder $rudderAsString \r\n")
                    out?.flush()
                })
            }
        }

    var Elevator: Float
        get() = elevator
        set(value) {
            elevator = value

            if (out != null) {
                dispatchQueue.put(Runnable {
                    var elevatorAsString = (elevator / 3).toString()
                    out?.print("set /controls/flight/elevator $elevatorAsString \r\n")
                    out?.flush()
                })
            }
        }

    var Aileron: Float
        get() = aileron
        set(value) {
            aileron = value

            if (out != null) {
                dispatchQueue.put(Runnable {
                    var aileronAsString = (aileron / 3).toString()
                    out?.print("set /controls/flight/aileron $aileronAsString \r\n")
                    out?.flush()
                })
            }
        }

    fun connect(ip: String, port: Int) {
        dispatchQueue.put(Runnable {
            client = Socket(ip, (port))
            out = PrintWriter(client!!.getOutputStream(), true)
        })
    }

    fun changeView(viewNumber: Int) {
        dispatchQueue.put(Runnable {
            setProperty("/sim/current-view/view-number", viewNumber);
        })
    }

    fun turbo() {
        dispatchQueue.put(Runnable {
            setProperty("/velocities/airspeed-kt", 35);
            setProperty("/orientation/pitch-deg", 15);
        })
    }

    fun engine_start() {
        dispatchQueue.put(Runnable {
            setProperty("/consumables/fuel/tank[0]/selected", "1");
            setProperty("/consumables/fuel/tank[1]/selected", "1");
            setProperty("/consumables/fuel/tank[2]/selected", "0");
            setProperty("/consumables/fuel/tank[3]/selected", "0");

            setProperty("/controls/engines/current-engine/mixture", "1");

            setProperty("/engines/active-engine/carb_ice", "0.0");
            setProperty("/engines/active-engine/carb_icing_rate", "0.0");
            setProperty("/controls/engines/current-engine/carb-heat", "1");

            setProperty("/engines/active-engine/running", "1");
            setProperty("/engines/active-engine/auto-start", "1");
            setProperty("/engines/active-engine/cranking", "1");


            setProperty("/controls/engines/engine[0]/primer", "3");
            setProperty("/controls/engines/engine[0]/primer-lever", "0");
            setProperty("/controls/engines/current-engine/throttle", "0.2");
            setProperty("/controls/flight/elevator-trim", "-0.03");

            setProperty("/controls/switches/dome-red", "0");
            setProperty("/controls/switches/dome-white", "0");
            setProperty("/controls/switches/magnetos", "3");
            setProperty("/controls/switches/master-bat", "1");
            setProperty("/controls/switches/master-alt", "1");
            setProperty("/controls/switches/master-avionics", "1");
            setProperty("/controls/switches/starter", "1");

            setProperty("/controls/lighting/beacon", "1");
            setProperty("/controls/lighting/taxi-light", "0");

            setProperty("/fdm/jsbsim/running", "0");
            setProperty("/fdm/jsbsim/inertia/pointmass-weight-lbs[0]", "170");
            setProperty("/fdm/jsbsim/inertia/pointmass-weight-lbs[1]", "0");

            setProperty("/sim/model/c172p/securing/tiedownL-visible", "0");
            setProperty("/sim/model/c172p/securing/tiedownR-visible", "0");
            setProperty("/sim/model/c172p/securing/tiedownT-visible", "0");
            setProperty("/sim/model/c172p/securing/pitot-cover-visible", "0");
            setProperty("/sim/model/c172p/securing/chock", "0");
            setProperty("/sim/model/c172p/securing/cowl-plugs-visible", "0");
            setProperty("/sim/model/c172p/cockpit/control-lock-placed", "0");

            setProperty("/controls/gear/gear-down-command", "1");

            setProperty("/sim/model/c172p/brake-parking", "0");

        })
    }


    fun setProperty(command: String, value: String) {
        out?.print("set $command $value \r\n");
        out?.flush()
    }

    fun setProperty(command: String, value: Float) {
        out?.print("set $command $value \r\n");
        out?.flush()
    }

    fun setProperty(command: String, value: Int) {
        out?.print("set $command $value \r\n");
        out?.flush()
    }
}



