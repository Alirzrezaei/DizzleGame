/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * The Game class is the main class of logic package that is called by gui package. An
 * instance of game is created in FXMLDocumentController class and when it is required to 
 * check the logic of the game, that instance will be used. Indeed this is Game class that controls 
 * the activities of the game by using other classes in logics. 
 * 
 * Two constructors of the game class is to make the games at the beginning or when user wants to 
 * load a save game. The Players with their boards and dices are defined here to control the game. 
 * Also round and players turns are specified here to be used in NextPlayer method to check with players 
 * turn it is at a moment. 
 * 
 * An instance gui is defined here and it is based on what is sent from gui package. That can be used to 
 * sent updates that are done in logic to gui to show the user. 
 * 
 * The bot turn method is to preform action of bot by calling ai method in bot class. SetDiceToBoard 
 * method is for human player to do its turn by checking if human player clicked position is legible to
 * put dice on or not. 
 * 
 * the method for roll again, drop out, save and load are to be called gui to respective roll the dices again, 
 * to drop out of current user, to save the current game and to load a game from a saved game file. 
 * 
 * the crossOccupiedPositions method at the end of the each turn is changing the status of the cells from 
 * occupied to crossed and collected the respective symbols and their points inside the cells. 
 * @author Rezaei
 */
public class Game {

    private Player players[];
    private GUIConnector gui;
    private int level;
    private final int totalRound;
    private int round;
    private int turn;
    private int subTurn;
    private int idxCurrent;
    private IO io;
    private List<Integer> dices;
    private List<Position> highlight;
    private boolean rolledAgain;
    private boolean flag;
    private int idxFlag;
    private List<Position> bomb;
    private Position logPos;
    private int logDice;
    private ObjectTypes logType;

    public Game(GUIConnector gui, int level, int noOfPlayers) {
        this.gui = gui;
        this.io = new IO();
        this.level = level;
        flag = false;
        idxFlag = 0;
        bomb = new LinkedList<>();

        totalRound = totalRound(noOfPlayers);
        round = 0;
        turn = 0;
        idxCurrent = 0;

        this.players = new Player[noOfPlayers];
        this.dices = new LinkedList<>();

        LevelReader levelObj = io.jsonParser(level, gui);

        for (int i = 0; i < players.length; i++) {
            if (i == 0) {
                players[i] = new Human(new Board(levelObj, level), false);
                gui.initPointTable(level);
            } else {
                players[i] = new Bot(new Board(levelObj, level), true);
            }
        }

        fillDices(noOfPlayers);
        this.gui.addImgsToAllGridPanes(levelObj, noOfPlayers, level);

        highlight = players[idxCurrent].getBoard().highlightList(dices);
        highlight(highlight);
        rolledAgain = false;

    }

    /**
     * the constructor to load the game from loaded file
     */
    protected Game(GUIConnector gui, SaveLoadObject load) {
        //clear();
        this.gui = gui;
        this.io = new IO();
        totalRound = totalRound(load.getPlayers().length);
        this.round = load.getRound();
        this.turn = load.getTurnOf();
        this.level = load.getLevelNo();
        this.dices = new LinkedList<>();
        bomb = new LinkedList<>();
        loadDices(load.getDice());

        players = new Player[load.getPlayers().length];
        this.gui.clear();
        for (int i = 0; i < load.getPlayers().length; i++) {
            if (i == 0) {
                players[i] = new Human(new Board(new IO().jsonParser(level, this.gui), level), false);
                gui.initPointTable(level);
            } else {
                players[i] = new Bot(new Board(new IO().jsonParser(level, this.gui), level), true);
            }
        }
        //this.gui.clear();

        this.gui.addImgsToAllGridPanes(new IO().jsonParser(level, this.gui), players.length, level);

        this.gui.setDicesOnGridPane(dices);
        updateLoadedBoard(load);
        LoadedCrossedPositionsPoint();
        highlight = players[idxCurrent].getBoard().highlightList(dices);
        if (highlight.isEmpty()) {
            gui.setBtnDisable(false);
        } else {
            highlight(highlight);
        }

        this.gui.updateRoundTurn(round, turn);
        this.idxCurrent = 0;
        rolledAgain = false;
    }

