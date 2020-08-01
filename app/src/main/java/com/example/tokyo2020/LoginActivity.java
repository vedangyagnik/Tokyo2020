package com.example.tokyo2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor loginPrefsEditor;
    public static final String PREFERENCES_NAME = "TokyoSP";
    public static UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class,"tokyo_2020_db").allowMainThreadQueries().build();

        //Remember me
        prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        etUsername.setText(prefs.getString("username", ""));
        etPassword.setText(prefs.getString("password", ""));
    }

    public void loginAction(View view) {
        // get username/password from UI
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        CheckBox rememberMe = (CheckBox) findViewById(R.id.rememberMeCheckbox);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        // check if user exists
        User u = db.userDoa().getUser(username, password);
        if (u == null) {
            // cannot find the user
            Toast t = Toast.makeText(getApplicationContext(), "Username or Password is invalid!", Toast.LENGTH_SHORT);
            t.show();
            return;
        }
        if (u.getType().contentEquals("admin")) {
            //Admin Login
            this.adminLogin();
        } else {
            //User Login
            prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
            loginPrefsEditor = prefs.edit();
            loginPrefsEditor.putString("name",u.getName());
            loginPrefsEditor.putInt("logged_user_id",u.getId());
            if(rememberMe.isChecked()){
                loginPrefsEditor.putString("username",username);
                loginPrefsEditor.putString("password",password);
            }else{
                loginPrefsEditor.putString("username","");
                loginPrefsEditor.putString("password","");
            }
            loginPrefsEditor.commit();
            this.userLogin(u.getName());
        }
    }

    public void toSignupActivity(View view){
        Intent i = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(i);
    }

    private void userLogin(String name) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("username", name);
        startActivity(i);
    }

    private void adminLogin() {
        Intent i = new Intent(LoginActivity.this, AddNewLocationActivity.class);
        startActivity(i);
    }
}
