package com.androidtutz.anushka.workmanagerdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.androidtutz.anushka.workmanagerdemo.MainActivity.KEY_COUNT_VALUE;
import static com.androidtutz.anushka.workmanagerdemo.MainActivity.KEY_RETURN_VALUE;

public class DemoWorker extends Worker {

    public DemoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Data data = getInputData();
        int count = data.getInt(KEY_COUNT_VALUE, 0);

        for (int i = 0; i < count; i++) {
            Log.i("MYTAG", "Count is " + i);
        }

        Data response = new Data.Builder()
                .putString(KEY_RETURN_VALUE, "Deu boa!!!")
                .build();

        return Result.success(response);
    }
}
