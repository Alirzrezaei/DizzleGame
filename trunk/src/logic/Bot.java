/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

import java.util.List;

/**
 * This method is method is a child of player class to create a bot type player. 
 * 
 * The bot player unlike human player is playing automatically when it is its turn. 
 * To choose the correct cell to put dice on it, this class is considering a set of priorities for
 * cells and symbols which are hidden inside cells. Below is the list priorities based on which the 
 * cell is selected. 
 * 
 * Puzzle piece
 * flag
 * Jewel, the most valuable first
 * Rocket
 * Bomb
 * Key
 * Keyhole
 * Line -> Completing a line with a die and most significant first
 * any cell
 * 
 * @author Rezaei
 */
public class Bot extends Player {
    /**
     * the constructor to create bot player with given board as board player which is 
     * created based on the json file of levels. The isBot value is passed true as this this 
     * is bot player. 
     * @param board board created from json file of levels
     * @param isBot true as this is bot player
     */
    public Bot(Board board, boolean isBot) {
        super(board, isBot);
    }
    /**
     * 
     * @param dices list of integer, available dices 
     * @return Botchoose object, to be played on the board,
     */
    protected BotChoose ai(List<Integer> dices ) {
        Position pos = null;
        BotChoose choose = null;
        List<Position> neighbors = this.getBoard().highlightList(dices);
        
        for (int i = 0; i < this.getBoard().getField().length; i++) {
            for (int j = 0; j < this.getBoard().getField()[i].length; j++) {
                if(this.getBoard().getField()[i][j] != null && !this.getBoard().getField()[i][j].isCrossed() 
                        && !this.getBoard().getField()[i][j].isOccupied() && !this.getBoard().getField()[i][j].isExploded()){
                    Position temp = new Position(i, j);
                    if(this.getBoard().contain(neighbors, temp)){
                        if(choose == null){
                            choose = new BotChoose(temp, this.getBoard().getField()[i][j].getPoint(), this.getBoard().getField()[i][j].getObject());    
                        }else{
                            choose = posCompare(choose, new BotChoose(temp, this.getBoard().getField()[i][j].getPoint(), 
                                    this.getBoard().getField()[i][j].getObject()));
                        }
                    }
                }
            }
        }
        return choose;
    }
    /**
     * this method is comparing the two given BotChoose objects. 
     * If the temp is better than the choose this will be replaced, otherwise choose will be remain 
     * as it is
     * @param choose  BotChoose, prior chosen one
     * @param temp BotChoose, current chosen one
     * @return BotChoose, it returns better BotChoose based on priority and value. 
     */
    private BotChoose posCompare(BotChoose choose, BotChoose temp){
        
        
        if(this.getBoard().getField()[temp.getPos().getX()][temp.getPos().getY()].getObject().getPriority() < 
               this.getBoard().getField()[choose.getPos().getX()][choose.getPos().getY()].getObject().getPriority()){
            choose = temp;
        }else if(choose.getType() == temp.getType() && choose.getPoint() < temp.getPoint()){
            choose = temp;
        }
        else{
            if(choose.getType() == ObjectTypes.noType){
                if(this.getBoard().isBothLines(temp.getPos())){
                    choose = new BotChoose(temp.getPos(), this.getBoard().isHorizontalLine(temp.getPos()) + this.getBoard().isVerticalLine(temp.getPos()),  
                            ObjectTypes.horizontalLine);
                }else{
                    if(this.getBoard().isHorizontalLine(temp.getPos()) > 0){
                        choose = new BotChoose(temp.getPos(), this.getBoard().isHorizontalLine(temp.getPos()), ObjectTypes.horizontalLine);
                    }
                    if(this.getBoard().isVerticalLine(temp.getPos()) > 0){
                        choose = new BotChoose(temp.getPos(), this.getBoard().isVerticalLine(temp.getPos()), ObjectTypes.verticalLine);
                    }
                }
            }else{
                if(choose.getType() == ObjectTypes.horizontalLine && this.getBoard().isHorizontalLine(temp.getPos()) > choose.getPoint()){
                    choose = new BotChoose(temp.getPos(), this.getBoard().isHorizontalLine(temp.getPos()), ObjectTypes.horizontalLine);
                }
                if(choose.getType() == ObjectTypes.verticalLine && this.getBoard().isVerticalLine(temp.getPos()) > choose.getPoint()){
                    choose = new BotChoose(temp.getPos(), this.getBoard().isVerticalLine(temp.getPos()), ObjectTypes.verticalLine);
                }
            
            }
            
            
        }
        
        return choose;
    }
}
