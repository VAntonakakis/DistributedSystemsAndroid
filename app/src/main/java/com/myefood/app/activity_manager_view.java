package com.myefood.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.example.Product;
import org.example.Store;
import java.util.ArrayList;
import java.util.List;

public class activity_manager_view extends AppCompatActivity {

    protected List<Product> products;

    protected List<String> templist = new ArrayList<>();

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view);

        ListView listView = findViewById(R.id.view_products_list);
        TextView textView = findViewById(R.id.view_products_storeName);
        Button button = findViewById(R.id.add_new_prod_button);
        textView.setText(getIntent().getStringExtra("shopNameActual"));
        products = ((Store) getIntent().getSerializableExtra("store")).getProducts();
        for (Product p : products) {
            templist.add(p.detailedToStringAndroid());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.storeName, templist);
        listView.setAdapter(adapter);
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_manager_view.this, add_products.class);
                intent.putExtra("store", (Store) (getIntent().getSerializableExtra("store")) );
                startActivity(intent);
            }
        });
    }
}