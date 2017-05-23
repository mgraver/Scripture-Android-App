package edu.byui_cs.jjmn.ponderize;

/**
 * Created by James Palmer on 22-Feb-17.
 */

public class ScriptureContainer {
  private String _reference;
  private String _text;
  private boolean _isCompleted;
  
  /**
   * Default constructor does nothing with initializing fields
   */
  public ScriptureContainer () {
  }
  
  /**
   * Nondefault constructor setting initial values for the reference
   *
   * @param ref Book of reference
   */
  public ScriptureContainer (String ref, String text) {
    this._reference = ref;
    this._text = text;
    
    this._isCompleted = false;
  }
  
  public String getText () {
    return _text;
  }
  
  public void setText (String text) {
    this._text = text;
  }
  
  public boolean getCompleted () {
    return _isCompleted;
  }
  
  public void setCompleted () {
    _isCompleted = true;
  }
  
  public String getReference () {
    return _reference;
  }
  
  public void setReference (String book) {
    this._reference = book;
  }
}
