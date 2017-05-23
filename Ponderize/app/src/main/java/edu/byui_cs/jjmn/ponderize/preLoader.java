package edu.byui_cs.jjmn.ponderize;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Creates a file in internal memory that holds all of the pre-loaded scriptures
 * Created by Nick on 3/24/2017.
 */

public class preLoader {
  
  public void loadPreLoaded (Context context, String scriptureFile) {
    StringBuilder jsonString = new StringBuilder ();
    InputStream inputStream;
    InputStreamReader inputStreamReader;
    BufferedReader bufferedReader;
    String line;
    try {
      inputStream = context.getResources ().getAssets ()
                          .open ("preloadScriptures", Context.MODE_WORLD_READABLE);
      inputStreamReader = new InputStreamReader (inputStream);
      bufferedReader = new BufferedReader (inputStreamReader);
      
      while ((line = bufferedReader.readLine ()) != null) {
        jsonString.append (line);
      }
    } catch ( Exception e ) {
      e.getMessage ();
    }
    
    // The file path of the file in the internal directory with the pre-loaded scriptures
    File saveFile = new File (scriptureFile);
    
    //Save list to saveFile.
    FileWriter writer;
    try {
      writer = new FileWriter (saveFile);
      writer.write (jsonString.toString ());
      writer.close ();
    } catch ( Exception e ) {
      e.printStackTrace ();
    }
  }
}