    /**
     * the constructor for testing
     */
    protected Game(GUIConnector gui, Player[] players, int level) {
        totalRound = totalRound(players.length);
    }

    /**
     * this method is returning the array of player.
     *
     * @return array of Player
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * get the level of current playing game
     *
     * @return int the current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * this method is returning the current round in which players are playing.
     *
     * @return int current level
     */
    public int getRound() {
        return round;
    }

    /**
     * this method is returing the current turn in which players are playing.
     *
     * @return int current turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * get the index of current player.
     *
     * @return int index of current player
     */
    public int getIdxCurrent() {
        return idxCurrent;
    }

    /**
     * get the list of current dices that can be placed on the board
     *
     * @return list of integer of current dices
     */
    public List<Integer> getDices() {
        return dices;
    }

    public int totalRound(int players) {
        if (players == 2) {
            return 6;
        } else if (players == 3) {
            return 4;
        } else {
            return 3;
        }
    }

    /**
     * this method is filling the list of dices based on the number of players
     * who plays for two players it is 7, for three players it is 10 and for
     * four players it is 13 dices.
     *
     * @param noOfPlayer int number players who players
     */
    private void fillDices(int noOfPlayer) {
        Random rand = new Random();
        dices.clear();

        for (int i = 0; i < (noOfPlayer * 3 + 1); i++) {
            dices.add((int) (Math.random() * ((6 - 1) + 1)) + 1);
        }
        Collections.sort(dices);
        this.gui.setDicesOnGridPane(dices);
    }

    /**
     * this method is controlling the players' turn. if a player's turn is
     * finished, it will call this method to change the next player. This method
     * is also checking if the game is ended based on end game condition. Also
     * if the current player is bot player, appropriate method will be called to
     * do bot turn.
     */
    void nextPlayer() {

        io.logger(idxCurrent, logDice, logPos, logType, players[idxCurrent].isDropOut(), this.gui, rolledAgain);
        rolledAgain = false;
        gui.lblDiceBack(rolledAgain);
        if (!endGame()) {
            if (dices.isEmpty() || dropedOutPlayer() == 0) {
                if (turn == players.length - 1) {
                    round++;
                    turn = 0;
                    subTurn = 0;
                    idxCurrent = 0;

                    crossOccupiedPositions();
                    resetDropOut();
                    fillDices(players.length);
                } else {
                    turn++;
                    subTurn = 0;
                    idxCurrent = turn;

                    crossOccupiedPositions();
                    resetDropOut();
                    fillDices(players.length);
                }
            } else {
                subTurn++;
                idxCurrent = (idxCurrent + 1) % players.length;
            }
            this.gui.updateRoundTurn(round, turn);

            if (players[idxCurrent].isDropOut()) {
                nextPlayer();
            }

            if (players[idxCurrent].isBot()) {
                botTurn();
            } else {

                highlight = players[idxCurrent].getBoard().highlightList(dices);

                highlight(highlight);

                if (highlight.isEmpty()) {
                    this.gui.setBtnDisable(false);
                }
            }

        } else {
            crossOccupiedPositions();
            io.closeLog(); 
            showWinner();
        }
    }
    /**
     * This method is checking if the clicked position by human is Ok to put dice on 
     * or remove dice from clicked position based on the conditions.
     * 
     * If the position is a valid position and the position is between a valid neighbor positions that a 
     * dice is available for it, user will can put dice on it. 
     * 
     * Also it is checked in this method if the user rolled again but there is no suitable 
     * dice for it to lay on the board. So user will delete a laid dice on the given positon from 
     * the board back to dice list. 
     * @param pos Position to lay dice or delete dice from board. 
     */
    public void setDiceToBoard(Position pos) {

        if (players[idxCurrent].getBoard().isValidPos(pos) && players[idxCurrent].getBoard().contain(highlight, pos)) {

            players[idxCurrent].getBoard().getField()[pos.getX()][pos.getY()].setOccupied(true);
            gui.setDiceToBoard(players[idxCurrent].getBoard().getField()[pos.getX()][pos.getY()].getValue(), pos, idxCurrent);

            removeFromDices(players[idxCurrent].getBoard().getField()[pos.getX()][pos.getY()].getValue());
            removeHighlight(highlight);
            if (dropedOutPlayer() == 1) {
                players[idxCurrent].setIsDropOut(true);
                gui.showPenOnBoard(idxCurrent);
            }
            logPos = pos;
            logDice = players[idxCurrent].getBoard().getField()[pos.getX()][pos.getY()].getValue();
            logType = players[idxCurrent].getBoard().getField()[pos.getX()][pos.getY()].getObject();

            nextPlayer();

        } else {
            if (rolledAgain && players[idxCurrent].getBoard().getField()[pos.getX()][pos.getY()].isOccupied()) {
                players[idxCurrent].getBoard().getField()[pos.getX()][pos.getY()].setOccupied(false);
                gui.removeDiceFromBoard(pos, idxCurrent);
                dices.add(players[idxCurrent].getBoard().getField()[pos.getX()][pos.getY()].getValue());
                Collections.sort(dices);
                gui.setDicesOnGridPane(dices);
                //rolledAgain = false;
                if (dropedOutPlayer() == 1) {
                    players[idxCurrent].setIsDropOut(true);
                    gui.showPenOnBoard(idxCurrent);
                }
                nextPlayer();
            }
        }
    
    }

