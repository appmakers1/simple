package com.example.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp_verify extends AppCompatActivity implements View.OnClickListener {

    private EditText mobileNO;
    private Button sendOtp;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private static final String TAG = "rohith";
    private String mverificationId;
    private EditText otpEnter;
    private int temp=0;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
        mobileNO = (EditText) findViewById(R.id.mobile_num);
        sendOtp = (Button) findViewById(R.id.otp_butn);
        otpEnter = (EditText) findViewById(R.id.otp_enter);

        mAuth = FirebaseAuth.getInstance();
       sendOtp.setOnClickListener(this);
        Toast.makeText(otp_verify.this, "first", Toast.LENGTH_LONG).show();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                String code = phoneAuthCredential.getSmsCode();
                Toast.makeText(otp_verify.this, "second", Toast.LENGTH_LONG).show();
                if(code!=null) {
                    otpEnter.setText(code);}
                signInWithPhoneAuthCredential(phoneAuthCredential);




            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(otp_verify.this, "error in verification", Toast.LENGTH_SHORT).show();
            }

            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                // Save the verification id somewhere
                // ...
                super.onCodeSent(verificationId, forceResendingToken);

                mverificationId = verificationId;
                mResendToken = forceResendingToken;
                temp=1;
                 sendOtp.setEnabled(true);
                 sendOtp.setText("verify otp");

                // The corresponding whitelisted code above should be used to complete sign-in.

            }
        };

    }



    //signing the user

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Intent i = new Intent(otp_verify.this, main_activity2.class);
                            startActivity(i);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(otp_verify.this, "error in verification", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {



        if (view==sendOtp)
        {
            String phoneno = mobileNO.getText().toString().trim();
            if (phoneno.isEmpty() || phoneno.length() < 10)
            {
                mobileNO.setError("enter a vaild mobile");
                mobileNO.requestFocus();
                return;
            }
            sendOtp.setEnabled(false);
            mobileNO.setEnabled(false);
            if(temp==0){
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + phoneno,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    otp_verify.this,               // Activity (for callback binding)
                    mCallbacks);  }
            // OnVerificationStateChangedCallbacks
            else
            {
                sendOtp.setEnabled(false);

                String mcode = otpEnter.getText().toString().trim();
                PhoneAuthCredential mcredential = PhoneAuthProvider.getCredential(mverificationId, mcode);
                signInWithPhoneAuthCredential(mcredential);

            }
        }
    }

}
