package com.example.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registration extends AppCompatActivity  implements View.OnClickListener {
    private EditText mobile_no;
    private EditText email_id;
    private EditText pass_word;
    private Button register;
    private TextView login_here;
    private FirebaseAuth firebaseauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseauth=FirebaseAuth.getInstance();
         mobile_no=(EditText)findViewById(R.id.mobile_num);
         email_id=(EditText)findViewById(R.id.email_id);
         pass_word=(EditText)findViewById(R.id.password);
         register=(Button)findViewById(R.id.regis_button);
         login_here=(TextView)findViewById(R.id.login_here);

         register.setOnClickListener( this);
         login_here.setOnClickListener(this);

    }
    private void userregister()
    {
        String mobile=mobile_no.getText().toString().trim();
        String email=email_id.getText().toString().trim();
        String password=pass_word.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(registration.this, " registration sucessfull", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(registration.this, " registration not sucessfull", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onClick(View view)
    {
        if(view==register)
        {
          userregister();
        }
        if(view==login_here)
        {
            Intent i = new Intent(registration.this, Login.class);
            startActivity(i);
        }
    }

}
