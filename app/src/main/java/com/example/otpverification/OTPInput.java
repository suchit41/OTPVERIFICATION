package com.example.otpverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPInput extends AppCompatActivity {
 private TextView mobileno;
private  EditText input1, input2, input3,input4, input5,input6;
private  Button submit;
String getotpbackend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpinput);
        mobileno = findViewById(R.id.enterno);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        input4 = findViewById(R.id.input4);
        input5 = findViewById(R.id.input5);
        input6 = findViewById(R.id.input6);
        submit = findViewById(R.id.otpsubmit);

        mobileno.setText(String.format("+91-%S",getIntent().getStringExtra("mobileno")));
        getotpbackend = getIntent().getStringExtra("backenddata");
       final ProgressBar progressBar = findViewById(R.id.progressbar);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!input1.getText().toString().trim().isEmpty() && !input2.getText().toString().isEmpty()){
                    String entercodeotp = input1.getText().toString()+
                            input2.getText().toString()+
                            input3.getText().toString()+
                            input4.getText().toString()+
                            input5.getText().toString()+
                            input6.getText().toString();
                    if(getotpbackend != null){
                        progressBar.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.INVISIBLE);
                        Toast.makeText(OTPInput.this, "Please check internet connection", Toast.LENGTH_SHORT).show();

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getotpbackend,entercodeotp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.VISIBLE);
                                       submit.setVisibility(View.VISIBLE);

                                       if(task.isSuccessful()){
                                           Intent intent = new Intent(OTPInput.this,Dashboard.class);
                                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                           startActivity(intent);
                                       }else {
                                           Toast.makeText(OTPInput.this, "Enter correct otp", Toast.LENGTH_SHORT).show();
                                       }

                                    }
                                });
                    }
                }else {
                    Toast.makeText(OTPInput.this, "Please Enter Full Number", Toast.LENGTH_SHORT).show();
                }

            }


        });
        setOTPinput();
    }
    private  void setOTPinput(){
        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!submit.toString().isEmpty()){
                    input2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}