    /**
     * this method is checking if the given position is neighboring to any of
     * occupied or crossed cells. then player is allow to play.
     *
     * @param pos clicked position by user
     * @return true if the given position is neighboring to occupied or crossed
     * cell according the conditions
     */
    private boolean isFilledNeighbor(Position pos) {
        Position[] neighbors = pos.getNeighbors();
        boolean isNeighbor = false;
        for (int i = 0; i < neighbors.length; i++) {
            if (players[idxCurrent].getBoard().isValidPos(neighbors[i])) {
                if (players[idxCurrent].getBoard().ifOccupied() && !players[idxCurrent].getBoard().inClosed()) {
                    if (players[idxCurrent].getBoard().getField()[neighbors[i].getX()][neighbors[i].getY()].isOccupied()) {
                        isNeighbor = true;
                    }
                } else {
                    if (players[idxCurrent].getBoard().getField()[neighbors[i].getX()][neighbors[i].getY()].isCrossed()) {
                        isNeighbor = true;
                    }
                }
            }
        }
        return isNeighbor;
    }

    /**
     * when user clicked on roll again button, this method is called to shuffle
     * the dices and show new dices on dice area. then based on new dices a list
     * will be calculated to show the possible moves.
     *
     * at the end if the list is again empty, user will be dropped out
     * automatically
     */
    private void rollAgainDices() {
        Random rand = new Random();
        int len = dices.size();
        dices.clear();
        for (int i = 0; i < len; i++) {
            dices.add((int) (Math.random() * ((6 - 1) + 1)) + 1);
        }

        Collections.sort(dices);
        
        io.rollLogger(dices, idxCurrent);
        
        gui.setDicesOnGridPane(dices);

        highlight = players[idxCurrent].getBoard().highlightList(dices);
        highlight(highlight);
        
        if (highlight.isEmpty()) {
            rolledAgain = true;  
            gui.lblDiceBack(rolledAgain);
        }

    }

    /**
     * when human player clicks on roll again button, the buttons will disable
     * and the remaining dices will be shuffled again to see if there is new
     */
    public void rollAgainClicked() {
        this.gui.setBtnDisable(true);

        rollAgainDices();
    }

    /**
     * when user can not play, the user can drop out.
     *
     * it means that he/she will not play for this round
     */
    public void DropOutClicked() {

        this.gui.setBtnDisable(true);
        players[idxCurrent].setIsDropOut(true);
        this.gui.showPenOnBoard(idxCurrent);
        nextPlayer();

    }

