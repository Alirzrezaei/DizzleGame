/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

import static java.util.Collections.list;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is the main board of each players. Its constructor is receiving
 * object of LevelReader which is loaded from json file of different levels (1,
 * 2, 3) and it contains all information about the cells values, starting
 * positions of each level, and symbols inside them and their points. The main
 * field is array (9x7) of type Cell object that information of LevelReader
 * object will be initialized into these cell. The array of scores is storing
 * scores for each level, when the player collected a cell with symbol and
 * point. The size of scores array is different for each level. The flagPoint is
 * an array of int to store the array of points from LevelReader object.
 *
 * Beside these attributes, board is doing many important functions. First it
 * loads all information from object of LevelReader to field of cells with
 * related information. It also keeps and adds points that player collected
 * during the playing into array of scores. furthermore upon request, it can
 * return point, symbol and value of the cell to other classes. Moreover, this
 * class class is controlling the status of the cells. If player put dice on
 * cell, its status will be isOccupied, and after a turn finished it will be
 * changed to isCrossed. If somebody else ignited a bomb on cell, the cell for
 * this player's board will be changed to exploded. The inclosed status is also
 * checked here means that player can put dice around laid dices on the board,
 * but if they are inclosed and there is not free cell around them, player can
 * put dice around other crossed cells. The class is also checking if a played
 * position is completing a line which has point that can be used by bot player
 * to play or during collecting points. The validity of positions are also
 * checked here based on the lengths of two dimensional array of cell. All
 * crossed function in this class is checking if all cells of in status of
 * isCrssed that the game will be finished base on it.
 *
 * @author Rezaei
 */
public class Board {

    private LevelReader objectBoard;
    private Cell[][] field;
    private Scores[] scores;
    private int[] flagPoints;

    /**
     * this is the main constructor to make objectBoard for the each player
     *
     * @param objectBoard is the board that is created using json file
     * @param level level number
     */
    Board(LevelReader objectBoard, int level) {
        this.objectBoard = objectBoard;
        field = new Cell[objectBoard.getField().length][objectBoard.getField()[0].length];
        setToField(objectBoard);
        field = rotateBoard();
        initPoints(level);

    }

    /**
     * this method is returning two dimensional array of cell with is the main
     * field of player
     *
     * @return array field of cells
     */
    public Cell[][] getField() {
        return field;
    }

    /**
     * return is an object that is created using json files.
     *
     * @return board of objects which created by json file
     */
    protected LevelReader getObjectBoard() {
        return this.objectBoard;
    }

    /**
     * returns the board of scores that players is storing it
     *
     * @return array of score table
     */
    public Scores[] getPointTable() {
        return scores;
    }

    protected int[] getFlagPoints() {
        return flagPoints;
    }

    /**
     * this method is reading the values of the levelReader object which is
     * created by json file and store all related information into a two
     * dimensional array of cells
     *
     * @param board levelReader object from json file
     */
    private void setToField(LevelReader board) {

        for (int i = 0; i < board.getField().length; i++) {
            for (int j = 0; j < board.getField()[i].length; j++) {
                boolean isCross = false;

                if (board.getField()[i][j] != null && board.getField()[i][j] == 0) {
                    isCross = true;
                    field[i][j] = new Cell(board.getField()[i][j], isCross, ObjectTypes.noType, 0);
                } else {
                    if (board.getField()[i][j] != null) {
                        Position pos = new Position(j, i);
                        ObjectTypes type = isObject(board, pos);
                        int point = getPoint(board, pos, type);

                        field[i][j] = new Cell(board.getField()[i][j], isCross, type, point);
                    }
                }

            }
        }

    }

