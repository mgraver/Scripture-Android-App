package edu.byui_cs.jjmn.ponderize;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Created by Nick on 2/22/2017.
 */

public class AchievementContainerTest {
  
  private AchievementContainer achievement, failedAchievement;
  
  
  @Before
  public void setup () throws Exception {
    achievement = new AchievementContainer ();
    failedAchievement = new AchievementContainer ();
    
    achievement.setAchievementName ("test");
    failedAchievement.setAchievementName ("failed");
    
    achievement.setIsAchieved (true);
    failedAchievement.setIsAchieved (false);
  }
  
  @Test
  public void achievementNameIsCorrect () throws Exception {
    assertEquals ("test", achievement.getAchievementName ());
    assertNotEquals ("wrong", achievement.getAchievementName ());
  }
  
  @Test
  public void achievementBoolIsCorrect () throws Exception {
    Assert.assertEquals (true, achievement.isIsAchieved ());
    Assert.assertEquals (false, failedAchievement.isIsAchieved ());
    
    Assert.assertNotEquals (false, achievement.isIsAchieved ());
    Assert.assertNotEquals (true, failedAchievement.isIsAchieved ());
  }
  
}
