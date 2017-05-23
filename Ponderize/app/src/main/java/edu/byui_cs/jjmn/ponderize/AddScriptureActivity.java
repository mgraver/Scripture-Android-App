package edu.byui_cs.jjmn.ponderize;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddScriptureActivity extends AppCompatActivity {
  
  private static ScriptureList list = ScriptureList.getInstance ();
  private List < ScriptureContainer > _scriptures;
  
  @Override
  protected void onCreate (Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);
    setContentView (R.layout.activity_add_scripture);
    
    _scriptures = list.getList ();
  }
  
  @Override
  protected void onStop () {
    super.onStop ();
    list.saveList ();
  }
  
  public void addScriptureToList (View view) {
    Log.d (getClass ().getSimpleName (), "Grab scripture info");
    String scriptureReference = getStringFromView (R.id.editScriptureReference);
    String scriptureText = getStringFromView (R.id.editScriptureText);
    
    Log.d (getClass ().getSimpleName (), "add to scripture list");
    _scriptures.add (new ScriptureContainer (scriptureReference, scriptureText));
    
    Log.d (getClass ().getSimpleName (), "Update main list");
    list.updateList (_scriptures);
    Toast.makeText (getApplicationContext (), "Scripture Added", Toast.LENGTH_SHORT).show ();
  }
  
  @NonNull
  private String getStringFromView (int view) {
    return ((EditText) findViewById (view)).getText ().toString ();
  }
  
  
}
