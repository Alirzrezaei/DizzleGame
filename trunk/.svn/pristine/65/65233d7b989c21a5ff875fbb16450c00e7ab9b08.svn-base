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
public class LevelReaderTest {
    
    public LevelReaderTest() {
    }

    @Test
    public void testLevelMaker_LevelOne_Field() {
        
        IO io = new IO();
        
        LevelReader levelObj = io.jsonParser(1, new FakeGUI());
        
        assertEquals(7, levelObj.getField().length);
        assertEquals(9, levelObj.getField()[0].length);
        
        assertEquals(2, (int)levelObj.getField()[0][2]);
    }
    
    @Test
    public void testLevelMaker_LevelTwo_Jewels() {
        
        IO io = new IO();
        
        LevelReader levelObj = io.jsonParser(2, new FakeGUI());
        
        assertEquals(3, levelObj.getJewels()[0].getPoints());
        
        assertEquals(6, levelObj.getJewels()[1].getPositions()[0].getX());
        assertEquals(1, levelObj.getJewels()[1].getPositions()[0].getY());
    }
    
}
