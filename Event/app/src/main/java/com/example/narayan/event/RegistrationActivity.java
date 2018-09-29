package com.example.narayan.event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

//https://www.youtube.com/watch?v=7Yc3Pt37coM

public class RegistrationActivity extends AppCompatActivity {

    private EditText UserName,UserEmail,UserPassword;
    private Button regButton;
    private TextView UserLogin,tvRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String name,email,pass;

    Animation uptodown,uptodownLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

        uptodown= AnimationUtils.loadAnimation(this,R.anim.uptodown);
        uptodownLong=AnimationUtils.loadAnimation(this,R.anim.uptodown);
        uptodownLong.setDuration(1500);

        setupUIViews();

        // set Font for Text View Registration
        Typeface typeface= Typeface.createFromAsset(getAssets(),"fonts/bimasakti.ttf");
        tvRegistration.setTypeface(typeface);

        //set Animations
        UserName.setAnimation(uptodown);
        //UserLogin.setAnimation(uptodown);
        UserPassword.setAnimation(uptodown);
        UserEmail.setAnimation(uptodown);
        tvRegistration.setAnimation(uptodownLong);
        //set Font


        firebaseAuth= FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    progressDialog.setMessage("Registering the User");
                    progressDialog.show();
                    //Upload Data to the DB
                    String user_email = UserEmail.getText().toString().trim();
                    String user_password = UserPassword.getText().toString().trim();

                    name=UserName.getText().toString().trim();
                    email=UserEmail.getText().toString().trim();
                    pass=UserPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(UserEmail.getText().toString().trim(),UserPassword.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        // Toast.makeText(RegistrationActivity.this,"Registration Sucessful",Toast.LENGTH_SHORT).show();
                                        Users user =new Users(
                                                name,
                                                email,
                                                pass
                                        );

                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    //assign a type for the user (student)
                                                    UserType userType=new UserType("student");
                                                    FirebaseDatabase.getInstance().getReference("Type")
                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .setValue(userType);

                                                    //progressDialog.dismiss();
                                                    Toast.makeText(RegistrationActivity.this,"Registration Sucessful",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegistrationActivity.this, com.example.narayan.event.Login.class));
                                                    finish();
                                                }
                                                else
                                                {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(RegistrationActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        //startActivity(new Intent(RegistrationActivity.this,MainActivity.class));

                                    }
                                    else
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegistrationActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });


        /*
        firebaseAuth=FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate())
                {
                    progressDialog.setMessage("Registering the User");
                    progressDialog.show();
                    //Upload Data to the DB
                    String user_email = UserEmail.getText().toString().trim();
                    String user_password = UserPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this,"Registration Sucessful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
           */
        UserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegistrationActivity.this, com.example.narayan.event.Login.class));
            }
        });



    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() !=null)
        {

        }
    }*/

    private void setupUIViews()
    {
        UserName=(EditText)findViewById(R.id.etUserName);
        UserEmail=(EditText)findViewById(R.id.etUserEmail);
        UserPassword=(EditText)findViewById(R.id.etUserPassword);
        regButton=(Button)findViewById(R.id.btnRegister);
        UserLogin=(TextView) findViewById(R.id.tvUserLogin);
        tvRegistration=(TextView)findViewById(R.id.tvRegistration);
    }

    private boolean validate()
    {
        Boolean result=false;

        String name=UserName.getText().toString();
        String password=UserPassword.getText().toString();
        String email=UserEmail.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty() )
        {
            Toast.makeText(this,"Please Enter All The Fields",Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<=8)
        {
            Toast.makeText(this,"Password Length Should be More than 8 characters",Toast.LENGTH_SHORT).show();
            //result=false;
        }
        else
        {
            result=true;
        }
        return result;
    }
}
