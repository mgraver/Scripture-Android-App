package edu.byui_cs.jjmn.ponderize;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by James Palmer on 17-Mar-17.
 */

public class ScriptureStorageTest {
  
  private final String BOOK = "Ether";
  private final String TEXT = "Wherefore, whoso believeth in God might with surety hope for a " +
                                    "better world, yea, even a place at the right hand of God, " +
                                    "which hope cometh of faith, maketh an anchor to the souls of" +
                                    " men, which would make them sure and steadfast, always " +
                                    "abounding in good works, being led to glorify God.";
  private ScriptureContainer scripture;
  private List < ScriptureContainer > scriptureList;
  
  @Mock
  private Context context;
  
  @Before
  public void setUp () throws Exception {
    scripture = new ScriptureContainer (BOOK, TEXT);
    scripture.setText (TEXT);
    
    context = mock (MainActivity.class);
    
    scriptureList = new ArrayList <> (10);
    
    for (int i = 0; i < 10; ++i) {
      scriptureList.add (scripture);
    }
  }
  
  @Test
  public void scriptureStorage_Should_SaveAndLoadOneScripture () throws Exception {
    
    ScriptureStorage scriptureSave = new ScriptureStorage ();
    
    File file = new File (context.getFilesDir (), "Ether124.txt");
    scriptureSave.saveScripture (scripture, file);
    
    ScriptureContainer loadedScripture = scriptureSave.loadScripture (file);
    
    assertEquals (scripture.getReference (), loadedScripture.getReference ());
    
    scripture.setReference ("James");
    assertNotEquals (scripture.getReference (), loadedScripture.getReference ());
  }
  
  @Test
  public void scriptureStorage_Should_SaveAndLoadManyScriptures () throws Exception {
    ScriptureStorage scriptureSave = new ScriptureStorage ();
    
    File file = new File (context.getFilesDir (), "allScriptures.txt");
    File noneFile = new File (context.getFilesDir (), "none.txt");
    scriptureSave.saveAllScriptures (scriptureList, file);
    
    List < ScriptureContainer > loadedScriptures = scriptureSave.loadAllScriptures (file);
    
    for (ScriptureContainer scriptureRef : loadedScriptures) {
      assertEquals (scripture.getReference (), scriptureRef.getReference ());
    }
    
    scripture.setReference ("James");
    for (ScriptureContainer scriptureRef : loadedScriptures) {
      assertNotEquals (scripture.getReference (), scriptureRef.getReference ());
    }
    
    List < ScriptureContainer > loadedScriptures1 = scriptureSave.loadAllScriptures (noneFile);
    assertEquals (0, loadedScriptures1.size ());
  }
}


