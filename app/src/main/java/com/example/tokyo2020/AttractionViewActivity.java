package com.example.tokyo2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AttractionViewActivity extends AppCompatActivity {

    SharedPreferences prefs;
    public static final String PREFERENCES_NAME = "TokyoSP";
    public WishlistDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_view);
        //Setup the database connection
        db = Room.databaseBuilder(getApplicationContext(),
                WishlistDatabase.class,"wishlists").allowMainThreadQueries().build();

        final Attraction att = (Attraction) getIntent().getSerializableExtra("attr_obj");

        String name = att.getName();
        String address = att.getAddress();
        String description = att.getDescription();
        String image_path = att.getImage_path();
        double price = att.getPrice();

        TextView attName = (TextView) findViewById(R.id.attNameView);
        TextView attAddress = (TextView) findViewById(R.id.attAddressView);
        TextView attDescription = (TextView) findViewById(R.id.attDescriptionView);
        TextView attPrice = (TextView) findViewById(R.id.attPriceView);
        ImageView attImage = (ImageView) findViewById(R.id.attImageView);

        if(image_path.length() == 0){
            attImage.setImageResource(R.drawable.default_image);
        } else {
            attImage.setImageBitmap(BitmapFactory.decodeFile(image_path));
        }
        attName.setText(name);
        attAddress.setText(address);
        attDescription.setText(description);
        attPrice.setText("Price: "+price+" CAD");

        Button addToWishListBtn = (Button) findViewById(R.id.addPlaceToWishListBtn);
        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
                int loggedUserId =  prefs.getInt("logged_user_id", 0);
                Wishlist wl = new Wishlist(loggedUserId, att.getName());
                db.wishlistDoa().insert(wl);
                Toast t = Toast.makeText(getApplicationContext(), "Place added to whishlist successfully.", Toast.LENGTH_LONG);
                t.show();
            }
        });
    }
}
