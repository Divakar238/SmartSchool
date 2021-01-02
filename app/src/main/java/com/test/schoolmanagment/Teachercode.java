package com.test.schoolmanagment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Teachercode extends AppCompatActivity {
    EditText code;
    Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachercode);
        code=findViewById(R.id.code);
        enter=findViewById(R.id.enter);


        SharedPreferences getn=getSharedPreferences("de",MODE_PRIVATE);
        String co=getn.getString("str","i");

        if (co.equals("13042001")){
            Intent intent=new Intent(Teachercode.this,TeacherLogin.class);
            startActivity(intent);
        }



        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String code2=code.getText().toString();
                if (code2.equals("13042001")){
                    SharedPreferences shrd=getSharedPreferences("de",MODE_PRIVATE);
                    SharedPreferences.Editor editor=shrd.edit();
                    editor.putString("str",code2);
                    editor.apply();
                    Intent intent=new Intent(Teachercode.this,TeacherLogin.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Teachercode.this,"PLEASE ENTER VALID CODE", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}