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


void setup() {                                          // put your setup code here, to run once:
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


void loop() {                                            // put your main code here, to run repeatedly
  
  String inString = "";                                  // Temporary string to hold the retrieved info
  if (Bluetooth.available() > 0){                        // Retreiveing the bluetooth serial input
    while(Bluetooth.available() > 0){
      inString += char(Bluetooth.read());
      delay(250);
    }
    Serial.println(inString);

    // WORKS UP TO HERE

    String valueBuff = "";
    int intBuff = 0;
    int currentWrite = 0;
    int stringLength = inString.length() + 1;
    char stringBuff[stringLength];
    inString.toCharArray(stringBuff, stringLength);

    // Parsing out the synthVisor config
    for (int i = 0; i <= stringLength; i++){              // iterating through the received data
      if (!stringBuff[i] == ':'){                         // Check to see if there is more value to parse through
        Serial.println(stringBuff[i]);
        valueBuff += stringBuff[i];                       // Filling the temporary string with the length of the value  
      } else {                                            // Reached a ":", convert string to int

        char intConBuff[valueBuff.length() + 1];
        valueBuff.toCharArray(intConBuff, valueBuff.length() + 1);
        intBuff = atoi(intConBuff);                       // Converting the created string to a working int - CURRENTLY NOT WORKING
        
        switch (currentWrite){                            // Determining which value to write to
          case 0:
            red = intBuff;
            Serial.println("Red: " + red);
            break;
          case 1:
            green = intBuff;
            Serial.println("Green: " + green);
            break;
          case 2:
            blue = intBuff;
            Serial.println("Blue: " + blue);
            break;
          case 3:
            brightness = intBuff;
            Serial.println("Brightness: " + brightness);
            break;
          case 4:
            blinkRate = intBuff;
            Serial.println("Blink Rate: " + blinkRate);
            break;
        }
        
        valueBuff = "";                                   // Resetting the valueBuffer
        currentWrite++;                                   // Incremeneting to the next switch for next value entry 
   }
  }
 }
}
