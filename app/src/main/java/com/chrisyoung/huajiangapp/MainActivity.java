package com.chrisyoung.huajiangapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest=this.findViewById(R.id.btnTest);
        btnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btnTest.getId()){
            Intent i=new Intent(this,ReamlTestActivity.class);
            startActivity(i);
        }

    }
}
