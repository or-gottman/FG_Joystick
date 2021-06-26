package com.example.joystick.model

import android.os.SystemClock.sleep
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
            setprop("/sim/current-view/view-number", viewNumber);
        })
    }

    fun turbo() {
        dispatchQueue.put(Runnable {
            setprop("/velocities/airspeed-kt", 35);
            setprop("/orientation/pitch-deg", 15);
        })
    }

    fun engine_start() {
        dispatchQueue.put(Runnable {
            setprop("/controls/switches/magnetos", "3");
            setprop("/controls/engines/current-engine/throttle", "0.2")

            setprop("/controls/engines/current-engine/mixture", "1")

            setprop("/controls/flight/elevator-trim", "0.0")
            setprop("/controls/switches/master-bat", "1")
            setprop("/controls/switches/master-alt", "1")
            setprop("/controls/switches/master-avionics", "1")


            setprop("/controls/lighting/nav-lights", "1")
            setprop("/controls/lighting/strobe", "1")
            setprop("/controls/lighting/beacon", "1")


            setprop("/controls/flight/flaps", "0.0")


            setprop("/sim/model/c172p/cockpit/control-lock-placed", "0")
            setprop("/sim/model/c172p/brake-parking", "0")
            setprop("/sim/model/c172p/securing/chock", "0")
            setprop("/sim/model/c172p/securing/cowl-plugs-visible", "0")
            setprop("/sim/model/c172p/securing/pitot-cover-visible", "0")
            setprop("/sim/model/c172p/securing/tiedownL-visible", "0")
            setprop("/sim/model/c172p/securing/tiedownR-visible", "0")
            setprop("/sim/model/c172p/securing/tiedownT-visible", "0")


            setprop("/engines/active-engine/carb_ice", "0.0")
            setprop("/engines/active-engine/carb_icing_rate", "0.0")
            setprop("/engines/active-engine/volumetric-efficiency-factor", "0.85")


            setprop("/consumables/fuel/tank[0]/water-contamination", "0.0")
            setprop("/consumables/fuel/tank[1]/water-contamination", "0.0")
            setprop("/consumables/fuel/tank[0]/sample-water-contamination", "0.0")
            setprop("/consumables/fuel/tank[1]/sample-water-contamination", "0.0")

            setprop("/controls/engines/engine[0]/primer-lever", "0")
            setprop("/controls/engines/engine/primer", "3")

            setprop("/controls/switches/starter", "1")
            setprop("/engines/active-engine/auto-start", "1")

            setprop("sim/model/open-pfuel-cap", "0")
            setprop("sim/model/open-sfuel-cap", "0")
            setprop("sim/model/open-pfuel-sump", "0")
            setprop("sim/model/open-sfuel-sump", "0")

        })
    }

    fun engine_start1() {
        dispatchQueue.put(Runnable {

//            setprop("/velocities/airspeed-kt", 100);
//            setprop("/velocities/uBody-fps", 163);
//            setprop("/orientation/pitch-deg", 0);


//            setprop("/consumables/fuel/tank[2]/selected", 1);
//            setprop("/consumables/fuel/tank[3]/selected", 1);
//            setprop("/consumables/fuel/tank[0]/selected", 0);
//            setprop("/consumables/fuel/tank[1]/selected", 0);
            setprop("/consumables/fuel/tank[0]/selected", 1);
            setprop("/consumables/fuel/tank[1]/selected", 1);
            setprop("/consumables/fuel/tank[2]/selected", 0);
            setprop("/consumables/fuel/tank[3]/selected", 0);


//        var auto_mixture = getprop("/fdm/jsbsim/engine/auto-mixture");
            setprop("/controls/engines/current-engine/mixture", 1);


            setprop("/engines/active-engine/carb_ice", 0.0f);
            setprop("/engines/active-engine/carb_icing_rate", 0.0f);
            setprop("/controls/engines/current-engine/carb-heat", 1);

            setprop("/engines/active-engine/running", 1);
            setprop("/engines/active-engine/auto-start", 1);
            setprop("/engines/active-engine/cranking", 1);


            setprop("/controls/engines/engine[0]/primer", 3);
            setprop("/controls/engines/engine[0]/primer-lever", 0);
            setprop("/controls/engines/current-engine/throttle", 0.2f);
            setprop("/controls/flight/elevator-trim", -0.03f);

            setprop("/controls/switches/dome-red", 0);
            setprop("/controls/switches/dome-white", 0);
            setprop("/controls/switches/magnetos", 3);
            setprop("/controls/switches/master-bat", 1);
            setprop("/controls/switches/master-alt", 1);
            setprop("/controls/switches/master-avionics", 1);
            setprop("/controls/switches/starter", 1);

            setprop("/controls/lighting/beacon", 1);
            setprop("/controls/lighting/taxi-light", 0);

            setprop("/fdm/jsbsim/running", 0);
            setprop("/fdm/jsbsim/inertia/pointmass-weight-lbs[0]", 170);
            setprop("/fdm/jsbsim/inertia/pointmass-weight-lbs[1]", 0);

            setprop("/sim/model/c172p/securing/tiedownL-visible", 0);
            setprop("/sim/model/c172p/securing/tiedownR-visible", 0);
            setprop("/sim/model/c172p/securing/tiedownT-visible", 0);
            setprop("/sim/model/c172p/securing/pitot-cover-visible", 0);
            setprop("/sim/model/c172p/securing/chock", 0);
            setprop("/sim/model/c172p/securing/cowl-plugs-visible", 0);
            setprop("/sim/model/c172p/cockpit/control-lock-placed", 0);

            setprop("/controls/gear/gear-down-command", 1);

            setprop("/sim/model/c172p/brake-parking", 0);

        })
    }
    fun setprop(command: String, value: String) {
        out?.print("set $command $value \r\n");
        out?.flush()
    }

    fun setprop(command: String, value: Float) {
        out?.print("set $command $value \r\n");
        out?.flush()
    }

    fun setprop(command: String, value: Int) {
        out?.print("set $command $value \r\n");
        out?.flush()
    }


    fun some() {


        setprop("/velocities/airspeed-kt", 100);
        setprop("/velocities/uBody-fps", 163);
        setprop("/orientation/pitch-deg", 0);


//        setprop("/consumables/fuel/tank[2]/selected", 1);
//        setprop("/consumables/fuel/tank[3]/selected", 1);
//        setprop("/consumables/fuel/tank[0]/selected", 0);
//        setprop("/consumables/fuel/tank[1]/selected", 0);
        setprop("/consumables/fuel/tank[0]/selected", 1);
        setprop("/consumables/fuel/tank[1]/selected", 1);
        setprop("/consumables/fuel/tank[2]/selected", 0);
        setprop("/consumables/fuel/tank[3]/selected", 0);


//        var auto_mixture = getprop("/fdm/jsbsim/engine/auto-mixture");
//        setprop("/controls/engines/current-engine/mixture", auto_mixture);


        setprop("/engines/active-engine/carb_ice", 0.0f);
        setprop("/engines/active-engine/carb_icing_rate", 0.0f);
        setprop("/controls/engines/current-engine/carb-heat", 1);

        setprop("/engines/active-engine/running", 1);
        setprop("/engines/active-engine/auto-start", 1);
        setprop("/engines/active-engine/cranking", 1);


        setprop("/controls/engines/engine[0]/primer", 3);
        setprop("/controls/engines/engine[0]/primer-lever", 0);
        setprop("/controls/engines/current-engine/throttle", 0.2f);
        setprop("/controls/flight/elevator-trim", -0.03f);

        setprop("/controls/switches/dome-red", 0);
        setprop("/controls/switches/dome-white", 0);
        setprop("/controls/switches/magnetos", 3);
        setprop("/controls/switches/master-bat", 1);
        setprop("/controls/switches/master-alt", 1);
        setprop("/controls/switches/master-avionics", 1);
        setprop("/controls/switches/starter", 1);

        setprop("/controls/lighting/beacon", 1);
        setprop("/controls/lighting/taxi-light", 0);

        setprop("/fdm/jsbsim/running", 0);
        setprop("/fdm/jsbsim/inertia/pointmass-weight-lbs[0]", 170);
        setprop("/fdm/jsbsim/inertia/pointmass-weight-lbs[1]", 0);

        setprop("/sim/model/c172p/securing/tiedownL-visible", 0);
        setprop("/sim/model/c172p/securing/tiedownR-visible", 0);
        setprop("/sim/model/c172p/securing/tiedownT-visible", 0);
        setprop("/sim/model/c172p/securing/pitot-cover-visible", 0);
        setprop("/sim/model/c172p/securing/chock", 0);
        setprop("/sim/model/c172p/securing/cowl-plugs-visible", 0);
        setprop("/sim/model/c172p/cockpit/control-lock-placed", 0);

//        if (getprop("/fdm/jsbsim/bushkit") == 4) {
        setprop("/controls/gear/gear-down-command", 1);
//        }

        setprop("/sim/model/c172p/brake-parking", 0);


    }


}



