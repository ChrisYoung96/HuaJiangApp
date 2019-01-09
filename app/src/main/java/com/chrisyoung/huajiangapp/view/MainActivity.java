package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.test.ReamlTestActivity;
import com.chrisyoung.huajiangapp.test.nettest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnTest;
    private Button btnTestNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest=this.findViewById(R.id.btnTest);
        btnTestNet=this.findViewById(R.id.btnNetTest);
        btnTest.setOnClickListener(this);
        btnTestNet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btnTest.getId()){
            Intent i=new Intent(this,ReamlTestActivity.class);
            startActivity(i);
        }else if(view.getId()==btnTestNet.getId()){
            Intent i=new Intent(this,nettest.class);
            startActivity(i);
        }

    }

}
