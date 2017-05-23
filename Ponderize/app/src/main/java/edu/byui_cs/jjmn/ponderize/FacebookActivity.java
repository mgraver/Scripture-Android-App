package edu.byui_cs.jjmn.ponderize;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

public class FacebookActivity extends AppCompatActivity {
  
  
  /**
   * FACEBOOK THING CallbackManager - Like the facebook container to do everything.
   */
  CallbackManager callbackManager;
  
  @Override
  protected void onCreate (Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);
    setContentView (R.layout.activity_facebook);
    
    // Not sure what this code snippet does
    // DOES NOT WORK WITHOUT
    callbackManager = CallbackManager.Factory.create ();

        /* ************************************************************************************
         * FACEBOOK LOGIN BUTTON CODE
         * Joseph Koetting
         * Mar 8, 2017
         * Allows the user to log into to the application
         ************************************************************************************/
    
    LoginManager.getInstance ().registerCallback (callbackManager,
          new FacebookCallback < LoginResult > () {
            
            /**
             * {@inheritDoc}
             * @param loginResult
             */
            // Successfully logged into facebook
            @Override
            public void onSuccess (LoginResult loginResult) {
              Log.d ("MAIN ACTIVITY FACE", "LOGIN SUCCESSFUL");
            }
            
            /**
             * {@inheritDoc}
             */
            // Cancelled logging into facebook
            @Override
            public void onCancel () {
              Log.d ("MAIN ACTIVITY FACE", "LOGIN CANCELLED");
            }
            
            /**
             * {@inheritDoc}
             * @param exception
             */
            // Error logging in
            @Override
            public void onError (FacebookException exception) {
              Log.e ("MAIN ACTIVITY FACE", "LOGIN ERROR", exception);
            }
          });
  }
}
