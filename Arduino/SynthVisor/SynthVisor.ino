#include <SoftwareSerial.h> // While prototyping, I got annoyed repeatedly pulling in and out the tx/rx pins to upload to arduino
SoftwareSerial Bluetooth(2,3); // RX|TX (Arduino side)

char data_in; // This will be used to store the incoming stream from the SynthVisor App

// Using three lEDs to test extrapolation untill I have access to an 8x8 led matrix
const int redLED    = 9;    // Red LED
const int greenLED  = 10;   // Green LED
const int blueLED   = 11;   // Blue LED

void setup() {
  // put your setup code here, to run once:
  
  Serial.begin(9600);     // Baudrate for the PC serial monitor
  Bluetooth.begin(9600);  // Baudrate for the Bluetooth serial

  pinMode(redLED, OUTPUT);   
  pinMode(greenLED, OUTPUT);
  pinMode(blueLED, OUTPUT);

  digitalWrite(redLED, LOW);
  digitalWrite(greenLED, LOW);
  digitalWrite(blueLED, LOW);
}

void loop() {
  // put your main code here, to run repeatedly:

  while(Bluetooth.available() == 0);
  if (Bluetooth.available() > 0){
    data_in = Bluetooth.read();
    Serial.println(data_in);      // Debug printOut to see what is recieved
    
    
    
    
    

  }

  /*
  while(Bluetooth.available() == 0);
  if (Bluetooth.available() > 0){
    data = Bluetooth.parseInt();
  }

  delay(400);
  Serial.println(data);

  if (data == 1){
    digitalWrite(ledPin, HIGH);
  } else {
    digitalWrite(ledPin, LOW);
  }
  */
}
