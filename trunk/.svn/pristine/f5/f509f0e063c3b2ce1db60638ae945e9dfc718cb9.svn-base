/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

/**
 * This class is a blue print for save and load process. 
 * During saving the game, level number, current round, current turn array of dices and the 
 * information of players are saved into an object and then this object is used to 
 * create a json file. 
 * 
 * This class is also used to load the game and check the validity of the json file based on the 
 * attributes it has. The object then can be used to load a saved game. 
 * @author Alireza
 */
public class SaveLoadObject {
    
    private int levelNo;
    private int round;
    private int turnOf;
    private int [] dice;
    private SaveLoadPlayer [] players;
    /**
     * returning the level number which is stored in json file (saved game).
     * @return int level number
     */
    public int getLevelNo() {
        return levelNo;
    }
    /**
     * returning the current round which is stored in json file (saved game).
     * @return int current round
     */
    public int getRound() {
        return round;
    }
    /**
     * returning the current turn which is stored in json file (saved game).
     * @return int current turn
     */
    public int getTurnOf() {
        return turnOf;
    }
    /**
     * returning the array of dices which is stored in json file (saved game).
     * @return int array of dices
     */
    public int[] getDice() {
        return dice;
    }
    /**
     * This method is setting level number into object to save a game
     * @param levelNo int level number
     */
    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }
    /**
     * This method is setting current round into object to save a game
     * @param round int round
     */
    public void setRound(int round) {
        this.round = round;
    }
    /**
     * This method is setting current turn into object to save a game
     * @param turnOf int current turn
     */
    public void setTurnOf(int turnOf) {
        this.turnOf = turnOf;
    }
    /**
     * This method is setting dices into object to save a game
     * @param dice int array of dices
     */
    public void setDice(int[] dice) {
        this.dice = dice;
    }
    /**
     * This method is setting player's information into object to save a game
     * @param players SaveLoadPlayer player's information
     */
    public void setPlayers(SaveLoadPlayer[] players) {
        this.players = players;
    }
    /**
     * return array of players with related information with are stored in json file (saved game)
     * @return array of SaveLoadPlayer 
     */
    public SaveLoadPlayer[] getPlayers() {
        return players;
    }
    
    
}
