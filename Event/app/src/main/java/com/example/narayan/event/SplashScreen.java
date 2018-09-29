package com.example.narayan.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private int SleepTimer=3;
    ImageView splashLogo;
    Animation uptodown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);//cut pasted from line after "super.oncreate"
        getSupportActionBar().hide();

        uptodown= AnimationUtils.loadAnimation(this,R.anim.uptodown);
        splashLogo=(ImageView)findViewById(R.id.ivSplashLogo);
        splashLogo.setAnimation(uptodown);//set animation to logo

        SplashLauncher splashLauncher=new SplashLauncher();
        splashLauncher.start();
    }

    private class SplashLauncher extends Thread{
        public void run(){
            try{
                sleep(1000 * SleepTimer);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            Intent intent=new Intent(getApplicationContext(), com.example.narayan.event.Login.class);
            startActivity(intent);
            SplashScreen.this.finish();
        }
    }
}
