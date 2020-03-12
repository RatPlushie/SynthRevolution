package com.ratbox.synthrevolution;

import android.annotation.SuppressLint;
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

public class SynthTab1 extends Fragment {

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

    // Loading all the values from the synthVisor object onResume
    @Override
    public void onResume() {
        super.onResume();

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
    }
}



