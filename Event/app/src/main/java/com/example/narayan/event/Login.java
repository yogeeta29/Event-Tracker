package com.example.narayan.event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.narayan.event.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText Email;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private TextView Register,tvlogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private int counter=5,playCount=0;

    ImageView loginLogo;
    Animation uptodown,uptodownLong,rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        //Animations
        rotate=AnimationUtils.loadAnimation(this,R.anim.rotate);
        uptodown= AnimationUtils.loadAnimation(this,R.anim.uptodown);
        uptodownLong=AnimationUtils.loadAnimation(this,R.anim.uptodown);
        uptodownLong.setDuration(1500);


        tvlogin=(TextView)findViewById(R.id.tvLogin);
        Typeface typeface= Typeface.createFromAsset(getAssets(),"fonts/bimasakti.ttf");
        tvlogin.setTypeface(typeface);


        loginLogo=(ImageView)findViewById(R.id.ivLoginLogo);
        Email=(EditText)findViewById(R.id.etEmail);
        Password=(EditText)findViewById(R.id.etPassword);
        Info=(TextView) findViewById(R.id.tvInfo);
        Login=(Button)findViewById(R.id.btnLogin);
        Register=(TextView)findViewById(R.id.tvRegister);

        Info.setText("Number of Attempts remaining: 5");

        //set Animation to All
        tvlogin.setAnimation(uptodownLong);//set animation
        loginLogo.setAnimation(uptodown);
        //Email.setAnimation(uptodown);
        //Password.setAnimation(uptodown);
        //Info.setAnimation(uptodown);
        //Login.setAnimation(uptodown);
        //Register.setAnimation(uptodown);


        loginLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playCount<2)
                    Toast.makeText(getApplicationContext(),"Login !!!! don't Play with the logo",Toast.LENGTH_SHORT).show();
                else if(playCount>=2 && playCount<3)
                    Toast.makeText(getApplicationContext(),"ARE YOU STUPID !!!???",Toast.LENGTH_SHORT).show();
                else if(playCount>=3 && playCount<4)
                    Toast.makeText(getApplicationContext(),"AGAIN ??? SERIOUSLY ???..... WOULD YOU PLEASE LOG IN ? ",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Eh.... Whatever.... Do what you want....",Toast.LENGTH_SHORT).show();
                //tvlogin.setAnimation(uptodownLong);
                tvlogin.startAnimation(uptodownLong);//to repeat on every click
                loginLogo.startAnimation(rotate);
                playCount++;
                //rotate.start();


            }
        });


        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null)
        {
            String name;
            finish();
            startActivity(new Intent(Login.this,MainActivity.class));
        }

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Login.this,RegistrationActivity.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {

                    SignIn(Email.getText().toString(), Password.getText().toString());

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validate()
    {
        String email,pass;
        boolean result;
        email=Email.getText().toString().trim();
        pass=Password.getText().toString().trim();
        if(email.isEmpty() || pass.isEmpty())
        {
            result=false;
        }
        else
        {
            result=true;
        }
        return result;
    }

    private void SignIn(String userName,String userPassword)
    {
        progressDialog.setMessage("Verifying the User");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Login Sucessful",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(Login.this,MainActivity.class));

                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Login Failed: Incorrect Email Id or Password",Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: "+counter);
                    if(counter==0)
                    {
                        Login.setEnabled(false);
                    }
                }
            }
        });
    }

}
