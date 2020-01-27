package com.androidtutz.anushka.workmanagerdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.androidtutz.anushka.workmanagerdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public final static String KEY_COUNT_VALUE = "key_count";
    public final static String KEY_RETURN_VALUE = "key_return";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        Data data = new Data.Builder()
                .putInt(KEY_COUNT_VALUE, 30000)
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(DemoWorker.class)
                .setInputData(data)
                .setConstraints(constraints)
                .build();

        binding.fab.setOnClickListener(view -> WorkManager
                .getInstance().enqueue(request));

        WorkManager.getInstance()
                .getWorkInfoByIdLiveData(request.getId())
                .observe(this, workInfo -> {
                    if(workInfo != null) {
                        binding.content.tvStatus.setText(workInfo.getState().name());

                        if(workInfo.getState().isFinished()) {
                            Data data1 = workInfo.getOutputData();

                            Toast.makeText(this, data1.getString(KEY_RETURN_VALUE),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}