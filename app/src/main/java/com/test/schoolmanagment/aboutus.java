package com.test.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class aboutus extends AppCompatActivity {
    TextView texrone,texttwo,text3,text4,text5;
    ImageView imageView,imageView2;

    private EditText mEditTextTo;
    private EditText mEditTextSubject;
    private EditText mEditTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        texrone=findViewById(R.id.txtone);
        texttwo=findViewById(R.id.txttwo);
        text3=findViewById(R.id.txt3);
        text4=findViewById(R.id.txt4);
        text5=findViewById(R.id.txt5);
        imageView=findViewById(R.id.imagevieww);
        imageView2=findViewById(R.id.instagram);

        mEditTextTo=findViewById(R.id.edit_text_to);
        mEditTextSubject=findViewById(R.id.edit_text_subject);
        mEditTextMessage=findViewById(R.id.edit_text_message);
        Button buttonSend=findViewById(R.id.buttonsend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("https://instagram.com/alokvidhyaviharsr.sec.school?igshid=12cqfsihlffaf");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
    }

    private void sendMail(){
        String recipentlist=mEditTextTo.getText().toString();
        String[] recipents=recipentlist.split(",");
        String subject=mEditTextSubject.getText().toString();
        String message=mEditTextMessage.getText().toString();
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipents);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an email client"));
    }


}