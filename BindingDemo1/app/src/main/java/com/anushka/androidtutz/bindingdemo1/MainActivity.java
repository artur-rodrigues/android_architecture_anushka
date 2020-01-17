package com.anushka.androidtutz.bindingdemo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView emailText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText=findViewById(R.id.tvStudentName);
        emailText=findViewById(R.id.tvStudentEmail);

        nameText.setText(getCurrentStudent().getStudentName());
        emailText.setText(getCurrentStudent().getStudentEmail());


    }

    private Student getCurrentStudent(){

        Student student = new Student();
        student.setStudentName("Alex");
        student.setStudentEmail("alex@gmail.com");
        return student;

    }
}
