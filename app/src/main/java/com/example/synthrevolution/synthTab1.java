package com.example.synthrevolution;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class synthTab1 extends Fragment {

    SeekBar seekbarBrightness;
    SeekBar seekbarRed;
    SeekBar seekbarBlue;
    SeekBar seekbarGreen;

    TextView totalLED_Brightness;
    TextView totalLED_Red;
    TextView totalLED_Green;
    TextView totalLED_Blue;

    View colourView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.synth_tab1, container, false);

        seekbarBrightness   = view.findViewById(R.id.LEDBrightnessSeekbar);
        seekbarRed          = view.findViewById(R.id.LEDRedSeekbar);
        seekbarBlue         = view.findViewById(R.id.LEDBlueSeekbar);
        seekbarGreen        = view.findViewById(R.id.LEDGreenSeekbar);

        totalLED_Brightness = view.findViewById(R.id.LEDBrightnessTotal);
        totalLED_Red        = view.findViewById(R.id.LEDRedTotal);
        totalLED_Green      = view.findViewById(R.id.LEDGreenTotal);
        totalLED_Blue       = view.findViewById(R.id.LEDBlueTotal);

        colourView          = view.findViewById(R.id.colourView);


        // Initialisation of the visor info object
        final VisorState mVisor = new VisorState();

        // Populating TextViews onCreate
        totalLED_Brightness.setText(Integer.toString(mVisor.brightness));
        totalLED_Red.setText(Integer.toString(mVisor.RGB_Red));
        totalLED_Blue.setText(Integer.toString(mVisor.RGB_Blue));
        totalLED_Green.setText(Integer.toString(mVisor.RGB_Green));


        seekbarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // Storing the current value of the seekBar
                mVisor.setBrightness(progress);

                // Displaying the progress bar numerically in the textView
                totalLED_Brightness.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        seekbarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // Storing the current value of the seekBar
                mVisor.setRGB_Red(progress);

                // Displaying the progress bar numerically in the textView
                totalLED_Red.setText(Integer.toString(progress));

                colourView.setBackgroundColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        seekbarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // Storing the current value of the seekBar
                mVisor.setRGB_Green(progress);

                // Displaying the progress bar numerically in the textView
                totalLED_Green.setText(Integer.toString(progress));

                colourView.setBackgroundColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        seekbarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // Storing the current value of the seekBar
                mVisor.setRGB_Blue(progress);

                // Displaying the progress bar numerically in the textView
                totalLED_Blue.setText(Integer.toString(progress));

                colourView.setBackgroundColor();
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




    class VisorState {
        int brightness;
        int RGB_Red;
        int RGB_Green;
        int RGB_Blue;

        Color rgbColour;


        // Constructor method for onCreate
        VisorState(){
            brightness = 0;
            RGB_Red = 0;
            RGB_Green = 0;
            RGB_Blue = 0;

            rgbColour = new Color(255, 255, 255);




        }

        void setBrightness(int progress){
            brightness = progress;
        }

        void setRGB_Red(int progress){
            RGB_Red = progress;
        }

        void setRGB_Green(int progress){
            RGB_Green = progress;
        }

        void setRGB_Blue(int progress){
            RGB_Blue = progress;
        }






    }
}
