package com.myefood.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

import org.example.Product;
import org.example.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart extends AppCompatActivity {

    private ListView cartListView;
    private Map<Product, Integer> cart;

    Product p;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);
        Button backButton = findViewById(R.id.backButtonCart);
        Button orderButton = findViewById(R.id.btnOrderCart);
        cart = (Map<Product, Integer>) getIntent().getSerializableExtra("cart");

        List<String> items = new ArrayList<>();
        double total = 0;

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            p = entry.getKey();
            int quantity = entry.getValue();
            double lineTotal = p.getPrice() * quantity;
            lineTotal = Math.round(lineTotal * 100.0) / 100.0;
            total += lineTotal;
            items.add(quantity + "x " + p.getName() + " - " + lineTotal + " €");
        }

        items.add("Total: " + total + " €");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.storeName, items);
        cartListView.setAdapter(adapter);
        backButton.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Products.class);
                intent.putExtra("store", ((Store)getIntent().getSerializableExtra("store")));
                startActivity(intent);
            }
        });
        orderButton.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
/*
StringBuilder sb = new StringBuilder("neworder::" + store.getStoreID());
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
        sb.append("::").append(entry.getKey().getName())
        .append("::").append(entry.getValue());
        }
 */