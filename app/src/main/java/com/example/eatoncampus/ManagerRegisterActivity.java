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

public class ManagerRegisterActivity extends AppCompatActivity {
    EditText managerID_input, managerFname_input, managerLname_input, managerRID_input, managerPassword_input;
    Button managerRegister_button;
    String managerID, managerFname, managerLname, managerRID, managerPassword;
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_register);
        managerID_input = findViewById(R.id.managerID_input);
        managerFname_input = findViewById(R.id.manager_fname_input);
        managerLname_input = findViewById(R.id.manager_lname_input);
        managerRID_input = findViewById(R.id.manager_RID_input);
        managerPassword_input = findViewById(R.id.manager_password_input);
        managerRegister_button = findViewById(R.id.manager_register_button);
        db = new SQLiteHandler(this);
    }

    // register new manager account
    public void managerRegister(View view) {
        managerID = managerID_input.getText().toString();
        managerFname = managerFname_input.getText().toString();
        managerLname = managerLname_input.getText().toString();
        managerRID = managerRID_input.getText().toString();
        managerPassword = managerPassword_input.getText().toString();

        if (validRegister()) {
            Manager.managerID = managerID;
//            Manager.manager_RID = managerRID;
            boolean added = db.addManager(managerID, managerFname, managerLname, managerRID, managerPassword);
            if (added) {
                Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                // go to manager home
                Intent ManagerHome = new Intent(this, ManagerHomeActivity.class);
                ManagerHome.putExtra("manager_id", managerID);
                startActivity(ManagerHome);
            }
            else Toast.makeText(this, "Register Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    // check if registration info is valid
    private boolean validRegister() {
        if (managerID.equals("")) {
            managerID_input.setError("Input Your Manager ID");
            return false;
        }
        if (db.checkManagerID(managerID)) {
            managerID_input.setError("Manager ID has been registered");
            return false;
        }
        if (managerFname.equals("")) {
            managerFname_input.setError("Input Your First Name");
            return false;
        }
        if (managerLname.equals("")) {
            managerLname_input.setError("Input Your Last Name");
            return false;
        }
        if (managerRID.equals("")) {
            managerRID_input.setError("Input Your Restaurant ID");
            return false;
        }
        if (managerPassword.equals("")) {
            managerPassword_input.setError("Input Your Password");
            return false;
        }
        return true;
    }

}
