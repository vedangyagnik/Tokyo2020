package com.example.tokyo2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    public static UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class,"tokyo_2020_db").allowMainThreadQueries().build();
    }

    public void signupAction(View view){
        EditText fullname = (EditText) findViewById(R.id.suFullname);
        EditText username = (EditText) findViewById(R.id.suUsername);
        EditText password = (EditText) findViewById(R.id.suPassword);
        EditText confirmPassword = (EditText) findViewById(R.id.suConfirmPassword);

        if(fullname.getText().toString().length() == 0) {
            fullname.setError("required");
            Toast.makeText(this, "Please enter full name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(username.getText().toString().length() == 0) {
            username.setError("required");
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.getText().toString().length() == 0) {
            password.setError("required");
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.getText().toString().contentEquals(confirmPassword.getText().toString())) {
            confirmPassword.setError("required");
            Toast.makeText(this, "Password and confirm password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        User u = new User(fullname.getText().toString(), username.getText().toString(), password.getText().toString(), "user");
        db.userDoa().insert(u);
        this.toLoginActivity(view);
        Toast.makeText(this, "You are registered with us successfully.", Toast.LENGTH_LONG).show();
    }

    public void toLoginActivity(View view){
        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
