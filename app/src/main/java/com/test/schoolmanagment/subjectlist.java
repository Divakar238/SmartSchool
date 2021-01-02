package com.test.schoolmanagment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class subjectlist extends AppCompatActivity {
   EditText t1,videos;
   TextView textView;
   Button enter;
    String st;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectlist);
        t1=findViewById(R.id.data);
        videos=findViewById(R.id.videolink);
        enter=findViewById(R.id.enter);
        textView=findViewById(R.id.cls);
        SharedPreferences getShared=getSharedPreferences("demo",MODE_PRIVATE);
        final String st=getShared.getString("str","");
        String o="Enter "+st+" URL";
        textView.setText(o);
        enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String classnamee=st;
                String  classlink=t1.getText().toString().trim();
                String videolink=videos.getText().toString().trim();
                classname.notes=classlink;
                classname.video=videolink;

                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference node=db.getReference("classes");
                 if(!videolink.isEmpty()&&!classlink.isEmpty()){
                    node.child(classnamee).child("classlink").setValue(classlink);
                    node.child(classnamee).child("videoslink").setValue(videolink);

                }
                if(!classlink.isEmpty()){
                    node.child(classnamee).child("classlink").setValue(classlink);

                }
                if(!videolink.isEmpty()) {
                    node.child(classnamee).child("videoslink").setValue(videolink);
                }

                t1.setText("");
                videos.setText("");
                Toast.makeText(getApplicationContext(),"link inserted",Toast.LENGTH_LONG).show();

            }
        });



    }

}