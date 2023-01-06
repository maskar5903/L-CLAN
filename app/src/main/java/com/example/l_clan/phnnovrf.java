package com.example.l_clan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phnnovrf extends AppCompatActivity {
    Button buttonverifyno;
    EditText edittextnoverifyno;
    ProgressBar progressbarverifyno;
    String verificationIdG;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phnnovrf);
        buttonverifyno = findViewById(R.id.buttonverifyno);
        edittextnoverifyno = findViewById(R.id.edittextnoverifyno);
        progressbarverifyno = findViewById(R.id.progressbarverifyno);
        mAuth = FirebaseAuth.getInstance();

        String phoneno = getIntent().getStringExtra("phoneno");
        Toast.makeText(this, phoneno, Toast.LENGTH_SHORT).show();

        sendverificationcodetouser(phoneno);   //jab sahi ho jaye to shuru kr dena
    }

    private void sendverificationcodetouser(String phoneno) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phoneno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
//                String code = credential.getSmsCode();  //jab sahi ho jaye tab kr lena
            String code = edittextnoverifyno.getText().toString();
                if (code!=null){
                    progressbarverifyno.setVisibility(View.VISIBLE);
                    verifycode(code);
                }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(phnnovrf.this, "verification failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(verificationId, token);
            verificationIdG = verificationId;
        }
    };
    private void verifycode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationIdG,code);
        signinbycredentials(credential);
    }

    private void signinbycredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(phnnovrf.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(phnnovrf.this, "verification successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),userprofile.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(phnnovrf.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}