package com.example.eatoncampus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class EditMenuActivity extends AppCompatActivity {
    ListView menu_list;
    SQLiteHandler db;
    List<List<String>> info;
    List<String> menu_id_list, menu_name_list, menu_price_list;
    String managerID, restaurantName, menuID, menuName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        db = new SQLiteHandler(this);
        Intent intent = getIntent();
        managerID = intent.getStringExtra("manager_id");

        menu_list = findViewById(R.id.menu_list);

        restaurantName = intent.getStringExtra("restaurant_name");

        info = db.getRestaurantInfo(restaurantName);
        menu_id_list = info.get(1);
        menu_name_list = info.get(2);
        menu_price_list = info.get(3);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu_name_list);

        menu_list.setAdapter(adapter);

        menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuID = menu_id_list.get(position);
                goToEdit();
            }
        });
    }

    private void goToEdit() {
        Intent edit = new Intent(this, EditingActivity.class);
        edit.putExtra("menu_id", menuID);
        edit.putExtra("manager_id", managerID);
        startActivity(edit);
    }

}
