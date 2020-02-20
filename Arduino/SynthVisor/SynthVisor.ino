#include <SoftwareSerial.h>                             // While prototyping, I got annoyed repeatedly pulling in and out the tx/rx pins to upload to arduino - moved serial to pins 2,3
SoftwareSerial Bluetooth(2,3);                          // RX|TX (Arduino side)

                                                        // Using 5 lEDs to test extrapolation untill I have access to an 8x8 led matrix
const int redLED        = 9;                            // Red LED
const int greenLED      = 10;                           // Green LED
const int blueLED       = 11;                           // Blue LED
const int brightnessLED = 5;                            // Brightness indicator LED
const int blinkLED      = 6;                            // Blink rate indicator LED

int red = 0;                        
int green = 0;
int blue = 0;
int brightness = 0;
int blinkRate = 0;

void setup() {                                          // Put your setup code here, to run once:
  Serial.begin(9600);                                   // Baudrate for the PC serial monitor
  Bluetooth.begin(9600);                                // Baudrate for the Bluetooth serial

  pinMode(redLED, OUTPUT);   
  pinMode(greenLED, OUTPUT);
  pinMode(blueLED, OUTPUT);
  pinMode(brightnessLED, OUTPUT);
  pinMode(blinkLED, OUTPUT);

  digitalWrite(redLED, LOW);
  digitalWrite(greenLED, LOW);
  digitalWrite(blueLED, LOW);
  digitalWrite(brightnessLED, LOW);
  digitalWrite(blinkLED, LOW);
}


void loop() {                                                 // Put your main code here, to run repeatedly
  
  if (Bluetooth.available() > 0){                             // Retreiveing the bluetooth serial input

    char receivedArray[19];
    int  blueCounter = 0;
    
    while(Bluetooth.available() > 0){
      receivedArray[blueCounter] = char(Bluetooth.read());    // Storing received byte into array
      //Serial.write(receivedArray[blueCounter]);               
      blueCounter++;

      digitalWrite(redLED, HIGH);                             // Blink LED to show something being receieved
      delay(5);
      digitalWrite(redLED, LOW);
      
      delay(250);                                             // Delay to allow the bluetooth to catch up
    }

    for (char c : receivedArray){
      Serial.println(c);
    }

   

    


 }
}
