package com.travelplan.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PlacesScreen extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_screen);

        Button btnHome=(Button)findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);

        Button btnGetDirection=(Button)findViewById(R.id.btnGetDirection);
        btnGetDirection.setOnClickListener(this);

        Button btnTravelListScreen=(Button)findViewById(R.id.btnTravelList);
        btnTravelListScreen.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.places_screen, menu);
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

            case R.id.btnHome:
                btnHomeClicked();
                break;
            case R.id.btnGetDirection:
                btnGetDirectionClicked();
                break;
            case R.id.btnTravelList:
                btnAddTravelList();
                break;
        }
    }

    private void btnGetDirectionClicked() {
        startActivity(new Intent("com.travelplan.app.DirectionsScreen"));
    }

    private void btnHomeClicked() {
        startActivity(new Intent("com.travelplan.app.MainActivity"));
    }

    private void btnAddTravelList()
    {
        startActivity(new Intent("com.travelplan.app.TravelList"));
    }
}
