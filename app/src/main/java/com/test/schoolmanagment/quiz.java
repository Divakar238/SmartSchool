package com.test.schoolmanagment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class quiz extends AppCompatActivity {

    Button quizbutton;
    String classnamepassed;
    EditText quizlink;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        quizlink=findViewById(R.id.quizlink);
         quizbutton=findViewById(R.id.quizenter);

        SharedPreferences getShared=getSharedPreferences("demo",MODE_PRIVATE);
        classnamepassed=getShared.getString("str","");
        quizbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String classnamee=classnamepassed;
                String  qulink=quizlink.getText().toString().trim();

                classname.quilink=qulink;
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference node=db.getReference("classes");
                node.child(classnamee).child("quizlink").setValue(qulink);
                quizlink.setText("");
                Toast.makeText(getApplicationContext(),"Link Successfully Stored ",Toast.LENGTH_LONG).show();

            }
        });








    }
}