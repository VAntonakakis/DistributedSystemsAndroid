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
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString();
                String pass = password.getText().toString();

                if (uname.equals("u") && pass.equals("u")){
                    Toast.makeText(getApplicationContext(), "User connecting...", Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getApplicationContext(), ShopMenu.class);
                    startActivity(intent);
                    }
                else if (uname.equals("m") && pass.equals("m")){
                    Toast.makeText(getApplicationContext(), "Manager connecting...", Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getApplicationContext(), manager_menu.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or password", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}