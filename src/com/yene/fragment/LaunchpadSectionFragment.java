package com.yene.fragment;

import com.yene.bustiming.R;
import com.yene.bustiming.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A fragment that launches other parts of the demo application.
 */
public  class LaunchpadSectionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_launchpad, container, false);
        return rootView;
    }
  
}
