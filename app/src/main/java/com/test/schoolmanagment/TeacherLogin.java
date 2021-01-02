package com.test.schoolmanagment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.core.Tag;

import static android.app.PendingIntent.getActivity;

public class TeacherLogin extends AppCompatActivity {
    private EditText Email;
    private EditText Password;
    private  EditText classn;
    private Button Login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog processDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        Email =  findViewById(R.id.edimail);
        Password =findViewById(R.id.edipassword);
        Login = findViewById(R.id.loginmain);

        Button logg;
        logg=com.test.schoolmanagment.classname.teacher=Login;

        firebaseAuth = FirebaseAuth.getInstance();
        processDialog = new ProgressDialog(this);


        logg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validated()){
                    validate(Email.getText().toString(), Password.getText().toString());
                }
                else{
                    Toast.makeText(TeacherLogin.this,"enterd information is wrong",Toast.LENGTH_SHORT).show();
                }
            }

        });




        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        if (auth != null) {
            startActivity(new Intent(TeacherLogin.this, TeacherHomeScreen.class));
            finish();
        }

    }


    private void validate (String userEmail, String userPassword){

        processDialog.setMessage("Please Wait....");
        processDialog.show();


        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference uidRef = rootRef.child("Teacher").child(uid);
                    ValueEventListener eventListener = new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String classnamee = dataSnapshot.child("classnam").getValue(String.class);

                            SharedPreferences shrd=getSharedPreferences("demo",MODE_PRIVATE);
                            SharedPreferences.Editor editor=shrd.edit();
                            editor.putString("str",classnamee);
                            editor.apply();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    };
                    uidRef.addListenerForSingleValueEvent(eventListener);


                    processDialog.dismiss();
                    Toast.makeText(TeacherLogin.this,"LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TeacherLogin.this,TeacherHomeScreen.class));

                }else {
                    Toast.makeText(TeacherLogin.this,"LOGIN FAILED", Toast.LENGTH_SHORT).show();
                    processDialog.dismiss();

                }
            }
        });
    }

    private Boolean validated(){
        boolean result = false;
        String name = Email.getText().toString();
        String password = Password.getText().toString();

        if(name.isEmpty() && password.isEmpty()){
            Toast.makeText(this,"Please Enter all details",Toast.LENGTH_SHORT).show();
        }
        else{
            result =true;
        }
        return result;
    }

}