/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Alireza
 */
public class FakeGUI implements GUIConnector{
    /**
     * this method is called from game during player creation to set all images onto the board of players
     * based on id of player and level number which are provided as parameters. 
     * @param levelObj object of loaded level
     * @param player int id of player
     * @param level int level number
     */
    @Override
    public void addImgsToAllGridPanes(LevelReader levelObj, int player, int level) {
    }
    /**
     * this method is receiving a list of integers as parameter and set the correct dices into 
     * the grid pane which is to show the dices
     * @param list integer
     */
    @Override
    public void setDicesOnGridPane(List<Integer> list) {
    }
     /**
     * this method is setting disable or enable of the roll again and dropout buttons 
     * based on given boolean value, true will disable and false will enable buttons
     * @param value boolean value
     */
    @Override
    public void setBtnDisable(boolean value) {
    }
     /**
     * this method is putting appropriate dice on a cell of given position on the board of given 
     * player id.
     * @param dice int dice
     * @param pos Position of dice
     * @param player int id of player
     */
    @Override
        public void setDiceToBoard(int dice, Position pos, int player){
            
        }
         /**
     * Removes the highlight of a domino position on the game grid. I have got
     * help from last assignment
     *
     * @param pos position of the top-left half of the domino.
     */
    @Override
    public void removeHighlightPos(Position pos){
    
}
     /**
     * this method is adding highlight to the cell of given position that human player 
     * knows where he can put the dice on. 
     * @param pos Position of the cell
     */
    @Override
    public void highlightPos(Position pos){

}
    /**
     * this method is setting related pen symbol on players field based on given player
     * id. The method is calling two methods to get the players' pane and players' pen image. 
     * @param player int id of player
     */
    @Override
    public void showPenOnBoard(int idPlayer) {
        
    }
    /**
     * This method is removing the related image view of the pen from the players' board
     * based on given player id. 
     * @param player int id of player
     */
    @Override
    public void removePenFromBoard(int player) {
       
    }
     /**
     * This method is clearing the board and other gui components of the game to start
     * new game. 
     */
    @Override
    public void clear() {
        
    }
    /**
     * This method is initializing the table of the point based on the level number
     * @param level int level number
     */
    @Override
    public void initPointTable(int level) {
    }
    /**
      * This method is updating the point received by player for a symbol in the given index
      * @param point int, received point
      * @param idx int index of point
      */
    @Override
    public void updateScores( int point, int idx) {
    }
    /**
     * This method is putting cross symbol on given position for given player id. 
     * @param pos Position of the cell on the player's board
     * @param player int player id
     */
    @Override
    public void setCrossedPos(Position pos, int player) {

    }
    /**
     * this method is removing the symbol of dice from board of the player from given position
     * @param pos position of cell
     * @param player int index of player
     */
    @Override
    public void removeDiceFromBoard(Position pos, int player) {
      
    }
    /**
     * this method is update the labels of turns of user. 
     * @param round int round   
     * @param turn int turn
     */
    @Override
    public void updateRoundTurn(int round, int turn) {

    }
    /**
     * set the symbol of exploded on bomb cell of the players
     * @param player int, index of player
     * @param pos Position of the bomb cell
     */
    @Override
    public void setExploded(int player, Position pos) {
        
    }
    /**
     * This method is receiving information from logic part and show the turns a text 
     * area to user. 
     * @param player int id player
     * @param dice int laid dice
     * @param pos Position laid dice
     * @param type ObjectTypes collected Symbol
     * @param dropOut boolean drop out (nonactive player)
     */
    @Override
    public void logger(int player, int dice, Position pos, ObjectTypes type, boolean dropOut, boolean roll){
        
    }
    /**
     * This method is to show error messages in a pop-up window based on the int code of
     * the message. 
     * @param msgType int code of error
     */
    @Override
    public void msgWindow(int msgType){
        
    }
    /**
     * This method is putting cross symbol on given position for given player id. 
     * @param pos Position of the cell on the player's board
     * @param player int player id
     */
    @Override
    public void setCrossedPosLoad(Position pos, int player) {
    }
    /**
     * This method is showing the winner or winners of the game based on the calculated 
     * points which are done in logic package
     * @param winners List winner with id and point
     */
    @Override
    public void winnerWindow(List<Winner> winners){
        
    }
    /**
     * This method is used to log the activities of the game in the log area of the game. 
     * 
     * 
     * @param dices List of Integer
     * @param idPlayer int Id of player
     */
    public void loggerRoll(List<Integer> dices, int idPlayer){
        
    }
    public void lblDiceBack(boolean roll){
        
    }

    
}
