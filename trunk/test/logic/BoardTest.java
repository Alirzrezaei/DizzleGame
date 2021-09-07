/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Alireza
 */
public class BoardTest {
    
    public BoardTest() {
    }

    @Test
    public void testBoard_getBoardPoint_Bomb() {
        IO io = new IO();
        LevelReader obj = io.jsonParser(1, new FakeGUI());
        
        Board board = new Board(obj, 1);
        
        assertEquals(2, board.getField()[7][2].getPoint());
        assertEquals(ObjectTypes.bomb, board.getField()[7][2].getObject());
    }
    
    @Test
    public void testBoard_getBoardPoint_Jewel() {
        IO io = new IO();
        LevelReader obj = io.jsonParser(1, new FakeGUI());
        
        Board board = new Board(obj, 1);
        
        assertEquals(3, board.getField()[8][1].getPoint());
   
        assertEquals(ObjectTypes.jewel, board.getField()[8][1].getObject());
    }
    @Test
    public void testBoard_isHorizotalLine_allCrossed_exceptPos() {
        IO io = new IO();
        LevelReader obj = io.jsonParser(1, new FakeGUI());
        
        Board board = new Board(obj, 1);
        
        board.getField()[0][1].setCrossed(true);
        board.getField()[1][1].setCrossed(true);
        board.getField()[2][1].setCrossed(true);
        board.getField()[3][1].setCrossed(true);
        board.getField()[4][1].setCrossed(true);
        board.getField()[5][1].setCrossed(true);
        board.getField()[7][1].setCrossed(true);
        board.getField()[8][1].setCrossed(true);   
        
        assertEquals(10, board.isHorizontalLine(new Position(6, 1)));
        
    }
    @Test
    public void testBoard_isHorizotalLine_mixCrossed_occupied_exceptPos() {
        IO io = new IO();
        LevelReader obj = io.jsonParser(1, new FakeGUI());
        
        Board board = new Board(obj, 1);
        
        board.getField()[0][4].setCrossed(true);
        board.getField()[1][4].setCrossed(true);
        board.getField()[2][4].setCrossed(true);
        board.getField()[3][4].setCrossed(true);
        board.getField()[4][4].setCrossed(true);
        board.getField()[5][4].setCrossed(true);
        board.getField()[7][4].setOccupied(true);
        board.getField()[8][4].setOccupied(true);   
        
        assertEquals(10, board.isHorizontalLine(new Position(6, 4)));
        
    }
    @Test
    public void testBoard_isHorizotalLine_NotLine() {
        IO io = new IO();
        LevelReader obj = io.jsonParser(1, new FakeGUI());
        
        Board board = new Board(obj, 1);
        
        board.getField()[0][4].setCrossed(true);
        board.getField()[1][4].setCrossed(true);
        board.getField()[2][4].setCrossed(true);
        board.getField()[3][4].setCrossed(true);
        board.getField()[4][4].setCrossed(true);
        board.getField()[5][4].setCrossed(true);
        board.getField()[8][4].setOccupied(true);   
        
        assertEquals(0, board.isHorizontalLine(new Position(6, 4)));
        
    }
    
    @Test
    public void testBoard_isVertical_allCrossed_exceptPos() {
        IO io = new IO();
        LevelReader obj = io.jsonParser(1, new FakeGUI());
        
        Board board = new Board(obj, 1);
        
        board.getField()[3][0].setCrossed(true);
        board.getField()[3][1].setCrossed(true);
        board.getField()[3][2].setCrossed(true);
        board.getField()[3][3].setCrossed(true);
        board.getField()[3][4].setCrossed(true);
        board.getField()[3][6].setCrossed(true);   
        
        assertEquals(10, board.isVerticalLine(new Position(3, 5)));
        
    }
    @Test
    public void testBoard_isVertical_mixCrossed_occupied_exceptPos() {
        IO io = new IO();
        LevelReader obj = io.jsonParser(1, new FakeGUI());
        
        Board board = new Board(obj, 1);
        
        board.getField()[3][0].setOccupied(true);
        board.getField()[3][1].setCrossed(true);
        board.getField()[3][2].setCrossed(true);
        board.getField()[3][3].setCrossed(true);
        board.getField()[3][4].setCrossed(true);
        board.getField()[3][6].setOccupied(true);   
        
        assertEquals(10, board.isVerticalLine(new Position(3, 5)));
        
    }
    
     @Test
    public void testBoard_isVertical_notLine() {
        IO io = new IO();
        LevelReader obj = io.jsonParser(1, new FakeGUI());
        
        Board board = new Board(obj, 1);
        
        board.getField()[3][0].setOccupied(true);
        board.getField()[3][1].setCrossed(true);
        board.getField()[3][2].setCrossed(true);
        board.getField()[3][3].setCrossed(true);
        board.getField()[3][4].setCrossed(true);
        
        
        assertEquals(0, board.isVerticalLine(new Position(3, 5)));
        
    }
}
