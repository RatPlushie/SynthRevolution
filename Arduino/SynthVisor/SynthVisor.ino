#include <SoftwareSerial.h>                                   // While prototyping, I got annoyed repeatedly pulling in and out the tx/rx pins to upload to arduino - moved serial to pins 2,3
SoftwareSerial Bluetooth(2,3);                                // RX|TX (Arduino side)

const int rgb_red = 9;                                        // For prototyping purposes, the rgb matrix is substituted as a singular RGB LED
const int rgb_green = 10;
const int rgb_blue = 11;
const int indicatorLED = 12;                                  // LED to display when bluetooth data is being received

int visorConfig[] = {0, 0, 0, 0, 0};
/*  [0] Red
 *  [1] Green
 *  [2] Blue
 *  [3] Brightness
 *  [4] BlinkRate
 */

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
  
  if (Bluetooth.available() > 0){                             // Waiting for a bluetooth input

    if (Bluetooth.read() == 'C'){                             // If Config mode is received, parse the RGB, Brightness, and BlinkRate

      Bluetooth.write('C');                                   // Bluetooth handshake into Config mode

      delay(200);

      int count = 0;
      char charBuffer[] = {0, 0, 0, '\0'};

      for (int i = 0; i <= 2; i++){                           // Fill the charBuffer with the red value bytes
        charBuffer[i] = char(Bluetooth.read());
        delay(250);

        digitalWrite(indicatorLED, HIGH);                     // LED feedback blink
        delay(5);
        digitalWrite(indicatorLED, LOW);
      }
      visorConfig[0] = atoi(charBuffer);                      // Storing the red value in the array


      for (int i = 0; i <= 2; i++){                           // Fill the charBuffer with the green value
        charBuffer[i] = char(Bluetooth.read());
        delay(250);

        digitalWrite(indicatorLED, HIGH);                     // LED feedback blink
        delay(5);
        digitalWrite(indicatorLED, LOW);
      }
      visorConfig[1] = atoi(charBuffer);                      // Storing the green value in the array

      
      for (int i = 0; i <= 2; i++){                           // Fill the charBuffer with the blue value
        charBuffer[i] = char(Bluetooth.read());
        delay(250);

        digitalWrite(indicatorLED, HIGH);                     // LED feedback blink
        delay(5);
        digitalWrite(indicatorLED, LOW);
      }
      visorConfig[2] = atoi(charBuffer);                      // Storing the blue value in the array


      for (int i = 0; i <= 2; i++){                           // Fill the charBuffer with the brightness value
        charBuffer[i] = char(Bluetooth.read());
        delay(250);

        digitalWrite(indicatorLED, HIGH);                     // LED feedback blink
        delay(5);
        digitalWrite(indicatorLED, LOW);
      }
      visorConfig[3] = atoi(charBuffer);                      // Storing the brightness value in the array


      for (int i = 0; i <= 2; i++){                           // Fill the charBuffer with the blinkRate value
        charBuffer[i] = char(Bluetooth.read());
        delay(250);

        digitalWrite(indicatorLED, HIGH);                     // LED feedback blink
        delay(5);
        digitalWrite(indicatorLED, LOW);
      }
      visorConfig[4] = atoi(charBuffer);                      // Storing the blinkRate value in the array

      Bluetooth.write('1');                                   // Android handshake

      Serial.println("Visor Config");                         // Debug printout of all values
      Serial.print("Red: ");
      Serial.println(visorConfig[0]);
      Serial.print("Green: ");
      Serial.println(visorConfig[1]);
      Serial.print("Blue: ");
      Serial.println(visorConfig[2]);
      Serial.print("Brightness: ");
      Serial.println(visorConfig[3]);
      Serial.print("Blink Rate: ");
      Serial.println(visorConfig[4]);

      analogWrite(rgb_red, visorConfig[0]);                   // Lighting the LED to the RGB value
      analogWrite(rgb_green, visorConfig[1]);
      analogWrite(rgb_blue, visorConfig[2]);
      
    } else if (Bluetooth.read() == 'P'){                      // Placeholder mode for recieving eye patterns and other LED configurations
      // WIP
    }
  } 
}
