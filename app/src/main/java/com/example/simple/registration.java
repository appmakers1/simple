package com.example.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
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
    int RC_SIGN_IN=0;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseauth=FirebaseAuth.getInstance();
        //user registration
         mobile_no=(EditText)findViewById(R.id.mobile_num);
         email_id=(EditText)findViewById(R.id.email_id);
         pass_word=(EditText)findViewById(R.id.password);
         register=(Button)findViewById(R.id.regis_button);
         login_here=(TextView)findViewById(R.id.login_here);

         register.setOnClickListener( this);
         login_here.setOnClickListener(this);
         //google sign
         signInButton=findViewById(R.id.google_singin);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        signInButton.setOnClickListener(this);




    }
    // Google signin
    private void signIn()
    {
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try{
            GoogleSignInAccount account=completedTask.getResult(ApiException.class);
            startActivity(new Intent(registration.this,main_activity2.class));
        }
        catch(ApiException e){

        Log.w("Google Sign In Error","SignInResult:faoled coe" +e.getStatusCode());
        Toast.makeText(registration.this,"failed",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
            startActivity(new Intent(registration.this,main_activity2.class));
        super.onStart();
    }
    //user registration

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
        if(view==signInButton)
        {
            signIn();
        }
    }

}
