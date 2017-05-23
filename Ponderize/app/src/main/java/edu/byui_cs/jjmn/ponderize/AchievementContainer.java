package edu.byui_cs.jjmn.ponderize;

/**
 * Created by Nick on 2/22/2017.
 */

public class AchievementContainer {
  
  //Variables
  private String _achievementName;
  private boolean _isAchieved;
    /*
    TODO add some sort of act to complete,
    probably an int or another boolean
    of representing the act
    */
  
  
  public AchievementContainer () {
    _isAchieved = false;
  }
  
  public String getAchievementName () {
    return _achievementName;
  }
  
  public void setAchievementName (String achievementName) {
    _achievementName = achievementName;
  }
  
  public boolean isIsAchieved () {
    return _isAchieved;
  }
  
  public void setIsAchieved (boolean isAchieved) {
    this._isAchieved = isAchieved;
  }
}
