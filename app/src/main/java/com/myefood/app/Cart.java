package com.myefood.app;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

import org.example.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart extends AppCompatActivity {

    private ListView cartListView;
    private Map<Product, Integer> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);

        cart = (Map<Product, Integer>) getIntent().getSerializableExtra("cart");

        List<String> items = new ArrayList<>();
        double total = 0;

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int quantity = entry.getValue();
            double lineTotal = p.getPrice() * quantity;
            total += lineTotal;
            items.add(quantity + "x " + p.getName() + " - " + lineTotal + "$");
        }

        items.add("Total: " + total + "$");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.storeName, items);
        cartListView.setAdapter(adapter);
    }
}
