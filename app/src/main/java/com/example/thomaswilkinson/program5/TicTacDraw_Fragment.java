package com.example.thomaswilkinson.program5;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TicTacDraw_Fragment extends Fragment {
    DrawView dv;
    public TicTacDraw_Fragment(){
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View myView = inflater.inflate(R.layout.tictacdraw_fragment, container, false);
        dv = myView.findViewById(R.id.dv1);

        return myView;
    }
}
