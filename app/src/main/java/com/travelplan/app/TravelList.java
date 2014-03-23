package com.travelplan.app;


import android.app.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.travelplan.expandListView.Adapter.ExpandListAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TravelList extends Activity  {

    private ExpandListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_list);
        try
        {
            final ExpandableListView lstView=(ExpandableListView)findViewById(R.id.expListTravelLists);

            loadFromFile();

            //final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, list);
            adapter=new ExpandListAdapter(TravelList.this,list);

            lstView.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error: "+e.toString(),Toast.LENGTH_SHORT).show();
        }

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

    final ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();

    File sdcard = new File(Environment.getExternalStorageDirectory()+"/TravelPlan");
    ExpandListGroup group;
    ExpandListChild child;

    private void loadFromFile(){
        try {
            File file = new File(sdcard,"/TravelLists.txt");
            StringBuilder text = new StringBuilder();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                group=new ExpandListGroup();

                String[] splittedLine=line.split("-");

                text.append(line);
                text.append('\n');

                group.setName(splittedLine[0]);

                getPlacesFromATravelList(splittedLine[0]);

                list.add(group);
            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    ///////////////////////////////////////////// Function for getting place names for every single Travel List

    private void getPlacesFromATravelList(String travelList)
    {
        try
        {
            File file2 = new File(sdcard,"/"+travelList+".txt");
            StringBuilder placesText = new StringBuilder();

            BufferedReader br = new BufferedReader(new FileReader(file2));
            String line;
            if(file2.length()==0)
            {
                child=new ExpandListChild();
                ArrayList<ExpandListChild> childList=new ArrayList<ExpandListChild>();
                group.setItems(childList);
            }
            else
            {
            while ((line = br.readLine()) != null) {

                child=new ExpandListChild();
                ArrayList<ExpandListChild> childList=new ArrayList<ExpandListChild>();
                placesText.append(line);
                placesText.append('\n');

                child.setName(line);
                childList.add(child);

                group.setItems(childList);
            }
            }
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }


    /*private class StableArrayAdapter extends ArrayAdapter<String> {

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

    }*/
}
