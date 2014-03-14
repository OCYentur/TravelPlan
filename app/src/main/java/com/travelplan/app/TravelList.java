package com.travelplan.app;


import android.app.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TravelList extends Activity  {
    TextView txtView;
    String[] values = new String[] { };
    final ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_list);

        final ListView lstView=(ListView)findViewById(R.id.listTravelLists);
        txtView=(TextView)findViewById(R.id.txtShowResults);

        loadFromFile();

        final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        lstView.setAdapter(adapter);

        /*lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });*/
    }

    public final String FILENAME= "TravelList.txt";
    int count=0;
    private void loadFromFile(){

        String line=null;
        String res = null;

        try {
            InputStream in = openFileInput(FILENAME);
            if (in != null) {
                InputStreamReader input = new InputStreamReader(in);
                BufferedReader buffReader = new BufferedReader(input);
                res = "";
                while (( line = buffReader.readLine()) != null) {
                    res += line;
                    txtView.setText("NO "+count );
                    values[count]=line;
                    count++;
                }
                for (int i = 0; i < values.length; ++i) {
                    list.add(values[i]);
                }
                in.close();

                //Toast.makeText(getApplicationContext(), "File Data == " + res, Toast.LENGTH_SHORT).show();
            }else{
           /* Do something*/
            }
        } catch(Exception e){
            Toast.makeText(getApplicationContext(),e.toString() +   e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    //public String[] ListItems = new String[]{};
    /*private void PopulateDataToListView()
    {

        try
        {
            Resources ResFiles = getResources();
            InputStream ReadDbFile = ResFiles.openRawResource(R.raw.travellist);
            byte[] Bytes = new byte[ReadDbFile.available()];
            ReadDbFile.read(Bytes);
            String DbLines = new String(Bytes);
            ListItems= new String[]{DbLines};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_travel_list, R.id.listTravelLists, ListItems);
            lstView.setAdapter(adapter);

        }
        catch (Exception e) {
        }
    }*/

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