    /**
     * this method is searching the levelReader object based on the given
     * position to save the related objected into the cell. noType will saved
     * otherwise.
     *
     * @param board levelReader based on Json file
     * @param pos Position of the cell
     * @return type of Object, noType otherwise
     */
    protected ObjectTypes isObject(LevelReader board, Position pos) {
        ObjectTypes type = ObjectTypes.noType;

        if (type == ObjectTypes.noType) {
            type = isJewel(board, pos);
        }

        if (type == ObjectTypes.noType) {
            type = isBomb(board, pos);
        }
        if (type == ObjectTypes.noType) {
            type = isPuzzle(board, pos);
        }
        if (type == ObjectTypes.noType) {
            type = isFlag(board, pos);
        }
        if (type == ObjectTypes.noType) {
            type = isKey(board, pos);
        }
        if (type == ObjectTypes.noType) {
            type = isKeyhole(board, pos);
        }
        if (type == ObjectTypes.noType) {
            type = isRocket(board, pos);
        }
        if (type == ObjectTypes.noType) {
            type = isPlanet(board, pos);
        }
        return type;
    }

    /**
     * this method is checking if there is jewel object in given position
     *
     * @param board levelReader object based on json file
     * @param pos Position of cell
     * @return type of Jewel in in this position, noType otherwise
     */
    private ObjectTypes isJewel(LevelReader board, Position pos) {
        ObjectTypes type = ObjectTypes.noType;
        for (int i = 0; i < board.getJewels().length && type == ObjectTypes.noType; i++) {
            for (int j = 0; j < board.getJewels()[i].getPositions().length && type == ObjectTypes.noType; j++) {
                if (board.getJewels()[i].getPositions()[j].equals(pos)) {
                    type = ObjectTypes.jewel;
                }
            }
        }
        return type;
    }

    /**
     * this method is returning bomb if there any in given position and if there
     * is no bomb, it will return noType
     *
     * @param board levelReader object based on json file
     * @param pos Position of cell
     * @return type of bomb if in given position, noType otherwise
     */
    protected ObjectTypes isBomb(LevelReader board, Position pos) {
        ObjectTypes type = ObjectTypes.noType;
        for (int i = 0; i < board.getBombs().getPositions().length && type == ObjectTypes.noType; i++) {
            if (board.getBombs().getPositions()[i].equals(pos)) {
                type = ObjectTypes.bomb;
            }
        }
        return type;
    }

    /**
     * return puzzle if there is in given position and return noType otherwise
     *
     * @param board levelReader object based on json file
     * @param pos Position of cell
     * @return type of puzzle if in given position, noType otherwise
     */
    private ObjectTypes isPuzzle(LevelReader board, Position pos) {
        ObjectTypes type = ObjectTypes.noType;
        for (int i = 0; i < board.getPuzzles().length && type == ObjectTypes.noType; i++) {
            for (int j = 0; j < board.getPuzzles()[i].getPositions().length && type == ObjectTypes.noType; j++) {
                if (board.getPuzzles()[i].getPositions()[j].equals(pos)) {
                    type = ObjectTypes.puzzle;
                }
            }
        }
        return type;
    }

    /**
     * return key type if there is in given position and return noType otherwise
     *
     * @param board levelReader Object based on json file
     * @param pos Position of cell
     * @return type of key if in given position, noType otherwise
     */
    private ObjectTypes isKey(LevelReader board, Position pos) {
        ObjectTypes type = ObjectTypes.noType;
        for (int i = 0; i < board.getKeys().length && type == ObjectTypes.noType; i++) {
            if (board.getKeys()[i].getPosition().equals(pos)) {

                type = ObjectTypes.key;

            }
        }
        return type;
    }

    /**
     * return keyhole type if there is in given position and return noType
     * otherwise
     *
     * @param board levelReader Object based on json file
     * @param pos Position of cell
     * @return type of keyhole if in given position, noType otherwise
     */
    private ObjectTypes isKeyhole(LevelReader board, Position pos) {
        ObjectTypes type = ObjectTypes.noType;
        for (int i = 0; i < board.getKeys().length && type == ObjectTypes.noType; i++) {
            for (int j = 0; j < board.getKeys()[i].getHoles().length; j++) {
                if (board.getKeys()[i].getHoles()[j].equals(pos)) {

                    type = ObjectTypes.keyhole;

                }
            }
        }
        return type;
    }

