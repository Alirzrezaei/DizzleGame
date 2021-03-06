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
public class IOTest {
    
    public IOTest() {
    }

    @Test
    public void testIO_parsingJsonFile() {
        
         IO io = new IO();
        
        LevelReader levelObj = io.jsonParser(1, new FakeGUI());
        
        assertEquals(7, levelObj.getField().length);
        assertEquals(9, levelObj.getField()[0].length);
        
        assertEquals(2, (int)levelObj.getField()[0][2]);
    }
    
}
