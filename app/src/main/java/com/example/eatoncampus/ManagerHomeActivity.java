package com.example.eatoncampus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ManagerHomeActivity extends AppCompatActivity {
    TextView restaurantName_text;
    Button orders, create_menu, edit_menu, update_info, logout_button;
    SQLiteHandler db;
    String restaurantName, managerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        db = new SQLiteHandler(this);
        Intent intent = getIntent();
        managerID = intent.getStringExtra("manager_id");
        restaurantName = db.get_restaurantName_by_mID(managerID);
        restaurantName_text = findViewById(R.id.restaurantName_text);
        restaurantName_text.setText(restaurantName);

        orders = findViewById(R.id.order_button);
        create_menu = findViewById(R.id.create_menu_button);
        edit_menu = findViewById(R.id.edit_menu_button);
        update_info = findViewById(R.id.update_info_button);
        logout_button = findViewById(R.id.logout_button);

    }

    // go to orders
    public void goToOrders(View view) {
        Intent orders = new Intent(this, ManagerOrderActivity.class);
        orders.putExtra("manager_id", managerID);
        orders.putExtra("restaurant_name", restaurantName);
        startActivity(orders);
    }

    // go to create menu
    public void goToCreateMenu(View view) {
        Intent create = new Intent(this, CreateMenuActivity.class);
        create.putExtra("manager_id", managerID);
        startActivity(create);
    }

    // go to edit menu
    public void goToEditMenu(View view) {
        Intent edit = new Intent(this, EditMenuActivity.class);
        edit.putExtra("restaurant_name", restaurantName);
        edit.putExtra("manager_id", managerID);
        startActivity(edit);
    }

    // go to update restaurant info
    public void goToUpdate(View view) {
        Intent update = new Intent(this, UpdateInfoActivity.class);
        update.putExtra("manager_id", managerID);
        startActivity(update);
    }

    // go to login page
    public void logout(View view) {
        Intent login = new Intent(this, LogInActivity.class);
        startActivity(login);
    }

}
