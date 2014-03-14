package com.travelplan.app;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    String[] places = new String[] { "CN Tower", "Niagara Falls", "Wonderland", "Casa Loma", "Stanley Park Seawall",
                                    "Salt Spring Island", "La Citadelle", "Basilique Notre-Dame", "Musee de la Civilisation",
                                    "Vieux-Port de Montreal", "Rogers Center", "Canadian Canoe Museum",
                                    "National Gallery of Canada", "Royal Ontario Museum",
                                    "Alexander Graham Bell National Historic Site" };
    boolean placeSelectedFromString=false;
    AutoCompleteTextView autoComp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText textViewSearch = (EditText)findViewById(R.id.textViewSearch);

        Button btnCreateTrip= (Button) findViewById(R.id.btnCreateTrip);
        btnCreateTrip.setOnClickListener(this);

        Button btnSearch=(Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        Button btnManualNavigation=(Button)findViewById(R.id.btnManualNavigation);
        btnManualNavigation.setOnClickListener(this);

        Button btnTravelList=(Button)findViewById(R.id.btnTravelList);
        btnTravelList.setOnClickListener(this);

        autoComp = (AutoCompleteTextView)findViewById(R.id.autoCompTxtSearch);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, places);
        autoComp.setAdapter(adapter);

        autoComp.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView <?> parent, View arg1, int pos,long id) {
                placeSelectedFromString=true;
            }
        });

        autoComp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                placeSelectedFromString=false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            case R.id.btnSearch:
                btnSearchClicked();
                break;
            case R.id.btnCreateTrip:
                btnCreateTripClicked();
                break;
            case R.id.btnManualNavigation:
                btnManualNavigationClicked();
                break;
            case R.id.btnTravelList:
                btnTravelListClicked();
                break;
        }

    }

    private void btnTravelListClicked() {
        startActivity(new Intent("com.travelplan.app.TravelList"));
    }

    private void btnManualNavigationClicked() {
        startActivity(new Intent("com.travelplan.app.ManualNavigationScreen"));

    }

    private void btnCreateTripClicked() {
        startActivity(new Intent("com.travelplan.app.CreateTripScreen"));
    }

    private void btnSearchClicked(){
        if (placeSelectedFromString==true)
        {
            startActivity(new Intent("com.travelplan.app.PlacesScreen"));
            /*Intent i=new Intent(autoComp.getContext(),PlacesScreen.class);
            i.putExtra("selectedPlace",autoComp.getText().toString());
            startActivity(i);*/
        }
        else if(placeSelectedFromString==false)
        {
            startActivity(new Intent("com.travelplan.app.SearchScreen"));
        }
    }
}
