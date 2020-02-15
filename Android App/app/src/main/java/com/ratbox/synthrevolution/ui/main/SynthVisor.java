package com.ratbox.synthrevolution.ui.main;

public class SynthVisor {

    public int RGB_Red;
    public int RGB_Green;
    public int RGB_Blue;
    public int LED_Brightness;
    public int blinkRate;
    public String hex;

    public int[] swatch1;
    public int[] swatch2;
    public int[] swatch3;
    public int[] swatch4;

    public String bluetoothName;
    public String bluetoothMAC;
    public String bluetoothUUID;

    
    // SynthVisor constructor method
    public SynthVisor(){

        RGB_Red         = 255;
        RGB_Green       = 255;
        RGB_Blue        = 255;
        LED_Brightness  = 100;
        blinkRate       = 100;
        hex             = "#ffffff"; // RRGGBB

        swatch1         = new int[]{0, 0, 0};
        swatch2         = new int[]{0, 0, 0};
        swatch3         = new int[]{0, 0, 0};
        swatch4         = new int[]{0, 0, 0};

        bluetoothName   = "";
        bluetoothMAC    = "";
        bluetoothUUID   = "";
    }

    // RGB/HEX Value string
    public String getResults(){
        String results = "RGB: " + RGB_Red + ", " + RGB_Green + ", " + RGB_Blue + "\nHEX: " + hex;
        return results;
    }

    public void setRGB_Red(int red){
        RGB_Red = red;
    }

    public void setRGB_Green(int green){
        RGB_Green = green;
    }

    public void setRGB_Blue(int blue){
        RGB_Blue = blue;
    }

    public void setHex(String hexValue){
        hex = hexValue;
    }

    public void setLED_Brightness(int brightness){
        LED_Brightness = brightness;
    }

    public void setBlinkRate(int rate){
        blinkRate = rate;
    }

    public void setSwatch(int swatch){

        switch (swatch){

            case 1:

                swatch1 = new int[]{RGB_Red, RGB_Green, RGB_Blue};
                break;

            case 2:

                swatch2 = new int[]{RGB_Red, RGB_Green, RGB_Blue};
                break;

            case 3:

                swatch3 = new int[]{RGB_Red, RGB_Green, RGB_Blue};
                break;

            case 4:

                swatch4 = new int[]{RGB_Red, RGB_Green, RGB_Blue};
                break;
        }
    }

    public void clickSwatch(int swatchNo){

        int[][] swatch = new int[][]{swatch1, swatch2, swatch3, swatch4};

        setRGB_Red(swatch[swatchNo][0]);
        setRGB_Green(swatch[swatchNo][1]);
        setRGB_Blue(swatch[swatchNo][2]);

        // Building the hex string
        String[] RGBArray = new String[]{Integer.toHexString(RGB_Red), Integer.toHexString(RGB_Green), Integer.toHexString(RGB_Blue)};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#");
        for (String i : RGBArray){
            if (i.equals("0")){
                stringBuilder.append("00");
            } else {
                stringBuilder.append(i);
            }
        }

        setHex(stringBuilder.toString());
    }
}
