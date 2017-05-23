package edu.byui_cs.jjmn.ponderize;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by James Palmer on 22-Feb-17.
 */

public class ScriptureContainerTest {
  private ScriptureContainer sc0, sc1;
  
  private final String BOOK = "Ether 12:4";
  private final String TEXT = "4 Wherefore, whoso believeth in God might with surety hope for a " +
                                    "better world, yea, even a place at the right hand of God, " +
                                    "which hope cometh of faith, maketh an anchor to the souls of" +
                                    " men, which would make them sure and steadfast, always " +
                                    "abounding in good works, being led to glorify God.";
  
  @Before
  public void setUp () throws Exception {
    sc0 = new ScriptureContainer (BOOK, TEXT);
    sc1 = new ScriptureContainer ();
    
    sc1.setReference (BOOK);
    sc1.setText (TEXT);
    sc1.setCompleted ();
  }
  
  @Test
  public void scriptureContainer_Should_DisplayScriptureReference () throws Exception {
    assertEquals ("Ether 12:4", sc0.getReference ());
    assertNotEquals ("Ether 12-4", sc0.getReference ());
  }
  
  @Test
  public void scriptureContain_Should_DisplayScriptureText () throws Exception {
    assertEquals ("4 Wherefore, whoso believeth in God might with surety hope for a better world," +
                        " " +
                        "yea, even a place at the right hand of God, which hope cometh of faith," +
                        " maketh an anchor to the souls of men, which would make them sure and " +
                        "steadfast, always abounding in good works, being led to glorify God.",
          sc0.getText ());
    assertEquals ("4 Wherefore, whoso believeth in God might with surety hope for a better world," +
                        " " +
                        "yea, even a place at the right hand of God, which hope cometh of faith," +
                        " maketh an anchor to the souls of men, which would make them sure and " +
                        "steadfast, always abounding in good works, being led to glorify God.",
          sc1.getText ());
    
    assertNotEquals ("", sc0);
    assertNotEquals ("", sc1);
  }
  
  @Test
  public void scriptureContainer_Should_Return_Complete () throws Exception {
    assertEquals (false, sc0.getCompleted ());
    assertEquals (true, sc1.getCompleted ());
    assertNotEquals (true, sc0.getCompleted ());
    assertNotEquals (false, sc1.getCompleted ());
  }
}
