package com.example.thomaswilkinson.program5;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TicTacDraw_Fragment extends Fragment {
    DrawView dv;
    Button button;
    public TicTacDraw_Fragment(){
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View myView = inflater.inflate(R.layout.tictacdraw_fragment, container, false);
        dv = myView.findViewById(R.id.dv1);
        button = (Button) myView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dv.resetButton();
            }
        });
        return myView;
    }

}
