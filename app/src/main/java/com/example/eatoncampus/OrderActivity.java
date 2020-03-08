package com.example.eatoncampus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderActivity extends AppCompatActivity {
    TextView restaurantName_text, hours_text, address_text;
    EditText pickup_time_input;
    ListView menuList;
    String restaurantName, time, pickup_time, order_status, hours, address, studentID, restaurantID;
    Double total_price;
    List<List<String>> info;
    List<String> menu_id_list, menu_name_list, menu_price_list, name_price_list;
    Set<Integer> choice;
    SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        choice = new HashSet<>();
        db = new SQLiteHandler(this);
        total_price = 0.0;
        Intent intent = getIntent();
        studentID = intent.getStringExtra("student_id");

        pickup_time_input = findViewById(R.id.pickup_time_input);
        restaurantName_text = findViewById(R.id.restaurantName_text);

        hours_text = findViewById(R.id.hours_text);
        address_text = findViewById(R.id.address_text);


        restaurantName = intent.getStringExtra("restaurant_name");
        restaurantName_text.setText(restaurantName);


        info = db.getRestaurantInfo(restaurantName);

        menuList = findViewById(R.id.menu_list);

        List<String> restaurant_info = info.get(0);
        restaurantID = restaurant_info.get(0);
        address = restaurant_info.get(1);
        hours = restaurant_info.get(2);

        hours_text.setText("Hours: " + hours);
        address_text.setText("Address: " + address);

        menu_id_list = info.get(1);
        menu_name_list = info.get(2);
        menu_price_list = info.get(3);
        name_price_list = new ArrayList<>();
        int n = menu_name_list.size();
        for (int i = 0; i < n; i++) {
            name_price_list.add(menu_name_list.get(i) + " : " + menu_price_list.get(i) + "$");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, name_price_list);

        menuList.setAdapter(adapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!choice.add(position)) {
                    choice.remove(position);
                    view.setBackgroundColor(Color.WHITE);
                }
                else view.setBackgroundColor(Color.BLUE);
            }
        });
    }

    public void createOrder(View view) {
        if (validInput()) {
            for (int i : choice) {
                total_price += Double.parseDouble(menu_price_list.get(i));
            }
            time = db.getDateTime();

            pickup_time = pickup_time_input.getText().toString();

            order_status = "Placed";

            int order_id = db.addOrder(studentID, total_price, time, pickup_time, order_status);
//            for (int i : choice) {
//                db.add_to_order_menu(order_id, menu_id_list.get(i));
//            }
            if (order_id > 0) {
                boolean add_om = false;
                for (int i : choice) {
                    add_om = db.add_to_order_menu(order_id, menu_id_list.get(i));
                }
                if (add_om) Toast.makeText(this, "Order Placed!", Toast.LENGTH_SHORT).show();
                // go to student home
                Intent studentHome = new Intent(this, StudentHomeActivity.class);
                studentHome.putExtra("student_id", studentID);
                startActivity(studentHome);
            }
        }
        else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }

    }

    public void goHome(View view) {
        Intent studentHome = new Intent(this, StudentHomeActivity.class);
        studentHome.putExtra("student_id", studentID);
        startActivity(studentHome);
    }

    private boolean validInput() {
        if (choice.size() == 0) {
            Toast.makeText(this, "Choose Your Food!", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (pickup_time.equals("")) {
//            pickup_time_input.setError("Input Pickup Time");
//            return false;
//        }
        return true;
    }

}
