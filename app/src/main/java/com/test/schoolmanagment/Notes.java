package com.test.schoolmanagment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Notes extends AppCompatActivity{
    Button video,notes,quiz;
    String videoo,notess,quizli;
    String classnamepassed;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        video=findViewById(R.id.video);
        notes=findViewById(R.id.note);
        quiz=findViewById(R.id.quizlinkstudent);

        SharedPreferences getnamepass=getSharedPreferences("cl",MODE_PRIVATE);
        classnamepassed=getnamepass.getString("st","");

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    db = FirebaseDatabase.getInstance().getReference("classes");
                db.child(classnamepassed).addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            notess = snapshot.child("classlink").getValue().toString();
                            Intent intentt = new Intent(Notes.this, WebView.class);
                            intentt.putExtra("classlin", notess);
                            startActivity(intentt);
                        }
                        catch(NullPointerException e){

                            Toast.makeText(Notes.this, "Link Not Given by teacher Yet", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Notes.this, "Url not given by teacher yet", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });



        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db= FirebaseDatabase.getInstance().getReference().child("classes").child(classnamepassed);

                     db.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             try {
                                 videoo = snapshot.child("videoslink").getValue().toString();
                                 Intent intentt = new Intent(Notes.this, WebView.class);
                                 intentt.putExtra("classlin", videoo);
                                 startActivity(intentt);
                             }
                             catch (NullPointerException e){
                                 Toast.makeText(Notes.this,"Teacher not link Shared Yet",Toast.LENGTH_SHORT).show();
                             }

                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });


            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db= FirebaseDatabase.getInstance().getReference().child("classes").child(classnamepassed);
                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                quizli = snapshot.child("quizlink").getValue().toString();
                                Intent intentt = new Intent(Notes.this, WebView.class);
                                intentt.putExtra("quizlin", quizli);
                                startActivity(intentt);
                            }
                            catch (NullPointerException e){
                                Toast.makeText(Notes.this,"Teacher Link not Shared Yet",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Notes.this,"Links are not provided by teacher yet",Toast.LENGTH_SHORT).show();
                        }
                    });


            }
        });


    }
}