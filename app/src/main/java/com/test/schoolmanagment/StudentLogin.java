package com.test.schoolmanagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentLogin extends AppCompatActivity  {
    private EditText studentid;
    private EditText Password;
    private Button Login;
    private ProgressDialog processDialog;
    private DatabaseReference ref;
    String classnamepassed;
    private DataSnapshot dataSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
           studentid =  findViewById(R.id.editemail);
            Password =findViewById(R.id.editpassword);
            Login = findViewById(R.id.loginmainbutton);
           SharedPreferences getShared=getSharedPreferences("cl",MODE_PRIVATE);
           classnamepassed=getShared.getString("st","");
            ref=FirebaseDatabase.getInstance().getReference().child("students").child(classnamepassed);



        SharedPreferences getnamepass=getSharedPreferences("information",MODE_PRIVATE);
        String id=getnamepass.getString("id","i");
        String pas=getnamepass.getString("passwor","p");
        if(id!="i"&&pas!="p"){
          startActivity(new Intent(StudentLogin.this,HomeScreen.class));
        }

        try{
            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (validate()) {
                        final String stdID = studentid.getText().toString();



                        ref.child(stdID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Students students = dataSnapshot.getValue(Students.class);
                                String studentname;
                                try{
                                studentname = dataSnapshot.child("studentName").getValue().toString();
                                SharedPreferences name = getSharedPreferences("studentname", MODE_PRIVATE);
                                SharedPreferences.Editor namee = name.edit();
                                namee.putString("stnm", studentname);
                                namee.apply();
                                String PIN = Password.getText().toString();
                                processDialog.setMessage("Please Wait....");
                                processDialog.show();

                                assert students != null;
                                if (PIN.equals(students.getStudenId())) {
                                    String id = studentid.getText().toString();
                                    String passwor = Password.getText().toString();
                                    SharedPreferences shrd = getSharedPreferences("information", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = shrd.edit();
                                    editor.putString("id", id);
                                    editor.putString("passwor", passwor);
                                    editor.apply();

                                    Toast.makeText(StudentLogin.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(StudentLogin.this, HomeScreen.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(StudentLogin.this, "Enter Correct Pin", Toast.LENGTH_SHORT).show();
                                    processDialog.hide();
                                }
                            }
                                catch(NullPointerException e){
                                    Toast.makeText(StudentLogin.this, "Student Not Exist", Toast.LENGTH_SHORT).show();
                                }

                        }

                            @Override
                            public void onCancelled(DatabaseError error) {
                            }
                        });


                }

                }
            });

            }
        catch (Exception e){
            Toast.makeText(StudentLogin.this,"Student Not Exist",Toast.LENGTH_SHORT).show();
        }

            processDialog = new ProgressDialog(this);


    }

    private Boolean validate(){


        boolean result = false;
        String name = studentid.getText().toString();
        String password = Password.getText().toString();

        if(name.isEmpty() && password.isEmpty()  ){
            Toast.makeText(this,"Please Enter all details",Toast.LENGTH_SHORT).show();
        }
        else if (name.isEmpty()){
            Toast.makeText(this,"please Enter name",Toast.LENGTH_SHORT).show();
        }
        else if (password.isEmpty()){
            Toast.makeText(this,"please Enter password",Toast.LENGTH_SHORT).show();
        }
        else{
            result =true;
        }
        return result;
    }


}