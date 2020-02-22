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

    char inBlueArray[Bluetooth.available()];
    int  blueCounter = 0;
  
    while(Bluetooth.available() > 0){                         // While-loop until there is no more to read
      inBlueArray[blueCounter] = char(Bluetooth.read());      // Storing received byte into array               
      blueCounter++;                                          // Iterating the counter for the next loop

      digitalWrite(indicatorLED, HIGH);                       // Blink LED to show something being receieved
      delay(2);
      digitalWrite(indicatorLED, LOW);
      
      delay(250);                                             // Delay to allow the bluetooth to catch up
    }

    blueCounter = 0;                                          // Resetting the array position counter


    if (inBlueArray[0] == 'R'){                               // Parsing Red 
      char redArr[] = {inBlueArray[1], inBlueArray[2], inBlueArray[3]};
      red = atoi(redArr); 
      Bluetooth.write('1');
      Serial.print("Red: ");
      Serial.println(red); 
      
        
    } else if (inBlueArray[0] == 'G'){                         // Parsing Green
      char greenArr[] = {inBlueArray[1], inBlueArray[2], inBlueArray[3]};
      green = atoi(greenArr); 
      Bluetooth.write('1');  
      Serial.print("Green: ");
      Serial.println(green); 
       
    } else if (inBlueArray[0] == 'B'){                         // Parsing Blue 
      char blueArr[] = {inBlueArray[1], inBlueArray[2], inBlueArray[3]};
      blue = atoi(blueArr);
      Bluetooth.write('1'); 
      Serial.print("Blue: ");
      Serial.println(blue); 
      
    } else if (inBlueArray[0] == 'I'){                         // Parsing Brightness 
      char brightnessArr[] = {inBlueArray[1], inBlueArray[2], inBlueArray[3]};
      brightness = atoi(brightnessArr);
      Bluetooth.write('1'); 
      Serial.print("Brightness: "); 
      Serial.println(brightness); 
         
    } else if (inBlueArray[0] == 'L'){                         // Parsing BlinkRate 
      char blinkArr[] = {inBlueArray[1], inBlueArray[2], inBlueArray[3]};
      blinkRate = atoi(blinkArr);
      Bluetooth.write('1'); 
      Serial.print("Blink Rate: ");
      Serial.println(blinkRate);  
    }
  }
}
