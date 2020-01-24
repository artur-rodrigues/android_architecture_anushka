package com.androidtutz.anushka.didemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidtutz.anushka.didemo.App;
import com.androidtutz.anushka.didemo.R;
import com.androidtutz.anushka.didemo.model.SmartPhone;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    SmartPhone smartPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getComponent().inject(this);
        smartPhone.makeACall();

        findViewById(R.id.button)
                .setOnClickListener(
                        view -> {
                            startActivity(new Intent(this, Main2Activity.class));
                            finish();
                        }
                );
    }
}
