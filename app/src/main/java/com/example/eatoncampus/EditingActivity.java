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

public class EditingActivity extends AppCompatActivity {
    EditText menu_name_input, menu_price_input;
    Button edit_button, cancel_button;
    SQLiteHandler db;
    String managerID, menuID, menu_name, menu_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);
        db = new SQLiteHandler(this);
        Intent intent = getIntent();
        menuID = intent.getStringExtra("menu_id");
        managerID = intent.getStringExtra("manager_id");

        menu_name_input = findViewById(R.id.menu_name_input);
        menu_price_input = findViewById(R.id.menu_price_input);
        edit_button = findViewById(R.id.edit_button);
        cancel_button = findViewById(R.id.cancel_button);

    }

    // add menu
    public void editMenu(View view) {
        menu_name = menu_name_input.getText().toString();
        menu_price = menu_price_input.getText().toString();

        if (validInput()) {
            boolean edit = db.editMenu(menuID, menu_name, menu_price);
            if (edit) {
                Toast.makeText(this, "Menu Edited!", Toast.LENGTH_SHORT).show();
                Intent managerHome = new Intent(this, ManagerHomeActivity.class);
                managerHome.putExtra("manager_id", managerID);
                startActivity(managerHome);
            }
        }
        else Toast.makeText(this, "Edit Failed!", Toast.LENGTH_SHORT).show();
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
        if (menu_name.equals("")) {
            menu_name_input.setError("Input Menu Name");
            return false;
        }
        if (menu_price.equals("")) {
            menu_price_input.setError("Input Menu Price");
            return false;
        }
        return true;
    }

}
