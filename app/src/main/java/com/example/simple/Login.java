package com.example.simple;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button login_Button;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private EditText emailId;
    private EditText passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailId = (EditText) findViewById(R.id.email_no);
        passWord = (EditText) findViewById(R.id.pass_word);
        login_Button = (Button) findViewById(R.id.login_button);
        login_Button.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    //method for user login
    public void userLogin() {
        String email = emailId.getText().toString().trim();
        String password = passWord.getText().toString().trim();


        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        //if the task is successfull
                        if (task.isSuccessful()) {
                            //start the profile activity

                            Toast.makeText(Login.this, "finish", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Login.this, main_activity2.class);
                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                            startActivity(i);

                        } else {
                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {

        if (view == login_Button)
            userLogin();

    }
}

