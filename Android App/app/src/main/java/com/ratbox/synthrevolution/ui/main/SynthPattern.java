package com.ratbox.synthrevolution.ui.main;

import android.util.Log;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class SynthPattern {

    public List<String>         patternNameList;
    public ArrayList<char[]>    patternConfList;

    // Constructor method to load from file, the patterns
    public SynthPattern(){
        patternNameList = new ArrayList<>();
        patternConfList = new ArrayList<>();
    }

    public void setPatternNameList(List<String> patternNames){
        patternNameList = patternNames;
    }

    public void setPatternConfList(ArrayList<char[]> patternArray){
        patternConfList = patternArray;
    }

    // Method for loading the saved pattern onto the checkboxes
    public void displayPattern(int patternIndex, CheckBox[] checkBoxes){
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
