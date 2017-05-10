package edu.cpp.nada.weroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(LogoActivity.this, PlacesActivity.class));
            }
        }, 4000);
    }
}
