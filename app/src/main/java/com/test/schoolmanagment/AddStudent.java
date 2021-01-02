package com.test.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStudent extends AppCompatActivity {
    private EditText studentName, mcneeseId,passwordd;
    private Button add;

    private FirebaseAuth firebaseAuth;

    private TextView btnvaluedatabase;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        firebaseAuth = FirebaseAuth.getInstance();
        btnvaluedatabase = findViewById(R.id.passedclassnamedatabase);

        SharedPreferences getShared=getSharedPreferences("demo",MODE_PRIVATE);
        String classnamepassed=getShared.getString("str","");
        btnvaluedatabase.setText(classnamepassed);

        databaseReference = FirebaseDatabase.getInstance().getReference("students");


        studentName = (EditText)findViewById(R.id.studentNamedatabase);
        mcneeseId = (EditText)findViewById(R.id.mcneeseiddatabase);
        passwordd=findViewById(R.id.passwordd);
        add = (Button) findViewById(R.id.addStudentdatabase);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });



    }


    public void addStudent(){
        String studentNameValue = studentName.getText().toString();
        String mcneeseIdValue = mcneeseId.getText().toString();
        String id=passwordd.getText().toString();
        if(!TextUtils.isEmpty(studentNameValue)&&!TextUtils.isEmpty(mcneeseIdValue)){
            Students students = new Students(id,studentNameValue,mcneeseIdValue);
            databaseReference.child(btnvaluedatabase.getText().toString()).child(mcneeseId.getText().toString()).setValue(students);
            studentName.setText("");
            mcneeseId.setText("");
            passwordd.setText("");
            Toast.makeText(AddStudent.this,"Student Details Added",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(AddStudent.this,"Please Fill Fields",Toast.LENGTH_SHORT).show();
        }
    }




}
