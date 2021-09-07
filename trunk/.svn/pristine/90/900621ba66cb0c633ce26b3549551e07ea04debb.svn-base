/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

/**
 * This enum class is storing object types along with their priorities. 
 * The object (symbol) types are used to store on the board and then collect their point based on the
 * type of object in the cell. The priority is being used by bot player to choose best cell to put dice on. 
 * @author Alireza
 */
public enum ObjectTypes {
    /**
     * objects that can be stored in cells of board of player. 
     */
    bomb(4), flag(1), jewel(2),  key(5), 
    keyhole(6),  planet(9), puzzle(0), rocket(3), 
    noType(8), horizontalLine(7), verticalLine(7);
    
    private int priority;
    /**
     * setting priority of the symbols (objects) to be used by bot player
     * @param priority int priority of symbol
     */
     ObjectTypes(int priority) {
        this.priority = priority;
    }
    /**
     * returning the priority of each called symbol (object)
     * @return int priority of symbol
     */
     protected int getPriority(){
         return this.priority;
     }
}
