package edu.byui_cs.jjmn.ponderize;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static edu.byui_cs.jjmn.ponderize.R.layout.activity_main;

// FOR FACEBOOK

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {
  
  public static final String SCRIPTURE_TITLE = "SCRIPTURE_TITLE";
  public static final String SCRIPTURE_TEXT = "SCRIPTURE_TEXT";
  private static List < ScriptureContainer > omniList;
  private static ScriptureList list = ScriptureList.getInstance ();
  private ArrayList < ScriptureContainer > memList = new ArrayList <> ();
  private ArrayList < ScriptureContainer > proList = new ArrayList <> ();
  private ListView proView;
  private ListView memView;
  
  @Override
  protected void onCreate (Bundle savedInstanceState) {
    
    Log.v (getClass ().getSimpleName (), "Create main activity.");
    super.onCreate (savedInstanceState);
    setContentView (activity_main);
    
    setupTabs ();
    /* ******************************************************************************************
     * Loads the pre-loaded scriptures into an array and loads them into the scripture view
     ********************************************************************************************/
    // init array
    Log.d (getClass ().getSimpleName (), "Setting up lists");
    
    FillTabsFromList fillTabsFromList = new FillTabsFromList (memList, proList).invoke ();
    proView = fillTabsFromList.getProView ();
    memView = fillTabsFromList.getMemView ();

  }
  
  @Override
  protected void onResume () {
    super.onResume ();
    
    omniList = list.getList ();
    
    proView.setAdapter (null);
    memView.setAdapter (null);
    
    FillTabsFromList fillTabsFromList = new FillTabsFromList (memList, proList).invoke ();
    proView = fillTabsFromList.getProView ();
    memView = fillTabsFromList.getMemView ();
    
  }
  
  private void setupTabs () {
    TabHost host = (TabHost) findViewById (R.id.tabHostMain);
    host.setup ();
    
    //Progressing Tab
    Log.v (getClass ().getSimpleName (), "Setup Progressing Tab");
    TabHost.TabSpec spec = host.newTabSpec ("Progressing");
    spec.setContent (R.id.Progressing);
    spec.setIndicator ("Progressing");
    host.addTab (spec);
    
    //Memorized Tab
    spec = host.newTabSpec ("Memorized");
    spec.setContent (R.id.Memorized);
    spec.setIndicator ("Memorized");
    host.addTab (spec);
  }
  
  /**
   * Activity changer to ScriptureViewActivity
   *
   * @param view Current view
   */
  //For navigation testing buttons
  public void launch_ScriptureViewActivity (View view) {
    Intent i = new Intent (this, ScriptureViewActivity.class);
    startActivity (i);
  }
  
  /**
   * Activity changer to SettingsActivity
   *
   * @param view Current view
   */
  public void launch_SettingsActivity (View view) {
    Intent i = new Intent (this, SettingsActivity.class);
    startActivity (i);
  }
  
  /**
   * Activity changer to AddScriptureActivity
   *
   * @param view Current view
   */
  public void launch_AddScriptureActivity (View view) {
    Intent i = new Intent (this, AddScriptureActivity.class);
    startActivity (i);
  }
  
  private class FillTabsFromList {
    private ArrayList < ScriptureContainer > memList;
    private ArrayList < ScriptureContainer > proList;
    private ListView memView;
    private ListView proView;
    
    public FillTabsFromList (ArrayList < ScriptureContainer > memList, ArrayList < ScriptureContainer > proList) {
      this.memList = memList;
      this.proList = proList;
    }
    
    public ListView getMemView () {
      return memView;
    }
    
    public ListView getProView () {
      return proView;
    }
    
    public FillTabsFromList invoke () {
      proList.clear ();
      memList.clear ();
      omniList = list.getList ();
  
      if (omniList.size() > 0) {
        Collections.sort(omniList, new Comparator<ScriptureContainer> () {
          @Override
          public int compare(final ScriptureContainer object1, final ScriptureContainer object2) {
            return object1.getReference ().compareTo(object2.getReference ());
          }
        });
      }
      
      // Look at scriptures, determine if completed or not
      // Adds to appropriate list view
      Log.v (getClass ().getSimpleName (), "Add scriptures to lists");
      for (ScriptureContainer sc : omniList) {
        Log.v (getClass ().getSimpleName (), "Attempt to separate lists");
        if (sc.getCompleted ()) {
          Log.v (getClass ().getSimpleName (), "Add to memorized list");
          memList.add (sc);
          Log.v (getClass ().getSimpleName (), "Added");
        } else {
          Log.v (getClass ().getSimpleName (), "Add to progressing list");
          proList.add (sc);
          Log.v (getClass ().getSimpleName (), "Added");
        }
      }
      
      Log.d (getClass ().getSimpleName (), "Set views");
      // grab list view reference
      memView = (ListView) findViewById (R.id.memorizedScripts);
      proView = (ListView) findViewById (R.id.progressingScripts);
      
      Log.d (getClass ().getSimpleName (), "Create Adapters");
      // create new scripture adapter
      ScriptureAdapter memAdapter = new ScriptureAdapter (MainActivity.this, memList);
      ScriptureAdapter proAdapter = new ScriptureAdapter (MainActivity.this, proList);
      
      Log.d (getClass ().getSimpleName (), "Update list views");
      // set list views adapter to new scripture adapter
      memView.setAdapter (memAdapter);
      proView.setAdapter (proAdapter);
      proView.setOnItemClickListener (
            new AdapterView.OnItemClickListener () {
              
              @Override
              public void onItemClick (AdapterView < ? > parent, View view, int position, long id) {
                
                // Make a new Intent
                Intent myIntent = new Intent (view.getContext (), ScriptureViewActivity.class);
                
                // Grab References
                TextView scriptureTitleView = (TextView) view.findViewById (R.id.list_item_scripture_title);
                TextView scriptureTextView = (TextView) view.findViewById (R.id.list_item_scripture_text);
                
                // Convert to string
                String scriptureTitle = scriptureTitleView.getText ().toString ();
                String scriptureText = scriptureTextView.getText ().toString ();
                
                // Put into intent
                myIntent.putExtra (SCRIPTURE_TITLE, scriptureTitle);
                myIntent.putExtra (SCRIPTURE_TEXT, scriptureText);
                
                // Open the new activity
                startActivityForResult (myIntent, 0);
              }
            });

        /* ************************************************************************************
         * LIST VIEW ON CLICK LISTENER
         * Joseph Koetting
         * Mar 4, 2017
         * When an item in the list view is clicked,
         * Opens a new Scripture View Activity
         ************************************************************************************/
      
      memView.setOnItemClickListener (
            new AdapterView.OnItemClickListener () {
              
              @Override
              public void onItemClick (AdapterView < ? > parent, View view, int position, long id) {
                
                // Make a new Intent
                Intent myIntent = new Intent (view.getContext (), ScriptureViewActivity.class);
                
                // Grab References
                TextView scriptureTitleView = (TextView) view.findViewById (R.id.list_item_scripture_title);
                TextView scriptureTextView = (TextView) view.findViewById (R.id.list_item_scripture_text);
                
                // Convert to string
                String scriptureTitle = scriptureTitleView.getText ().toString ();
                String scriptureText = scriptureTextView.getText ().toString ();
                
                // Put into intent
                myIntent.putExtra (SCRIPTURE_TITLE, scriptureTitle).putExtra (SCRIPTURE_TEXT, scriptureText);
                
                // Open the new activity
                startActivityForResult (myIntent, 0);
              }
            }
      );
      return this;
    }
  }
}

