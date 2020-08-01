package com.example.tokyo2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Attr;

public class AddNewLocationActivity extends AppCompatActivity {

    public static AttractionDatabase db;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_location);

        //Setup Database connection
        db = Room.databaseBuilder(getApplicationContext(),
                AttractionDatabase.class,"attractions").allowMainThreadQueries().build();

        Button browseButton = (Button) findViewById(R.id.browseImageBtn);
        browseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            EditText browseImgLabel = (EditText) findViewById(R.id.browseImageLabel);
            browseImgLabel.setText(picturePath);
            cursor.close();
        }
    }

    public void saveAttractionAction(View view){
        EditText attName = (EditText) findViewById(R.id.attName);
        EditText attAdrees = (EditText) findViewById(R.id.attAddress);
        EditText attDesc = (EditText) findViewById(R.id.attDescription);
        EditText attImagePath = (EditText) findViewById(R.id.browseImageLabel);
        EditText attprice = (EditText) findViewById(R.id.attPrice);

        String name = attName.getText().toString();
        String address = attAdrees.getText().toString();
        String description = attDesc.getText().toString();
        String imagePath = attImagePath.getText().toString();
        double price = Double.parseDouble(attprice.getText().toString());

        if(name.length() == 0) {
            attName.setError("required");
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return;
        }
//        if(imagePath.length() == 0) {
//            attImagePath.setError("required");
//            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
//            return;
//        }


        Attraction att = new Attraction(name, address, description, imagePath, price);

        db.attDoa().insert(att);

        Toast t = Toast.makeText(getApplicationContext(), "Attraction saved Successfully...!", Toast.LENGTH_LONG);
        t.show();

        //clearFields
        attName.getText().clear();
        attAdrees.getText().clear();
        attDesc.getText().clear();
        attprice.getText().clear();
        attImagePath.getText().clear();
    }
}
