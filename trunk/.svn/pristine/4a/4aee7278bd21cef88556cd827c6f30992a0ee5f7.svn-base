/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;
import com.google.gson.annotations.SerializedName;


/**
 * The class is based on the information of levels json file. When the json file is
 * loaded by IO class this class is called to using GSON library to check the validation of the 
 * file. Then if all information of json file was based on this class structure an instance of the 
 * levelReader is created to make the boards of players. 
 * An array of Integer is the values of the field in json file. Symbols (objects) are provided with 
 * their point and positions to be later embedded into the cells of board, which are different types of jewels, 
 * bombs, different types of puzzles, horizonal and vertical lines, keys and keyholes, flag, rocket and planets. 
 * @author Alireza
 */
public class LevelReader {
    private Integer field[][];
    private boardObjects jewels[];
    private boardObjects bombs;
    private boardObjects puzzles[];
    
    @SerializedName("horizontal-lines")
    private boardObjects[] horizontalLines;
    
    @SerializedName("vertical-lines")
    private boardObjects[] verticalLines;
    
    private KeyObjects[] keys;
    private Flag flag;
    
    private Position rocket;
    private Position planet;
    /**
     * returning the two dimensional array of integers which are values of each cell
     * to put dice on it. 
     * @return array of Integers
     */
    public Integer[][] getField() {
        return field;
    }
    /**
     * returning board objects of jewels
     * @return boardObject type jewel
     */
    public boardObjects[] getJewels() {
        return jewels;
    }
    /**
     * returning board object of bomb
     * @return boardObject of bomb
     */
    public boardObjects getBombs() {
        return bombs;
    }
    /**
     * returning board object of puzzle
     * @return boardObject of puzzle
     */
    public boardObjects[] getPuzzles() {
        return puzzles;
    }
    /**
     * returning board object of horizontal line
     * @return boardObject of horizontal line
     */
    public boardObjects[] getHorizontalLines() {
        return horizontalLines;
    }
    /**
     * returning board object of vertical line
     * @return boardObject of vertical line
     */
    public boardObjects[] getVerticalLines() {
        return verticalLines;
    }
    /**
     * returning board object of keys 
     * @return keyObjects of keys
     */
    public KeyObjects[] getKeys() {
        return keys;
    }
     /**
     * returning board object of flag
     * @return Flag of flag
     */
    public Flag getFlag() {
        return flag;
    }
     /**
     * returning position of rocket
     * @return Position of rocket
     */
    public Position getRocket() {
        return rocket;
    }
    /**
     * returning the position of planet
     * @return Position of planet
     */
    public Position getPlanet() {
        return planet;
    }
    /**
     * The helper class is for the objects of keys and their associated holes.
     * The class is storing the position of key and array of positions of holes which are 
     * in same color as key. 
     */
    public static class KeyObjects {
        private Position position;
        private Position[] holes;
        /**
         * returning the position of this key as called
         * @return Position of key
         */
        public Position getPosition() {
            return position;
        }
        /**
         * returning the array of positions of related holes to this key
         * @return 
         */
        public Position[] getHoles() {
            return holes;
        }
  
    }
    /**
     * The helper class is for the objects of flag and the array of points for it.
     * The class is storing the position of the flag along with the array of points
     * that can be collected by players in order of accesses. 
     */
    public static class Flag {

       private int [] points;
       private Position position;
       /**
        * returning the points of the flag
        * @return int array of points
        */
        public int[] getPoints() {
            return points;
        }
        /**
         * returning the position of the flag
         * @return Position of flag
         */
        public Position getPosition() {
            return position;
        }
   
       
    }
    
    
    
    
  
}
