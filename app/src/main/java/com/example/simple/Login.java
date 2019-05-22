package com.example.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button login_Button;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login_Button = (Button) findViewById(R.id.login_button);
        login_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==login_Button)
        Toast.makeText(Login.this,"login successful",Toast.LENGTH_SHORT).show();
    }
}