    /**
     * return flag type if there is in given position and return noType
     * otherwise
     *
     * @param board levelReader Object based on json file
     * @param pos Position of cell
     * @return type of flag if in given position, noType otherwise
     */
    private ObjectTypes isFlag(LevelReader board, Position pos) {

        ObjectTypes type = ObjectTypes.noType;

        if (board.getFlag() != null && board.getFlag().getPosition().equals(pos)) {
            type = ObjectTypes.flag;
        }

        return type;
    }

    /**
     * return rocket type if there is in given position and return noType
     * otherwise
     *
     * @param board levelReader Object based on json file
     * @param pos Position of cell
     * @return type of rocket if in given position, noType otherwise
     */
    private ObjectTypes isRocket(LevelReader board, Position pos) {

        ObjectTypes type = ObjectTypes.noType;

        if (board.getRocket() != null && board.getRocket().equals(pos)) {
            type = ObjectTypes.rocket;
        }

        return type;
    }

    /**
     * return planet type if there is in given position and return noType
     * otherwise
     *
     * @param board levelReader Object based on json file
     * @param pos Position of cell
     * @return type of planet if in given position, noType otherwise
     */
    private ObjectTypes isPlanet(LevelReader board, Position pos) {
        //assert (objectBoard.getRocket() != null);

        ObjectTypes type = ObjectTypes.noType;

        if (board.getRocket() != null && board.getPlanet().equals(pos)) {
            type = ObjectTypes.planet;
        }

        return type;
    }

    /**
     * this method is checking is if the given pos is valid position on the
     * board
     *
     * @param pos Position of cell
     * @return true if is valid, otherwise false
     */
    protected boolean isValidPos(Position pos) {
        return isValidX(pos.getX()) && isValidY(pos.getY()) && this.field[pos.getX()][pos.getY()] != null;
    }

    /**
     * check if x value of a cell is valid
     *
     * @param x X value of the cell
     * @return true if valid, false otherwise
     */
    private boolean isValidX(int x) {
        return x >= 0 && x < this.field.length;
    }

    /**
     * check if y value of a cell is valid
     *
     * @param y Y value of the cell
     * @return true if valid, false otherwise
     */
    private boolean isValidY(int y) {
        return y >= 0 && y < this.field[0].length;
    }

    /**
     * this method is checking if there is an object and point for the given
     * position.
     *
     * @param board levelReader object based on json file
     * @param pos Position of the cell
     * @param type type of object in the cell
     * @return value of the cell
     */
    private int getPoint(LevelReader board, Position pos, ObjectTypes type) {
        int point = 0;
        if (type == ObjectTypes.puzzle) {
            point = board.getPuzzles()[0].getPoints();
        }

        if (type == ObjectTypes.bomb) {
            point = board.getBombs().getPoints();
        }

        if (type == ObjectTypes.jewel) {
            for (int i = 0; i < board.getJewels().length; i++) {
                for (int j = 0; j < board.getJewels()[i].getPositions().length; j++) {

                    if (board.getJewels()[i].getPositions()[j].equals(pos)) {
                        point = board.getJewels()[i].getPoints();
                    }
                }
            }
        }

        if (type == ObjectTypes.flag) {
            flagPoints = board.getFlag().getPoints();
        }

        return point;
    }

