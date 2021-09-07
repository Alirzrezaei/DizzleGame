/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

/**
 * The Cell class is the smallest part of the main board of the game. It has attributes that can be 
 * used during playing the game. This attributes (information) are loaded from the each level json file, 
 * that is loaded by LevelReader class and converted in the board class. 
 * Crossed, occupied and exploded are determining the status of the cell. The value is used to put same
 * dice on it and object determines which symbols is inside the cell. Also Each object has point that player
 * can collect during the play. 
 * @author Rezaei
 */
public class Cell {
    
    private boolean crossed; 
    private boolean occupied;
    private boolean exploded;
    private Integer value;
    private ObjectTypes object;
    private int point;
    /**
     * the main constructor to make a cell object for the board with given parameters
     * @param value the value of the cell 1 to 6 and 0 if it is crossed
     * @param crossed cell is crossed at the beginning of game if its value is 0
     * @param object type of object which is stored on the game
     * @param point point of the object
     */
     Cell(Integer value, boolean crossed, ObjectTypes object, int point){
        this.value = value;
        this.crossed = crossed;
        this.object = object;
        this.point = point;
    }
    /**
     * setting the point of the cell
     * @param point int point of cell
     */
    public void setPoint(int point) {
        this.point = point;
    }
    /**
     * returning the point of the cell
     * @return int point of cell
     */
    public int getPoint() {
        return point;
    }
     /**
      * returning the symbol or object of the cell
      * @return ObjectTypes of cell
      */
    public ObjectTypes getObject() {
        return object;
    }
    /**
     * setting the symbol inside the cell
     * @param object ObjectTypes of cell
     */
    public void setObject(ObjectTypes object) {
        this.object = object;
    }
    /**
     * setting crossed on the cell
     * @param crossed true if crossed, false otherwise
     */
    public void setCrossed(boolean crossed) {
        this.crossed = crossed;
    }
    /**
     * setting dice on the cell, 
     * @param occupied true if dice on, false otherwise
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    /**
     * setting exploded as true if other player/players ignited the bomb, false otherwise. 
     * @param exploded true exploded, false otherwise
     */
    public void setExploded(boolean exploded){
        this.exploded = exploded;
    }
    /**
     * setting value of the cell 
     * @param value int value of cell
     */
    public void setValue(Integer value) {
        this.value = value;
    }
    /**
     * returning true if the cell is crossed and false otherwise.
     * @return boolean true if crossed, false otherwise
     */
    public boolean isCrossed() {
        return crossed;
    }
    /**
     * returning true if the cell is occupied by a dice and if not, returning false. 
     * @return boolean true if dice on, false otherwise
     */
    public boolean isOccupied() {
        return occupied;
    }
    /**
     * returning true if the cell is exploded before and false otherwise
     * @return boolean true if exploded and false otherwise
     */
    public boolean isExploded(){
        return exploded;
    }
    /**
     * returning the value of the cell as Integer value
     * @return Integer value of cell
     */
    public Integer getValue() {
        return value;
    }
    
}
