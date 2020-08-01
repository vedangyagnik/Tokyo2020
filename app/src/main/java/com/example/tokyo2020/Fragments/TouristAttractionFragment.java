package com.example.tokyo2020.Fragments;


import android.app.Person;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tokyo2020.AddNewLocationActivity;
import com.example.tokyo2020.Attraction;
import com.example.tokyo2020.AttractionDatabase;
import com.example.tokyo2020.AttractionListViewAdapter;
import com.example.tokyo2020.AttractionViewActivity;
import com.example.tokyo2020.R;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TouristAttractionFragment extends Fragment {

    ArrayList<Attraction> attractions;
    AttractionListViewAdapter adapter;
    View view;
    public AttractionDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Setup the database connection
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                AttractionDatabase.class,"attractions").allowMainThreadQueries().build();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tourist_attraction, container, false);

        // get employees from database
        List<Attraction> attractionList = db.attDoa().getAttractionList();

        attractions = (ArrayList<Attraction>) attractionList;

        adapter = new AttractionListViewAdapter(getContext(), R.layout.attraction_row_layout, attractions);
        final ListView attListView = (ListView) view.findViewById(R.id.attListView);

        attListView.setAdapter(adapter);

        AdapterView.OnItemClickListener attListClickItem =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Attraction att = (Attraction) attListView.getItemAtPosition(position);
                        Intent i = new Intent(getActivity(), AttractionViewActivity.class);
                        i.putExtra("attr_obj", att);
                        startActivity(i);
                    }
                };

        attListView.setOnItemClickListener(attListClickItem);

        return view;
    }

}
