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
public class PositionTest {
    
    public PositionTest() {
    }

    @Test
    public void testConstructor() {
        Position pos = new Position(0, 1);
        
        assertEquals(0, pos.getX());
        assertEquals(1, pos.getY());
    }
    
    @Test
    public void testIsNeighbors_up(){
        Position pos = new Position(2, 2);
        
        assertTrue(pos.isNeighbor(new Position(1, 2)));
    }
    @Test
    public void testIsNeighbors_down(){
        Position pos = new Position(2, 2);
        
        assertTrue(pos.isNeighbor(new Position(3, 2)));
    }
    @Test
    public void testIsNeighbors_left(){
        Position pos = new Position(2, 2);
        
        assertTrue(pos.isNeighbor(new Position(2, 1)));
    }
    @Test
    public void testIsNeighbors_right(){
        Position pos = new Position(2, 2);
        
        assertTrue(pos.isNeighbor(new Position(2, 3)));
    }
    @Test
    public void testIsNeighbors_NotNeighbor(){
        Position pos = new Position(2, 2);
        
        assertFalse(pos.isNeighbor(new Position(0, 2)));
    }
    @Test
    public void testGetNeighbors(){
        Position pos = new Position(2, 2);
        Position[] neighbors = pos.getNeighbors();
        
        assertEquals(4, neighbors.length);
        
        assertEquals(3, neighbors[0].getX());
        assertEquals(2, neighbors[0].getY());
       
        assertEquals(2, neighbors[2].getX());
        assertEquals(3, neighbors[2].getY());
    }
    @Test
    public void testPositionEquals_equal(){
        Position pos = new Position(2, 2);
        
        assertTrue(pos.equals(new Position(2, 2)));
    }
    @Test
    public void testPositionEquals_notEqual(){
        Position pos = new Position(2, 2);
        
        assertFalse(pos.equals(new Position(3, 2)));
    }
    
}
