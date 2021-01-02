package com.test.schoolmanagment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
public class HomeScreen extends AppCompatActivity {
    Button learn,liveclass,contact,logout,changepassword;
    FirebaseAuth firebaseAuth;
    TextView textView,date;
    String classnamepassed;
    DatabaseReference db;
    String linkk;
    String student;
    int counter=0;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        learn = findViewById(R.id.learn);
        liveclass = findViewById(R.id.liveclass);
        firebaseAuth = FirebaseAuth.getInstance();
        contact = findViewById(R.id.contact);
        logout = findViewById(R.id.logout);
        textView = findViewById(R.id.clc);
        changepassword = findViewById(R.id.changepass);
        date = findViewById(R.id.date);



        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        final String datee = month + 1 + "-" + day + "-" + year;

        date.setText(datee);


//        for classname
        SharedPreferences getnamepass = getSharedPreferences("cl", MODE_PRIVATE);
        classnamepassed = getnamepass.getString("st", "");

//        for student name
        SharedPreferences studentname = getSharedPreferences("studentname", MODE_PRIVATE);
        student = studentname.getString("stnm", "i");


        textView.setText(String.format("Hello, %s", student));


        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentt = new Intent(HomeScreen.this, Notes.class);
                startActivity(intentt);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences getnamepass = getSharedPreferences("information", MODE_PRIVATE);
                SharedPreferences cls = getSharedPreferences("cl", MODE_PRIVATE);
                cls.edit().clear().commit();
                getnamepass.edit().clear().commit();
                finishAffinity();
                Toast.makeText(HomeScreen.this, "LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeScreen.this, MainActivity.class));

            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, WebView.class);
                intent.putExtra("livechat","https://tawk.to/chat/5f5f95e64704467e89eeda7d/default");
                startActivity(intent);
            }
        });


//        SharedPreferences getname = getSharedPreferences("information", MODE_PRIVATE);
//        String id = getname.getString("id", "i");
//
//        db = FirebaseDatabase.getInstance().getReference().child("Attendence").child(classnamepassed).child("Date = " + datee);
//
//            db.child(id).addValueEventListener(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String studentname = dataSnapshot.child("studentName").getValue().toString();
//                    if (student.equals(studentname)) {
//                        counter++;
//
//                        atten.setText(String.valueOf("Total Attendence " + counter));
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(HomeScreen.this, "not fetched data", Toast.LENGTH_SHORT).show();
//
//                }
//            });

        liveclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db= FirebaseDatabase.getInstance().getReference().child("meeting").child(classnamepassed);
                db.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            linkk = Objects.requireNonNull(snapshot.child("meetinglink").getValue()).toString();
                        }
                        catch (NullPointerException e){
                            Toast.makeText(HomeScreen.this,"Meeting is not Started Yet",Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });





    }

    private void gotoUrl(String linkk) {
        Uri uri=Uri.parse(linkk);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


    @Override
    public void onBackPressed () {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent _intentOBJ= new Intent(Intent.ACTION_MAIN);
                _intentOBJ.addCategory(Intent.CATEGORY_HOME);
                _intentOBJ.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                _intentOBJ.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(_intentOBJ);
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}
