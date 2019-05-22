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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registration extends AppCompatActivity  implements View.OnClickListener {
    private EditText mobile_no;
    private EditText email_id;
    private EditText pass_word;
    private Button register;
    private TextView login_here;
    private FirebaseAuth firebaseauth;
    //private FirebaseAuth mfirebaseauth;
    private CallbackManager mCallbackManager;
    private static final String TAG="facelog";
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
        //facebook login
       // mfirebaseauth=FirebaseAuth.getInstance();
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.face_signin);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);

            }
        });





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
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // for facebook to handle sign in result
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseauth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(registration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    // for google to handle sign in result
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

        super.onStart();
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
            startActivity(new Intent(registration.this,main_activity2.class));
        //facebook login
        FirebaseUser currentUser=firebaseauth.getCurrentUser();

    }
    // updateui for facebook
    private void updateUI()
    {
        Toast.makeText(registration.this, "you are logged in", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(registration.this,Login.class);
        startActivity(intent);
    }
    //user registration for google

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
