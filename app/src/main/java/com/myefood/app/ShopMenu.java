package com.myefood.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class ShopMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_menu);

        ListView listView = findViewById(R.id.StoreMenuList);

        ArrayList<String> shopList = new ArrayList<>();
        //to do
        // pairnei ola ta magazia kai ta bazei se auto to array list
        shopList.add(getIntent().getStringExtra("Longitude"));
        shopList.add(getIntent().getStringExtra("Latitude"));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, shopList);
        listView.setAdapter(arrayAdapter);

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    arrayAdapter.notifyDataSetChanged(); // ενημέρωση UI
                } else {
                    String error = (String) msg.obj;
                    Toast.makeText(ShopMenu.this, "Σφάλμα: " + error, Toast.LENGTH_SHORT).show();
                }
            }
        };

        RequestFromServer<String> thread = new RequestFromServer<>(handler, shopList, "send");
        thread.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedShop = shopList.get(position);
                Intent intent = new Intent(ShopMenu.this, Products.class);
                intent.putExtra("shopName", selectedShop);
                startActivity(intent);

                Toast.makeText(ShopMenu.this, "Επέλεξες: " + selectedShop, Toast.LENGTH_SHORT).show();
            }
        });
    }
}