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
import android.widget.TextView;
import android.widget.Toast;

public class CreateMenuActivity extends AppCompatActivity {
    EditText menu_name_input, menu_price_input, menu_rid_input;
    Button create_button;
    SQLiteHandler db;
    String managerID, menu_name, menu_price, menu_rid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);
        db = new SQLiteHandler(this);
        Intent intent = getIntent();
        managerID = intent.getStringExtra("manager_id");

        menu_name_input = findViewById(R.id.menu_name_input);
        menu_price_input = findViewById(R.id.menu_price_input);
        menu_rid_input = findViewById(R.id.menu_rid_input);
        create_button = findViewById(R.id.create_button);


    }

    // add menu
    public void addMenu(View view) {
        menu_name = menu_name_input.getText().toString();
        menu_price = menu_price_input.getText().toString();
        menu_rid = menu_rid_input.getText().toString();

        if (validInput()) {
            boolean added = db.addMenu(menu_name, menu_price, menu_rid);
            if (added) {
                Toast.makeText(this, "Menu Created!", Toast.LENGTH_SHORT).show();
                Intent managerHome = new Intent(this, ManagerHomeActivity.class);
                managerHome.putExtra("manager_id", managerID);
                startActivity(managerHome);
            }
        }
        else Toast.makeText(this, "LogIn Failed!", Toast.LENGTH_SHORT).show();
    }

    // check if menu info is valid
    private boolean validInput() {
        if (menu_name.equals("")) {
            menu_name_input.setError("Input Menu Name");
            return false;
        }
        if (menu_price.equals("")) {
            menu_price_input.setError("Input Menu Price");
            return false;
        }
        if (menu_rid.equals("")) {
            menu_rid_input.setError("Input Your Restaurant ID");
            return false;
        }
        if (!db.checkRestauntID(menu_rid)) {
            menu_rid_input.setError("Incorrect Restaurant ID");
            return false;
        }
        return true;
    }

}
