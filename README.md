# ardw-sensor-fx

Retrieves the temperature and humidity measurements from a DHT11 sensor, and renders them in a Java FX chart.

![Overview](https://github.com/llschall/ardw-sensor-fx/blob/main/doc/arduino_d.jpg?raw=true)

![Overview](https://github.com/llschall/ardw-sensor-fx/blob/main/doc/screenshot.png?raw=true)

## Setup (Windows / Linux)

The following command will download the **ardw-sensor-fx** program to the current folder and start it.

```
git clone https://github.com/llschall/ardw-sensor-fx;
./ardw-sensor-fx/gradlew -p ardw-sensor-fx run;
```

## Setup of the Arduino board

### Hardware

* Connect the DHT ground (pin -) to the Arduino gnd. _Picture: black cable_
* Connect the DHT power (pin +) to the Arduino 5V. _Picture: red cable_
* Connect the DHT signal (pin out) to the Arduino pin 4. _Picture: green cable_

![Overview](https://github.com/llschall/ardw-sensor-fx/blob/main/doc/arduino_a.jpg?raw=true)
![Overview](https://github.com/llschall/ardw-sensor-fx/blob/main/doc/arduino_b.jpg?raw=true)
![Overview](https://github.com/llschall/ardw-sensor-fx/blob/main/doc/arduino_c.jpg?raw=true)

And of course connect you Arduino board to your PC using any USB cable.
<br>
Bluetooth connection might work as well but has not been tested so far.

### Software

Upload the ino file content hereafter to your Arduino board, using for instance the official Arduino IDE:
https://github.com/llschall/ardw-sensor-fx/blob/main/ino/ardw-sensor-fx/ardw-sensor-fx.ino

It might be necessary to firstly import the **Ardwloop** and **DHT** libraries to your Arduino IDE to make the
compilation successful.

![Overview](https://github.com/llschall/ardw-sensor-fx/blob/main/doc/ardwloop.png?raw=true)
