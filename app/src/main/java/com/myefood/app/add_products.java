package com.myefood.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.example.Store;

public class add_products extends AppCompatActivity {

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_products);
        Button button = findViewById(R.id.AddProductButton);

        Store s = (Store) getIntent().getSerializableExtra("store");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.ProductName)).getText().toString();
                String type = ((EditText) findViewById(R.id.ProductType)).getText().toString();
                double price = Double.parseDouble(((EditText) findViewById(R.id.ProductPrice)).getText().toString());
                int amount = Integer.parseInt(((EditText) findViewById(R.id.ProductAmount)).getText().toString());
                new RequestFromServer<>(null, "newProd::" + s.getStoreID() + "::" + name + "::" + type + "::" + price + "::" + amount).start();
            }
        });

        }
    }