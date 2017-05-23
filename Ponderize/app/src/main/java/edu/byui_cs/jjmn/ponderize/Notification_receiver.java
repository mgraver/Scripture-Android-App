package edu.byui_cs.jjmn.ponderize;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Joseph on 3/15/17.
 */

public class Notification_receiver extends BroadcastReceiver {
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void onReceive (Context context, Intent intent) {
    
    // no idea
    NotificationManager nManager = (NotificationManager) context.getSystemService ((Context
                                                                                          .NOTIFICATION_SERVICE));
    
    // When notification is clicked, open Main Activity
    Intent repeating_intent = new Intent (context, MainActivity.class);
    repeating_intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    
    // no idea
    PendingIntent pendingIntent = PendingIntent.getActivity (context,
          100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
    
    
    // builds the notification
    NotificationCompat.Builder builder = new NotificationCompat.Builder (context)
                                               .setContentIntent (pendingIntent)
                                               .setSmallIcon (android.R.drawable.arrow_up_float)
                                               .setContentTitle ("Ponderize")
                                               .setContentText ("Time to Ponder some Scriptures!")
                                               .setAutoCancel (true);
    
    // runs notification
    nManager.notify (100, builder.build ());
  }
}
