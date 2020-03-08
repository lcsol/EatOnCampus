package com.example.eatoncampus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateInfoActivity extends AppCompatActivity {
    EditText address_input, hours_input;
    Button update_button, cancel_button;
    SQLiteHandler db;
    String managerID, restaurant_id, address, hours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        db = new SQLiteHandler(this);
        Intent intent = getIntent();
        managerID = intent.getStringExtra("manager_id");

        restaurant_id = db.get_restaurantID_by_mID(managerID);

        address_input = findViewById(R.id.address_input);
        hours_input = findViewById(R.id.hours_input);
        update_button = findViewById(R.id.update_button);
        cancel_button = findViewById(R.id.cancel_button);


    }
    // add menu
    public void editMenu(View view) {
        address = address_input.getText().toString();
        hours = hours_input.getText().toString();

        if (validInput()) {
            boolean update = db.updateInfo(restaurant_id, address, hours);
            if (update) {
                Toast.makeText(this, "Restaurant Info Updated!", Toast.LENGTH_SHORT).show();
                Intent managerHome = new Intent(this, ManagerHomeActivity.class);
                managerHome.putExtra("manager_id", managerID);
                startActivity(managerHome);
            }
        }
        else Toast.makeText(this, "Update Failed!", Toast.LENGTH_SHORT).show();
    }

    // cancel
    public void cancel(View view) {
//        Intent edit = new Intent(this, EditMenuActivity.class);
//        startActivity(edit);
        Intent managerHome = new Intent(this, ManagerHomeActivity.class);
        managerHome.putExtra("manager_id", managerID);
        startActivity(managerHome);
    }

    // check if menu info is valid
    private boolean validInput() {
        if (address.equals("")) {
            address_input.setError("Input Menu Name");
            return false;
        }
        if (hours.equals("")) {
            hours_input.setError("Input Menu Price");
            return false;
        }
        return true;
    }

}
