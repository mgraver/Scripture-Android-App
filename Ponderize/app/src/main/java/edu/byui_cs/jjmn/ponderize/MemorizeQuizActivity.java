package edu.byui_cs.jjmn.ponderize;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.util.ArrayList;
import java.util.List;

public class MemorizeQuizActivity extends AppCompatActivity {

  private String[] originalVerse;
  private String displayString;
  private List<String> testWords;
  int guessIndex;
  String Verse;
  String Title;

  /**
   * {@inheritDoc}
   *
   * @param savedInstanceState
   */
  @Override
  protected void onCreate (Bundle savedInstanceState) {
    Log.v (getClass ().getSimpleName (), "Open MemorizeQuiz activity.");
    super.onCreate (savedInstanceState);
    setContentView (R.layout.activity_memorize_quiz);

    guessIndex = 0;
    Intent intent = getIntent();
    Verse = intent.getStringExtra(MainActivity.SCRIPTURE_TEXT);
    Title = intent.getStringExtra(MainActivity.SCRIPTURE_TITLE);

    originalVerse = Verse.trim().split("\\s+");
    testWords = removeWords(originalVerse);
    displayString = toString(testWords);

    TextView title = (TextView) findViewById(R.id.quizTitle);
    title.setText(Title);
    TextView quiz = (TextView) findViewById(R.id.quizContent);
    quiz.setText(displayString);



    //FACEBOOK SHARE BUTTON CODE     *******************************

    // Configures share window
    String masteredTitle = "I Memorized " + title;
    ShareLinkContent content = new ShareLinkContent.Builder()
            .setContentTitle("I Memorized " + Title + "!")
            .setContentUrl(Uri.parse("http://lds.org"))
            .setContentDescription(Verse)
            .build();

    //FACEBOOK THING CallbackManager - Like the facebook container to do everything.
    CallbackManager callbackManager = CallbackManager.Factory.create();

    // get reference to share button
    final ShareButton shareButton = (ShareButton) findViewById(R.id.fb_share_button);

    // share window is displayed
    shareButton.setShareContent(content);
  }

  private List<String> removeWords(String [] words) {
    List<String> testWords = new ArrayList<String>();
    char [] charWord;
    for (int i = 0; i < words.length; i++) {
        charWord = words[i].toCharArray();

      for (int j = 0; j < charWord.length; j++)
          charWord[j] = '_';
      testWords.add(new String(charWord));
    }
    return testWords;
  }

  public String toString(List<String> words) {
      StringBuilder stringBuilder = new StringBuilder();
      for (String word: words) {
          stringBuilder.append(word + " ");
      }
      return stringBuilder.toString();
  }

  public void updateQuiz(String answer) {
    answer = answer.toLowerCase();
    String compareWord = new String(originalVerse[guessIndex]);
    compareWord = compareWord.toLowerCase();

    if (answer.equals(compareWord)) {
      testWords.set(guessIndex, originalVerse[guessIndex]);
      guessIndex++;
      displayString = toString(testWords);
      TextView quiz = (TextView) findViewById(R.id.quizContent);
      quiz.setText(displayString);
    } else {
      Context context = getApplicationContext();
      CharSequence text = "Incorrect";
      int duration = Toast.LENGTH_SHORT;

      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
    }

    if (guessIndex == originalVerse.length) {
      Button btn = (Button) findViewById(R.id.checkBtn);
      btn.setVisibility(View.INVISIBLE);

      EditText editText = (EditText) findViewById(R.id.answerText);
      editText.setVisibility(View.INVISIBLE);

      ShareButton shareButton = (ShareButton) findViewById(R.id.fb_share_button);
      shareButton.setVisibility(View.VISIBLE);

      Toast.makeText(getApplicationContext(), "Memorized", Toast.LENGTH_SHORT).show();


      ScriptureList scriptureList = ScriptureList.getInstance();
      List<ScriptureContainer> list = scriptureList.getList();

      for (ScriptureContainer sc : list) {
        Log.v("Searching", sc.getReference() + " ==? " + Title);
        if (Title.equals(sc.getReference())) {
          Log.v("FOUND", sc.getReference() + " == " + Title);
          sc.setCompleted();
        }
      }
    }
  }

  public void getAnswer(View view) {
    EditText editText = (EditText) findViewById(R.id.answerText);
    String answer = editText.getText().toString();
    editText.setText("");
    updateQuiz(answer);
  }
}
