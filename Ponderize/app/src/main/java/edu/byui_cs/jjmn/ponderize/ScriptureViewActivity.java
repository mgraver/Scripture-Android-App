package edu.byui_cs.jjmn.ponderize;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

public class ScriptureViewActivity extends AppActivity {

  // init scripture data
  String _scriptureTitle;
  String _scriptureText;

  public static final String SCRIPTURE_TEXT = "SCRIPTURE_TEXT";
  public static final String SCRIPTURE_TITLE = "SCRIPTURE_TITLE";

  // for the memorize tab
  private Stack<Integer> indexStack = new Stack<>();
  private String[] originalVerse;
  private String[] displayVerse;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.v(getClass().getSimpleName(), "Open ScriptureView activity.");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scripture_view);

    TabHost host = (TabHost) findViewById(R.id.tabHostScript);
    host.setup();

    // Receive intent from main activity
    Intent intent = getIntent();

    // Extract data from intent
    _scriptureTitle = intent.getStringExtra(MainActivity.SCRIPTURE_TITLE);
    _scriptureText = intent.getStringExtra(MainActivity.SCRIPTURE_TEXT);

    // Get reference for Text view
    TextView scriptureTextView = (TextView) this.findViewById(R.id.txtScriptureText);

    //Test Text
    scriptureTextView.setText(_scriptureText);

    //Scripture Tab
    TabHost.TabSpec spec = host.newTabSpec("Scripture");
    spec.setContent(R.id.Scripture);
    spec.setIndicator(_scriptureTitle);
    host.addTab(spec);

    //Notes Tab
    spec = host.newTabSpec("Notes");
    spec.setContent(R.id.Notes);
    spec.setIndicator("Notes");
    host.addTab(spec);

    //Memorize tab
    spec = host.newTabSpec("Memorize");
    spec.setContent(R.id.Memorize);
    spec.setIndicator("Memorize");
    host.addTab(spec);

    //load the notes for the scripture
    Context scriptureContext = this.getApplicationContext();
    //get the edit text reference
    EditText noteView = (EditText) findViewById(R.id.etxtNotes);
    //load the note
    NoteStorage loadNote = new NoteStorage();
    loadNote.loadNote(_scriptureTitle, scriptureContext, noteView);


    // MEMORIZE SCRIPTURE TAB CODE ****************************************

    originalVerse = _scriptureText.trim ().split ("\\s+");
    displayVerse = _scriptureText.trim ().split ("\\s+");

    TextView aView = (TextView) findViewById (R.id.ScriptureText);
    aView.setText (toString (displayVerse));

    SeekBar seekBar = (SeekBar) findViewById (R.id.seekBar);
    seekBar.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
      @Override
      public void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser) {

        int removeCompare = (int) ((progress / 100.0) * displayVerse.length);

        if (removeCompare > indexStack.size ()) {
          int removeCount = removeCompare - indexStack.size ();
          removeWords (removeCount);
        } else {
          if (removeCompare < indexStack.size ()) {
            int addCount = indexStack.size () - removeCompare;
            addWords (addCount);
          }
        }
        TextView aView = (TextView) findViewById (R.id.ScriptureText);
        String stringVerse = ScriptureViewActivity.this.toString (displayVerse);
        aView.setText (stringVerse);
      }

      @Override
      public void onStartTrackingTouch (SeekBar seekBar) {
        //Auto generated, don't need.
      }

      @Override
      public void onStopTrackingTouch (SeekBar seekBar) {
        //Auto generated, don't need.
      }
    });
  }


  @Override
  public boolean onCreateOptionsMenu (Menu menu) {
    return super.onCreateOptionsMenu (menu);
  }

  @Override
  public boolean onOptionsItemSelected (MenuItem item) {
    return super.onOptionsItemSelected (item);
  }

  @Override
  protected void onStop () {
    super.onStop ();
    Log.v (getClass ().getName (), "Paused");

    //get the context for the scripture view
    Context scriptureContext = this.getApplicationContext ();

    //Get the editText reference
    EditText saveText = (EditText) this.findViewById (R.id.etxtNotes);

    //save the note
    NoteStorage note = new NoteStorage ();
    note.saveNote (_scriptureTitle, scriptureContext, saveText);

  }

  /**************************************
   * removeWords
   * Removes word from the verse to
   * practice memorizing.
   *************************************/

  public void removeWords (int wordRemoval) {

    //Remove a certain amount of words.
    for (int i = 0; i < wordRemoval; i++) {
      Random random = new Random ();

      //Delete a random word.
      int wordIndex = random.nextInt (originalVerse.length);
      Iterator< Integer > it = indexStack.iterator ();

      //Make sure that the random int we remove it not already removed.
      while (it.hasNext ()) {
        if (it.next () == wordIndex) {
          wordIndex = random.nextInt (originalVerse.length);
          it = indexStack.iterator ();
        }
      }

      //Remove word.
      String word = displayVerse[wordIndex];
      char[] charArray = word.toCharArray ();

      for (int x = 1; x < charArray.length; x++) {
        charArray[x] = '_';
      }
      //Add modified word back.
      word = new String (charArray);
      displayVerse[wordIndex] = word;

      indexStack.push (wordIndex);
    }
  }

  /**********************************
   * addWords
   * add words back to the string to
   * check your self.
   *********************************/
  public void addWords (int addCount) {
    for (int i = 0; i < addCount; i++) {
      int wordIndex = indexStack.pop ();
      displayVerse[wordIndex] = originalVerse[wordIndex];
    }
  }

  /*******************************
   * getReference
   * changes an array to string.
   *******************************/
  public String toString (String[] words) {
    StringBuilder builder = new StringBuilder ();

    for (String word : words) {
      builder.append (word).append (" ");
    }

    return builder.toString ();
  }

  public void toPractice(View view)
  {
    Intent i = new Intent (this, MemorizeQuizActivity.class);
    i.putExtra(SCRIPTURE_TEXT, _scriptureText).putExtra(SCRIPTURE_TITLE, _scriptureTitle);
    startActivity (i);
  }

}
