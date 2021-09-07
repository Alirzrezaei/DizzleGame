/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

/**
 * The position class is the x and y coordinate of a position the board of player. 
 * The can be used by other classed to access the cells or check if two positions are 
 * neighbor and also neighbors of this position will be provided by this class. 
 * In addition, the class is checking the equality of the two positions by comparing their 
 * x and y coordinates. in the end, it also print the coordinates of position. 
 * @author Rezaei
 */
public class Position {
    private int x;
    private int y;
    /**
     * The constructor to store a position based on given x and y coordinates. 
     * @param x
     * @param y 
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    /**
     * returning x coordinate of position
     * @return int x coordinate
     */
    public int getX() {
        return x;
    }
    /**
     * return y coordinate of position
     * @return int y coordinate
     */
    public int getY() {
        return y;
    }
    /**
     * setting x coordinate of position
     * @param x int x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * setting y coordinate of position 
     * @param y int y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * This method is checking if the given position is next to this position or
     * not. 
     * @param pos position to checked
     * @return true if they are next to each other, false otherwise
     */
    protected boolean isNeighbor(Position pos){
        
        if(this.x + 1 == pos.getX() && this.y == pos.getY()){
            return true;
        }else if(this.x - 1 == pos.getX() && this.y == pos.getY()){
            return true;
        }else if(this.x == pos.getX() && this.y + 1 == pos.getY()){
            return true;
        }else if(this.x == pos.getX() && this.y - 1 == pos.getY()){
            return true;
        }else{
            return false;
        }
    }
   /**
    * This method is calculating the four other positions which are next to this position and return them 
    * as an array of positions. 
    * @return array of positions
    */ 
    protected Position[] getNeighbors(){
        Position [] neighbors = new Position[4];
        
        neighbors[0] = new Position(this.x + 1, this.y);
        neighbors[1] = new Position(this.x - 1, this.y);
        neighbors[2] = new Position(this.x, this.y + 1);
        neighbors[3] = new Position(this.x, this.y - 1);
        
        return neighbors;
    }
    /**
     * This method is checking if the x and y of given position is equal to x and y of this
     * position and if they are the same, two positions are equal. 
     * @param pos position to be checked
     * @return true if two positions are equal and false otherwise. 
     */
    protected boolean equals(Position pos){
        return pos != null && this.getX() == pos.getX() && this.getY() == pos.getY();
    }
    /**
     * This method is returning this position as String value that can be used to print it
     * @return String value of position
     */
    @Override
    public String toString(){
        return ""+ this.getX()+", "+ this.getY();
    }
}
