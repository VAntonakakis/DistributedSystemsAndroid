package com.myefood.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.example.Store;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class manager_menu extends AppCompatActivity {
    Button button;
    private ArrayList<Store> storeList = new ArrayList<>();
    private ArrayList<String> storeNames = new ArrayList<>();
    private ArrayAdapter<Store> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        button = findViewById(R.id.AddNewStoreButton);
        ListView listView = findViewById(R.id.ManagerStoresList);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.storeName, storeList);
        listView.setAdapter(arrayAdapter);

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == -1) {
                    Toast.makeText(manager_menu.this, "Σφάλμα από τον server", Toast.LENGTH_SHORT).show();
                } else {
                    @SuppressWarnings("unchecked")
                    Map.Entry<Integer, List<Store>> kvp = (Map.Entry<Integer, List<Store>>)msg.obj;
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

        new RequestFromServer<>(handler, "admin").start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), add_store.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Store selectedStore = storeList.get(position);
            String storeName = selectedStore.getName();
            Intent intent = new Intent(manager_menu.this, activity_manager_view.class);
            intent.putExtra("store", selectedStore);
            intent.putExtra("shopNameActual", storeName);
            startActivity(intent);

            Toast.makeText(manager_menu.this, "Επέλεξες: " + selectedStore.getName(), Toast.LENGTH_SHORT).show();
        });
    }
}