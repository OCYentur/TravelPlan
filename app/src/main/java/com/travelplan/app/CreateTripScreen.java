package com.travelplan.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

public class CreateTripScreen extends ActionBarActivity implements View.OnClickListener {

    EditText editText;
    TextView txtCreatedTravelList;
    private final String FILENAME= "TravelList.txt";
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip_screen);
        try {
            Button btnCreate=(Button)findViewById(R.id.btnCreate);
            btnCreate.setOnClickListener(this);

            Button btnShowTravelLists=(Button)findViewById(R.id.btnShowTravelLists);
            btnShowTravelLists.setOnClickListener(this);

            editText=(EditText)findViewById(R.id.textDestination);
            DatePicker datePickerFrom=(DatePicker)findViewById(R.id.datePickerFrom);
            DatePicker datePickerTo= (DatePicker)findViewById(R.id.datePickerTo);

            txtCreatedTravelList=(TextView)findViewById(R.id.txtCreatedTravelList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_trip_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnCreate:
                btnCreateTripClicked();
                break;
            case R.id.btnShowTravelLists:
                btnShowTravelListsClicked();
                break;
        }


    }

    private void btnShowTravelListsClicked() {
        startActivity(new Intent("com.travelplan.app.TravelList"));

    }

    private void btnCreateTripClicked() {

        new AlertDialog.Builder(this)
        .setMessage(R.string.dialog_screen_message)
        .setTitle(R.string.dialog_screen_title)
        .setPositiveButton(R.string.dialog_screen_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                try {

                    //saveToTextFile(editText.getText().toString());
                    Toast.makeText(getApplicationContext(),"The list - "+editText.getText()+" - created!",Toast.LENGTH_SHORT).show();
                    //txtCreatedTravelList.setText("NO: "+count);
                    try {
                        File root = getFilesDir();
                        Log.v("TEST","FILEDIR--->"+root.toString());
                        if (root.canWrite()){
                            File f = new File(root, "test.txt");

                            boolean filestat = f.createNewFile();
                            Log.v("TEST"," CREATE FILE =>"+ filestat);

                            FileWriter fw = new FileWriter(f);
                            BufferedWriter out = new BufferedWriter(fw);
                            out.write("Hello world");
                            out.close();
                        }
                    } catch (IOException e) {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        })
        .setNegativeButton(R.string.dialog_screen_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        })
        .setIcon(R.drawable.ic_launcher)
        .show();

    }

    private void saveToTextFile(String data) throws FileNotFoundException {
        File fileDir = getFilesDir();
        String filedir=fileDir.toString();
        Log.v("TEST","FILEDIR--->"+filedir);

        File newFile=new File(getFilesDir() + "/TravelList.txt");
        try
        {
            boolean filestat = newFile.createNewFile();
            Log.v("TEST"," CREATE FILE =>"+ filestat);
            if(newFile.exists())
            {
                //FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                FileOutputStream fos = new FileOutputStream(newFile.getAbsolutePath());
                fos.write(data.getBytes());
                fos.close();
                count++;
            }
        } catch (IOException e) {
            Log.e("Controller", e.getMessage() + e.getLocalizedMessage() + e.getCause());
        }
    }
}
