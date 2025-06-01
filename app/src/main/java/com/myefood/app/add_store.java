package com.myefood.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.example.Store;


public class add_store extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        Button AddStoreButton = findViewById(R.id.AddStoreButton);
        AddStoreButton.setOnClickListener(view -> {
            String name = ((EditText) findViewById(R.id.StoreName)).getText().toString();
            double log = Double.parseDouble(((EditText) findViewById(R.id.StoreLog)).getText().toString());
            double lat = Double.parseDouble(((EditText) findViewById(R.id.StoreLat)).getText().toString());
            String food = ((EditText) findViewById(R.id.StoreFoodCategory)).getText().toString();
            double stars = Double.parseDouble(((EditText) findViewById(R.id.StoreStars)).getText().toString());
            int votes = Integer.parseInt(((EditText) findViewById(R.id.StoreVotes)).getText().toString());
            String logo = ((EditText) findViewById(R.id.StoreLogo)).getText().toString();
            int id = Integer.parseInt(((EditText) findViewById(R.id.StoreId)).getText().toString());

            Store myStore = new Store(name, log, lat, food, stars, votes, logo, id);

            new RequestFromServer<>(null, myStore).start();
        });
    }

}