package com.myefood.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.example.Product;
import org.example.Store;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Products extends AppCompatActivity {

    private final Map<Product, Integer> cart = new HashMap<>();
    private List<Product> productlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        ListView listView = findViewById(R.id.ProductsList);

        productlist = ((Store)getIntent().getSerializableExtra("store")).getProducts();
        List<String> productName = new ArrayList<>();
        for(Product p : productlist){
            productName.add("Category: "+ p.getType() + "\nItem name: " + p.getName()+ " " + "\nPrice: " + p.getPrice()+ "$");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.storeName, productName);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProdcut = productlist.get(position);
                int quantity = cart.getOrDefault(selectedProdcut, 0);
                cart.put(selectedProdcut, quantity + 1);

                Toast.makeText(Products.this, selectedProdcut.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.viewCartButton).setOnClickListener(v -> {
            Intent intent = new Intent(Products.this, Cart.class);
            intent.putExtra("cart", new HashMap<>(cart));
            intent.putExtra("store", ((Store)getIntent().getSerializableExtra("store")));
            startActivity(intent);
        });

    }
}