package com.ratbox.synthrevolution;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class synthTab1 extends Fragment {

    private static final String FILENAME = "SynthVisorConfig.txt";

    private ImageView           colourPickerWheel;

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
        colourPickerSelected.setBackgroundColor(Color.rgb(MainActivity.synthVisor.RGB_Red, MainActivity.synthVisor.RGB_Green, MainActivity.synthVisor.RGB_Blue));
        colourPickerResults.setText(MainActivity.synthVisor.getResults());
        LEDBrightnessTotal.setText(Integer.toString(MainActivity.synthVisor.LED_Brightness));
        seekBarLEDBrightness.setProgress(MainActivity.synthVisor.LED_Brightness);
        seekBarBlinkRate.setProgress(MainActivity.synthVisor.blinkRate);
        blinkRateTotal.setText(Integer.toString(MainActivity.synthVisor.blinkRate));


        // Setting the saved colours of the user saved swatches
        colourSwatchButton1.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch1[0], MainActivity.synthVisor.swatch1[1], MainActivity.synthVisor.swatch1[2]));
        colourSwatchButton2.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch2[0], MainActivity.synthVisor.swatch2[1], MainActivity.synthVisor.swatch2[2]));
        colourSwatchButton3.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch3[0], MainActivity.synthVisor.swatch3[1], MainActivity.synthVisor.swatch3[2]));
        colourSwatchButton4.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch4[0], MainActivity.synthVisor.swatch4[1], MainActivity.synthVisor.swatch4[2]));

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
                            MainActivity.synthVisor.setRGB_Red(Color.red(pixel));
                            MainActivity.synthVisor.setRGB_Green(Color.green(pixel));
                            MainActivity.synthVisor.setRGB_Blue(Color.blue(pixel));

                            // Building the hex string
                            String[] RGBArray = new String[]{Integer.toHexString(MainActivity.synthVisor.RGB_Red), Integer.toHexString(MainActivity.synthVisor.RGB_Green), Integer.toHexString(MainActivity.synthVisor.RGB_Blue)};
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("#");
                            for (String i : RGBArray){
                                if (i.equals("0")){
                                    stringBuilder.append("00");
                                } else {
                                    stringBuilder.append(i);
                                }
                            }

                            MainActivity.synthVisor.setHex(stringBuilder.toString());

                            // Setting background colour of the selected colour view window according to picked colour
                            colourPickerSelected.setBackgroundColor(Color.rgb(MainActivity.synthVisor.RGB_Red, MainActivity.synthVisor.RGB_Green, MainActivity.synthVisor.RGB_Blue));

                            // Setting the textView with the RGB and HEX values
                            colourPickerResults.setText(MainActivity.synthVisor.getResults());
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

                MainActivity.synthVisor.setLED_Brightness(progress);
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

                MainActivity.synthVisor.setBlinkRate(progress);
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
                MainActivity.synthVisor.clickSwatch(0);

                // Setting the textView with the RGB and HEX values
                colourPickerResults.setText(MainActivity.synthVisor.getResults());

                // Setting the displayed colour in the selected colour view
                colourPickerSelected.setBackgroundColor(Color.rgb(MainActivity.synthVisor.RGB_Red, MainActivity.synthVisor.RGB_Green, MainActivity.synthVisor.RGB_Blue));
            }
        });


        // onLongClickListener for swatch1
        colourSwatchButton1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Updating the new values of the swatch
                MainActivity.synthVisor.setSwatch(1);

                // Setting the new colour of the swatch button
                colourSwatchButton1.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch1[0], MainActivity.synthVisor.swatch1[1], MainActivity.synthVisor.swatch1[2]));

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
                MainActivity.synthVisor.clickSwatch(1);

                // Setting the textView with the RGB and HEX values
                colourPickerResults.setText(MainActivity.synthVisor.getResults());

                // Setting the displayed colour in the selected colour view
                colourPickerSelected.setBackgroundColor(Color.rgb(MainActivity.synthVisor.RGB_Red, MainActivity.synthVisor.RGB_Green, MainActivity.synthVisor.RGB_Blue));
            }
        });


        // onLongClickListener for swatch2
        colourSwatchButton2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Updating the new values of the swatch
                MainActivity.synthVisor.setSwatch(2);

                // Setting the new colour of the swatch button
                colourSwatchButton2.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch2[0], MainActivity.synthVisor.swatch2[1], MainActivity.synthVisor.swatch2[2]));

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
                MainActivity.synthVisor.clickSwatch(2);

                // Setting the textView with the RGB and HEX values
                colourPickerResults.setText(MainActivity.synthVisor.getResults());

                // Setting the displayed colour in the selected colour view
                colourPickerSelected.setBackgroundColor(Color.rgb(MainActivity.synthVisor.RGB_Red, MainActivity.synthVisor.RGB_Green, MainActivity.synthVisor.RGB_Blue));
            }
        });


        // onLongClickListener for swatch3
        colourSwatchButton3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Updating the new values of the swatch
                MainActivity.synthVisor.setSwatch(3);

                // Setting the new colour of the swatch button
                colourSwatchButton3.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch3[0], MainActivity.synthVisor.swatch3[1], MainActivity.synthVisor.swatch3[2]));

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
                MainActivity.synthVisor.clickSwatch(3);

                // Setting the textView with the RGB and HEX values
                colourPickerResults.setText(MainActivity.synthVisor.getResults());

                // Setting the displayed colour in the selected colour view
                colourPickerSelected.setBackgroundColor(Color.rgb(MainActivity.synthVisor.RGB_Red, MainActivity.synthVisor.RGB_Green, MainActivity.synthVisor.RGB_Blue));
            }
        });


        // onLongClickListener for swatch4
        colourSwatchButton4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Updating the new values of the swatch
                MainActivity.synthVisor.setSwatch(4);

                // Setting the new colour of the swatch button
                colourSwatchButton4.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch4[0], MainActivity.synthVisor.swatch4[1], MainActivity.synthVisor.swatch4[2]));

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

                           MainActivity.synthVisor.setRGB_Red(Integer.parseInt(tempString));

                           i++;

                           break;

                       case 1: // RGB Green

                           String[] splitGreen = tempString.split("=");
                           tempString = splitGreen[splitGreen.length - 1];

                           MainActivity.synthVisor.setRGB_Green(Integer.parseInt(tempString));

                           i++;

                           break;

                       case 2: // RGB Blue

                           String[] splitBlue = tempString.split("=");
                           tempString = splitBlue[splitBlue.length - 1];

                           MainActivity.synthVisor.setRGB_Blue(Integer.parseInt(tempString));

                           i++;

                           break;

                       case 3: // LED Brightness

                           String[] splitBrightness = tempString.split("=");
                           tempString = splitBrightness[splitBrightness.length - 1];

                           MainActivity.synthVisor.setLED_Brightness(Integer.parseInt(tempString));

                           i++;

                           break;

                       case 4: // Blink Rate

                           String[] splitBlinkRate = tempString.split("=");
                           tempString = splitBlinkRate[splitBlinkRate.length - 1];

                           MainActivity.synthVisor.setBlinkRate(Integer.parseInt(tempString));

                           i++;

                           break;

                       case 5: // HEX

                           String[] splitHEX = tempString.split("=");
                           tempString = splitHEX[splitHEX.length - 1];

                           MainActivity.synthVisor.setHex(tempString);

                           i++;

                           break;

                       case 6: // Swatch1

                           String[] splitSwatch1 = tempString.split("=");
                           tempString = splitSwatch1[splitSwatch1.length - 1];

                           String swatch1RGB[] = tempString.split(",");

                           MainActivity.synthVisor.swatch1 = new int[]{Integer.parseInt(swatch1RGB[0]), Integer.parseInt(swatch1RGB[1]), Integer.parseInt(swatch1RGB[2])};

                           i++;

                           break;

                       case 7: // Swatch2

                           String[] splitSwatch2 = tempString.split("=");
                           tempString = splitSwatch2[splitSwatch2.length - 1];

                           String swatch2RGB[] = tempString.split(",");

                           MainActivity.synthVisor.swatch2 = new int[]{Integer.parseInt(swatch2RGB[0]), Integer.parseInt(swatch2RGB[1]), Integer.parseInt(swatch2RGB[2])};

                           i++;

                           break;

                       case 8:

                           String[] splitSwatch3 = tempString.split("=");
                           tempString = splitSwatch3[splitSwatch3.length - 1];

                           String swatch3RGB[] = tempString.split(",");

                           MainActivity.synthVisor.swatch3 = new int[]{Integer.parseInt(swatch3RGB[0]), Integer.parseInt(swatch3RGB[1]), Integer.parseInt(swatch3RGB[2])};

                           i++;

                           break;

                       case 9:

                           String[] splitSwatch4 = tempString.split("=");
                           tempString = splitSwatch4[splitSwatch4.length - 1];

                           String swatch4RGB[] = tempString.split(",");

                           MainActivity.synthVisor.swatch4 = new int[]{Integer.parseInt(swatch4RGB[0]), Integer.parseInt(swatch4RGB[1]), Integer.parseInt(swatch4RGB[2])};

                           i++;

                           break;
                   }
            }

            // Displaying the synthVisors config
            colourPickerSelected.setBackgroundColor(Color.rgb(MainActivity.synthVisor.RGB_Red, MainActivity.synthVisor.RGB_Green, MainActivity.synthVisor.RGB_Blue));
            colourPickerResults.setText(MainActivity.synthVisor.getResults());
            LEDBrightnessTotal.setText(Integer.toString(MainActivity.synthVisor.LED_Brightness));
            seekBarLEDBrightness.setProgress(MainActivity.synthVisor.LED_Brightness);
            seekBarBlinkRate.setProgress(MainActivity.synthVisor.blinkRate);
            blinkRateTotal.setText(Integer.toString(MainActivity.synthVisor.blinkRate));

            // Setting the saved colours of the user saved swatches
            colourSwatchButton1.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch1[0], MainActivity.synthVisor.swatch1[1], MainActivity.synthVisor.swatch1[2]));
            colourSwatchButton2.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch2[0], MainActivity.synthVisor.swatch2[1], MainActivity.synthVisor.swatch2[2]));
            colourSwatchButton3.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch3[0], MainActivity.synthVisor.swatch3[1], MainActivity.synthVisor.swatch3[2]));
            colourSwatchButton4.setColorFilter(Color.rgb(MainActivity.synthVisor.swatch4[0], MainActivity.synthVisor.swatch4[1], MainActivity.synthVisor.swatch4[2]));

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
    }


    // Saving synthVisorConfig
    @Override
    public void onPause() {
        super.onPause();

        BufferedWriter writer = null;

        try {
            FileOutputStream fileOutputStream = getContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);

            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            writer.write("RGB_Red =" + MainActivity.synthVisor.RGB_Red);
            writer.newLine();

            writer.write("RGB_Green =" + MainActivity.synthVisor.RGB_Green);
            writer.newLine();

            writer.write("RGB_Blue =" + MainActivity.synthVisor.RGB_Blue);
            writer.newLine();

            writer.write("LED_Brightness =" + MainActivity.synthVisor.LED_Brightness);
            writer.newLine();

            writer.write("Blink Rate =" + MainActivity.synthVisor.blinkRate);
            writer.newLine();

            writer.write("HEX =" + MainActivity.synthVisor.hex);
            writer.newLine();

            writer.write("Swatch1 =" + MainActivity.synthVisor.swatch1[0] + "," + MainActivity.synthVisor.swatch1[1] + "," + MainActivity.synthVisor.swatch1[2]);
            writer.newLine();

            writer.write("Swatch2 =" + MainActivity.synthVisor.swatch2[0] + "," + MainActivity.synthVisor.swatch2[1] + "," + MainActivity.synthVisor.swatch2[2]);
            writer.newLine();

            writer.write("Swatch3 =" + MainActivity.synthVisor.swatch3[0] + "," + MainActivity.synthVisor.swatch3[1] + "," + MainActivity.synthVisor.swatch3[2]);
            writer.newLine();

            writer.write("Swatch4 =" + MainActivity.synthVisor.swatch4[0] + "," + MainActivity.synthVisor.swatch4[1] + "," + MainActivity.synthVisor.swatch4[2]);
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



