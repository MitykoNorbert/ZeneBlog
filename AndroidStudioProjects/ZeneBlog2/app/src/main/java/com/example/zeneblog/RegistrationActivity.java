package com.example.zeneblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText userNameEdt, pwdEdt, cnfPwdEdt;
    private Button registerBtn;
    private ProgressBar loadingPB;
    private TextView loginTV;
    private FirebaseAuth mAuth;
    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        userNameEdt = findViewById(R.id.idEditUserName);
        pwdEdt = findViewById(R.id.idEditPassword);
        cnfPwdEdt = findViewById(R.id.idEditCnfPwd);
        registerBtn = findViewById(R.id.idBtnRegister);
        loadingPB = findViewById(R.id.idPBLoading);
        loginTV = findViewById(R.id.idTVLogin);
        mAuth = FirebaseAuth.getInstance();
        notificationHandler = new NotificationHandler(this);




        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                String cnfPwd = cnfPwdEdt.getText().toString();
                if(!pwd.equals(cnfPwd)){
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    loadingPB.setVisibility(View.GONE);
                }else if(
                        TextUtils.isEmpty(userName)||
                        TextUtils.isEmpty(pwd)||
                        TextUtils.isEmpty(cnfPwd)
                ){ //or? nem &&, ||
                    Toast.makeText(RegistrationActivity.this, "Fill you credentials...", Toast.LENGTH_SHORT).show();
                    loadingPB.setVisibility(View.GONE);
                }else{
                    mAuth.createUserWithEmailAndPassword(userName,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 loadingPB.setVisibility(View.GONE);
                                 Toast.makeText(RegistrationActivity.this, "Successful registration!", Toast.LENGTH_SHORT).show();
                                 notificationHandler.send(userName);
                                 Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                 finish();
                             }else{
                                 loadingPB.setVisibility(View.GONE);
                                 Toast.makeText(RegistrationActivity.this, "Failed to register user.", Toast.LENGTH_SHORT).show();
                             }
                        }
                    });
                }
            }
        });
    }
}
