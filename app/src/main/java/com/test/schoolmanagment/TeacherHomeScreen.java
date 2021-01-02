package com.test.schoolmanagment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TeacherHomeScreen extends AppCompatActivity {
    Button sub,attendence,meeting,quiz;
    Button logoutt;
    Button changepass;
    FirebaseUser User;
    private ProgressDialog processDialog;

    FirebaseAuth fbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home_screen);
        fbs=FirebaseAuth.getInstance();
        changepass=findViewById(R.id.changepass);
        sub=findViewById(R.id.sub);
        logoutt=findViewById(R.id.logoutt);
        attendence=findViewById(R.id.atten);
        meeting=findViewById(R.id.metting);
        processDialog = new ProgressDialog(this);
        quiz=findViewById(R.id.quizlink);

        User=FirebaseAuth.getInstance().getCurrentUser();
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeacherHomeScreen.this,subjectlist.class);
                startActivity(intent);
            }
        });

        logoutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbs.signOut();
                finish();
                SharedPreferences getShared=getSharedPreferences("demo",MODE_PRIVATE);
                getShared.edit().clear().commit();
                SharedPreferences getn=getSharedPreferences("de",MODE_PRIVATE);
                getn.edit().clear().commit();
                startActivity(new Intent(TeacherHomeScreen.this,MainActivity.class));
                finishAffinity();

                Toast.makeText(TeacherHomeScreen.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
            }
        });

        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeacherHomeScreen.this,TeacherPower.class);
                startActivity(intent);

            }
        });

        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeacherHomeScreen.this,meeting.class);
                startActivity(intent);
            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
           final EditText resetPassword=new EditText(v.getContext());

           final  AlertDialog.Builder passwordresetDialog=new AlertDialog.Builder(v.getContext());
           passwordresetDialog.setTitle("Reset Password");
           passwordresetDialog.setMessage("Enter New Password > 6 Characters long.");
           passwordresetDialog.setView(resetPassword);

           passwordresetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

               @Override
               public void onClick(DialogInterface dialog, int which) {
                   processDialog.setMessage("Please Wait....");
                   processDialog.show();

                   String newpassword=resetPassword.getText().toString();
                   User.updatePassword(newpassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                           Toast.makeText(TeacherHomeScreen.this,"password Reset Successfully.",Toast.LENGTH_SHORT).show();
                           processDialog.dismiss();
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(TeacherHomeScreen.this,"Password Reset Failed",Toast.LENGTH_SHORT).show();
                           processDialog.dismiss();
                       }
                   });
               }
           }).setNegativeButton("No", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.cancel();
               }
           });
           passwordresetDialog.create().show();
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(TeacherHomeScreen.this,quiz.class);
                startActivity(intent);


            }
        });


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