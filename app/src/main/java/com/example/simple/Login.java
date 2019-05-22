package com.example.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button login_Button;
    private Intent i;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login_Button = (Button) findViewById(R.id.login_button);
        login_Button.setOnClickListener(this);
        i = new Intent(Login.this,main_activity2.class);
    }

    @Override
    public void onClick(View view) {
        if(view==login_Button)
            startActivity(i);
    }
}
