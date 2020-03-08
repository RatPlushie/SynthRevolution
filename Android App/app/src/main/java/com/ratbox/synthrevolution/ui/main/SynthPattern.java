package com.ratbox.synthrevolution.ui.main;

import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class SynthPattern {

    private static final String FILENAME = "VisorPatternConfig.txt";

    public List<String>         patternNameList;
    public ArrayList<char[]>    patternConfList;

    // Constructor method to load from file, the patterns
    public SynthPattern(Context context){
        patternNameList = new ArrayList<>();
        patternConfList = new ArrayList<>();

        // Attempting to read the save file and parse its contents
        BufferedReader reader = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(FILENAME);
            reader = new BufferedReader(new InputStreamReader(fileInputStream));

            String readString;
            while ((readString = reader.readLine()) != null){ // EoF Check
                // Splitting the line into name and pattern
                String[] splitString = readString.split("=");

                char[] patternArray = splitString[1].toCharArray();

                // Storing the name and pattern in the lists
                patternNameList.add(splitString[0]);
                patternConfList.add(patternArray);
            }

            Log.d("File Read", "Successful");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("PatternFile", "No file found, Creating default");

            // No pattern save file was found on the device, creating a new save file and filling it with the default synth eye pattern
            BufferedWriter writer = null;
            try {
                FileOutputStream fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
                writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

                // Creating the default pattern for the eye
                char[] defaultPattern = {'0','0','0','0','0','0','0','0',
                        '0','0','1','1','1','1','1','0',
                        '0','1','1','1','1','1','1','1',
                        '1','1','0','1','1','1','1','1',
                        '1','1','0','1','1','1','1','0',
                        '1','1','0','1','1','1','0','0',
                        '0','1','0','1','1','0','0','0',
                        '0','0','0','0','0','0','0','0'};

                // adding the default pattern array to the list of arrays
                patternNameList.add("Default");
                patternConfList.add(defaultPattern);

                // StringBuilder init
                StringBuilder stringBuilder = new StringBuilder();

                // Building string to write
                stringBuilder.append("Default=");
                for (char c : defaultPattern){
                    stringBuilder.append(c);
                }

                // Writing string to file
                writer.write(stringBuilder.toString());

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();

            } catch (IOException ex) {
                ex.printStackTrace();

            } finally { // Closing the writer
                if (writer != null){
                    try {
                        writer.close();

                    } catch (IOException eIO) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally { // closing the reader
            if (reader != null){
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Method for loading the saved pattern onto the checkboxes
    public void getPattern(int patternIndex, CheckBox[] checkBoxes){
        char[] tempChar = patternConfList.get(patternIndex);
        for (int i = 0; i <= checkBoxes.length - 1; i++){

            // Parsing the bit to a bool
            Boolean bool;
            if (tempChar[i] == '1'){
                bool = true;
            } else {
                bool = false;
            }

            // Setting the checkbox to either checked or unchecked
            checkBoxes[i].setChecked(bool);
        }

        Log.d("CheckBoxes", "Set");
    }
}
