/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

import java.util.List;

/**
 * This class is to make players to play the game. There are two child class for the player class, 
 * human and bot players. The human player is waiting for human action to pursue the game and bot player
 * contains a method called ai to automatically choose best cell to put dice on it based some priorities. 
 * This class has attributes like isBot which determines if this player is a bot player. Also, it has 
 * isDropOut attribute which indicates that this player is drop out to player in this turn or not. 
 * Board is the main attribute of the player that has all information of the player's playing board, with crossed,
 * occupied, exploded, values of cell, points and symbols. 
 * @author Rezaei
 */
public class Player {
    private boolean isBot;
    private boolean isDropedOut;
    private Board board;
    
    private int flagReachedAs;
    
    /**
     * Main constructor to make the player to play the game. Values are passed from child 
     * classes, human and bot. 
     * @param board Board of player
     * @param isBot true if this player is bot, false if human
     */
    Player(Board board, boolean isBot){
         isDropedOut = false;
         this.isBot = isBot;
         this.board = board;
    }
    /**
     * This method is setting the boolean value of drop out. If user dropped out, this 
     * method is called. 
     * @param value boolean true if user dropout, false otherwise
     */
    protected void setIsDropOut(boolean value){
        isDropedOut = value;
    }
    /**
     * This method is returning the boolean value of isBot, true if bot, false if human.
     * @return boolean true if bot, false if human
     */
    protected boolean isBot(){
        return isBot;
    }
    /**
     * This method is returning the value of isDropOut, true if the user is dropped out and false 
     * if the user is active. 
     * @return boolean true for dropped out, false for active user
     */
    protected boolean isDropOut(){
        return isDropedOut;
    }
    /**
     * This method is returning board of the user 
     * @return Board board of player
     */
    protected Board getBoard(){
        return this.board;
    }
    /**
     * This method is being implemented by bot as it should find best cell to put 
     * dice on based on information of its board and available dices to play. 
     * @param level int level number
     * @param dices list of dices
     * @return BotChoose object for suitable cell
     */
    protected BotChoose ai(List<Integer> dices) {
        return null;
    }
    /**
     * setting the order of flag reached by player. 
     * @param flag int flag reached as
     */
    protected void setFlagReachedAs(int flag){
        flagReachedAs = flag;
    }
    /**
     * returning the players order as reached to the flag. 
     * @return int order of flag reached
     */
    protected int getFlagReachedAs(){
        return flagReachedAs;
    }
}
