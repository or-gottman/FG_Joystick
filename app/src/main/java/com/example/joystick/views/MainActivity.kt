package com.example.joystick.views

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapp.views.JoystickView
import com.example.joystick.R
import com.example.joystick.model.Model
import com.example.joystick.view_model.ViewModel


class MainActivity : AppCompatActivity(), JoystickView.JoystickListener {
    var viewModel: ViewModel? = null
    var Model: Model?= null
    var ip: EditText? = null
    var port: EditText? = null
    lateinit var resetButton:Button
    lateinit var connectButton:Button
    lateinit var throttleBar: SeekBar
    lateinit var rudderBar: SeekBar
    lateinit var joystick : JoystickView
    lateinit var engineStartButton: ImageButton
    lateinit var turboButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        joystick = JoystickView(this)
        setContentView(R.layout.activity_main)
        ip = findViewById(R.id.ip);
        port = findViewById(R.id.port);
        connectButton = findViewById(R.id.connect_btn)
        resetButton = findViewById(R.id.resetButton);
        throttleBar = findViewById(R.id.throttleBar)
        rudderBar = findViewById(R.id.rudderBar)
        engineStartButton = findViewById<ImageButton>(R.id.engine_start)
        turboButton = findViewById<ImageButton>(R.id.turbo_button)
        Model = Model()
        viewModel = ViewModel(Model!!);
        connect()
        resetConnectionData()

        turboButton.setOnClickListener { viewModel!!.VM_turbo() }
        engineStartButton.setOnClickListener { viewModel!!.VM_engine_start()
        }



        rudderBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                val progressAsFloat = progress.toFloat()
                val translatedProgress = (progressAsFloat - 50) / 50
                viewModel!!.VM_Rudder = translatedProgress
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        throttleBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                val progressAsFloat = progress.toFloat()
                val translatedProgress = progressAsFloat / 100
                viewModel!!.VM_Throttle = translatedProgress
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    private fun resetConnectionData() {
        resetButton.setOnClickListener {
            ip?.setText("", TextView.BufferType.EDITABLE);
            port?.setText("", TextView.BufferType.EDITABLE);
        }
    }

    private fun connect() {
        connectButton.setOnClickListener {
            // Hide keyboard after clicking "Connect" button
            val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive)
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

            viewModel?.connect(ip?.text.toString(), (port?.text.toString()).toInt())
        }
    }

    override fun onJoystickMoved(xPercent: Float, yPercent: Float) {
        viewModel?.VM_Elevator = yPercent * -1
        viewModel?.VM_Aileron = xPercent
    }
}