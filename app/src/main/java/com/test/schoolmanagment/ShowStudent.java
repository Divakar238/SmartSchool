package com.test.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowStudent extends AppCompatActivity {
    TextView textView;
    String h;
    Button button,share_button,prvs_btn,nxt_btn,btnconvert;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student);
        textView=findViewById(R.id.txt);

        button=findViewById(R.id.cpyBtn);
        share_button=findViewById(R.id.shareBtn);
        prvs_btn=findViewById(R.id.prvBtn);
        nxt_btn=findViewById(R.id.nextBtn);
        final ArrayList<String> dStory=getIntent().getStringArrayListExtra("story");
        final ArrayList<String> mStory=getIntent().getStringArrayListExtra("stor");
        position=getIntent().getIntExtra("position",0);
        assert dStory != null;
        assert mStory!=null;
       textView.setText("Name:"+dStory.get(position).concat("\nRoll No. :"+mStory.get(position)));



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard= (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip=ClipData.newPlainText("Text", "name:"+dStory.get(position).concat("\nenrollment no:"+mStory.get(position)));
                assert clipboard != null;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ShowStudent.this,"copied",Toast.LENGTH_SHORT).show();

            }
        });
        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "name:"+dStory.get(position).concat("\nenrollment no:"+mStory.get(position)));
                intent.setType("text/plain");
                intent=Intent.createChooser(intent,"Share by");
                startActivity(intent);
            }
        });

        nxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=(position+1)%dStory.size();
                textView.setText("name:"+dStory.get(position).concat("\nenrollment no:"+mStory.get(position)));
            }
        });

        prvs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=(position-1)%dStory.size();
                if(position<=0){
                    Toast.makeText(ShowStudent.this,"you're in fornt page",Toast.LENGTH_SHORT).show();
                }
                else
                    textView.setText("name:"+dStory.get(position).concat("\nenrollment no:"+mStory.get(position)));
            }
        });
    }

}