    /**
     * if the current player is bot this player is called
     */
    private void botTurn() {

        BotChoose choose = players[idxCurrent].ai(dices);
        if (choose != null && !players[idxCurrent].isDropOut()) {
            int value = players[idxCurrent].getBoard().getField()[choose.getPos().getX()][choose.getPos().getY()].getValue();
            players[idxCurrent].getBoard().getField()[choose.getPos().getX()][choose.getPos().getY()].setOccupied(true);
            removeFromDices(value);
            gui.setDiceToBoard(value, choose.getPos(), idxCurrent);

            if (dropedOutPlayer() == 1) {
                players[idxCurrent].setIsDropOut(true);
                gui.showPenOnBoard(idxCurrent);
            }

            logPos = choose.getPos();
            logDice = players[idxCurrent].getBoard().getField()[choose.getPos().getX()][choose.getPos().getY()].getValue();
            logType = players[idxCurrent].getBoard().getField()[choose.getPos().getX()][choose.getPos().getY()].getObject();

        } else {
            if (!players[idxCurrent].isDropOut()) {
                players[idxCurrent].setIsDropOut(true);
                gui.showPenOnBoard(idxCurrent);
            }

        }

        nextPlayer();

    }

    /**
     * this method is checking if the game is finished. the game will be
     * finished when the current players board is full or all rounds are played
     * completely.
     *
     * @return true if current player's board is full or round are finished
     */
    boolean endGame() {

        return players[idxCurrent].getBoard().isAllCrossed()
                || (round >= totalRound - 1 && turn >= players.length - 1 && dropedOutPlayer() == 0)
                || (round >= totalRound - 1 && turn >= players.length - 1 && dices.isEmpty());

    }

    /**
     * this method is receiving a list of possible moves for human player and
     * pass each nodes of the list which are Positions to gui show them on the
     * board
     *
     * @param list positions of possible moves
     */
    private void highlight(List<Position> list) {
        for (int i = 0; i < list.size(); i++) {
            this.gui.highlightPos(list.get(i));
        }
    }

    /**
     * this method is removing the already placed dice on the board from dice
     * list and update the gui to show the list without removed value
     *
     * @param value int value aff dice to be removed
     */
    private void removeFromDices(int value) {
        
        boolean removed = false;

        for (int i = 0; i < dices.size() && !removed; i++) {
            if (dices.get(i) == value) {
                removed = true;
                dices.remove(i);
            }
        }
        this.gui.setDicesOnGridPane(dices);
    }

    /**
     * after human player puts a dice on the board, this method is called to
     * remove the highlighted cell on the board.
     *
     * @param list Postions, last possible moved which should be removed
     */
    private void removeHighlight(List<Position> list) {
        for (int i = 0; i < list.size(); i++) {
            this.gui.removeHighlightPos(list.get(i));
        }
    }

    /**
     * this methos is reseting the droped out of the player ot false
     */
    private void resetDropOut() {
        for (int i = 0; i < this.players.length; i++) {
            if (players[i].isDropOut()) {
                this.gui.removePenFromBoard(i);
                players[i].setIsDropOut(false);
            }
        }
    }

    /**
     * this method checking how many player is dropped out.
     *
     * @return int number of remained active player
     */
    private int dropedOutPlayer() {
        int dropOut = players.length;
        for (int i = 0; i < players.length; i++) {
            if (players[i].isDropOut()) {
                dropOut--;
            }
        }
        return dropOut;
    }

    /**
     * tbis method is clearing the gui components
     */
    public void clear() {
        gui.clear();
    }

