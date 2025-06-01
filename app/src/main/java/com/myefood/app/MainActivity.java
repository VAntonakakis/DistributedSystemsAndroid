package com.myefood.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.loginButton);
        EditText username = findViewById(R.id.Username);
        EditText password = findViewById(R.id.Password);
        EditText Longitude = findViewById(R.id.MainLongitude);
        EditText Latitude = findViewById(R.id.MainLatitude);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Uname = username.getText().toString();
                String Pass = password.getText().toString();
                String longitude = Longitude.getText().toString();
                String latitude = Latitude.getText().toString();

                if (Uname.equals("u") && Pass.equals("u")){
                    //Toast.makeText(getApplicationContext(), "User connecting...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ShopMenu.class);
                    intent.putExtra("Longitude", longitude);
                    intent.putExtra("Latitude", latitude);
                    startActivity(intent);
                }

                else if (Uname.equals("m") && Pass.equals("m")){
                    //Toast.makeText(getApplicationContext(), "Manager connecting...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), manager_menu.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or password", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}