package edu.byui_cs.jjmn.ponderize;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {
  
  
  /**
   * A preference value change listener that updates the preference's summary
   * to reflect its new value.
   */
  private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener () {
    @Override
    public boolean onPreferenceChange (Preference preference, Object value) {
      String stringValue = value.toString ();
      
      if (preference instanceof ListPreference) {
        
        // For list preferences, look up the correct display value in
        // the preference's 'entries' list.
        ListPreference listPreference = (ListPreference) preference;
        int index = listPreference.findIndexOfValue (stringValue);
        
        // Set the summary to reflect the new value.
        preference.setSummary (
              index >= 0
                    ? listPreference.getEntries ()[index]
                    : null);
        
      } else if (preference instanceof RingtonePreference) {
        // For ringtone preferences, look up the correct display value
        // using RingtoneManager.
        if (TextUtils.isEmpty (stringValue)) {
          // Empty values correspond to 'silent' (no ringtone).
          preference.setSummary (R.string.pref_ringtone_silent);
          
        } else {
          Ringtone ringtone = RingtoneManager.getRingtone (
                preference.getContext (), Uri.parse (stringValue));
          
          if (ringtone == null) {
            // Clear the summary if there was a lookup error.
            preference.setSummary (null);
          } else {
            // Set the summary to reflect the new ringtone display
            // name.
            String name = ringtone.getTitle (preference.getContext ());
            preference.setSummary (name);
          }
        }
        
      } else {
        // For all other preferences, set the summary to the value's
        // simple string representation.
        preference.setSummary (stringValue);
      }
      return true;
    }
  };
  
  /**
   * Helper method to determine if the device has an extra-large screen. For
   * example, 10" tablets are extra-large.
   */
  private static boolean isXLargeTablet (Context context) {
    return (context.getResources ().getConfiguration ().screenLayout
                  & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
  }
  
  /**
   * Binds a preference's summary to its value. More specifically, when the
   * preference's value is changed, its summary (line of text below the
   * preference title) is updated to reflect the value. The summary is also
   * immediately updated upon calling this method. The exact display format is
   * dependent on the type of preference.
   *
   * @see #sBindPreferenceSummaryToValueListener
   */
  private static void bindPreferenceSummaryToValue (Preference preference) {
    // Set the listener to watch for value changes.
    preference.setOnPreferenceChangeListener (sBindPreferenceSummaryToValueListener);
    
    // Trigger the listener immediately with the preference's
    // current value.
    sBindPreferenceSummaryToValueListener.onPreferenceChange (preference,
          PreferenceManager
                .getDefaultSharedPreferences (preference.getContext ())
                .getString (preference.getKey (), ""));
  }
  
  @Override
  public void onBackPressed () {
    Notifications ();
    Intent setIntent = new Intent (this, MainActivity.class);
    startActivity (setIntent);
  }
  
  @Override
  public boolean onOptionsItemSelected (MenuItem menuItem) {
    switch (menuItem.getItemId ()) {
      case android.R.id.home: {
      }
    }
    return (super.onOptionsItemSelected (menuItem));
  }
  
  @Override
  protected void onCreate (Bundle savedInstanceState) {
    Log.v (getClass ().getSimpleName (), "Open Settings activity.");
    super.onCreate (savedInstanceState);
    setupActionBar ();
  }
  
  /**
   * Set up the {@link android.app.ActionBar}, if the API is available.
   */
  private void setupActionBar () {
    ActionBar actionBar = getSupportActionBar ();
    if (actionBar != null) {
      // Show the Up button in the action bar.
      actionBar.setDisplayHomeAsUpEnabled (true);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onIsMultiPane () {
    return isXLargeTablet (this);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  @TargetApi ( Build.VERSION_CODES.HONEYCOMB )
  public void onBuildHeaders (List < Header > target) {
    loadHeadersFromResource (R.xml.pref_headers, target);
  }
  
  /**
   * This method stops fragment injection in malicious applications.
   * Make sure to deny any unknown fragments here.
   */
  protected boolean isValidFragment (String fragmentName) {
    return PreferenceFragment.class.getName ().equals (fragmentName)
                 || GeneralPreferenceFragment.class.getName ().equals (fragmentName)
                 || DataSyncPreferenceFragment.class.getName ().equals (fragmentName)
                 || NotificationPreferenceFragment.class.getName ().equals (fragmentName);
  }
  
  public void Notifications () {
    
    // get shared preferences
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (this);
    
    // get booleans for each day of the week
    Boolean sun = preferences.getBoolean ("check_box_preference_1", false);
    Boolean mon = preferences.getBoolean ("check_box_preference_2", false);
    Boolean tue = preferences.getBoolean ("check_box_preference_3", false);
    Boolean wed = preferences.getBoolean ("check_box_preference_4", false);
    Boolean thu = preferences.getBoolean ("check_box_preference_5", false);
    Boolean fri = preferences.getBoolean ("check_box_preference_6", false);
    Boolean sat = preferences.getBoolean ("check_box_preference_7", false);
    
    // get time and ampm
    String ampm = preferences.getString ("listPref", "");
    String time = preferences.getString ("time", "");
    
    // Testing to make sure preferences load correctly.
    Log.e ("CALENDER CHECKING SUN", sun.toString ());
    Log.e ("CALENDER CHECKING MON", mon.toString ());
    Log.e ("CALENDER CHECKING TUE", tue.toString ());
    Log.e ("CALENDER CHECKING WED", wed.toString ());
    Log.e ("CALENDER CHECKING THU", thu.toString ());
    Log.e ("CALENDER CHECKING FRI", fri.toString ());
    Log.e ("CALENDER CHECKING SAT", sat.toString ());
    Log.e ("CALENDER CHECKING TIME", time);
    Log.e ("CALENDER CHECKING AMPM", ampm);
    
    // init calendar object
    Calendar calendar = Calendar.getInstance ();
    
    // makes it happen on different days
    if (sun) {
      calendar.set (Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    }
    if (mon) {
      calendar.set (Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    }
    if (tue) {
      calendar.set (Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
    }
    if (wed) {
      calendar.set (Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
    }
    if (thu) {
      calendar.set (Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
    }
    if (fri) {
      calendar.set (Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
    }
    if (sat) {
      calendar.set (Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
    }
    
    int addTwelveHours = Integer.parseInt (ampm) * 12;
    
    if (time.charAt (1) == ':') {
      // Set Hour
      calendar.set (Calendar.HOUR_OF_DAY, time.charAt (0) + addTwelveHours);
      
      // Set Minute
      Integer minute = (time.charAt (2) - '0') * 10 + time.charAt (3) - '0';
      calendar.set (Calendar.MINUTE, minute);
    } else {
      // Set Hour
      Integer hour = (time.charAt (0) - '0') * 10 + time.charAt (0) - '0';
      calendar.set (Calendar.HOUR_OF_DAY, hour + addTwelveHours);
      
      // Set Minute
      Integer minute = (time.charAt (3) - '0') * 10 + time.charAt (4) - '0';
      calendar.set (Calendar.MINUTE, minute);
    }
    
    // No idea what this does. Requires to work
    Intent intent = new Intent (getApplicationContext (), Notification_receiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast (getApplicationContext (), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    
    // Sets the alarm
    AlarmManager alarmManager = (AlarmManager) getSystemService (ALARM_SERVICE);
    alarmManager.setRepeating (AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis (), AlarmManager.INTERVAL_DAY, pendingIntent);
  }
  
  /**
   * This fragment shows general preferences only. It is used when the
   * activity is showing a two-pane settings UI.
   */
  @TargetApi ( Build.VERSION_CODES.HONEYCOMB )
  public static class GeneralPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate (Bundle savedInstanceState) {
      super.onCreate (savedInstanceState);
      addPreferencesFromResource (R.xml.pref_general);
      setHasOptionsMenu (true);
      
      // Bind the summaries of EditText/List/Dialog/Ringtone preferences
      // to their values. When their values change, their summaries are
      // updated to reflect the new value, per the Android Design
      // guidelines.
      bindPreferenceSummaryToValue (findPreference ("example_text"));
      bindPreferenceSummaryToValue (findPreference ("example_list"));
    }
    
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
      int id = item.getItemId ();
      if (id == android.R.id.home) {
        startActivity (new Intent (getActivity (), SettingsActivity.class));
        return true;
      }
      return super.onOptionsItemSelected (item);
    }
  }
  
  /**
   * This fragment shows notification preferences only. It is used when the
   * activity is showing a two-pane settings UI.
   */
  @TargetApi ( Build.VERSION_CODES.HONEYCOMB )
  public static class NotificationPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate (Bundle savedInstanceState) {
      super.onCreate (savedInstanceState);
      addPreferencesFromResource (R.xml.pref_notification);
      setHasOptionsMenu (true);
      
      // Bind the summaries of EditText/List/Dialog/Ringtone preferences
      // to their values. When their values change, their summaries are
      // updated to reflect the new value, per the Android Design
      // guidelines.
      bindPreferenceSummaryToValue (findPreference ("notifications_new_message_ringtone"));
    }
    
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
      int id = item.getItemId ();
      if (id == android.R.id.home) {
        startActivity (new Intent (getActivity (), SettingsActivity.class));
        return true;
      }
      return super.onOptionsItemSelected (item);
    }
  }
  
  /**
   * This fragment shows data and sync preferences only. It is used when the
   * activity is showing a two-pane settings UI.
   */
  @TargetApi ( Build.VERSION_CODES.HONEYCOMB )
  public static class DataSyncPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate (Bundle savedInstanceState) {
      super.onCreate (savedInstanceState);
      addPreferencesFromResource (R.xml.pref_data_sync);
      setHasOptionsMenu (true);
      
      // Bind the summaries of EditText/List/Dialog/Ringtone preferences
      // to their values. When their values change, their summaries are
      // updated to reflect the new value, per the Android Design
      // guidelines.
      bindPreferenceSummaryToValue (findPreference ("sync_frequency"));
    }
    
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
      int id = item.getItemId ();
      if (id == android.R.id.home) {
        startActivity (new Intent (getActivity (), SettingsActivity.class));
        return true;
      }
      return super.onOptionsItemSelected (item);
    }
  }
}
