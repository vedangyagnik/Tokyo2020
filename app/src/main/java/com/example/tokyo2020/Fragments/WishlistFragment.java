package com.example.tokyo2020.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tokyo2020.Attraction;
import com.example.tokyo2020.AttractionDatabase;
import com.example.tokyo2020.AttractionListViewAdapter;
import com.example.tokyo2020.AttractionViewActivity;
import com.example.tokyo2020.R;
import com.example.tokyo2020.WishlistDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass
 */
public class WishlistFragment extends Fragment {

    View view;
    SharedPreferences prefs;
    public static final String PREFERENCES_NAME = "TokyoSP";
    public WishlistDatabase db;
    ArrayList<String> wishlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        //Setup the database connection
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                WishlistDatabase.class,"wishlists").allowMainThreadQueries().build();

        prefs = getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        int loggedUserId =  prefs.getInt("logged_user_id", 0);

        // get wishlist from database
        wishlist = (ArrayList<String>) db.wishlistDoa().getWishlist(loggedUserId);

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, wishlist);
        final ListView wlListView = (ListView) view.findViewById(R.id.wishlistview);
        wlListView.setAdapter(itemAdapter);
        TextView emptyText = (TextView)view.findViewById(R.id.empty);
        wlListView.setEmptyView(emptyText);

        return view;
    }

}
