package com.test.schoolmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class TakeAttendenceList extends AppCompatActivity {
    ListView listView;
    DatabaseReference databaseReference;
    ArrayList<String> title_list,story_list;
    myStory MyStory;
    String classs;
   DatabaseReference ref;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence_list);

        listView=findViewById(R.id.lis);


//        date
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        final String date = month+1 + "-" + day + "-" + year;

        SharedPreferences getShared=getSharedPreferences("demo",MODE_PRIVATE);
        classs=getShared.getString("str","");
        ref=FirebaseDatabase.getInstance().getReference("students");
        databaseReference= FirebaseDatabase.getInstance().getReference("students").child(classs);
        MyStory=new myStory();
        title_list=new ArrayList<>();
        story_list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.row,R.id.rowTxt,title_list);
        Toast.makeText(TakeAttendenceList.this,date,Toast.LENGTH_LONG).show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                title_list.clear();
                story_list.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    MyStory=ds.getValue(myStory.class);
                    if (MyStory != null) {
                        title_list.add(MyStory.getMcneeseId());
                        story_list.add(MyStory.getStudentName());

                    }
                    else{
                        Toast.makeText(TakeAttendenceList.this,"There is no student",Toast.LENGTH_SHORT).show();
                    }

                }
                listView.setAdapter(adapter);



//....................... TO LOCATION
                final   DatabaseReference toPath = FirebaseDatabase.getInstance()
                        .getReference("Attendence")
                        .child(classs)  // NEED TO GET DATE INPUT
                        .child("Date = "+date);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                             classname.position2=position;
                        ref.child(classs).orderByChild("mcneeseId").equalTo(title_list.get(position)).addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    //.........................
                                    ValueEventListener valueEventListener = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            toPath.child(title_list.get(classname.position2)).setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isComplete()) {

                                                        //  Toast.makeText(TakeAttendence.this,"Attendence Accepted",Toast.LENGTH_SHORT).show();

                                                    } else {

                                                    }
                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {}
                                    };
                                    ref.child(classs).child(title_list.get(classname.position2)).addListenerForSingleValueEvent(valueEventListener);

                                    Toast.makeText(TakeAttendenceList.this,"Attendence Accepted",Toast.LENGTH_SHORT).show();
                                }

                                else
                                {
                                    Toast.makeText(TakeAttendenceList.this,"Invalid",Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TakeAttendenceList.this,"data not fetched by some reason..",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Attendence Accepted! Do you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               Intent intent=new Intent(TakeAttendenceList.this,TeacherPower.class);
               startActivity(intent);
               finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}
