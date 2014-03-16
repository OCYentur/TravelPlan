package com.travelplan.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
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
import java.util.Date;
import java.util.List;

public class CreateTripScreen extends ActionBarActivity implements View.OnClickListener {

    EditText txtDestination;
    TextView txtCreatedTravelList;
    DatePicker datePickerFrom;
    DatePicker datePickerTo;
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

            txtDestination=(EditText)findViewById(R.id.textDestination);
            datePickerFrom=(DatePicker)findViewById(R.id.datePickerFrom);
            datePickerTo= (DatePicker)findViewById(R.id.datePickerTo);

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
                    addTextToFile(txtDestination.getText().toString());
                    Toast.makeText(getApplicationContext(),"The item - "+txtDestination.getText()+" - has been added to text file!",Toast.LENGTH_SHORT).show();
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

    public void addTextToFile(String text) {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            Log.e("External Storage Status: -> ","OK! <-");

            File dir=new File(Environment.getExternalStorageDirectory()+"/TravelPlan");
            dir.mkdirs();
            File textFile = new File(dir+"/TravelLists.txt");
            if (!textFile.exists()) {
                try {
                    textFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                BufferedWriter buf = new BufferedWriter(new FileWriter(textFile, true));
                buf.append(text.toUpperCase()+" --- From: "+ DateSet(datePickerFrom)+" - To: "+DateSet(datePickerTo));
                buf.newLine();
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Log.e("External Storage Status: -> ","Failed! <-");
        }
    }

    public String DateSet(DatePicker date)
    {
        String editedDate;
        editedDate=date.getMonth()+"."+date.getDayOfMonth()+"."+date.getYear();
        return editedDate;
    }
}
