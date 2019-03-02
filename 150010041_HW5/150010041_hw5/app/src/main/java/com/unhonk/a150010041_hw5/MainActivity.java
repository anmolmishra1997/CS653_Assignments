package com.unhonk.a150010041_hw5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;

import java.io.File;
import java.util.List;

import umich.cse.yctung.androidlibsvm.LibSVM;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button1_click(View view)
    {
        int ACTION_CODE = 1;
        // This always works
        Intent i = new Intent(this, FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i, ACTION_CODE);
    }

    public void button2_click(View view)
    {
        int ACTION_CODE = 2;
        // This always works
        Intent i = new Intent(this, FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i, ACTION_CODE);
    }


    LibSVM svm = new LibSVM();
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Use the provided utility method to parse the result
            List<Uri> files = Utils.getSelectedFilesFromResult(intent);

            for (Uri uri: files)
            {
                File file = Utils.getFileForUri(uri);
                Trainer trainer = new Trainer(file.toString());
                trainer.translate();
                trainer.close();
                trainer.svmTrain();


//                Toast.makeText(this,
//                        file.toString(),
//                        Toast.LENGTH_SHORT).show();
                // Do something with the result...
            }
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK)
        {
            // Use the provided utility method to parse the result
            List<Uri> files = Utils.getSelectedFilesFromResult(intent);

            for (Uri uri: files) {
                File file = Utils.getFileForUri(uri);
                Trainer trainer = new Trainer(file.toString());
                trainer.translate();
                trainer.close();
                trainer.svmPredict();


                Predictor predictor = new Predictor(file.toString());
                predictor.combine();
                predictor.close();
//
//                Toast.makeText(this,
//                        file.toString(),
//                        Toast.LENGTH_SHORT).show();
                // Do something with the result...
            }
        }
    }

}
