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

    ImageView   colourPickerWheel;
    TextView    colourPickerResults;
    TextView    LEDBrightnessTotal;
    View        colourPickerSelected;
    SeekBar     seekbarLEDBrightness;


    Bitmap      colourBitmap;

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


        // Set initial display of selected colour and hex/RGB string
        colourPickerSelected.setBackgroundColor(Color.rgb(255, 255, 255));
        colourPickerResults.setText("RGB: 255, 255, 255 \nHEX: #ffffff");
        LEDBrightnessTotal.setText(Integer.toString(0));

        colourPickerWheel.setDrawingCacheEnabled(true);
        colourPickerWheel.buildDrawingCache(true);

        // Image view on touch listener
        colourPickerWheel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                    colourBitmap = colourPickerWheel.getDrawingCache();

                    int pixel = colourBitmap.getPixel((int)event.getX(), (int)event.getY());

                    // Getting the RGB values
                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    // Getting the Hex values
                    String hex = "#" + Integer.toHexString(pixel);

                    // Setting background colour of the selected colour view window according to picked colour
                    colourPickerSelected.setBackgroundColor(Color.rgb(r,g,b));

                    // Setting the textview with the RGB and HEX values
                    colourPickerResults.setText("RGB: " + r + ", " + g + ", " + b + "\nHEX: " + hex);
                }
                return true;
            }
        });


        seekbarLEDBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                LEDBrightnessTotal.setText(Integer.toString(progress));

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
