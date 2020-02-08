package com.xstudioo.noteme;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button BnOkay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_welcome);

        BnOkay = (Button)findViewById(R.id.bnOkay);
        BnOkay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        loadHome();
    }

    private void loadHome(){
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }
}
