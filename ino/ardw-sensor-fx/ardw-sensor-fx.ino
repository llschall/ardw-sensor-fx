

// https://github.com/llschall/ardwloop
// Version 0.1.4
#include <Ardwloop.h>

// https://github.com/adafruit/DHT-sensor-library
// Version 1.4.6
#include "DHT.h"

// The data pin of the DHT sensor is connected to the Arduino pin 4
DHT dht(4, DHT11);

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);

  dht.begin();

  // https://github.com/llschall/ardwloop/wiki#ardw_setup
  ardw_setup();
}

void loop() {

  // https://github.com/llschall/ardwloop/wiki#ardw_loop
  ardw_loop();

  int v = ardw_r()->a.v;

  // Multiply the value by 10 makes the first decimal of the measure appearing in its incoming integer conversion
  ardw_s()->b.v = dht.readHumidity()*10;
  ardw_s()->b.w = dht.readTemperature()*10;

  if (v == 1) {
    digitalWrite(LED_BUILTIN, HIGH);
  } else {
    digitalWrite(LED_BUILTIN, LOW);
  }
  delay(99);
}
