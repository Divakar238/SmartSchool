package com.test.schoolmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentListShowDelete extends AppCompatActivity {
    ListView listView;
    DatabaseReference databaseReference;
    ArrayList<String> title_list,story_list;
    myStory MyStory;


    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_show_delete);

        listView=findViewById(R.id.list);

        SharedPreferences getShared=getSharedPreferences("demo",MODE_PRIVATE);
        String classs=getShared.getString("str","");
        databaseReference= FirebaseDatabase.getInstance().getReference("students").child(classs);
        MyStory=new myStory();
        title_list=new ArrayList<>();
        story_list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.row,R.id.rowTxt,title_list);

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

                        Toast.makeText(StudentListShowDelete.this,"There is no student",Toast.LENGTH_SHORT).show();
                    }

                }
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                            Intent intent=new Intent(StudentListShowDelete.this,ShowStudent.class);
                            intent.putExtra("story",  story_list);
                        intent.putExtra("stor",  title_list);
                            intent.putExtra("position",position);
                            startActivity(intent);

                    }
                });
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view,  int position, long id) {
                        classname.position=position;
                        AlertDialog.Builder builder=new AlertDialog.Builder(StudentListShowDelete.this);
                        builder.setMessage("Are you sure you want to delete ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child(title_list.set(classname.position,MyStory.getMcneeseId())).removeValue();
                                Toast.makeText(StudentListShowDelete.this,"Student Deleted",Toast.LENGTH_SHORT).show();
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
                        return true;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StudentListShowDelete.this,"data not fetch by some reason..",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logoutMenu:{
                Intent intent=new Intent(StudentListShowDelete.this,AddStudent.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
