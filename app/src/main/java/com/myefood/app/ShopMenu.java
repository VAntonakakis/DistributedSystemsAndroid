package com.myefood.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.example.Store;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopMenu extends AppCompatActivity {

    private ArrayList<Store> storeList = new ArrayList<>();
    private ArrayList<String> storeNames = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_menu);

        String lon = getIntent().getStringExtra("Longitude");
        String lat = getIntent().getStringExtra("Latitude");

        ListView listView = findViewById(R.id.StoreMenuList);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.storeName, storeNames);
        listView.setAdapter(arrayAdapter);

        // Handler για την επικοινωνία με το UI Thread
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == -1) {
                    Toast.makeText(ShopMenu.this, "Σφάλμα από τον server", Toast.LENGTH_SHORT).show();
                } else {
                    @SuppressWarnings("unchecked")
                    Map.Entry<Integer,List<Store>> kvp = (Map.Entry<Integer, List<Store>>)msg.obj;
                    List<Store> newStores = kvp.getValue();

                    storeList.clear();
                    storeList.addAll(newStores);

                    storeNames.clear();
                    for (Store s : storeList) {
                        storeNames.add(s.getName());
                    }

                    arrayAdapter.notifyDataSetChanged();
                }
            }
        };

        new RequestFromServer<>(handler, "send", lat, lon).start();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Store selectedStore = storeList.get(position);
            Intent intent = new Intent(ShopMenu.this, Products.class);
            intent.putExtra("store", selectedStore);
            startActivity(intent);

            //Toast.makeText(ShopMenu.this, "Επέλεξες: " + selectedStore.getName(), Toast.LENGTH_SHORT).show();
        });
    }
}