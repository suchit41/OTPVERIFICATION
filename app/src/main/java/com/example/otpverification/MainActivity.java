package com.example.otpverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
private EditText enterno;
private Button getotp;
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterno = findViewById(R.id.enterno);
        getotp = findViewById(R.id.getotp);
        progressBar = findViewById(R.id.progressbar);



        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enterno.getText().toString().trim().isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter Mobile No",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                getotp.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + enterno.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        MainActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.VISIBLE);
                                getotp.setVisibility(View.INVISIBLE);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.VISIBLE);
                                getotp.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                getotp.setVisibility(View.VISIBLE);

                            }
                            @Override
                            public void onCodeSent(@NonNull String backenddata, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(backenddata, forceResendingToken);
                                progressBar.setVisibility(View.VISIBLE);
                                getotp.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(MainActivity.this,OTPInput.class);
                                intent.putExtra("mobileno",enterno.getText().toString());
                                intent.putExtra("backenddata",backenddata);
                                startActivity(intent);
                            }
                        }
                );

            }
        });
    }
}