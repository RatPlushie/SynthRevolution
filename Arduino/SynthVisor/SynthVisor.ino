#include <SoftwareSerial.h>                                   // While prototyping, I got annoyed repeatedly pulling in and out the tx/rx pins to upload to arduino - moved serial to pins 2,3
SoftwareSerial Bluetooth(2,3);                                // RX|TX (Arduino side)

const int rgb_red = 9;                                        // For prototyping purposes, the rgb matrix is substituted as a singular RGB LED
const int rgb_green = 10;
const int rgb_blue = 11;
const int indicatorLED = 12;                                  // LED to display when bluetooth data is being received

int red = 0;                                                  // Global Values for the visor
int green = 0;
int blue = 0;
int brightness = 0;
int blinkRate = 0;

void setup() {                                                // Put your setup code here, to run once:
  Serial.begin(9600);                                         // Baudrate for the PC serial monitor
  Bluetooth.begin(9600);                                      // Baudrate for the Bluetooth serial

  pinMode(rgb_red, OUTPUT);
  pinMode(rgb_green, OUTPUT);
  pinMode(rgb_blue, OUTPUT);
  pinMode(indicatorLED, OUTPUT);

  digitalWrite(rgb_red, LOW);
  digitalWrite(rgb_green, LOW);
  digitalWrite(rgb_blue, LOW);
  digitalWrite(indicatorLED, LOW);
}


void loop() {                                                 // Put your main code here, to run repeatedly
  
  if (Bluetooth.available() > 0){                             // Retreiveing the bluetooth serial input and parsing it into the global variables

    char receivedArray[20];
    int  blueCounter = 0;
    
    while(Bluetooth.available() > 0){                         // While-loop until there is no more to read
      receivedArray[blueCounter] = char(Bluetooth.read());    // Storing received byte into array               
      blueCounter++;                                          // Iterating the counter for the next loop

      digitalWrite(indicatorLED, HIGH);                       // Blink LED to show something being receieved
      delay(2);
      digitalWrite(indicatorLED, LOW);
      
      delay(250);                                             // Delay to allow the bluetooth to catch up
    }

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
            //Serial.println(red);
            break;

          case 1:
            green = atoi(valArray);
            //Serial.println(green);
            break;

          case 2:
            blue = atoi(valArray);
            //Serial.println(blue);
            break;

          case 3:
            brightness = atoi(valArray);
            //Serial.println(brightness);
            break;

          case 4:                                   
            blinkRate = atoi(valArray);
            //Serial.println(blinkRate);
            break;
        }

        colourCount++;                                        // Incrementing to the next visor config value in the switch series
        valCount = 0;                                         // Restting the counter for the valArray builder
      }  
    }
    
    Serial.println("VisorConfig:");                           // Serial Debug of parsed config
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
    Serial.println("");
    
 }

  analogWrite(rgb_red, red);                                  // Displaying the loaded colour to the LED
  analogWrite(rgb_green, green);
  analogWrite(rgb_blue, blue);
 
}
