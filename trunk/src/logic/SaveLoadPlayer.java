/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

/**
 * This class is specifically used for saving or loading the players information from or to 
 * json file. 
 * The attributes are determining if the player is active or not and also which cells are crossed, occupied 
 * or exploded. Also it store the order of reaching the flag by user. 
 * @author Alireza
 */
public class SaveLoadPlayer {
    private boolean active;
    private Position [] checked;
    private Position [] diceOn;
    private Position [] exploded;
    private int flagReachedAs;
   /**
    * if the player is active player it return true, otherwise false
    * @return boolean true for active, else false
    */
    public boolean isActive() {
        return active;
    }
    /**
     * This method is returning a array positions which are cells on the board that are 
     * crossed at the end of each turn/round.
     * @return array of Positions for crossed 
     */
    public Position[] getChecked() {
        return checked;
    }
     /**
     * This method is returning a array positions which are cells on the board that are 
     * occupied during playing
     * @return array of Positions for occupied 
     */
    public Position[] getDiceOn() {
        return diceOn;
    }
     /**
     * This method is returning a array positions which are cells on the board that are 
     * exploded at the end of each turn/round.
     * @return array of Positions for exploded 
     */
    public Position[] getExploded() {
        return exploded;
    }
    /** 
     * This method is returning the order that player reached a flag symbol as int value. 
     * then it can be used by player to get the correct point during loading
     * @return int order of flag reached by player
     */
    public int getFlagReachedAs() {
        return flagReachedAs;
    }
    /**
     * This method is setting if the player is active or not. 
     * @param active boolean true for active, otherwise false
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * Setting all crossed positions of the player's board as an array of positions. 
     * @param checked array of crossed positions
     */
    public void setChecked(Position[] checked) {
        this.checked = checked;
    }
    /**
     * Setting all occupied positions of the player's board as an array of positions. 
     * @param diceOn array of occupied positions
     */
    public void setDiceOn(Position[] diceOn) {
        this.diceOn = diceOn;
    }
    /**
     * Setting all exploded positions of the player's board as an array of positions. 
     * @param exploded array of exploded positions
     */
    public void setExploded(Position[] exploded) {
        this.exploded = exploded;
    }
    /**
     * setting the order that player reached the flag. After loading saved game the correct 
     * point to be stored to point table based on it. 
     * @param flagReachedAs int flag reached order
     */
    public void setFlagReachedAs(int flagReachedAs) {
        this.flagReachedAs = flagReachedAs;
    }
    
    
}
