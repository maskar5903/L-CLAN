package com.example.l_clan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class first_page extends AppCompatActivity {
    Button startfp;
    Button gotouserprofilefp;
    TextView textfp1;
    TextView textfp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        startfp = findViewById(R.id.startfp);
        textfp1 = findViewById(R.id.textfp1);
        textfp2 = findViewById(R.id.textfp2);
        gotouserprofilefp = findViewById(R.id.gotouserprofilefp);
    }
    public void gotouserprofile(View view){
        finish();
    }
    public void startwithlearning(View view){
        startfp.setVisibility(View.GONE);
        textfp1.setText(R.string.textfp1);
        textfp2.setText(R.string.textfp2);
    }
}