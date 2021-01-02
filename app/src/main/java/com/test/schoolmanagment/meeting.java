package com.test.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class meeting extends AppCompatActivity {
    FirebaseDatabase db;
    EditText meetinglink;
    Button enterr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        meetinglink=findViewById(R.id.link);
        enterr=findViewById(R.id.meet);

        db=FirebaseDatabase.getInstance();

        enterr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences getShared=getSharedPreferences("demo",MODE_PRIVATE);
                String classs=getShared.getString("str","");
                String mettinglin=meetinglink.getText().toString().trim();
                classname.link=mettinglin;

                dataholder obj=new dataholder(classname.link);

                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference node=db.getReference("meeting");
                node.child(classs).setValue(obj);
                meetinglink.setText("");
                Toast.makeText(getApplicationContext(),"Link Entered",Toast.LENGTH_LONG).show();

            }
        });
    }
}