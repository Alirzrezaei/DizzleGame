/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

/**
 * This class is being used by bot player to choose the best cell that player can put 
 * dice on it to get more points out of it. The BotChoose class is storing type of cell, 
 * point and position of it that based on them the comparison of different chooses can be done. 
 * @author Rezaei
 */
public class BotChoose {
    private ObjectTypes type;
    private int point;
    private Position pos;
    /**
     * this is the main constructor to make the choose 
     * @param pos Position of chosen cell
     * @param point int point of chosen cell
     * @param type ObjectType of chosen cell
     */
    public BotChoose(Position pos, int point, ObjectTypes type) {
        
        this.pos = pos;
        this.point = point;
        this.type = type;
    }
    /**
     * returning type of chosen cell
     * @return ObjectTypes
     */
    public ObjectTypes getType() {
        return type;
    }
    /**
     * returning the point of chosen cell
     * @return int point of cell
     */
    public int getPoint() {
        return point;
    }
    /**
     * returning position of the chosen cell
     * @return Position of chosen cell
     */
    public Position getPos() {
        return pos;
    }   
       
    
}
