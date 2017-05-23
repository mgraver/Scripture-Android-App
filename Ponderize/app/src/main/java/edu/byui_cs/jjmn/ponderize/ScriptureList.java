package edu.byui_cs.jjmn.ponderize;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by James on 29-Mar-17.
 */

class ScriptureList {
  
  private static final ScriptureList ourInstance = new ScriptureList ();
  private static List < ScriptureContainer > list;
  
  private ScriptureList () {
    
    Context context = getApplicationContext ();
    Log.d (getClass ().getSimpleName (), "try to get save file");
    String scriptureFilePath = context.getFilesDir () + "/scriptureFile.json";
    // The file path of the file in the internal directory with the pre-loaded scriptures
    Log.d (getClass ().getSimpleName (), "Point to file");
    
    File saveFile = new File (context.getFilesDir (), "/scriptureFile.json");
    
    Log.d (getClass ().getSimpleName (), "Did we find a file");
    if (!saveFile.exists ()) {
      new preLoader ().loadPreLoaded (context, scriptureFilePath);
    }
    
    Log.d (getClass ().getSimpleName (), "Store scriptures");
    ScriptureStorage loadScriptures = new ScriptureStorage ();
    list = loadScriptures.loadAllScriptures (saveFile);
  }
  
  /**
   * @return
   */
  static ScriptureList getInstance () {
    return ourInstance;
  }
  
  /**
   *
   */
  public void updateList (List < ScriptureContainer > scriptures) {
    Log.d (getClass ().getSimpleName (), "load scriptures again");
    list = scriptures;
  }
  
  /**
   *
   */
  public void saveList () {
    Log.d (getClass ().getSimpleName (), "save scriptures again");
    new ScriptureStorage ().saveAllScriptures (list,
          new File (getApplicationContext ().getFilesDir (), "/scriptureFile.json"));
  }
  
  /**
   * @return
   */
  public List < ScriptureContainer > getList () {
    return list;
  }
  
}
