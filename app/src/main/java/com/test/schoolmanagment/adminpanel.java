package com.test.schoolmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class adminpanel extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText emailregister,passowwrdregister;
    private Button buttonregister;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    Spinner classregister;
    private ProgressDialog processDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        emailregister=findViewById(R.id.emailnameregister);
        passowwrdregister=findViewById(R.id.passwordRegister);

        buttonregister=findViewById(R.id.buttonRegister);
        databaseReference=FirebaseDatabase.getInstance().getReference("Teacher");
        firebaseAuth=FirebaseAuth.getInstance();


        classregister=findViewById(R.id.classnameregister);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(adminpanel.this,R.array.classes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classregister.setAdapter(adapter);
        classregister.setOnItemSelectedListener(adminpanel.this);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String classnam= classname.message;
                final String email=emailregister.getText().toString();
                final String password=passowwrdregister.getText().toString();
                processDialog = new ProgressDialog(adminpanel.this);

                if(validate()) {


                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(adminpanel.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    processDialog.setMessage("Please Wait....");
                                    processDialog.show();

                                    if (task.isSuccessful()) {

                                        dataholder information = new dataholder(classnam,
                                                email,
                                                password);

                                        FirebaseDatabase.getInstance().getReference("Teacher").child(firebaseAuth.getInstance().getCurrentUser().getUid()).setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(adminpanel.this, "Teacher details added succesfully", Toast.LENGTH_SHORT).show();
                                                emailregister.setText("");
                                                passowwrdregister.setText("");


                                            }
                                        });

                                    } else {
                                        Toast.makeText(adminpanel.this, "something error is there", Toast.LENGTH_SHORT).show();

                                    }

                                    processDialog.dismiss();

                                    // ...
                                }
                            });
                }


            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();
        classname.message=text;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(adminpanel.this,"please select class",Toast.LENGTH_SHORT).show();

    }

    private Boolean validate(){
        boolean result = false;
        String name = emailregister.getText().toString();
        String password = passowwrdregister.getText().toString();
        String classname = com.test.schoolmanagment.classname.message;

        if(name.isEmpty() && password.isEmpty() || classname.isEmpty()){
            Toast.makeText(this,"Please Enter all details",Toast.LENGTH_SHORT).show();
        }else{
            result =true;
        }
        return result;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}