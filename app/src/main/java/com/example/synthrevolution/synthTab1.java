package com.example.synthrevolution;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
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

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class synthTab1 extends Fragment {

    private static final String FILENAME = "SynthVisorConfig.txt";

    private ImageView   colourPickerWheel;

    private TextView    colourPickerResults;
    private TextView    LEDBrightnessTotal;
    private TextView    blinkRateTotal;

    private View        colourPickerSelected;

    private SeekBar     seekbarLEDBrightness;
    private SeekBar     seekbarBlinkRate;

    private ImageButton colourSwatchButton1;
    private ImageButton colourSwatchButton2;
    private ImageButton colourSwatchButton3;
    private ImageButton colourSwatchButton4;

    private Bitmap      colourBitmap;

    public  SynthVisor  synthVisor = new SynthVisor();


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.synth_tab1, container, false);

        colourPickerWheel       = view.findViewById(R.id.colourPickerImageView);
        colourPickerResults     = view.findViewById(R.id.colourPickerTextView);
        colourPickerSelected    = view.findViewById(R.id.colourView);
        seekbarLEDBrightness    = view.findViewById(R.id.LEDBrightnessSeekBar);
        seekbarBlinkRate        = view.findViewById(R.id.blinkRateSeekbar);
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
        seekbarLEDBrightness.setProgress(synthVisor.LED_Brightness);
        seekbarBlinkRate.setProgress(synthVisor.blinkRate);
        blinkRateTotal.setText(Integer.toString(synthVisor.blinkRate));

        // Setting the saved colours of the user saved swatches
        colourSwatchButton1.setColorFilter(Color.rgb(synthVisor.swatch1[0], synthVisor.swatch1[1], synthVisor.swatch1[2]));
        colourSwatchButton2.setColorFilter(Color.rgb(synthVisor.swatch2[0], synthVisor.swatch2[1], synthVisor.swatch2[2]));
        colourSwatchButton3.setColorFilter(Color.rgb(synthVisor.swatch3[0], synthVisor.swatch3[1], synthVisor.swatch3[2]));
        colourSwatchButton4.setColorFilter(Color.rgb(synthVisor.swatch4[0], synthVisor.swatch4[1], synthVisor.swatch4[2]));

        // Caches required for using the colour picker
        colourPickerWheel.setDrawingCacheEnabled(true);
        colourPickerWheel.buildDrawingCache(true);

        // Image view on touch listener to select colours for the visor LEDs
        colourPickerWheel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                    colourBitmap = colourPickerWheel.getDrawingCache();

                    int pixel = colourBitmap.getPixel((int)event.getX(), (int)event.getY());

                    // Getting the RGB values
                    synthVisor.setRGB_Red(Color.red(pixel));
                    synthVisor.setRGB_Green(Color.green(pixel));
                    synthVisor.setRGB_Blue(Color.blue(pixel));

                    // Getting the Hex values
                    synthVisor.setHex("#" + Integer.toHexString(pixel));

                    // Setting background colour of the selected colour view window according to picked colour
                    colourPickerSelected.setBackgroundColor(Color.rgb(synthVisor.RGB_Red, synthVisor.RGB_Green, synthVisor.RGB_Blue));

                    // Setting the textView with the RGB and HEX values
                    colourPickerResults.setText(synthVisor.getResults());
                }
                return true;
            }
        });

        // Seekbar onChange listener to update the synthVisor's brightness level
        seekbarLEDBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        // Seekbar onChange listener to update the synthVisor's blink rate
        seekbarBlinkRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

                // Updating values in the synthVisor object
                synthVisor.setRGB_Red(synthVisor.swatch1[0]);
                synthVisor.setRGB_Green(synthVisor.swatch1[1]);
                synthVisor.setRGB_Blue(synthVisor.swatch1[2]);
                synthVisor.setHex("#" + Integer.toHexString(synthVisor.RGB_Red) + Integer.toHexString(synthVisor.RGB_Green) + Integer.toHexString(synthVisor.RGB_Blue));

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

                // Making a save toast message
                Toast.makeText(getActivity(), "Colour saved", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        // onClickListener for swatch2 listener
        colourSwatchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating values in the synthVisor object
                synthVisor.setRGB_Red(synthVisor.swatch2[0]);
                synthVisor.setRGB_Green(synthVisor.swatch2[1]);
                synthVisor.setRGB_Blue(synthVisor.swatch2[2]);
                synthVisor.setHex("#" + Integer.toHexString(synthVisor.RGB_Red) + Integer.toHexString(synthVisor.RGB_Green) + Integer.toHexString(synthVisor.RGB_Blue));

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

                // Making a save toast message
                Toast.makeText(getActivity(), "Colour saved", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        // onClickListener for swatch3 listener
        colourSwatchButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating values in the synthVisor object
                synthVisor.setRGB_Red(synthVisor.swatch3[0]);
                synthVisor.setRGB_Green(synthVisor.swatch3[1]);
                synthVisor.setRGB_Blue(synthVisor.swatch3[2]);
                synthVisor.setHex("#" + Integer.toHexString(synthVisor.RGB_Red) + Integer.toHexString(synthVisor.RGB_Green) + Integer.toHexString(synthVisor.RGB_Blue));

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

                // Making a save toast message
                Toast.makeText(getActivity(), "Colour saved", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        // onClickListener for swatch4 listener
        colourSwatchButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating values in the synthVisor object
                synthVisor.setRGB_Red(synthVisor.swatch4[0]);
                synthVisor.setRGB_Green(synthVisor.swatch4[1]);
                synthVisor.setRGB_Blue(synthVisor.swatch4[2]);
                synthVisor.setHex("#" + Integer.toHexString(synthVisor.RGB_Red) + Integer.toHexString(synthVisor.RGB_Green) + Integer.toHexString(synthVisor.RGB_Blue));

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

                // Making a save toast message
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

        
    }


    // Saving synthVisorConfig
    @Override
    public void onPause() {
        super.onPause();

        BufferedWriter writer = null;

        try {
            FileOutputStream fileOutputStream = getContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);

            writer = new BufferedWriter((new OutputStreamWriter(fileOutputStream)));

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


class SynthVisor{

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

    // SynthVisor constructor method
    SynthVisor(){

        // TODO - at onCreate import last selected colour
        RGB_Red         = 255;
        RGB_Green       = 255;
        RGB_Blue        = 255;
        LED_Brightness  = 100;
        blinkRate       = 100;
        hex             = "#ffffff";

        // TODO - at onCreate import saved colour swatches
        swatch1         = new int[]{200, 54, 145};
        swatch2         = new int[]{255, 0, 0};
        swatch3         = new int[]{0, 255, 0};
        swatch4         = new int[]{0, 0, 255};
    }

    // RGB/HEX Value string
    String getResults(){
        String results = "RGB: " + RGB_Red + ", " + RGB_Green + ", " + RGB_Blue + "\nHEX: " + hex;
        return results;
    }

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

        String i = hexValue;

        if (i.split("").length > 7){

            String[] hexArray = i.split("");

            i = hexArray[0] + hexArray[3] + hexArray[4] + hexArray[5] + hexArray[6] + hexArray[7] + hexArray[8];
        }

        hex = i;
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
}