    /**
     * this method is rotating the objectBoard based on what user see on GUI
     *
     * @return baord of cells
     */
    private Cell[][] rotateBoard() {
        Cell[][] temp = new Cell[field[0].length][field.length];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                temp[j][i] = field[i][j];
            }
        }
        return temp;
    }

    /**
     * this method is checking all the baord and if there is occupied cell will
     * return true
     *
     * @return true if a cell is occupied
     */
    protected boolean ifOccupied() {
        boolean occupied = false;

        for (int i = 0; i < field.length && !occupied; i++) {
            for (int j = 0; j < field[0].length && !occupied; j++) {
                if (field[i][j] != null && field[i][j].isOccupied()) {
                    occupied = true;
                }
            }
        }
        return occupied;
    }

    /**
     * this method is checking if occupied cell/cells is/are in closed area and
     * there is not free neighbor for them
     *
     * @return true is inclosed, false otherwise
     */
    protected boolean inClosed() {
        boolean inClosed = true;

        for (int i = 0; i < field.length && inClosed; i++) {
            for (int j = 0; j < field[0].length && inClosed; j++) {
                if (field[i][j] != null && field[i][j].isOccupied()) {
                    Position pos = new Position(i, j);
                    Position[] neighbors = pos.getNeighbors();
                    for (int k = 0; k < neighbors.length && inClosed; k++) {
                        if (isValidPos(neighbors[k]) && !field[neighbors[k].getX()][neighbors[k].getY()].isCrossed()
                                && !field[neighbors[k].getX()][neighbors[k].getY()].isOccupied()) {
                            if (field[neighbors[k].getX()][neighbors[k].getY()].getObject() == ObjectTypes.keyhole) {
                                if (isAllCrossed()) {
                                    inClosed = false;
                                }

                            } else {
                                inClosed = false;
                            }
                        }
                    }

                }
            }
        }

        return inClosed;
    }

    /**
     * this method is checking if the given position is the given list. if not,
     * false value will be returned
     *
     * @param list of Positions
     * @param pos Position
     * @return true if pos in list, false otherwise
     */
    protected boolean contain(List<Position> list, Position pos) {
        boolean inList = false;
        for (int i = 0; i < list.size() && !inList; i++) {
            if (list.get(i).equals(pos)) {
                inList = true;
            }
        }
        return inList;
    }

    /**
     * this method is checking if there is free valid cells based on the
     * available dices that player put a dice on it.
     *
     * @param dices list of available dices
     * @return List valid neighbor to highlight and play
     */
    protected List<Position> highlightList(List<Integer> dices) {
        List<Position> temp = new LinkedList<>();
        if (ifOccupied() && !inClosed()) {
            temp = neighborWithOccupied(dices);
        } else {
            temp = neighborWithCrossed(dices);
        }
        return temp;
    }

    /**
     * this method is checking if there is free dices neighboring with occupied
     * cells and its value is in the dices that player can place dice on it
     *
     * @param dices list of available dices
     * @return list of positions
     */
    private List<Position> neighborWithOccupied(List<Integer> dices) {
        List<Position> temp = new LinkedList<>();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null && field[i][j].isOccupied()) {
                    Position pos = new Position(i, j);
                    Position[] neighbors = pos.getNeighbors();
                    for (int k = 0; k < neighbors.length; k++) {
                        if (isValidPos(neighbors[k]) && !field[neighbors[k].getX()][neighbors[k].getY()].isCrossed()
                                && !field[neighbors[k].getX()][neighbors[k].getY()].isOccupied()
                                && !field[neighbors[k].getX()][neighbors[k].getY()].isExploded()
                                && dices.contains(field[neighbors[k].getX()][neighbors[k].getY()].getValue())) {

                            if (field[neighbors[k].getX()][neighbors[k].getY()].getObject() == ObjectTypes.keyhole) {
                                if (isKeyCrossed(neighbors[k])) {
                                    temp.add(neighbors[k]);
                                }
                            } else {
                                temp.add(neighbors[k]);
                            }
                        }
                    }
                }
            }
        }
        return temp;
    }

    /**
     * this method is checking if there is free dices neighboring with crossed
     * cells and its value is in the dices that player can place dice on it
     *
     * @param dices list of available dices
     * @return list of positions
     */
    private List<Position> neighborWithCrossed(List<Integer> dices) {
        List<Position> temp = new LinkedList<>();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null && field[i][j].isCrossed()) {
                    Position pos = new Position(i, j);
                    Position[] neighbors = pos.getNeighbors();
                    for (int k = 0; k < neighbors.length; k++) {
                        if (isValidPos(neighbors[k]) && !field[neighbors[k].getX()][neighbors[k].getY()].isCrossed()
                                && !field[neighbors[k].getX()][neighbors[k].getY()].isOccupied()
                                && !field[neighbors[k].getX()][neighbors[k].getY()].isExploded()
                                && dices.contains(field[neighbors[k].getX()][neighbors[k].getY()].getValue())) {
                            if (field[neighbors[k].getX()][neighbors[k].getY()].getObject() == ObjectTypes.keyhole) {
                                if (isKeyCrossed(neighbors[k])) {
                                    temp.add(neighbors[k]);
                                }
                            } else {
                                temp.add(neighbors[k]);
                            }

                        }
                    }

                }
            }
        }
        return temp;
    }

    /**
     * this method is initiallizing the score board based on the given level
     * number
     *
     * @param level int value, level of the game
     */
    private void initPoints(int level) {
        if (level == 1) {
            fstLevelPointTable();
        } else if (level == 2) {
            sndLevelPointTable();
        } else {
            thrdLevelPointTable();
        }
    }

    /**
     * initialize score board for level one
     */
    private void fstLevelPointTable() {
        scores = new Scores[5];
        scores[0] = new Scores(0, ObjectTypes.jewel);
        scores[1] = new Scores(0, ObjectTypes.horizontalLine);
        scores[2] = new Scores(0, ObjectTypes.verticalLine);
        scores[3] = new Scores(0, ObjectTypes.puzzle);
        scores[4] = new Scores(0, ObjectTypes.bomb);

    }

    /**
     * update score table first level with received point and type.
     *
     * @param point int point of a crossed cell
     * @param type type of a crossed cell
     * @return int index of the table to update gui
     */
    protected int setFstLevelPoints(int point, ObjectTypes type) {
        int idx = 0;
        if (type == ObjectTypes.jewel) {
            idx = 0;
            scores[0].setPoint(scores[0].getPoint() + point);
        } else if (type == ObjectTypes.horizontalLine) {
            idx = 1;
            scores[1].setPoint(scores[1].getPoint() + point);
        } else if (type == ObjectTypes.verticalLine) {
            idx = 2;
            scores[2].setPoint(scores[2].getPoint() + point);
        } else if (type == ObjectTypes.puzzle) {
            idx = 3;
            scores[3].setPoint(scores[3].getPoint() + point);
        } else {
            idx = 4;
            scores[4].setPoint(scores[4].getPoint() + point);
        }

        return idx;
    }

    /**
     * update score table second level level with received point and type.
     *
     * @param point int point of a crossed cell
     * @param type type of a crossed cell
     * @param pos position of a crossed cell
     * @return int index of the table to update gui
     */
    protected int setSndLevelPoints(int point, ObjectTypes type, Position pos) {
        int idx = 0;
        if (type == ObjectTypes.jewel) {
            if (point == 3) {
                idx = 0;
                scores[0].setPoint(scores[0].getPoint() + point);
            } else {
                idx = 1;
                scores[1].setPoint(scores[1].getPoint() + point);
            }
        } else if (type == ObjectTypes.horizontalLine) {
            idx = 2;
            scores[2].setPoint(scores[2].getPoint() + point);
        } else if (type == ObjectTypes.verticalLine) {
            idx = 3;
            scores[3].setPoint(scores[3].getPoint() + point);
        } else if (type == ObjectTypes.puzzle) {
            if (pos.equals(objectBoard.getPuzzles()[0].getPositions()[0])
                    || pos.equals(objectBoard.getPuzzles()[0].getPositions()[1])) {
                idx = 4;
                scores[4].setPoint(scores[4].getPoint() + point);
            } else {
                idx = 5;
                scores[5].setPoint(scores[5].getPoint() + point);
            }

        } else {
            idx = 6;
            scores[6].setPoint(scores[6].getPoint() + point);
        }

        return idx;
    }

    /**
     * initialize score board for level two
     */
    private void sndLevelPointTable() {
        scores = new Scores[7];
        scores[0] = new Scores(0, ObjectTypes.jewel);
        scores[1] = new Scores(0, ObjectTypes.jewel);
        scores[2] = new Scores(0, ObjectTypes.horizontalLine);
        scores[3] = new Scores(0, ObjectTypes.verticalLine);
        scores[4] = new Scores(0, ObjectTypes.puzzle);
        scores[5] = new Scores(0, ObjectTypes.puzzle);
        scores[6] = new Scores(0, ObjectTypes.bomb);
    }

    /**
     * update score table third level level with received point and type.
     *
     * @param point int point of a crossed cell
     * @param type type of a crossed cell
     * @return int index of the table to update gui
     */
    protected int setThrdLevelPoints(int point, ObjectTypes type) {
        int idx = 0;
        if (type == ObjectTypes.jewel) {
            if (point == 1) {
                idx = 0;
                scores[0].setPoint(scores[0].getPoint() + point);
            } else if (point == 2) {
                idx = 1;
                scores[1].setPoint(scores[1].getPoint() + point);
            } else {
                idx = 2;
                scores[2].setPoint(scores[2].getPoint() + point);
            }
        } else if (type == ObjectTypes.horizontalLine) {
            idx = 3;
            scores[3].setPoint(scores[3].getPoint() + point);
        } else if (type == ObjectTypes.verticalLine) {
            idx = 4;
            scores[4].setPoint(scores[4].getPoint() + point);
        } else if (type == ObjectTypes.puzzle) {

            idx = 5;
            scores[5].setPoint(scores[5].getPoint() + point);

        } else if (type == ObjectTypes.flag) {

            idx = 6;
            scores[6].setPoint(scores[6].getPoint() + point);

        } else {
            idx = 7;
            scores[7].setPoint(scores[7].getPoint() + point);
        }

        return idx;
    }

    /**
     * initialize score board for level three
     */
    private void thrdLevelPointTable() {

        scores = new Scores[8];
        scores[0] = new Scores(0, ObjectTypes.jewel);
        scores[1] = new Scores(0, ObjectTypes.jewel);
        scores[2] = new Scores(0, ObjectTypes.jewel);
        scores[3] = new Scores(0, ObjectTypes.horizontalLine);
        scores[4] = new Scores(0, ObjectTypes.verticalLine);
        scores[5] = new Scores(0, ObjectTypes.puzzle);
        scores[6] = new Scores(0, ObjectTypes.flag);
        scores[7] = new Scores(0, ObjectTypes.bomb);
    }

    /**
     * check if the given position is completing a horizontal line. If it is
     * true, it will return the point of the line, zero otherwise
     *
     * @param pos Position of cell
     * @return int, point of the line
     */
    protected int isHorizontalLine(Position pos) {
        int point = 0;
        int tmp = 0;
        boolean isHorizontal = true;
        for (int k = 0; k < objectBoard.getHorizontalLines().length && isHorizontal; k++) {
            if (pos.getY() == objectBoard.getHorizontalLines()[k].getPositions()[0].getY()) {
                tmp = objectBoard.getHorizontalLines()[k].getPoints();
                for (int j = 0; j < objectBoard.getHorizontalLines()[k].getPositions()[1].getX() && isHorizontal; j++) {
                    Position temp = new Position(j, pos.getY());
                    if (field[j][pos.getY()] != null && !field[j][pos.getY()].isCrossed()
                            && !field[j][pos.getY()].isOccupied() && !pos.equals(temp)) {
                        isHorizontal = false;
                    }
                }

            }
        }
        if (isHorizontal) {
            point = tmp;
        }
        return point;
    }

    /**
     * check if the given position is completing a vertical line. If it is true,
     * it will return the point of the line, zero otherwise
     *
     * @param pos Position of cell
     * @return int, point of the line
     */
    protected int isVerticalLine(Position pos) {
        int point = 0;
        int tmp = 0;
        boolean isVertical = true;
        for (int k = 0; k < objectBoard.getVerticalLines().length && isVertical; k++) {
            if (pos.getX() == objectBoard.getVerticalLines()[k].getPositions()[0].getX()) {
                tmp = objectBoard.getVerticalLines()[k].getPoints();
                for (int j = 0; j <= objectBoard.getVerticalLines()[k].getPositions()[1].getY() && isVertical; j++) {
                    Position temp = new Position(pos.getX(), j);
                    if (field[pos.getX()][j] != null && !field[pos.getX()][j].isCrossed()
                            && !field[pos.getX()][j].isOccupied() && !pos.equals(temp)) {
                        isVertical = false;

                    }
                }

            }
        }
        if (isVertical) {
            point = tmp;
        }

        return point;
    }

    protected boolean isBothLines(Position pos) {

        return isHorizontalLine(pos) > 0 && isVerticalLine(pos) > 0;
    }

    /**
     * check if all the cells of the field is crossed, otherwise it is returning
     * false
     *
     * @return true if all cells crossed, false otherwise
     */
    protected boolean isAllCrossed() {
        boolean allCrossed = true;
        for (int i = 0; i < field.length && allCrossed; i++) {
            for (int j = 0; j < field[i].length && allCrossed; j++) {
                if (field[i][j] != null && !field[i][j].isCrossed()) {
                    allCrossed = false;
                }
            }
        }
        return allCrossed;
    }

    /**
     * this method is checking if the scored horizonal line is completed and
     * then return the point of that line. If not completed, then return 0
     *
     * @param xAxis int xAxis
     * @param yAxis int, Y axis
     * @return int, point of the completed line
     */
    protected int pointOfHorizontalLine(int xAxis, int yAxis) {
        int point = 0;

        for (int k = 0; k < objectBoard.getHorizontalLines().length && point == 0; k++) {
            if (yAxis == objectBoard.getHorizontalLines()[k].getPositions()[0].getY()
                    && (xAxis >= objectBoard.getHorizontalLines()[k].getPositions()[0].getX()
                    && xAxis <= objectBoard.getHorizontalLines()[k].getPositions()[1].getX())) {
                boolean isHorizontal = true;
                for (int j = objectBoard.getHorizontalLines()[k].getPositions()[0].getX(); j <= objectBoard.getHorizontalLines()[k].getPositions()[1].getX() && isHorizontal; j++) {
                    Position temp = new Position(j, yAxis);
                    if (field[j][yAxis] != null && !field[j][yAxis].isCrossed()) {
                        isHorizontal = false;
                    }
                }
                if (isHorizontal) {
                    point = objectBoard.getHorizontalLines()[k].getPoints();
                }
            }
        }

        return point;
    }

    /**
     * this method is checking if the scored vertical line is completed and then
     * return the point of that line. If not completed, then return 0
     *
     * @param xAxis int X axis
     * @param yAxis int, Y axis
     * @return int, point of the completed line
     */
    protected int pointOfVerticalLine(int xAxis, int yAxis) {
        int point = 0;

        for (int k = 0; k < objectBoard.getVerticalLines().length && point == 0; k++) {
            if (xAxis == objectBoard.getVerticalLines()[k].getPositions()[0].getX()
                    && (yAxis >= objectBoard.getVerticalLines()[k].getPositions()[0].getY()
                    && yAxis <= objectBoard.getVerticalLines()[k].getPositions()[1].getY())) {
                boolean isVertical = true;
                for (int j = 0; j <= objectBoard.getVerticalLines()[k].getPositions()[1].getY() && isVertical; j++) {
                    Position temp = new Position(xAxis, j);
                    if (field[xAxis][j] != null && !field[xAxis][j].isCrossed()) {
                        isVertical = false;
                    }
                }
                if (isVertical) {
                    point = objectBoard.getVerticalLines()[k].getPoints();
                }
            }
        }

        return point;
    }

    /**
     * this method is checking puzzle type of given position and check if all
     * the positions of the this puzzle type is crossed.
     *
     * @param pos Position of puzzle
     * @return true if all positions of a puzzle type crossed, false otherwise
     */
    protected boolean ifAllPuzzileCrossed(Position pos) {
        boolean crossed = true;

        for (int i = 0; i < this.objectBoard.getPuzzles().length; i++) {
            for (int j = 0; j < this.objectBoard.getPuzzles()[i].getPositions().length; j++) {
                if (this.objectBoard.getPuzzles()[i].getPositions()[j].equals(pos)) {
                    crossed = allPuzzlePositionsCrossed(this.objectBoard.getPuzzles()[i].getPositions());
                }
            }

        }

        return crossed;
    }

    /**
     * this method is checking if all positions of the puzzle type are crossed.
     *
     * @param positions list of positions of puzzles
     * @return false if a position is not crossed, true otherwise
     */
    private boolean allPuzzlePositionsCrossed(Position[] positions) {
        boolean crossed = true;

        for (int i = 0; i < positions.length && crossed; i++) {
            if (!field[positions[i].getX()][positions[i].getY()].isCrossed()) {
                crossed = false;
            }
        }

        return crossed;
    }

    /**
     * if the given position is a keyhole, it checks that corresponding key
     * position should be crossed
     *
     * @param pos position of keyhole
     * @return true if corresponding key pos is crossed, false otherwise
     */
    private boolean isKeyCrossed(Position pos) {
        for (int i = 0; i < objectBoard.getKeys().length; i++) {
            for (int j = 0; j < objectBoard.getKeys()[i].getHoles().length; j++) {
                if (objectBoard.getKeys()[i].getHoles()[j].equals(pos)
                        && field[objectBoard.getKeys()[i].getPosition().getX()][objectBoard.getKeys()[i].getPosition().getY()].isCrossed()) {
                    return true;

                }
            }

        }
        return false;
    }

    /**
     * this method is setting the cross sign on the planet cell and return its
     * position.
     *
     * @return Position of the planet symbol
     */
    protected Position setCrossOnPlanet() {
        field[objectBoard.getPlanet().getX()][objectBoard.getPlanet().getY()].setCrossed(true);

        return objectBoard.getPlanet();
    }

    /**
     * this method is adding all crossed cells of the board to a list and return
     * it
     *
     * @return List of Position of the crossed
     */
    protected List<Position> getAllCrossed() {
        List<Position> temp = new LinkedList<>();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null && field[i][j].isCrossed()) {
                    temp.add(new Position(i, j));
                }
            }
        }

        return temp;
    }

    /**
     * this method is adding all occupied cells of the board to a list and
     * return it
     *
     * @return List of Positions of the occupied
     */
    protected List<Position> getAllOccupied() {
        List<Position> temp = new LinkedList<>();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null && field[i][j].isOccupied()) {
                    temp.add(new Position(i, j));
                }
            }
        }

        return temp;
    }

    /**
     * this method is adding all exploded cells of the board to a list and
     * return it
     *
     * @return List of the Positions of the exploded
     */
    protected List<Position> getAllExploded() {
        List<Position> temp = new LinkedList<>();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null && field[i][j].isExploded()) {
                    temp.add(new Position(i, j));
                }
            }
        }
        return temp;
    }

    /**
     * This method is checking if the given position is last position of the
     * horizontal line symbol in LevelReader object.
     *
     * @param pos Position to be compared
     * @return true if pos is last position of line, false otherwise
     */
    protected boolean horizonalLineLastPos(Position pos) {
        boolean lastPos = false;

        for (int i = 0; i < this.objectBoard.getHorizontalLines().length && !lastPos; i++) {
            if (pos.equals(this.objectBoard.getHorizontalLines()[i].getPositions()[1])) {
                lastPos = true;
            }
        }

        return lastPos;
    }

    /**
     * This method is checking if the given position is last position of the
     * vertical line symbol in LeverReader object
     *
     * @param pos Position to be compared
     * @return true if pos is last position of line, false otherwise
     */
    protected boolean verticalLineLastPos(Position pos) {
        boolean lastPos = false;

        for (int i = 0; i < this.objectBoard.getVerticalLines().length && !lastPos; i++) {
            if (pos.equals(this.objectBoard.getVerticalLines()[i].getPositions()[1])) {
                lastPos = true;
            }
        }
        return lastPos;
    }

    /**
     * check if the given position is the first position of the puzzle object in
     * the LevelReader object
     *
     * @param pos Position to be compared
     * @return true if pos is first in puzzle positions, false otherwise
     */
    protected boolean puzzleFirstPos(Position pos) {
        boolean lastPos = false;

        for (int i = 0; i < this.objectBoard.getPuzzles().length && !lastPos; i++) {
            if (pos.equals(this.objectBoard.getPuzzles()[i].getPositions()[0])) {
                lastPos = true;
            }
        }

        return lastPos;
    }

    protected int sumPoints() {
        int sum = 0;
        for (int i = 0; i < scores.length; i++) {
            if (i == scores.length - 1) {
                sum = sum - scores[i].getPoint();
            } else {
                sum = sum + scores[i].getPoint();
            }
        }
        return sum;
    }
}
