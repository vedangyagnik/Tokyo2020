package com.example.tokyo2020;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tokyo2020.Fragments.TouristAttractionFragment;

import java.util.ArrayList;

public class AttractionListViewAdapter extends ArrayAdapter<Attraction> {

    private ArrayList<Attraction> attractions;
    private Context context;
    private int resource;

    public AttractionListViewAdapter(@NonNull Context context,
                                 int resource,
                                 @NonNull ArrayList<Attraction> objects) {
        super(context, resource, objects);
        this.attractions=objects;
        this.context=context;
        this.resource=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        String name =attractions.get(position).getName();
        String address = attractions.get(position).getAddress();
        String description =attractions.get(position).getDescription();
        String image_path =attractions.get(position).getImage_path();
        double price =attractions.get(position).getPrice();

        //Create an Layout inflater;
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource,parent,false);

        //fetch text views
        TextView attName = (TextView) convertView.findViewById(R.id.attNameRow);
        TextView attAddress = (TextView) convertView.findViewById(R.id.attAddressRow);
        TextView attDescription = (TextView) convertView.findViewById(R.id.attDescriptionRow);
        ImageView attImage = (ImageView) convertView.findViewById(R.id.attImageRow);

        if(image_path.length() == 0){
            attImage.setImageResource(R.drawable.default_image);
        } else {
            attImage.setImageBitmap(BitmapFactory.decodeFile(image_path));
        }
        attName.setText(name);
        attAddress.setText(address);
        attDescription.setText(description);

        return convertView;


    }

}
