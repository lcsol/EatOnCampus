package com.example.eatoncampus;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ConfirmOrderActivity extends AppCompatActivity {
    ListView menu_list;
    TextView total_text;
    EditText pickup_time_input;
    Button confirm_button, cancel_button;
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        db = new SQLiteHandler(this);
        menu_list = findViewById(R.id.menu_list);
        total_text = findViewById(R.id.total_text);
        pickup_time_input = findViewById(R.id.pickup_time_input);
        confirm_button = findViewById(R.id.confirm_button);
        cancel_button = findViewById(R.id.cancel_button);


    }


}
