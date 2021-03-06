/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alireza
 */
public class GameTest {
    
    FakeGUI gui = new FakeGUI(); 
    
    public GameTest() {
    }

    @Test
    public void testGameConstructor() {
         Game game = new Game(gui, 1, 4);
         
         assertEquals(4, game.getPlayers().length);  
    }
    
    @Test
    public void testGame_dicesRandom_fourPlayers() {
         Game game = new Game(gui, 1, 4);
         
         assertEquals(13, game.getDices().size());  
    }
    @Test
    public void testGame_dicesRandom_twoPlayers() {
         Game game = new Game(gui, 1, 2);
         
         assertEquals(7, game.getDices().size());  
    }
    @Test
    public void testGame_Round() {
         Game game = new Game(gui, 1, 2);
         
         assertEquals(0, game.getRound());  
    }
    
     @Test
    public void testGame_canNotLay_Human() {
         Game game = new Game(gui, 1, 2);
         
         assertEquals(0, game.getIdxCurrent());  
         
         //can lay as it is not next to crossed to occupied 
         game.setDiceToBoard(new Position(2, 3));
         
         assertEquals(0, game.getIdxCurrent());
    }
    
}
