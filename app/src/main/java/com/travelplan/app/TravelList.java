package com.travelplan.app;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.travelplan.expandListView.Adapter.ExpandListAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class TravelList extends Activity  {

    private ExpandListAdapter adapter;
    private ExpandableListView lstView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_list);
        try
        {
            lstView=(ExpandableListView)findViewById(R.id.expListTravelLists);

            loadFromFile();

            //final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, list);
            adapter=new ExpandListAdapter(TravelList.this,groupList);

            lstView.setAdapter(adapter);
            // This listener will start if the user long presses to a group (Travel List) in expandableListView
            lstView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {

                    final File listWillBeDeleted = new File(sdcard,"/"+groupList.get(i).getName().toString()+".txt");
                    // Confirmation required to delete the travel list
                    new AlertDialog.Builder(TravelList.this)
                            .setMessage(R.string.dialog_screen_message_delete_travel_list)
                            .setTitle(R.string.dialog_screen_title)
                            .setPositiveButton(R.string.dialog_screen_yes, new DialogInterface.OnClickListener() {
                                // If the button "Yes" clicked, this function will start
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    File file = new File(sdcard,"/TravelLists.txt");
                                    StringBuilder text = new StringBuilder();
                                    BufferedReader br = null;
                                    String[] splittedLine={};
                                    try {
                                        br = new BufferedReader(new FileReader(file));
                                        String line;
                                        // Searching all the travel lists inside TravelLists.txt
                                        while ((line = br.readLine()) != null)
                                        {
                                            splittedLine=line.split("-");
                                            // If the clicked travel list is found, "if statement" will start
                                            if (splittedLine[0].contains(groupList.get(i).getName()))
                                            {
                                                //Toast.makeText(getApplicationContext(),"Array Size: "+groupList.size(),Toast.LENGTH_SHORT).show();

                                                // THE KEY PART: Clicked travel list will be removed from the array list which holds the travel lists
                                                groupList.remove(groupList.get(i));
                                                // Existing TravelLists.txt deleted
                                                file.delete();
                                                // An empty TravelLists.txt created - WARNING! Dates have not been set in this new txt file. CreateTravelList class needs to be called!!
                                                file.createNewFile();
                                                try {
                                                    // TravelLists.txt will be filled with travel lists which comes from the array list until the size has been reached by "for statement"
                                                    for (int count=0;count<=groupList.size();++count)
                                                    {
                                                        BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
                                                        // Writing the travel list based on position in array list to txt file
                                                        buf.append(groupList.get(count).getName().toString());
                                                        buf.newLine();
                                                        buf.close();
                                                        //Toast.makeText(getApplicationContext(),"Array Size: "+groupList.size(),Toast.LENGTH_SHORT).show();
                                                    }

                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // Delete the travellist.txt file
                                    listWillBeDeleted.delete();
                                    loadFromFile();
                                    Toast.makeText(getApplicationContext(),"The travel list "+splittedLine[0].toString()+" deleted from the device!",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(R.string.dialog_screen_no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            })
                            .setIcon(R.drawable.ic_launcher)
                            .show();

                    return false;
                }
            });
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

    final ArrayList<ExpandListGroup> groupList = new ArrayList<ExpandListGroup>();
    ArrayList<ExpandListChild> childList;

    File sdcard = new File(Environment.getExternalStorageDirectory()+"/TravelPlan");

    ExpandListGroup group;
    ExpandListChild child;

    private void loadFromFile()
    {
        try
        {
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
                groupList.add(group);
            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void getPlacesFromATravelList(String travelList)
    {
        try
        {
            File file2 = new File(sdcard,"/"+travelList+".txt");
            StringBuilder placesText = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file2));
            String line;
            //childList.clear();
            if(file2.length()==0)
            {
                childList=new ArrayList<ExpandListChild>();
                child=new ExpandListChild();
                group.setItems(childList);
            }
            else
            {
                childList=new ArrayList<ExpandListChild>();
                while ((line = br.readLine()) != null) {

                    child=new ExpandListChild();
                    placesText.append(line);
                    placesText.append('\n');

                    child.setName(line);
                    childList.add(child);
                }
                group.setItems(childList);
            }
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}