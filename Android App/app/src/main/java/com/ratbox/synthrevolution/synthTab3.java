package com.ratbox.synthrevolution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class synthTab3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate((R.layout.synth_tab3), container, false);

        // TODO - Create synth eye pattern maker

        // TODO - Add ability to animate the eyes (3 animation frames)
    }
}
