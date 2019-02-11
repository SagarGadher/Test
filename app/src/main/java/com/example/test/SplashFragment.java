package com.example.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().hide();
        View v = inflater.inflate(R.layout.layout_fragment_splash, container, false);
        Thread Splash = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5000);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_layout,new MenuFragment(),null).commit();
                }
                catch (Exception e){}
            }
        };
        Splash.start();
        return v;
    }

}
