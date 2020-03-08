package com.example.eatoncampus;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudentRegisterActivity extends AppCompatActivity {
    EditText studentID_input, studentFname_input, studentLname_input, studentPassword_input;
    Button studentRegister_button;
    String studentID, studentFname, studentLname, studentPassword;
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        studentID_input = findViewById(R.id.studentID_input);
        studentFname_input = findViewById(R.id.student_fname_input);
        studentLname_input = findViewById(R.id.student_lname_input);
        studentPassword_input = findViewById(R.id.student_password_input);
        studentRegister_button = findViewById(R.id.student_register_button);
        db = new SQLiteHandler(this);

    }

    // register new student account
    public void register(View view) {
        studentID = studentID_input.getText().toString();
        studentFname = studentFname_input.getText().toString();
        studentLname = studentLname_input.getText().toString();
        studentPassword = studentPassword_input.getText().toString();

        if (validRegister()) {
            Student.studentID = studentID;

            boolean added = db.addStudent(studentID, studentFname, studentLname, studentPassword);
            if (added) {
                Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                // go to student home
                Intent studentHome = new Intent(this, StudentHomeActivity.class);
                studentHome.putExtra("student_id", studentID);
                startActivity(studentHome);
            }
            else Toast.makeText(this, "Register Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    // check if registration info is valid
    private boolean validRegister() {
        if (studentID.equals("")) {
            studentID_input.setError("Input Your Student ID");
            return false;
        }
        if (db.checkStudentID(studentID)) {
            studentID_input.setError("Student ID has been registered");
            return false;
        }
        if (studentFname.equals("")) {
            studentFname_input.setError("Input Your First Name");
            return false;
        }
        if (studentLname.equals("")) {
            studentLname_input.setError("Input Your Last Name");
            return false;
        }
        if (studentPassword.equals("")) {
            studentPassword_input.setError("Input Your Password");
            return false;
        }
        return true;
    }

}
