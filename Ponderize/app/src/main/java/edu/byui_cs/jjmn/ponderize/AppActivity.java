package edu.byui_cs.jjmn.ponderize;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by James Palmer on 19-Feb-17.
 */

public abstract class AppActivity extends AppCompatActivity {
  
  @Override
  protected void onCreate (@Nullable Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);
    
    
    // Initialize settings
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences (this);
    // TODO: 19-Feb-17 get preferences for app
    
  }
  
  @Override
  public boolean onCreateOptionsMenu (Menu menu) {
    //getMenuInflater().inflate();// TODO: 19-Feb-17 add menu to R
    return super.onCreateOptionsMenu (menu);
  }
  
  @Override
  public boolean onOptionsItemSelected (MenuItem item) {
    switch (item.getItemId ()) {
     /* case : //settings
      case : //
      case :
      case :*/// TODO: 19-Feb-17  fill out menu options
    }
    return super.onOptionsItemSelected (item);
  }
}
