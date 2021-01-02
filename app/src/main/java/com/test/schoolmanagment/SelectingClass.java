package com.test.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SelectingClass extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spinner;
Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_class);
        enter=findViewById(R.id.enter);
        spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(SelectingClass.this,R.array.classes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(SelectingClass.this);
        SharedPreferences getnamepass=getSharedPreferences("cl",MODE_PRIVATE);
        String clas=getnamepass.getString("st","c");
        if(!clas.equals("c")){
            startActivity(new Intent(SelectingClass.this,StudentLogin.class));
        }


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences shrd=getSharedPreferences("cl",MODE_PRIVATE);
                SharedPreferences.Editor editor=shrd.edit();
                editor.putString("st",classname.message);
                editor.apply();
                Intent intent=new Intent(SelectingClass.this,StudentLogin.class);
                startActivity(intent);
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
        Toast.makeText(SelectingClass.this,"please select class",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        SharedPreferences cls=getSharedPreferences("cl",MODE_PRIVATE);
        cls.edit().clear().commit();
        super.onBackPressed();
    }
}