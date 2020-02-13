package com.example.auxilium;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HelpActivity extends AppCompatActivity {
    private FirebaseAuth f_auth;
    private FirebaseUser f_user;
    private Button btn_change_pass, btn_report_prob;
    public String support_email = "mailto:alyx.cmd@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setTitle("Help");

        btn_change_pass = findViewById(R.id.profile_btn_change_pass);
        btn_report_prob = findViewById(R.id.profile_btn_report_prob);

        // Change Password Function
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_auth = FirebaseAuth.getInstance();
                f_user = f_auth.getCurrentUser();

                String user_email = f_user.getEmail();
                f_auth.sendPasswordResetEmail(user_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(HelpActivity.this, "Reset Email Sent.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(HelpActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // Report Problem Function
        btn_report_prob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(support_email));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(HelpActivity.this, "No Email Client Available.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
