package edu.byui_cs.jjmn.ponderize;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by Nick on 3/20/2017.
 * Takes care of the saving and loading of notes
 */

public class NoteStorage {
  
  /**
   * noteSaver function
   */
  
  public void saveNote (String ref, Context context, EditText text) {
    
    //Create the file name from the scripture reference
    String scriptRef = ref.replaceAll ("\\s", "") + "Note.txt";
    
    //Create the file if it does not exist
    File note = new File (context.getFilesDir (), scriptRef);
    
    //Logging
    Log.v (getClass ().getName (), scriptRef);
    
    //grab the string from the text box
    String noteString = text.getText ().toString ();
    Log.v (getClass ().getName (), noteString);
    
    try {
      //Create an output stream for the note file.
      FileOutputStream fout = context.openFileOutput (scriptRef, Context.MODE_PRIVATE);
      
      //Write to the file
      fout.write (noteString.getBytes ());
      
      //End file writing.
      fout.close ();
    } catch ( Exception e ) {
      e.printStackTrace ();
    }
  }
  
  /**
   * noteLoader function
   */
  public void loadNote (String ref, Context context, EditText noteView) {
    
    String scriptRef = ref.replaceAll ("\\s", "") + "Note.txt";
    String placeHolder = "no notes on file";
    String noteString = "";
    //Read the text from the file
    try {
      FileInputStream fin = context.openFileInput (scriptRef);
      InputStreamReader isr = new InputStreamReader (fin);
      BufferedReader buff = new BufferedReader (isr);
      StringBuilder sb = new StringBuilder ();
      
      while ((placeHolder = buff.readLine ()) != null) {
        
        sb.append (placeHolder);
        noteString += placeHolder;
      }
    } catch ( Exception e ) {
      e.printStackTrace ();
    }
    
    noteView.setText (noteString);
  }
}
