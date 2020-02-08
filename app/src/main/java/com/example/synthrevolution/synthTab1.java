package com.example.synthrevolution;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class synthTab1 extends Fragment {

    private ImageView   colourPickerWheel;
    private TextView    colourPickerResults;
    private TextView    LEDBrightnessTotal;
    private View        colourPickerSelected;
    private SeekBar     seekbarLEDBrightness;

    private Bitmap      colourBitmap;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.synth_tab1, container, false);

        colourPickerWheel       = view.findViewById(R.id.colourPickerImageView);
        colourPickerResults     = view.findViewById(R.id.colourPickerTextView);
        colourPickerSelected    = view.findViewById(R.id.colourView);
        seekbarLEDBrightness    = view.findViewById(R.id.LEDBrightnessSeekBar);
        LEDBrightnessTotal      = view.findViewById(R.id.totalLEDBrightnessTextView);


        // Creating SynthVisor object
        final SynthVisor synthVisor = new SynthVisor();


        // Set initial display of required views
        colourPickerSelected.setBackgroundColor(Color.rgb(synthVisor.RGB_Red, synthVisor.RGB_Green, synthVisor.RGB_Blue));
        colourPickerResults.setText(synthVisor.getResults());
        LEDBrightnessTotal.setText(Integer.toString(synthVisor.LED_Brightness));
        seekbarLEDBrightness.setProgress(synthVisor.LED_Brightness);


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



        // Returns the view to the layout inflater
        return view;
    }
}


class SynthVisor{

    int RGB_Red;
    int RGB_Green;
    int RGB_Blue;
    int LED_Brightness;
    String hex;

    // SynthVisor constructor method
    SynthVisor(){
        RGB_Red         = 255;
        RGB_Green       = 255;
        RGB_Blue        = 255;
        LED_Brightness  = 100;
        hex             = "#ffffff";
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
        hex = hexValue;
    }

    void setLED_Brightness(int brightness){
        LED_Brightness = brightness;
    }

}
