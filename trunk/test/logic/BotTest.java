/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alireza
 */
public class BotTest {
    
    public BotTest() {
    }

    @Test
    public void testConstructor() {
        Player bot = new Bot(new Board(new IO().jsonParser(1, new FakeGUI()), 1), true);
        
        assertEquals(9, bot.getBoard().getField().length);
        assertEquals(7, bot.getBoard().getField()[0].length);
        assertTrue(bot.isBot());
        
    }
    @Test
    public void testChooseNoType_chooseLeft_Level1(){
        IO io = new IO();
        
        Board br = new Board(io.jsonParser(1, new FakeGUI()), 1);
        
        Player bot = new Bot(br, true);
        
        bot.getBoard().getField()[3][3].setCrossed(true);
        
        List<Integer> dice = new LinkedList<>();
        
        dice.add(3);
        dice.add(5);
        
        BotChoose choose = bot.ai(dice);
        
        assertEquals(2, choose.getPos().getX());
        assertEquals(3, choose.getPos().getY());
    }
    @Test
    public void testChooseNoType_chooseTop_Level1(){
        IO io = new IO();
        
        Board br = new Board(io.jsonParser(1, new FakeGUI()), 1);
        
        Player bot = new Bot(br, true);
        
        bot.getBoard().getField()[3][3].setCrossed(true);
        bot.getBoard().getField()[2][3].setCrossed(true);
        bot.getBoard().getField()[1][3].setCrossed(true);
        
        
        List<Integer> dice = new LinkedList<>();
        
        dice.add(3);
        dice.add(5);
        
        BotChoose choose = bot.ai(dice);
        
        assertEquals(1, choose.getPos().getX());
        assertEquals(2, choose.getPos().getY());
    }
    @Test
    public void testChoose_Jewel_Bomb_Level1(){
        IO io = new IO();
        
        Board br = new Board(io.jsonParser(1, new FakeGUI()), 1);
        
        Player bot = new Bot(br, true);
        
        bot.getBoard().getField()[3][3].setCrossed(true);
        bot.getBoard().getField()[2][3].setCrossed(true);
        bot.getBoard().getField()[1][3].setCrossed(true);
        
        
        List<Integer> dice = new LinkedList<>();
        
        dice.add(3);
        dice.add(1);
        
        BotChoose choose = bot.ai(dice);
        
        assertEquals(2, choose.getPos().getX());
        assertEquals(2, choose.getPos().getY());
        assertEquals(ObjectTypes.jewel, bot.getBoard().getField()[2][2].getObject());
    }
     @Test
    public void testChoose_2Jewel_Bomb_Level1(){
        IO io = new IO();
        
        Board br = new Board(io.jsonParser(1, new FakeGUI()), 1);
        
        Player bot = new Bot(br, true);
        
        bot.getBoard().getField()[3][3].setCrossed(true);
        bot.getBoard().getField()[2][3].setCrossed(true);
        bot.getBoard().getField()[1][3].setCrossed(true);
        
        
        List<Integer> dice = new LinkedList<>();
        
        dice.add(3);
        dice.add(1);
        dice.add(6);
        
        BotChoose choose = bot.ai(dice);
        
        assertEquals(0, choose.getPos().getX());
        assertEquals(3, choose.getPos().getY());
        assertEquals(ObjectTypes.jewel, bot.getBoard().getField()[0][3].getObject());
        
    }
    @Test
    public void testChoose_Bomb_Level1(){
        IO io = new IO();
        
        Board br = new Board(io.jsonParser(1, new FakeGUI()), 1);
        
        Player bot = new Bot(br, true);
        
        bot.getBoard().getField()[3][3].setCrossed(true);
        bot.getBoard().getField()[2][3].setCrossed(true);
        bot.getBoard().getField()[1][3].setCrossed(true);
        
        List<Integer> dice = new LinkedList<>();
        
        dice.add(3);
        
        BotChoose choose = bot.ai(dice);
        
        assertEquals(1, choose.getPos().getX());
        assertEquals(2, choose.getPos().getY());
        assertEquals(ObjectTypes.bomb, bot.getBoard().getField()[1][2].getObject());
    }
     @Test
    public void testChooseNoType_puzzle_Keyhole_Level3(){
        IO io = new IO();
        
        Board br = new Board(io.jsonParser(3, new FakeGUI()), 3);
        
        Player bot = new Bot(br, true);
        
        bot.getBoard().getField()[4][2].setOccupied(true);
        bot.getBoard().getField()[4][3].setOccupied(true);
        bot.getBoard().getField()[4][4].setOccupied(true);
        bot.getBoard().getField()[3][3].setCrossed(true);
        List<Integer> dice = new LinkedList<>();
        
        dice.add(4);
        dice.add(1);
        
        BotChoose choose = bot.ai(dice);
        
        assertEquals(4, choose.getPos().getX());
        assertEquals(5, choose.getPos().getY());
        assertEquals(ObjectTypes.puzzle, bot.getBoard().getField()[4][5].getObject());
    }
    @Test
    public void testChooseNoType_HorizontalLine_Level3(){
        IO io = new IO();
        
        Board br = new Board(io.jsonParser(3, new FakeGUI()), 3);
        
        Player bot = new Bot(br, true);
        
        bot.getBoard().getField()[0][4].setCrossed(true);
        bot.getBoard().getField()[1][4].setCrossed(true);
        bot.getBoard().getField()[2][4].setCrossed(true);
        bot.getBoard().getField()[3][4].setCrossed(true);
        List<Integer> dice = new LinkedList<>();

        dice.add(4);
        dice.add(5);
        
        BotChoose choose = bot.ai(dice);
        
        assertEquals(4, choose.getPos().getX());
        assertEquals(4, choose.getPos().getY());
    }
    
