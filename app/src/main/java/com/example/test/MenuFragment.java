package com.example.test;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    Button btnSQLite, btnRoom;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().show();
        View v = inflater.inflate(R.layout.layout_fragment_menu, container, false);
        btnSQLite = v.findViewById(R.id.btnSQLite);
        btnRoom = v.findViewById(R.id.btnRoom);

        btnSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SQLitePerfomance.class);
                startActivity(i);
            }
        });
        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),RoomPerfomance.class);
                startActivity(i);
            }
        });
        return v;
    }

}
