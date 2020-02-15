#include <SoftwareSerial.h> // While prototyping, I got annoyed repeatedly pulling in and out the tx/rx pins to upload to arduino
SoftwareSerial Bluetooth(2,3); // RX|TX (Arduino side)
long int data;

const int ledPin = 8; // READ LIGHT


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Bluetooth.begin(9600);
  pinMode(ledPin, OUTPUT);
  digitalWrite(ledPin, LOW);

  
}

void loop() {
  // put your main code here, to run repeatedly:

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

  
}