    /**
     * After finishing each turn, the occupied positions are crossed on the
     * player's baord. Also this method is checking if the points are collected
     * for player on occupied position. The board table is also updated based on
     * collected point.
     */
    private void crossOccupiedPositions() {
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players[i].getBoard().getField().length; j++) {
                for (int k = 0; k < players[i].getBoard().getField()[j].length; k++) {
                    Position pos = new Position(j, k);

                    if (players[i].getBoard().getField()[j][k] != null && players[i].getBoard().getField()[j][k].isOccupied()) {
                        players[i].getBoard().getField()[j][k].setCrossed(true);
                        players[i].getBoard().getField()[j][k].setOccupied(false);

                        if (players[i].getBoard().getField()[j][k].getPoint() > 0) {
                            if (players[i].getBoard().getField()[j][k].getObject() == ObjectTypes.puzzle) {
                                if (players[i].getBoard().ifAllPuzzileCrossed(pos)) {
                                    ObjectTypes type = players[i].getBoard().getField()[j][k].getObject();
                                    int point = players[i].getBoard().getField()[j][k].getPoint();
                                    setToScoreBoard(i, type, point, pos);
                                }

                            } else if (players[i].getBoard().getField()[j][k].getObject() == ObjectTypes.flag) {
                                flag = true;
                                ObjectTypes type = players[i].getBoard().getField()[j][k].getObject();
                                players[i].setFlagReachedAs(idxFlag);
                                int point = players[i].getBoard().getFlagPoints()[idxFlag];
                                setToScoreBoard(i, type, point, pos);
                            } else {
                                ObjectTypes type = players[i].getBoard().getField()[j][k].getObject();
                                int point = players[i].getBoard().getField()[j][k].getPoint();
                                if (type != ObjectTypes.bomb) {
                                    setToScoreBoard(i, type, point, pos);
                                }

                                if (type == ObjectTypes.bomb && !bomb.contains(pos)) {
                                    bomb.add(pos);
                                }
                            }

                        }
                        if (players[i].getBoard().getField()[j][k].getObject() == ObjectTypes.rocket) {
                            Position planet = players[i].getBoard().setCrossOnPlanet();
                            gui.setCrossedPos(planet, i);
                        }

                        if (players[i].getBoard().pointOfHorizontalLine(pos.getX(), pos.getY()) > 0) {
                            ObjectTypes type = ObjectTypes.horizontalLine;
                            int point = players[i].getBoard().pointOfHorizontalLine(pos.getX(), pos.getY());

                            setToScoreBoard(i, type, point, pos);
                        }

                        if (players[i].getBoard().pointOfVerticalLine(pos.getX(), pos.getY()) > 0) {
                            ObjectTypes type = ObjectTypes.verticalLine;
                            int point = players[i].getBoard().pointOfVerticalLine(pos.getX(), pos.getY());
                            setToScoreBoard(i, type, point, pos);
                        }
                        gui.setCrossedPos(pos, i);

                    }
                }
            }
        }
        if (flag) {
            flag = false;
            idxFlag++;
        }
        if (!bomb.isEmpty()) {
            explodedBomb(bomb);
            bomb.clear();
        }
    }

    /**
     * this method is setting the scores to score board of players.
     *
     * @param player int id of player
     * @param type object type of the cell
     * @param point point of the object
     * @param pos position of the cell
     */
    private void setToScoreBoard(int player, ObjectTypes type, int point, Position pos) {
        int idx;
        if (level == 1) {
            idx = players[player].getBoard().setFstLevelPoints(point, type);
        } else if (level == 2) {
            idx = players[player].getBoard().setSndLevelPoints(point, type, pos);
        } else {
            idx = players[player].getBoard().setThrdLevelPoints(point, type);
        }

        if (!players[player].isBot()) {
            gui.updateScores(players[player].getBoard().getPointTable()[idx].getPoint(), idx);
        }
    }

    /**
     * if other players ignited the bomb, players whose bomb are not crossed
     * will set it as exploded and update their gui accordingly
     *
     * @param pos list of bomb positions
     */
    private void explodedBomb(List<Position> pos) {
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < pos.size(); j++) {
                if (!players[i].getBoard().getField()[pos.get(j).getX()][pos.get(j).getY()].isCrossed()
                        && !players[i].getBoard().getField()[pos.get(j).getX()][pos.get(j).getY()].isExploded()) {
                    players[i].getBoard().getField()[pos.get(j).getX()][pos.get(j).getY()].setExploded(true);
                    setToScoreBoard(i, ObjectTypes.bomb, 2, pos.get(j));
                    gui.setExploded(i, pos.get(j));
                }
            }
        }
    }

    /**
     * this method call load method in IO class to create a game based on data
     * in saved json file
     *
     *
     * @param game game created in gui part
     * @return Game, created by loaded data
     * @throws FileNotFoundException
     */
    public Game load(Game game) throws FileNotFoundException {
        
      
        game = io.load(this.gui, game);
        
        return game;
    }

    /**
     * this method is called from gui part when player is clicked on save
     * button. Then it calls the save method in io class to save the game
     */
    public void save() {
        int[] dices = new int[this.dices.size()];
        for (int i = 0; i < dices.length; i++) {
            dices[i] = this.dices.get(i);

        }
        io.save(round, level, turn, dices, level, players);
    }

    /**
     * this method is storing the loaded dices into the list of dices
     *
     * @param dices int list of loaded dices
     */
    private void loadDices(int[] dices) {
        this.dices.clear();

        for (int i = 0; i < dices.length; i++) {
            this.dices.add(i, dices[i]);
        }

    }

    /**
     * This method is updating the board of each players based on data from
     * given loaded object.
     *
     * @param obj object of loaded from save file
     */
    private void updateLoadedBoard(SaveLoadObject obj) {

        for (int i = 0; i < obj.getPlayers().length; i++) {

            players[i].setFlagReachedAs(obj.getPlayers()[i].getFlagReachedAs());

            if (players[i].getFlagReachedAs() > idxFlag) {
                idxFlag = players[i].getFlagReachedAs();
            }

            players[i].setIsDropOut(!obj.getPlayers()[i].isActive());
            if(!obj.getPlayers()[i].isActive()){
                gui.showPenOnBoard(i);
            }
            /*
            this part is crossing the cells that are checked in loaded object
             */
            for (int j = 0; j < obj.getPlayers()[i].getChecked().length; j++) {
                players[i].getBoard().getField()[obj.getPlayers()[i].getChecked()[j].getX()][obj.getPlayers()[i].getChecked()[j].getY()].setCrossed(true);
                this.gui.setCrossedPosLoad(obj.getPlayers()[i].getChecked()[j], i);
            }
            /*
            this part is setting to occupied the cells that are diced on in loaded object
             */
            for (int j = 0; j < obj.getPlayers()[i].getDiceOn().length; j++) {
                players[i].getBoard().getField()[obj.getPlayers()[i].getDiceOn()[j].getX()][obj.getPlayers()[i].getDiceOn()[j].getY()].setOccupied(true);
                this.gui.setDiceToBoard(players[i].getBoard().getField()[obj.getPlayers()[i].getDiceOn()[j].getX()][obj.getPlayers()[i].getDiceOn()[j].getY()].getValue(), obj.getPlayers()[i].getDiceOn()[j], i);
            }
            /*
            this part is setting exploded the cells that are exploded in loaded object
             */
            for (int j = 0; j < obj.getPlayers()[i].getExploded().length; j++) {
                players[i].getBoard().getField()[obj.getPlayers()[i].getExploded()[j].getX()][obj.getPlayers()[i].getExploded()[j].getY()].setExploded(true);
                this.gui.setExploded(i, obj.getPlayers()[i].getExploded()[j]);

            }
        }
    }
    /**
     * this method is checking if the crossed positions of a loaded game has an symbol 
     * and their points will be summed in the point table 
     */
     private void LoadedCrossedPositionsPoint() {
         int idxFlagTemp;
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players[i].getBoard().getField().length; j++) {
                for (int k = 0; k < players[i].getBoard().getField()[j].length; k++) {
                    Position pos = new Position(j, k);
                    // check if the cell in board is crossed 
                    if (players[i].getBoard().getField()[j][k] != null && players[i].getBoard().getField()[j][k].isCrossed()) {        
                       
                        if (players[i].getBoard().getField()[j][k].getPoint() > 0) {
                             //check if the object in the table is puzzle
                            if (players[i].getBoard().getField()[j][k].getObject() == ObjectTypes.puzzle) {
                                if (players[i].getBoard().puzzleFirstPos(pos) && 
                                        players[i].getBoard().ifAllPuzzileCrossed(pos)) {
                                    ObjectTypes type = players[i].getBoard().getField()[j][k].getObject();
                                    int point = players[i].getBoard().getField()[j][k].getPoint();
                                    setToScoreBoard(i, type, point, pos);
                                }
                            // check if the cell contains flag
                            } else if (players[i].getBoard().getField()[j][k].getObject() == ObjectTypes.flag) {
                                
                                ObjectTypes type = players[i].getBoard().getField()[j][k].getObject();           
                                int point = players[i].getBoard().getFlagPoints()[players[i].getFlagReachedAs()];
                                setToScoreBoard(i, type, point, pos);
                            } else {
                                //for other symbols with point, but not bomb
                                ObjectTypes type = players[i].getBoard().getField()[j][k].getObject();
                                int point = players[i].getBoard().getField()[j][k].getPoint();
                                if (type != ObjectTypes.bomb) {
                                    setToScoreBoard(i, type, point, pos);
                                }
                            }
                        }
                        //check if a completed horizontal line is crossed
                        if (players[i].getBoard().horizonalLineLastPos(pos) && 
                                players[i].getBoard().pointOfHorizontalLine(pos.getX(), pos.getY()) > 0) {
                            ObjectTypes type = ObjectTypes.horizontalLine;
                            int point = players[i].getBoard().pointOfHorizontalLine(pos.getX(), pos.getY());

                            setToScoreBoard(i, type, point, pos);
                        }
                        //check if a completed vertical line is crossed
                        if (players[i].getBoard().verticalLineLastPos(pos) &&
                                players[i].getBoard().pointOfVerticalLine(pos.getX(), pos.getY()) > 0) {
                            ObjectTypes type = ObjectTypes.verticalLine;
                            int point = players[i].getBoard().pointOfVerticalLine(pos.getX(), pos.getY());
                            setToScoreBoard(i, type, point, pos);
                        }
                    }
                    // check if exploded position is on the board the point will be summed in point table
                    if (players[i].getBoard().getField()[j][k] != null && players[i].getBoard().getField()[j][k].isExploded()) {
                        ObjectTypes type = players[i].getBoard().getField()[j][k].getObject();
                        int point = players[i].getBoard().getField()[j][k].getPoint();
                        setToScoreBoard(i, type, point, pos);
                        
                    }
                }
            }
        } 

    }
    /**
     * This method is calculating the points of each player and check who is the winner
     * of the game based on the 
     */
    private void showWinner(){
       int point = 0;
       int [] points = new int[players.length];
       List<Winner> winners = new LinkedList<>();
       winners.add(new Winner(0, 0));
       
        for (int i = 0; i < players.length; i++) {
            points[i] = players[i].getBoard().sumPoints();
        }
        
        for (int i = 0; i < points.length; i++) {
           
            if(points[i] > point){
                point = points[i];
                winners.clear();
                winners.add(new Winner(i, point));
            }else{
                if( points[i] == point){
                    if(players[winners.get(0).getWinnerID()].getBoard().getAllCrossed().size() < 
                            players[i].getBoard().getAllCrossed().size()){
                        point = points[i];
                        winners.clear();
                        winners.add(new Winner(i, point));                       
                    }else{
                        if(players[winners.get(0).getWinnerID()].getBoard().getAllCrossed().size() ==
                            players[i].getBoard().getAllCrossed().size()){
                        point = points[i];
                        winners.add(new Winner(i, point));    
                        }
                    }
                }
            }
        }
        
        this.gui.winnerWindow(winners);
    }

}
