package com.ratbox.synthrevolution;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ratbox.synthrevolution.ui.main.BluetoothManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.EventListener;
import java.util.UUID;

import static com.ratbox.synthrevolution.ui.main.BluetoothRecyclerViewAdapter.SHARED_PREFS;
import static com.ratbox.synthrevolution.ui.main.BluetoothRecyclerViewAdapter.bluetoothManager;

public class synthTab1 extends Fragment {

    private static final String FILENAME = "SynthVisorConfig.txt";

    private ImageView   colourPickerWheel;

    private TextView            colourPickerResults;
    private TextView            LEDBrightnessTotal;
    private TextView            blinkRateTotal;

    private View                colourPickerSelected;

    private SeekBar             seekBarLEDBrightness;
    private SeekBar             seekBarBlinkRate;

    private ImageButton         colourSwatchButton1;
    private ImageButton         colourSwatchButton2;
    private ImageButton         colourSwatchButton3;
    private ImageButton         colourSwatchButton4;

    private Bitmap              colourBitmap;

    public  SynthVisor          synthVisor = new SynthVisor();

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.synth_tab1, container, false);

        colourPickerWheel       = view.findViewById(R.id.colourPickerImageView);
        colourPickerResults     = view.findViewById(R.id.colourPickerTextView);
        colourPickerSelected    = view.findViewById(R.id.colourView);
        seekBarLEDBrightness    = view.findViewById(R.id.LEDBrightnessSeekBar);
        seekBarBlinkRate        = view.findViewById(R.id.blinkRateSeekbar);
        LEDBrightnessTotal      = view.findViewById(R.id.totalLEDBrightnessTextView);
        blinkRateTotal          = view.findViewById(R.id.blinkRateTotalTextView);
        colourSwatchButton1     = view.findViewById(R.id.colourSwatch1Button);
        colourSwatchButton2     = view.findViewById(R.id.colourSwatch2Button);
        colourSwatchButton3     = view.findViewById(R.id.colourSwatch3Button);
        colourSwatchButton4     = view.findViewById(R.id.colourSwatch4Button);



        // Set initial display of required views
        colourPickerSelected.setBackgroundColor(Color.rgb(synthVisor.RGB_Red, synthVisor.RGB_Green, synthVisor.RGB_Blue));
        colourPickerResults.setText(synthVisor.getResults());
        LEDBrightnessTotal.setText(Integer.toString(synthVisor.LED_Brightness));
        seekBarLEDBrightness.setProgress(synthVisor.LED_Brightness);
        seekBarBlinkRate.setProgress(synthVisor.blinkRate);
        blinkRateTotal.setText(Integer.toString(synthVisor.blinkRate));

        // Setting the saved colours of the user saved swatches
        colourSwatchButton1.setColorFilter(Color.rgb(synthVisor.swatch1[0], synthVisor.swatch1[1], synthVisor.swatch1[2]));
        colourSwatchButton2.setColorFilter(Color.rgb(synthVisor.swatch2[0], synthVisor.swatch2[1], synthVisor.swatch2[2]));
        colourSwatchButton3.setColorFilter(Color.rgb(synthVisor.swatch3[0], synthVisor.swatch3[1], synthVisor.swatch3[2]));
        colourSwatchButton4.setColorFilter(Color.rgb(synthVisor.swatch4[0], synthVisor.swatch4[1], synthVisor.swatch4[2]));

        // Caches required for using the colour picker
        colourPickerWheel.setDrawingCacheEnabled(true);
        colourPickerWheel.buildDrawingCache(true);

        // Image view on touch listener to select colours for the visor LEDs from user touch
        colourPickerWheel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                    colourBitmap = colourPickerWheel.getDrawingCache();

                    try {
                        // Getting the colourBitmap of the touch-selected colour
                        int pixel = colourBitmap.getPixel((int)event.getX(), (int)event.getY());

                        // Testing if colour was picked is out of bounds
                        if (!Integer.toHexString(pixel).equals("0")){

                            // Getting the RGB values
                            synthVisor.setRGB_Red(Color.red(pixel));
                            synthVisor.setRGB_Green(Color.green(pixel));
                            synthVisor.setRGB_Blue(Color.blue(pixel));

                            // Building the hex string
                            String[] RGBArray = new String[]{Integer.toHexString(synthVisor.RGB_Red), Integer.toHexString(synthVisor.RGB_Green), Integer.toHexString(synthVisor.RGB_Blue)};
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("#");
                            for (String i : RGBArray){
                                if (i.equals("0")){
                                    stringBuilder.append("00");
                                } else {
                                    stringBuilder.append(i);
                                }
                            }

                            synthVisor.setHex(stringBuilder.toString());

                            // Setting background colour of the selected colour view window according to picked colour
                            colourPickerSelected.setBackgroundColor(Color.rgb(synthVisor.RGB_Red, synthVisor.RGB_Green, synthVisor.RGB_Blue));

                            // Setting the textView with the RGB and HEX values
                            colourPickerResults.setText(synthVisor.getResults());

                            // TODO
                            bluetoothManager.sendSynthVisor(1,1,1,1,1);
                        }

                    } catch (Exception colourPickerOutOfBounds){
                        Log.e("ColourPicker", "ColourBitmap out of bounds");
                    }
                }
                return true;
            }
        });


        // SeekBar onChange listener to update the synthVisor's brightness level
        seekBarLEDBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                LEDBrightnessTotal.setText(Integer.toString(progress));

                synthVisor.setLED_Brightness(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // SeekBar onChange listener to update the synthVisor's blink rate
        seekBarBlinkRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                blinkRateTotal.setText(Integer.toString(progress));

                synthVisor.setBlinkRate(progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // onClickListener for swatch1 listener
        colourSwatchButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating the synthVisor's RGB/Hex values to this specific swatch
                synthVisor.clickSwatch(0);

                // Setting the textView with the RGB and HEX values
                colourPickerResults.setText(synthVisor.getResults());

                // Setting the displayed colour in the selected colour view
                colourPickerSelected.setBackgroundColor(Color.rgb(synthVisor.RGB_Red, synthVisor.RGB_Green, synthVisor.RGB_Blue));


            }
        });


        // onLongClickListener for swatch1
        colourSwatchButton1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Updating the new values of the swatch
                synthVisor.setSwatch(1);

                // Setting the new colour of the swatch button
                colourSwatchButton1.setColorFilter(Color.rgb(synthVisor.swatch1[0], synthVisor.swatch1[1], synthVisor.swatch1[2]));

                // Making a saveConfig toast message
                Toast.makeText(getActivity(), "Colour saved", Toast.LENGTH_SHORT).show();

                return false;
            }
        });


        // onClickListener for swatch2 listener
        colourSwatchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating the synthVisor's RGB/Hex values to this specific swatch
                synthVisor.clickSwatch(1);

                // Setting the textView with the RGB and HEX values
                colourPickerResults.setText(synthVisor.getResults());

                // Setting the displayed colour in the selected colour view
                colourPickerSelected.setBackgroundColor(Color.rgb(synthVisor.RGB_Red, synthVisor.RGB_Green, synthVisor.RGB_Blue));
            }
        });


        // onLongClickListener for swatch2
        colourSwatchButton2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Updating the new values of the swatch
                synthVisor.setSwatch(2);

                // Setting the new colour of the swatch button
                colourSwatchButton2.setColorFilter(Color.rgb(synthVisor.swatch2[0], synthVisor.swatch2[1], synthVisor.swatch2[2]));

                // Making a saveConfig toast message
                Toast.makeText(getActivity(), "Colour saved", Toast.LENGTH_SHORT).show();

                return false;
            }
        });


        // onClickListener for swatch3 listener
        colourSwatchButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating the synthVisor's RGB/Hex values to this specific swatch
                synthVisor.clickSwatch(2);

                // Setting the textView with the RGB and HEX values
                colourPickerResults.setText(synthVisor.getResults());

                // Setting the displayed colour in the selected colour view
                colourPickerSelected.setBackgroundColor(Color.rgb(synthVisor.RGB_Red, synthVisor.RGB_Green, synthVisor.RGB_Blue));
            }
        });


        // onLongClickListener for swatch3
        colourSwatchButton3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Updating the new values of the swatch
                synthVisor.setSwatch(3);

                // Setting the new colour of the swatch button
                colourSwatchButton3.setColorFilter(Color.rgb(synthVisor.swatch3[0], synthVisor.swatch3[1], synthVisor.swatch3[2]));

                // Making a saveConfig toast message
                Toast.makeText(getActivity(), "Colour saved", Toast.LENGTH_SHORT).show();

                return false;
            }
        });


        // onClickListener for swatch4 listener
        colourSwatchButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating the synthVisor's RGB/Hex values to this specific swatch
                synthVisor.clickSwatch(3);

                // Setting the textView with the RGB and HEX values
                colourPickerResults.setText(synthVisor.getResults());

                // Setting the displayed colour in the selected colour view
                colourPickerSelected.setBackgroundColor(Color.rgb(synthVisor.RGB_Red, synthVisor.RGB_Green, synthVisor.RGB_Blue));
            }
        });


        // onLongClickListener for swatch4
        colourSwatchButton4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Updating the new values of the swatch
                synthVisor.setSwatch(4);

                // Setting the new colour of the swatch button
                colourSwatchButton4.setColorFilter(Color.rgb(synthVisor.swatch4[0], synthVisor.swatch4[1], synthVisor.swatch4[2]));

                // Making a saveConfig toast message
                Toast.makeText(getActivity(), "Colour saved", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        // Returns the view to the layout inflater
        return view;
    }


    // Loading synthVisorConfig
    @Override
    public void onResume() {
        super.onResume();

        BufferedReader reader = null;

        try {
            FileInputStream fileInputStream = getContext().openFileInput(FILENAME);
            reader = new BufferedReader(new InputStreamReader(fileInputStream));

            int i = 0; // Counter to know which line of the txt file the program is on
            String tempString;
            while ((tempString = reader.readLine()) != null){ // EoF check

                   switch (i){

                       case 0: // RGB Red

                           String[] splitRed = tempString.split("=");
                           tempString = splitRed[splitRed.length - 1];

                           synthVisor.setRGB_Red(Integer.parseInt(tempString));

                           i++;

                           break;

                       case 1: // RGB Green

                           String[] splitGreen = tempString.split("=");
                           tempString = splitGreen[splitGreen.length - 1];

                           synthVisor.setRGB_Green(Integer.parseInt(tempString));

                           i++;

                           break;

                       case 2: // RGB Blue

                           String[] splitBlue = tempString.split("=");
                           tempString = splitBlue[splitBlue.length - 1];

                           synthVisor.setRGB_Blue(Integer.parseInt(tempString));

                           i++;

                           break;

                       case 3: // LED Brightness

                           String[] splitBrightness = tempString.split("=");
                           tempString = splitBrightness[splitBrightness.length - 1];

                           synthVisor.setLED_Brightness(Integer.parseInt(tempString));

                           i++;

                           break;

                       case 4: // Blink Rate

                           String[] splitBlinkRate = tempString.split("=");
                           tempString = splitBlinkRate[splitBlinkRate.length - 1];

                           synthVisor.setBlinkRate(Integer.parseInt(tempString));

                           i++;

                           break;

                       case 5: // HEX

                           String[] splitHEX = tempString.split("=");
                           tempString = splitHEX[splitHEX.length - 1];

                           synthVisor.setHex(tempString);

                           i++;

                           break;

                       case 6: // Swatch1

                           String[] splitSwatch1 = tempString.split("=");
                           tempString = splitSwatch1[splitSwatch1.length - 1];

                           String swatch1RGB[] = tempString.split(",");

                           synthVisor.swatch1 = new int[]{Integer.parseInt(swatch1RGB[0]), Integer.parseInt(swatch1RGB[1]), Integer.parseInt(swatch1RGB[2])};

                           i++;

                           break;

                       case 7: // Swatch2

                           String[] splitSwatch2 = tempString.split("=");
                           tempString = splitSwatch2[splitSwatch2.length - 1];

                           String swatch2RGB[] = tempString.split(",");

                           synthVisor.swatch2 = new int[]{Integer.parseInt(swatch2RGB[0]), Integer.parseInt(swatch2RGB[1]), Integer.parseInt(swatch2RGB[2])};

                           i++;

                           break;

                       case 8:

                           String[] splitSwatch3 = tempString.split("=");
                           tempString = splitSwatch3[splitSwatch3.length - 1];

                           String swatch3RGB[] = tempString.split(",");

                           synthVisor.swatch3 = new int[]{Integer.parseInt(swatch3RGB[0]), Integer.parseInt(swatch3RGB[1]), Integer.parseInt(swatch3RGB[2])};

                           i++;

                           break;

                       case 9:

                           String[] splitSwatch4 = tempString.split("=");
                           tempString = splitSwatch4[splitSwatch4.length - 1];

                           String swatch4RGB[] = tempString.split(",");

                           synthVisor.swatch4 = new int[]{Integer.parseInt(swatch4RGB[0]), Integer.parseInt(swatch4RGB[1]), Integer.parseInt(swatch4RGB[2])};

                           i++;

                           break;
                   }
            }

            // Displaying the synthVisors config
            colourPickerSelected.setBackgroundColor(Color.rgb(synthVisor.RGB_Red, synthVisor.RGB_Green, synthVisor.RGB_Blue));
            colourPickerResults.setText(synthVisor.getResults());
            LEDBrightnessTotal.setText(Integer.toString(synthVisor.LED_Brightness));
            seekBarLEDBrightness.setProgress(synthVisor.LED_Brightness);
            seekBarBlinkRate.setProgress(synthVisor.blinkRate);
            blinkRateTotal.setText(Integer.toString(synthVisor.blinkRate));

            // Setting the saved colours of the user saved swatches
            colourSwatchButton1.setColorFilter(Color.rgb(synthVisor.swatch1[0], synthVisor.swatch1[1], synthVisor.swatch1[2]));
            colourSwatchButton2.setColorFilter(Color.rgb(synthVisor.swatch2[0], synthVisor.swatch2[1], synthVisor.swatch2[2]));
            colourSwatchButton3.setColorFilter(Color.rgb(synthVisor.swatch3[0], synthVisor.swatch3[1], synthVisor.swatch3[2]));
            colourSwatchButton4.setColorFilter(Color.rgb(synthVisor.swatch4[0], synthVisor.swatch4[1], synthVisor.swatch4[2]));

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (reader != null){
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // TODO - resume a bluetooth connection
    }


    // Saving synthVisorConfig
    @Override
    public void onPause() {
        super.onPause();

        BufferedWriter writer = null;

        try {
            FileOutputStream fileOutputStream = getContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);

            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            writer.write("RGB_Red =" + synthVisor.RGB_Red);
            writer.newLine();

            writer.write("RGB_Green =" + synthVisor.RGB_Green);
            writer.newLine();

            writer.write("RGB_Blue =" + synthVisor.RGB_Blue);
            writer.newLine();

            writer.write("LED_Brightness =" + synthVisor.LED_Brightness);
            writer.newLine();

            writer.write("Blink Rate =" + synthVisor.blinkRate);
            writer.newLine();

            writer.write("HEX =" + synthVisor.hex);
            writer.newLine();

            writer.write("Swatch1 =" + synthVisor.swatch1[0] + "," + synthVisor.swatch1[1] + "," + synthVisor.swatch1[2]);
            writer.newLine();

            writer.write("Swatch2 =" + synthVisor.swatch2[0] + "," + synthVisor.swatch2[1] + "," + synthVisor.swatch2[2]);
            writer.newLine();

            writer.write("Swatch3 =" + synthVisor.swatch3[0] + "," + synthVisor.swatch3[1] + "," + synthVisor.swatch3[2]);
            writer.newLine();

            writer.write("Swatch4 =" + synthVisor.swatch4[0] + "," + synthVisor.swatch4[1] + "," + synthVisor.swatch4[2]);
            writer.newLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (writer != null){
                try {
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


class SynthVisor {

    int RGB_Red;
    int RGB_Green;
    int RGB_Blue;
    int LED_Brightness;
    int blinkRate;
    String hex;

    int[] swatch1;
    int[] swatch2;
    int[] swatch3;
    int[] swatch4;

    String bluetoothName;
    String bluetoothMAC;
    String bluetoothUUID;

    // SynthVisor constructor method
    SynthVisor(){

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
    String getResults(){
        String results = "RGB: " + RGB_Red + ", " + RGB_Green + ", " + RGB_Blue + "\nHEX: " + hex;
        return results;
    }

    // TODO - instead of all these basic set functions convert them all to just "obj.variable = x" instead of calling methods, Clean your damn code!

    void setRGB_Red(int red){
        RGB_Red = red;
    }

    void setRGB_Green(int green){
        RGB_Green = green;
    }

    void setRGB_Blue(int blue){
        RGB_Blue = blue;
    }

    void setHex(String hexValue){
        hex = hexValue;
    }

    void setLED_Brightness(int brightness){
        LED_Brightness = brightness;
    }

    void setBlinkRate(int rate){
        blinkRate = rate;
    }

    void setSwatch(int swatch){

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

    void clickSwatch(int swatchNo){

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
