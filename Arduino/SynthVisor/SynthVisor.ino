#include <SoftwareSerial.h>                                   // While prototyping, I got annoyed repeatedly pulling in and out the tx/rx pins to upload to arduino - moved serial to pins 2,3
SoftwareSerial Bluetooth(2,3);                                // RX|TX (Arduino side)

                                                              // Using 5 lEDs to test extrapolation untill I have access to an 8x8 led matrix
const int redLED        = 9;                                  // Red LED
const int greenLED      = 10;                                 // Green LED
const int blueLED       = 11;                                 // Blue LED
const int brightnessLED = 5;                                  // Brightness indicator LED
const int blinkLED      = 6;                                  // Blink rate indicator LED

int red = 0;                        
int green = 0;
int blue = 0;
int brightness = 0;
int blinkRate = 0;

void setup() {                                                // Put your setup code here, to run once:
  Serial.begin(9600);                                         // Baudrate for the PC serial monitor
  Bluetooth.begin(9600);                                      // Baudrate for the Bluetooth serial

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

    char receivedArray[20];
    int  blueCounter = 0;
    
    while(Bluetooth.available() > 0){                         // While-loop until there is no more to read
      receivedArray[blueCounter] = char(Bluetooth.read());    // Storing received byte into array               
      blueCounter++;                                          // Iterating the counter for the next loop

      digitalWrite(redLED, HIGH);                             // Blink LED to show something being receieved
      delay(5);
      digitalWrite(redLED, LOW);
      
      delay(250);                                             // Delay to allow the bluetooth to catch up
    }

    /*
    for (char c : receivedArray){                             // DEBUG PRINT OUT
      Serial.write(c);
    }
    */

    int colourCount = 0;
    int valCount    = 0;
    char valArray[4];
    
    for (int i = 0; i <= sizeof(receivedArray); i++){        // Parsing the array into usable values
      
      if (receivedArray[i] != ','){                           // Testing for a break
        
        valArray[valCount] = receivedArray[i];                // Moving value into its own array
        valCount++;
        
      } else {                                                // Once reached a break it is to input the value into the correct integer

        valArray[4] = '\0';                                   // Adding null terminator to each valArray

        switch(colourCount){                                  // Inputing the final value into its correct visor config
          case 0:
            red = atoi(valArray);
            break;

          case 1:
            green = atoi(valArray);
            break;

          case 2:
            blue = atoi(valArray);
            break;

          case 3:
            brightness = atoi(valArray);
            break;

          case 4:                                   
            blinkRate = atoi(valArray);
            break;
        }

        colourCount++;                                        // Incrementing to the next visor config value in the switch series
        valCount = 0;                                         // Restting the counter for the valArray builder
      }  
    }
    
    Serial.println("");                                       // DEBUG PRINTOUT
    Serial.println("");
    Serial.println("VisorConfig:");
    Serial.print("Red: ");
    Serial.println(red);
    Serial.print("Green: ");
    Serial.println(green);
    Serial.print("Blue: ");
    Serial.println(blue);
    Serial.print("Brightness: "); 
    Serial.println(brightness);
    Serial.print("Blink Rate: ");
    Serial.println(blinkRate);
   
 }
}
