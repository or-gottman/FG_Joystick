# FlightGear Remote Controller Android App
Remote Controller Android Application for FlightGear Flight Simulator.

# About
FlightGear Flight Simulator is a free, open source multi-platform flight simulator developed by the FlightGear project since 1997.
The simulator runs on 32-bit & 64-bit Windows, Linux, macOS and more. As of now, the latest stable version is 2020.3.9, which was released on 13/06/2021.
Learn more about FlightGear at the [FlightGear Official Website](https://www.flightgear.org/).

The FG Joystick app is an Android application which connects to the FlightGear Simulator using the IP Address of the computer running it, as well as the Port used for the program.
The app allows you to first start the engine by tapping the Engine Start button, then to control the Throttle and the Rudder using two seekbars, as well as the Elevation and the Aileron using the large Joystick in the middle of the screen.
You can also tap the lightning button to boost the aircraft a little bit. Use this option carefully.

# Demonstration
<p align="center">
   <img src="https://github.com/or-gottman/FG_Joystick/blob/master/app/src/main/demo_small.gif" alt="Demo GIF"/>
</p>

# Prerequisites
For the best experience, download the latest version of FlightGear from the official website, at https://www.flightgear.org/download/.
Download and install the FG Joystick app on your Android phone, or on an emulator.

# Setting Up the Simulator
Follow to steps below:

1. Run the FlightGear simulator.
2. Go to Settings.
3. Scroll all the way down, and insert the following line to the "Additional Settings" section:
```
--telnet=socket,in,10,127.0.0.1,6400,tcp
```
4. You are free to change and experiment different features of the FlightGear simulator, such as an airport to begin your flight at, and the model of your aircraft.
5. Click "Fly!" at the bottom left of the screen.

<p align="center">
   <img src="https://github.com/or-gottman/FG_Joystick/blob/master/app/src/main/FGSettings.png" alt="Demo GIF"/>
</p>
* Note: Rather than 6400, you are free to choose any other available port on your machine.

# The Joystick App
While the FlightGear Simulator is loading, find the IPv4 Address of the computer running the simulator. On Windows machines you can do so by typing the "ipconfig" command in the commandline. On Linux and MacOS machines, by typing the "ifconfig" command in the terminal.
When the loading has finished, open up the Joystick application and type in the IP Address and the port which you chose when setting up the simulator. Make sure you're on the same Wireless Network as the computer running the simulator.
<p align="center">
   <img src="https://github.com/or-gottman/FG_Joystick/blob/master/app/src/main/AppIPPort.png" alt="Demo GIF"/>
</p>
When done, tap the "Connect" button.
You should now be able to control the aircraft.

# The Controls
As mentioned earlier, those are the main controls available to use:
1. **Engine Start Button** - This button starts the aircraft. This should be the first thing you do.
2. **Throttle Bar** - On the left hand side of the screen. This bar allows you to control the Throttle lever of the aircraft, which controls the amount of fuel provided to the engine. Basically, how fast the aircraft is going.
3. **Rudder Bar** - On the botton of the screen. The rudder is a primary flight control surface which controls rotation about the vertical axis of an aircraft.
4. **The Joystick** - The Joystick allows you to control the Elevation and the Aileron of the aircraft. Raised elevators push down on the tail and cause the nose to pitch up. This makes the wings fly at a higher angle of attack, which generates more lift and more drag. A raised aileron reduces lift on that wing and a lowered one increases lift, so moving the stick left causes the left wing to drop and the right wing to rise.
5. **Turbo Button** - The yellow lightning button at the top right of the screen. This gives the aircraft a little boost. Use this button carefully.

Have fun!

# Class Diagram - UML
<p align="center">
   <img src="https://github.com/or-gottman/FG_Joystick/blob/master/app/src/main/Joystick_UML.png" alt="Demo GIF"/>
</p>


# Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
