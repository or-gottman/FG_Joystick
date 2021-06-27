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
    var editText_ip: EditText? = null
    var editText_port: EditText? = null
    lateinit var button_reset:Button
    lateinit var button_connect:Button
    lateinit var seekbar_throttle: SeekBar
    lateinit var seekbar_rudder: SeekBar
    lateinit var joystick : JoystickView
    lateinit var button_engineStart: ImageButton
    lateinit var button_turbo: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        joystick = JoystickView(this)
        Model = Model()
        viewModel = ViewModel(Model!!);

        // EditTexts
        editText_ip = findViewById(R.id.ip);
        editText_port = findViewById(R.id.port);

        // Buttons
        button_connect = findViewById(R.id.connect_btn)
        button_reset = findViewById(R.id.resetButton);
        button_engineStart = findViewById<ImageButton>(R.id.engine_start)
        button_turbo = findViewById<ImageButton>(R.id.turbo_button)

        // Seekbars - Throttle and Rudder
        seekbar_throttle = findViewById(R.id.throttleBar)
        seekbar_rudder = findViewById(R.id.rudderBar)


        connect()
        resetData()

        button_turbo.setOnClickListener { viewModel!!.VM_turbo() }
        button_engineStart.setOnClickListener { viewModel!!.VM_engine_start() }



        seekbar_rudder.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                val progressAsFloat = progress.toFloat()
                viewModel!!.VM_Rudder = (progressAsFloat - 50) / 50
            }

            // No need to override
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekbar_throttle.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                val progressAsFloat = progress.toFloat()
                viewModel!!.VM_Throttle = progressAsFloat / 100
            }

            // No need to override
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun resetData() {
        button_reset.setOnClickListener {
            editText_ip?.setText("", TextView.BufferType.EDITABLE);
            editText_port?.setText("", TextView.BufferType.EDITABLE);
        }
    }

    private fun connect() {
        button_connect.setOnClickListener {
            // When clicking the connect button, hide the keyboard if visible
            val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive)
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

            viewModel?.connect(editText_ip?.text.toString(), (editText_port?.text.toString()).toInt())
        }
    }

    override fun onJoystickMoved(xPercent: Float, yPercent: Float) {
        viewModel?.VM_Elevator = yPercent * -1
        viewModel?.VM_Aileron = xPercent
    }
}