    @Test
    public void testChooseNoType_twoHorizontalLine_Level3(){
        IO io = new IO();
        
        Board br = new Board(io.jsonParser(3, new FakeGUI()), 3);
        
        Player bot = new Bot(br, true);
        
        bot.getBoard().getField()[0][3].setCrossed(true);
        bot.getBoard().getField()[0][1].setCrossed(true);
        bot.getBoard().getField()[1][1].setCrossed(true);
        bot.getBoard().getField()[3][3].setCrossed(true);
        bot.getBoard().getField()[2][1].setCrossed(true);
        bot.getBoard().getField()[3][1].setCrossed(true);
        bot.getBoard().getField()[4][1].setCrossed(true);
        bot.getBoard().getField()[6][1].setCrossed(true);
        bot.getBoard().getField()[7][1].setCrossed(true);
        bot.getBoard().getField()[8][1].setCrossed(true);
        bot.getBoard().getField()[7][2].setCrossed(true);
        bot.getBoard().getField()[1][0].setCrossed(true);
        bot.getBoard().getField()[4][0].setCrossed(true);
        
        List<Integer> dice = new LinkedList<>();

        dice.add(3);
        
        
        BotChoose choose = bot.ai(dice);
        
        assertEquals(5, choose.getPos().getX());
        assertEquals(1, choose.getPos().getY());
    }
    @Test
    public void testChooseNoType_posInJunction_Level2(){
        IO io = new IO();
        
        Board br = new Board(io.jsonParser(2, new FakeGUI()), 2);
        
        Player bot = new Bot(br, true);
        
        bot.getBoard().getField()[0][2].setCrossed(true);
        bot.getBoard().getField()[1][2].setCrossed(true);
        bot.getBoard().getField()[2][2].setCrossed(true);
        bot.getBoard().getField()[3][2].setCrossed(true);
        bot.getBoard().getField()[4][2].setCrossed(true);
        bot.getBoard().getField()[6][2].setCrossed(true);
        bot.getBoard().getField()[7][2].setCrossed(true);
        bot.getBoard().getField()[8][2].setCrossed(true);
        
        bot.getBoard().getField()[5][0].setCrossed(true);
        bot.getBoard().getField()[5][1].setCrossed(true);
        bot.getBoard().getField()[5][4].setCrossed(true);
        bot.getBoard().getField()[5][3].setCrossed(true);
        bot.getBoard().getField()[5][5].setCrossed(true);
        bot.getBoard().getField()[5][6].setCrossed(true);
        
        bot.getBoard().getField()[0][4].setCrossed(true);
        bot.getBoard().getField()[1][4].setCrossed(true);
        bot.getBoard().getField()[5][4].setCrossed(true);
        bot.getBoard().getField()[3][4].setCrossed(true);
        bot.getBoard().getField()[4][4].setCrossed(true);
        bot.getBoard().getField()[6][4].setCrossed(true);
        bot.getBoard().getField()[7][4].setCrossed(true);
        bot.getBoard().getField()[8][4].setCrossed(true);
        
        bot.getBoard().getField()[6][5].setCrossed(true);
        
        List<Integer> dice = new LinkedList<>();

        dice.add(1);

        BotChoose choose = bot.ai(dice);
        
        assertEquals(5, choose.getPos().getX());
        assertEquals(2, choose.getPos().getY());
    }
    @Test
    public void testChooseNoType_Jewel_posInJunction_Level2(){
        IO io = new IO();
        
        Board br = new Board(io.jsonParser(2, new FakeGUI()), 2);
        
        Player bot = new Bot(br, true);
        
        bot.getBoard().getField()[0][2].setCrossed(true);
        bot.getBoard().getField()[1][2].setCrossed(true);
        bot.getBoard().getField()[2][2].setCrossed(true);
        bot.getBoard().getField()[3][2].setCrossed(true);
        bot.getBoard().getField()[4][2].setCrossed(true);
        bot.getBoard().getField()[6][2].setCrossed(true);
        bot.getBoard().getField()[7][2].setCrossed(true);
        bot.getBoard().getField()[8][2].setCrossed(true);
        
        bot.getBoard().getField()[5][0].setCrossed(true);
        bot.getBoard().getField()[5][1].setCrossed(true);
        bot.getBoard().getField()[5][4].setCrossed(true);
        bot.getBoard().getField()[5][3].setCrossed(true);
        bot.getBoard().getField()[5][5].setCrossed(true);
        bot.getBoard().getField()[5][6].setCrossed(true);
        
        bot.getBoard().getField()[0][4].setCrossed(true);
        bot.getBoard().getField()[1][4].setCrossed(true);
        bot.getBoard().getField()[5][4].setCrossed(true);
        bot.getBoard().getField()[3][4].setCrossed(true);
        bot.getBoard().getField()[4][4].setCrossed(true);
        bot.getBoard().getField()[6][4].setCrossed(true);
        bot.getBoard().getField()[7][4].setCrossed(true);
        bot.getBoard().getField()[8][4].setCrossed(true);
        
    
        
        List<Integer> dice = new LinkedList<>();

        dice.add(1);

        BotChoose choose = bot.ai(dice);
        
        assertEquals(6, choose.getPos().getX());
        assertEquals(5, choose.getPos().getY());
    }
    
     @Test
    public void testChoose_Jewel_Puzzle_Level1(){
        IO io = new IO();
        
        Board br = new Board(io.jsonParser(1, new FakeGUI()), 1);
        
        Player bot = new Bot(br, true);
        
        bot.getBoard().getField()[4][5].setCrossed(true);
        bot.getBoard().getField()[4][6].setCrossed(true);
        bot.getBoard().getField()[3][6].setCrossed(true);
        bot.getBoard().getField()[4][2].setCrossed(true);
        bot.getBoard().getField()[3][2].setCrossed(true);
        
        List<Integer> dice = new LinkedList<>();
        
        dice.add(4);
        dice.add(1);
        
        BotChoose choose = bot.ai(dice);
        
        assertEquals(2, choose.getPos().getX());
        assertEquals(6, choose.getPos().getY());
        assertEquals(ObjectTypes.puzzle, bot.getBoard().getField()[2][6].getObject());
    }
    
}
