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
public class PlayerTest {
    
    public PlayerTest() {
    }

    @Test
    public void testConstructor_Haman() {
        Player player = new Human(new Board(new IO().jsonParser(1, new FakeGUI()), 1), false);
        
        assertFalse(player.isBot());
    }
    @Test
    public void testConstructor_Bot() {
        Player player = new Bot(new Board(new IO().jsonParser(1, new FakeGUI()), 1), true);
        
        assertTrue(player.isBot());
    }
    @Test
    public void testConstructor_isDropOut() {
        Player player = new Human(new Board(new IO().jsonParser(1, new FakeGUI()), 1), false);
        
        assertFalse(player.isDropOut());
        
        player.setIsDropOut(true);
        
        assertTrue(player.isDropOut());
    }
    
    
}
