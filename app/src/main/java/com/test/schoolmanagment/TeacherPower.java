package com.test.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TeacherPower extends AppCompatActivity {

    private TextView btnvalue;
    int request_code =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_power);

        btnvalue = findViewById(R.id.passedclassname);

        SharedPreferences getShared=getSharedPreferences("demo",MODE_PRIVATE);
        String classs=getShared.getString("str","");
        btnvalue.setText(classs);
    }
    public  void addStudents (View view){
        //startActivity(new Intent(this,AddStudent.class));

        String TextClassname = btnvalue.getText().toString();
        // starting our intent
        Intent classintent = new Intent(this,StudentListShowDelete.class);
        startActivity(classintent);
    }

    public  void takeAttendence(View view){
        // startActivity(new Intent(this,TakeAttendence.class));

        String TextClassname = btnvalue.getText().toString();
        // starting our intent
        Intent classintent = new Intent(this,TakeAttendenceList.class);
        startActivity(classintent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TeacherPower.this,TeacherHomeScreen.class));
    }
}
