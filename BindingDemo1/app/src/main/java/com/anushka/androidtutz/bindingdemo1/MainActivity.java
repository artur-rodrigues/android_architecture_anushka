package com.anushka.androidtutz.bindingdemo1;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anushka.androidtutz.bindingdemo1.databinding.ActivityMainBinding;
import com.anushka.androidtutz.bindingdemo1.event.StudentClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setStudent(new Student("Alex",  "alex@gmail.com"));
        binding.setStudentEvent(new StudentClick() {

            @Override
            public void enroll(Student student) {
                makeToast(student.toString());
            }

            @Override
            public void cancel() {
                makeToast("Cancelado");
            }
        });
    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
