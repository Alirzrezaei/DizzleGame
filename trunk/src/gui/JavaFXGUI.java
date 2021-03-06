/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package gui;


import java.util.LinkedList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import logic.GUIConnector;
import logic.LevelReader;
import logic.ObjectTypes;
import logic.Position;
import logic.Winner;
import logic.boardObjects;

/**
 * This call is to create and update of the gui of game. The components of the gui of the game 
 * are passed from FXMLDocumentController by calling this class constructor. The components are then used to 
 * update the elements of the game. 
 * At first using the LevelReader object, all the data pictures of dices and symbols and lines are put in grid panes 
 * of each player. Also the board table is initialized with appropriate photos based on the level numbers. 
 * The class also is updating the components of the gui based on the information which are sent from from logic part. 
 * When a player put dice on the board its picture should be shown and it should be deleted from dices. Roll again and 
 * drop out buttons are enabled when human player can not put a dice on the board. Also, round and turn labels are 
 * being updated at each turn/round. Errors during the game and score at the end of the game are being shown on a pop windows. 
 * 
 * @author Alireza
 */
public class JavaFXGUI implements GUIConnector {

    private GridPane grdPnHuman;
    private GridPane grdPnBot1;
    private GridPane grdPnBot2;
    private GridPane grdPnBot3;
    private GridPane grdPnDices;
    private GridPane grdPnPoints;
    
    private Pane pnHuman;
    private Pane pnBot1;
    private Pane pnBot2;
    private Pane pnBot3;

    private ImageView[][] imgVwHuman;
    private ImageView[][] imgVwBot1;
    private ImageView[][] imgVwBot2;
    private ImageView[][] imgVwBot3;

    private Group[][] grpHuman;
    private Group[][] grpBot1;
    private Group[][] grpBot2;
    private Group[][] grpBot3;

    private Label lblRound;
    private Label lblTurn;

    private Button btnRollAgain;
    private Button btnDropOut;
    
    private Label[] lblPoints;
    private Label[] lblPoint;
    
    private TextArea txtAreaLogs;
    
    private Label lblDiceBack;

    private final String path = "gui/img/";
    private final String ext = ".png";
    
    private ImageView[] imgVwDropOut;
    
    private List<String> logs = new LinkedList<>();
    
    private int cell2;
    private int grdHeight;
    
    private Pane [][] hPane; 
    private Pane [][] vPane;
    private String tmpPoint;
    
    /**
     * This method is main constructor to make the gui of the game based on information (components) 
     * which are passed from FXMLDocumentController class. 
     * @param grdPnHuman GridPane of human
     * @param grdPnBot1 GridPane of Bot1
     * @param grdPnBot2 GridPane of Bot2
     * @param grdPnBot3 GridPane of Bot3
     * @param grdPnDices GridPane of dices
     * @param pnRound Pane of current round
     * @param pnTurn Pane of current turn
     * @param btnRollAgain Button roll again
     * @param btnDropOut Button Drop out
     * @param grpHuman array of Groups of Human
     * @param grpBot1 array of Groups of Bot1
     * @param grpBot2 array of Groups of Bot2
     * @param grpBot3 array of Groups of Bot3
     * @param pnHuman Pane of Human
     * @param pnBot1 Pane of Bot1
     * @param pnBot2 Pane of Bot2
     * @param pnBot3 Pane of Bot3
     * @param grdPnPoints GridPane of pointTable
     */
    public JavaFXGUI(GridPane grdPnHuman, GridPane grdPnBot1, GridPane grdPnBot2,
            GridPane grdPnBot3, GridPane grdPnDices, Label pnRound, Label pnTurn,
            Button btnRollAgain, Button btnDropOut, Group[][] grpHuman, Group[][] grpBot1,
            Group[][] grpBot2, Group[][] grpBot3, Pane pnHuman, Pane pnBot1, Pane pnBot2, Pane pnBot3, 
            GridPane grdPnPoints, TextArea txtAreaLogs, Label lblDiceBack) {
        this.grdPnHuman = grdPnHuman;
        this.grdPnBot1 = grdPnBot1;
        this.grdPnBot2 = grdPnBot2;
        this.grdPnBot3 = grdPnBot3;
        this.grdPnPoints = grdPnPoints;
        
        this.grpHuman = grpHuman;
        this.grpBot1 = grpBot1;
        this.grpBot2 = grpBot2;
        this.grpBot3 = grpBot3;
        
        this.pnHuman = pnHuman;
        this.pnBot1 = pnBot1;
        this.pnBot2 = pnBot2;
        this.pnBot3 = pnBot3;
        
        this.grdPnDices = grdPnDices;
        this.lblRound = pnRound;
        this.lblTurn = pnTurn;
        this.btnDropOut = btnDropOut;
        this.btnRollAgain = btnRollAgain;
        
        
        imgVwDropOut = new ImageView[4];
        this.imgVwDropOut[0] = new ImageView(path+"pen"+ext);
        this.imgVwDropOut[1] = new ImageView(path+"pen"+ext);
        this.imgVwDropOut[2] = new ImageView(path+"pen"+ext);
        this.imgVwDropOut[3] = new ImageView(path+"pen"+ext);
        
        this.txtAreaLogs = txtAreaLogs;
        this.lblDiceBack = lblDiceBack;
        
        hPane= new Pane[4][]; 
        vPane = new Pane[4][];
    }
    /**
     * this method is called from game during player creation to set all images onto the board of players
     * based on id of player and level number which are provided as parameters. 
     * @param levelObj object of loaded level
     * @param player int id of player
     * @param level int level number
     */
    @Override
    public void addImgsToAllGridPanes(LevelReader levelObj, int player, int level) {
        
        for (int i = 0; i < player; i++) {
           
            hPane[i] = new Pane[levelObj.getHorizontalLines().length];
            vPane[i] = new Pane[levelObj.getVerticalLines().length];
            if (i == 0) {
                addImgToEachGridPane(grpHuman, levelObj, grdPnHuman, imgVwHuman, level);
                addLines(levelObj, i, level);
            } else if (i == 1) {
                addImgToEachGridPane(grpBot1, levelObj, grdPnBot1, imgVwBot1, level);
                addLines(levelObj, i, level);
            } else if (i == 2) {
                addImgToEachGridPane(grpBot2, levelObj, grdPnBot2, imgVwBot2, level);
                addLines(levelObj, i, level);
            } else if (i == 3) {
                addImgToEachGridPane(grpBot3, levelObj, grdPnBot3, imgVwBot3, level);
                addLines(levelObj, i, level);
            }
        }
        
        ChangeListener<Number> cellListner = (observable, oldValue, newValue) -> {
            

                horizontalLinesPosition(grdPnHuman, levelObj);
                verticalLinesPosition(grdPnHuman, levelObj);
                      
        };
        pnHuman.widthProperty().addListener(cellListner);
        
    }
    /**
     * this method is adding the appropriate dice value and symbols onto the the board of players based on the 
     * given level and values of the level objected which is loaded from json files. 
     * the method adds first image/images into the cell of a group and then adds the cell of the group in a cell of the grid pane. 
     * 
     * 
     * @param grp array of group object to add more pictures
     * @param levelObj object of loaded level
     * @param grd grid pane of player
     * @param imgVw array of image view for images
     * @param level int level number
     */
    private void addImgToEachGridPane(Group[][] grp, LevelReader levelObj, GridPane grd, ImageView[][] imgVw, int level) {
        
        
        
        int col = grd.getColumnConstraints().size();
        int row = grd.getRowConstraints().size();

        imgVw = new ImageView[col][row];
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (levelObj.getField()[j][i] != null) {
                    //get the corrent image of the board

                    Image img = new Image(path + getImage(levelObj.getField()[j][i]) + ext);

                    //assign image to image view
                    imgVw[i][j] = new ImageView(img);

                    grp[i][j].setStyle("-fx-background-color:lightgray;");
                    grp[i][j].getChildren().add(imgVw[i][j]);

                    //grd.setBackground(Background.EMPTY);
                    //imgVw[i][j].setBlendMode(BlendMode.MULTIPLY);
                    ImageView imgVwSymbol;
                    if (getBoardObjects(levelObj, i, j) != null) {
                        //Image imgSymbol = new Image(path+getJewelsSymbol(levelObj, i, j)+ext);
                        Image imgSymbol = new Image(path + getBoardObjects(levelObj, i, j) + ext);
                        imgVwSymbol = new ImageView(imgSymbol);
                        imgVwSymbol.opacityProperty().set(0.4);

                    } else {
                        imgVwSymbol = new ImageView();
                    }

                    //Group g = new Group(imgVwSymbol, imgVw[i][j]);
                    grp[i][j].getChildren().add(imgVwSymbol);

                    //assign image view to corresponding gridpane's cell
                    //grd.add(g, i, j);
                    //the image shall resize when the cell resizes
                    imgVw[i][j].fitWidthProperty().bind(grd.widthProperty().divide(col).subtract(grd.getHgap()));
                    imgVw[i][j].fitHeightProperty().bind(grd.heightProperty().divide(row).subtract(grd.getVgap()));

                    imgVwSymbol.fitWidthProperty().bind(grd.widthProperty().divide(col).subtract(grd.getHgap()));
                    imgVwSymbol.fitHeightProperty().bind(grd.heightProperty().divide(row).subtract(grd.getVgap()));
                } else {
                    Pane pn = new Pane();
                    if (level == 1) {
                        pn.setStyle("-fx-background-color:lightblue;");
                    } else if (level == 2) {
                        pn.setStyle("-fx-background-color:Thistle;");
                    } else {
                        pn.setStyle("-fx-background-color:mediumaquamarine;");
                    }
                    grp[i][j].getChildren().add(pn);
                    pn.prefWidthProperty().bind(grd.widthProperty().divide(col).subtract(grd.getHgap()));
                    pn.prefHeightProperty().bind(grd.heightProperty().divide(row).subtract(grd.getVgap()));
                }
            }
        }
        
    }

    /**
     * this method is geting right image based on the given integer number
     *
     * @param img int value on the board
     * @return String the corrent image
     */
    private String getImage(int img) {
        String temp;

        if (img == 0) {
            temp = "crossed";
        } else if (img == 1) {
            temp = "bOne";
        } else if (img == 2) {
            temp = "bTwo";
        } else if (img == 3) {
            temp = "bThree";
        } else if (img == 4) {
            temp = "bFour";
        } else if (img == 5) {
            temp = "bFive";
        } else if (img == 6) {
            temp = "bSix";
        } else {
            temp = "planet";
        }

        return temp;
    }

    /**
     * this method is setting the image view to the grid pane.
     *
     * @param imgVws Image of setting on grid pane
     * @param grd Grid pane have the image view
     */
    private void setImgVwToGridPane(ImageView[][] imgVws, GridPane grd) {
        int col = grd.getColumnConstraints().size();
        int row = grd.getRowConstraints().size();

        imgVws = new ImageView[col][row];

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                imgVws[i][j] = new ImageView();

                grd.add(imgVws[i][j], i, j);

                imgVws[i][j].fitWidthProperty().bind(grd.widthProperty().divide(col).subtract(grd.getHgap()));
                imgVws[i][j].fitHeightProperty().bind(grd.heightProperty().divide(row).subtract(grd.getVgap()));
            }
        }

    }
    /**
     * this method is receiving a list of integers as parameter and set the correct dices into 
     * the grid pane which is to show the dices
     * @param list integer
     */
    @Override
    public void setDicesOnGridPane(List<Integer> list) {
        grdPnDices.getChildren().clear();
        int col = this.grdPnDices.getColumnConstraints().size();
        int row = this.grdPnDices.getRowConstraints().size();
        for (int i = 0; i < list.size(); i++) {
            Image img = new Image(path + getDiceImage(list.get(i)) + ext);
            ImageView imgVw = new ImageView(img);
            grdPnDices.add(imgVw, 0, i);

            imgVw.fitWidthProperty().bind(grdPnDices.widthProperty().divide(col).subtract(grdPnDices.getHgap()));
            imgVw.fitHeightProperty().bind(grdPnDices.heightProperty().divide(row).subtract(grdPnDices.getVgap()));
        }
    }
    /**
     * this method is returning the correct Dice name based on the int value which is given 
     * as a parameter and it will be used in the path of image
     * @param number int value of dice
     * @return String name of dice
     */
    private String getDiceImage(int number) {
        String temp;

        if (number == 1) {
            temp = "dOne";
        } else if (number == 2) {
            temp = "dTwo";
        } else if (number == 3) {
            temp = "dThree";
        } else if (number == 4) {
            temp = "dFour";
        } else if (number == 5) {
            temp = "dFive";
        } else {
            temp = "dSix";
        }

        return temp;
    }
    /**
     * this method is checking given x and y coordinates of the board with x and y 
     * coordinates of the jewel position and return the name of it to use in image path. 
     * It returns null if there is no jewel on given object or positions of not the same. 
     * @param obj object of loaded level
     * @param x x coordinate on the board
     * @param y y coordinate on the board
     * @return String name of jewel symbol or null if no match between positions
     */
    private String getJewelsSymbol(LevelReader obj, int x, int y) {
        String s = null;

        for (boardObjects jewel : obj.getJewels()) {
            for (Position pos : jewel.getPositions()) {
                if (pos.getX() == x && pos.getY() == y) {
                    s = getJewel(jewel.getPoints());
                }
            }
        }

        return s;
    }
    /**
     * this method is checking the given point and returns the name of jewel based on it.
     * 1 -> jewelBlue
     * 2 -> jewelYellow
     * 3 -> jewelRed
     * @param point int point of jewel
     * @return String name of jewel
     */
    private String getJewel(int point) {
        String s;
        if (point == 1) {
            s = "jewelBlue";
        } else if (point == 2) {
            s = "jewelYellow";
        } else {
            s = "jewelRed";
        }
        return s;
    }
    /**
     * this method is checking given x and y coordinates of the board with x and y 
     * coordinates of the bomb position and return the name of it to use in image path. 
     * It returns null if there is no bomb on given object or positions of not the same. 
     * @param obj object of loaded level
     * @param x x coordinate on the board
     * @param y y coordinate on the board
     * @return String name of bomb symbol or null if no match between positions
     */
    private String getBomb(LevelReader obj, int x, int y) {
        String s = null;

        for (Position pos : obj.getBombs().getPositions()) {
            if (pos.getX() == x && pos.getY() == y) {
                s = "bomb";
            }
        }

        return s;
    }
    /**
     * this method is checking the given X and Y coordinates with the X and Y values of the position in 
     * each inner object of the given object and if these coordinates are the same. it returns the 
     * name of the symbol to be used in image path. 
     * @param obj loaded object from json file
     * @param x int X coordinate on the board
     * @param y int Y coordinate on the board
     * @return name of symbol if conditions are true, null otherwise
     */
    private String getBoardObjects(LevelReader obj, int x, int y) {
        String s;

        s = getBomb(obj, x, y);

        if (s == null) {
            s = getJewelsSymbol(obj, x, y);
        }
        if (s == null) {
            s = getPuzzleSymbols(obj, x, y);
        }
        if (s == null) {
            s = getKeysSymbols(obj, x, y);
        }
        if (s == null) {
            s = getKeyholesSymbols(obj, x, y);
        }
        if (s == null) {
            s = getFlagSymbol(obj, x, y);
        }
        if (s == null) {
            s = getRocketSymbol(obj, x, y);
        }

        return s;
    }
    /**
     * This method is checking the given X and Y coordinates with the X and Y values of the 
     * poistion of puzzles in the given object and return the name of puzzle if the conditions are 
     * true. otherwise it returns null. 
     * @param obj object of loaded board
     * @param x int X coordinate on board
     * @param y int Y coordinate on board
     * @return String name of puzzle or null if does not match
     */
    private String getPuzzleSymbols(LevelReader obj, int x, int y) {
        String s = null;

        for (int i = 0; i < obj.getPuzzles().length; i++) {
            for (int j = 0; j < obj.getPuzzles()[i].getPositions().length; j++) {
                if (obj.getPuzzles()[i].getPositions()[j].getX() == x
                        && obj.getPuzzles()[i].getPositions()[j].getY() == y) {
                    s = getPuzzle(obj.getPuzzles().length, i);
                }
            }
        }

        return s;
    }
    /**
     * this method is checking which puzzle should be chosen based on the given length of array
     * of puzzles and index of the puzzle in the array. if the array size is 1, the only puzzle is puzzleBlue. 
     * and if the length is more than 1, the first puzzle is puzzleGreen and second one is puzzleBlue. 
     * @param length int length of puzzle array
     * @param puzzle int idx of puzzle in array
     * @return String name of puzzle
     */
    private String getPuzzle(int length, int puzzle) {
        String s;

        if (length == 1) {
            s = "puzzleBlue";
        } else {
            if (puzzle == 0) {
                s = "puzzleGreen";
            } else {
                s = "puzzleBlue";
            }
        }
        return s;
    }
     /**
     * this method is checking given x and y coordinates of the board with x and y 
     * coordinates of the key position and return the name of it to use in image path. 
     * It returns null if there is no key on given object or positions of not the same. 
     * @param obj object of loaded level
     * @param x x coordinate on the board
     * @param y y coordinate on the board
     * @return String name of key symbol or null if no match between positions
     */
    private String getKeysSymbols(LevelReader obj, int x, int y) {
        String s = null;

        for (int i = 0; i < obj.getKeys().length; i++) {
            if (obj.getKeys()[i].getPosition().getX() == x && obj.getKeys()[i].getPosition().getY() == y) {
                s = getKey(i);
            }
        }

        return s;
    }
    /**
     * this method the index of key and returns appropriate key name based on the index of 
     * key. 0 for keyYellow and 1 for keyBlue
     * @param key int index of key
     * @return String name of key
     */
    private String getKey(int key) {
        String s;
        if (key == 0) {
            s = "keyYellow";
        } else {
            s = "keyBlue";
        }
        return s;
    }
     /**
     * this method is checking given x and y coordinates of the board with x and y 
     * coordinates of the keyhole position and return the name of it to use in image path. 
     * It returns null if there is no keyhole on given object or positions of not the same. 
     * @param obj object of loaded level
     * @param x x coordinate on the board
     * @param y y coordinate on the board
     * @return String name of keyhole symbol or null if no match between positions
     */
    private String getKeyholesSymbols(LevelReader obj, int x, int y) {
        String s = null;

        for (int i = 0; i < obj.getKeys().length; i++) {
            for (int j = 0; j < obj.getKeys()[i].getHoles().length; j++) {
                if (obj.getKeys()[i].getHoles()[j].getX() == x && obj.getKeys()[i].getHoles()[j].getY() == y) {
                    s = getKeyHole(i);
                }
            }

        }
        return s;
    }
    /**
     * this method the index of key and returns appropriate keyhole based on the index of 
     * key. 0 for keyholeYellow and 1 for keyholeBlue
     * @param key int index of key
     * @return String name of keyhole
     */
    private String getKeyHole(int key) {
        String s;
        if (key == 0) {
            s = "keyholeYellow";
        } else {
            s = "keyholeBlue";
        }
        return s;
    }
     /**
     * this method is checking given x and y coordinates of the board with x and y 
     * coordinates of the flagBlue position and return the name of it to use in image path. 
     * It returns null if there is no flagBlue on given object or positions are not the same. 
     * @param obj object of loaded level
     * @param x x coordinate on the board
     * @param y y coordinate on the board
     * @return String name of flagBlue symbol or null if no match between positions
     */
    private String getFlagSymbol(LevelReader obj, int x, int y) {

        if (obj.getFlag() != null && obj.getFlag().getPosition().getX() == x
                && obj.getFlag().getPosition().getY() == y) {
            return "flagBlue";
        }
        return null;
    }
    /**
     * this method is checking given x and y coordinates of the board with x and y 
     * coordinates of the rochet position and return the name of it to use in image path. 
     * It returns null if there is no rocket on given object or positions are not the same. 
     * @param obj object of loaded level
     * @param x x coordinate on the board
     * @param y y coordinate on the board
     * @return String name of rocket symbol or null if no match between positions
     */
    private String getRocketSymbol(LevelReader obj, int x, int y) {
        if (obj.getRocket() != null && obj.getRocket().getX() == x
                && obj.getRocket().getY() == y) {
            return "rocket";

        }
        return null;
    }
    /**
     * this method is setting disable or enable of the roll again and dropout buttons 
     * based on given boolean value, true will disable and false will enable buttons
     * @param value boolean value
     */
    @Override
    public void setBtnDisable(boolean value) {
        btnRollAgain.setDisable(value);
        btnDropOut.setDisable(value);
    }
    /**
     * this method is putting appropriate dice on a cell of given position on the board of given 
     * player id.
     * @param dice int dice
     * @param pos Position of dice
     * @param player int id of player
     */
    @Override
    public void setDiceToBoard(int dice, Position pos, int player) {

        int col = getGrdPane(player).getColumnConstraints().size();
        int row = getGrdPane(player).getRowConstraints().size();

        Image img = new Image(path + getDiceImage(dice) + ext);

        ImageView imgVw = new ImageView(img);

        getGrpField(player)[pos.getX()][pos.getY()].getChildren().add(imgVw);

        imgVw.fitWidthProperty().bind(getGrdPane(player).widthProperty().divide(col).subtract(getGrdPane(player).getHgap()));
        imgVw.fitHeightProperty().bind(getGrdPane(player).heightProperty().divide(row).subtract(getGrdPane(player).getVgap()));

    }
    /**
     * this method is returning proper array of groups based on given id of player
     * @param player int id of player
     * @return array of groups of player
     */
    private Group[][] getGrpField(int player) {
        if (player == 0) {
            return grpHuman;
        } else if (player == 1) {
            return grpBot1;
        } else if (player == 2) {
            return grpBot2;
        } else {
            return grpBot3;
        }
    }
    /**
     * this method is returning proper GridPane based on given id of player
     * @param player int id of player
     * @return Gridpane of player
     */
    private GridPane getGrdPane(int player) {
        if (player == 0) {
            return grdPnHuman;
        } else if (player == 1) {
            return grdPnBot1;
        } else if (player == 2) {
            return grdPnBot2;
        } else {
            return grdPnBot3;
        }
    }
    /**
     * this method is adding highlight to the cell of given position that human player 
     * knows where he can put the dice on. 
     * @param pos Position of the cell
     */
    @Override
    public void highlightPos(Position pos) {
        ColorAdjust changeToGreen = new ColorAdjust();
        changeToGreen.setHue(0.75);
        changeToGreen.setSaturation(1.0);
        changeToGreen.setBrightness(0.5);
        addEffectToDicePos(pos, changeToGreen, grpHuman);

    }

    /**
     * Removes the highlight of a domino position on the game grid. I have got
     * help from last assignment
     *
     * @param pos position of the top-left half of the domino.
     */
    @Override
    public void removeHighlightPos(Position pos) {
        addEffectToDicePos(pos, null, grpHuman);
    }

    /**
     * add effect to given position with given effect
     *
     * @param pos is the given position to de displayed the effect
     * @param effect is the given effect
     */
    private void addEffectToDicePos(Position pos, ColorAdjust effect, Group[][] GrdPane) {

        Node result = null;
        for (int i = 0; i < GrdPane.length; i++) {
            for (int j = 0; j < GrdPane[i].length; j++) {

                for (int k = 0; k < GrdPane[i][j].getChildren().size(); k++) {

                    Node child = GrdPane[i][j].getChildren().get(k);
                    if (child.isManaged()) {

                        if (i == pos.getX() && j == pos.getY()) {
                            result = child;
                            result.setEffect(effect);
                            break;
                        }
                    }
                }
                // getImageView(new Position(pos.getX() , pos.getY() + 1), 0).setEffect(effect);
            }
        }
    }
    /**
     * this method is setting related pen symbol on players field based on given player
     * id. The method is calling two methods to get the players' pane and players' pen image. 
     * @param player int id of player
     */
    @Override
    public void showPenOnBoard(int player) {
        getImgVwDropouById(player).fitWidthProperty().bind(getPaneById(player).widthProperty());
        getImgVwDropouById(player).fitHeightProperty().bind(getPaneById(player).heightProperty());

        getImgVwDropouById(player).setBlendMode(BlendMode.MULTIPLY);

        getPaneById(player).getChildren().add(getImgVwDropouById(player));
    }
    /**
     * This method is removing the related image view of the pen from the players' board
     * based on given player id. 
     * @param player int id of player
     */
    @Override
    public void removePenFromBoard(int player) {
        getPaneById(player).getChildren().remove(getImgVwDropouById(player));
    }
    /**
     * This method is returning the image view of player based on given id
     * @param player int id of player
     * @return Image view of Pen for given player
     */
    private ImageView getImgVwDropouById(int player){
        
       
         if(player == 0){
            return imgVwDropOut[0];
        }else if(player == 1){
            return imgVwDropOut[1];
        }else if(player == 2){
            return imgVwDropOut[2];
        }
        else{
            return imgVwDropOut[3];
        }
    }
    /**
     * This method is returning the pane of player based on given id
     * @param player int id of player
     * @return Pane of player
     */
    private Pane getPaneById(int player){
        if(player == 0){
            return pnHuman;
        }else if(player == 1){
            return pnBot1;
        }else if(player == 2){
            return pnBot2;
        }
        else{
            return pnBot3;
        }
    }
    /**
     * This method is adding sign of lines for each level for each player. 
     * @param levelObj level object created from level file
     * @param player int id of player
     * @param level int level of game
     */
    private void addLines(LevelReader levelObj, int player, int level){
        
        if(player == 0){
            addlinesToPane(player, level, pnHuman, grdPnHuman, levelObj);
        }else if(player == 1){
            addlinesToPane(player, level, pnBot1, grdPnBot1, levelObj);
        }else if(player == 2){
            addlinesToPane(player, level, pnBot2, grdPnBot2, levelObj);
        }else{
            addlinesToPane(player, level, pnBot3, grdPnBot3, levelObj);
        }
        
    }
    /**
     * this method is adding vertical and horizontal line symbols to the players board based on 
     * the given level number. It calls appropriate method to add symbols using level number
     * @param player int id of player
     * @param level int level number
     * @param pane Pane of player
     * @param grdPn GridPane of player
     * @param levelObj Object of loaded level 
     */
    private void addlinesToPane(int player, int level, Pane pane, GridPane grdPn, LevelReader levelObj){
        if(level == 1){
            addLineToLevel1(player, pane, grdPn, levelObj);
        }else if(level == 2){
            addLineToLevel2(player, pane, grdPn, levelObj);
        }else{
            addLineToLevel3(player, pane, grdPn, levelObj);
        }
    }
     /**
     * this method is adding vertical and horizontal line symbols of level 1 to the board
     * @param player int id of player
     * @param pane Pane pane of player
     * @param grdPn GridPane of player  
     * @param levelObj Object of the loaded level
     */
    private void addLineToLevel1(int player, Pane pane, GridPane grdPn, LevelReader levelObj){
        int col = grdPn.getColumnConstraints().size();
        int row = grdPn.getRowConstraints().size();
  
        int cell = (int) grdPn.getHeight() / row;
        
            
        //adding symbols of Horizontal lines

        for(int i = 0; i < levelObj.getHorizontalLines().length; i++){
            Pane pn = new Pane();
            hPane[player][i] = new Pane();
           
            getPaneById(player).getChildren().add(hPane[player][i]);
            
            ImageView imgVw = new ImageView();

            imgVw.setImage(new Image(path+"10Right"+ext));
            hPane[player][i].getChildren().add(imgVw);
            
            hPane[player][i].setLayoutY((cell) * (levelObj.getHorizontalLines()[i].getPositions()[0].getY()));
            
            hPane[player][i].setLayoutX(0);
               
            hPane[player][i].prefWidthProperty().bind(grdPn.widthProperty().divide(col).subtract(grdPn.getHgap()));
            hPane[player][i].prefHeightProperty().bind(grdPn.heightProperty().divide(row).subtract(grdPn.getVgap()));
            
            imgVw.fitWidthProperty().bind(hPane[player][i].widthProperty());
            imgVw.fitHeightProperty().bind(hPane[player][i].heightProperty());
            
        }
        //adding symbols of the vertical lines
        for(int i = 0; i < levelObj.getVerticalLines().length; i++){
            Pane pn = new Pane();
            vPane[player][i] = new Pane();
            getPaneById(player).getChildren().add(vPane[player][i]);
            Image img = null;
            
            if(levelObj.getVerticalLines()[i].getPoints() == 5){
                img = new Image(path+"5Up"+ext);
            }else{
                img = new Image(path+"10Up"+ext);
            }
            
            
            ImageView imgVw = new ImageView(img);
            
            
            vPane[player][i].getChildren().add(imgVw);
            
            
            
            
            vPane[player][i].setLayoutY(grdPn.getHeight());    
            vPane[player][i].setLayoutX(cell + (cell * (levelObj.getVerticalLines()[i].getPositions()[0].getX())));
            
            vPane[player][i].prefWidthProperty().bind(grdPn.widthProperty().divide(col).subtract(grdPn.getHgap()));
            vPane[player][i].prefHeightProperty().bind(grdPn.heightProperty().divide(row).subtract(grdPn.getVgap()));
            
            imgVw.fitWidthProperty().bind(vPane[player][i].widthProperty());
            imgVw.fitHeightProperty().bind(vPane[player][i].heightProperty());
            
        }
        
    }
    /**
     * This mehtod is setting the X and Y layout of the panes for horizontal lines 
     * based on the size of gide pane of human and positions of horizonal lines 
     * @param grdPn grid pane of human
     * @param levelObj LevelReader object to check the positions
     */
    private void horizontalLinesPosition(GridPane grdPn, LevelReader levelObj){
        
        int col = grdPn.getColumnConstraints().size();
        int row = grdPn.getRowConstraints().size();
  
        int cell = (int) grdPn.getHeight() / row;
        
        for (int i = 0; i < hPane[0].length; i++) {
            if(levelObj.getHorizontalLines()[i].getPositions()[0].getX() == 0 && levelObj.getHorizontalLines()[i].getPositions()[1].getX() == 8){
                hPane[0][i].setLayoutY((cell) * (levelObj.getHorizontalLines()[i].getPositions()[0].getY()));
            
                hPane[0][i].setLayoutX(0);
            }
        }
    }
    /**
     * This method is setting the X and Y layout of the panes for vertical lines 
     * based on the size of grid pane of human and positions are the vertical lines
     * @param grdPn grid pane of human
     * @param levelObj LevelReader object to check the positions
     */
    private void verticalLinesPosition(GridPane grdPn, LevelReader levelObj){
        
        int col = grdPn.getColumnConstraints().size();
        int row = grdPn.getRowConstraints().size();
  
        int cell = (int) grdPn.getHeight() / row;
       
        for (int i = 0; i < vPane[0].length; i++) {
            vPane[0][i].setLayoutY(grdPn.getHeight());    
            vPane[0][i].setLayoutX(50 + (cell * (levelObj.getVerticalLines()[i].getPositions()[0].getX())));
        }
        
    }
     /**
     * this method is adding vertical and horizontal line symbols of level 2 to the board
     * @param player int id of player
     * @param pane Pane pane of player
     * @param grdPn GridPane of player  
     * @param levelObj Object of the loaded level
     */
    private void addLineToLevel2(int player, Pane pane, GridPane grdPn, LevelReader levelObj){
        int col = grdPn.getColumnConstraints().size();
        int row = grdPn.getRowConstraints().size();
        
         int cell = (int) grdPn.getHeight() / row;
         
         // adding symbols of horizantal lines
         for(int i = 0; i < levelObj.getHorizontalLines().length; i++){
            Pane pn = new Pane();
            hPane[player][i] = new Pane();
            Image img = null;
            if(levelObj.getHorizontalLines()[i].getPositions()[1].getX() == (col - 1)){
                getPaneById(player).getChildren().add(hPane[player][i]);
                img = new Image(path+"15Right"+ext);
                
                hPane[player][i].setLayoutY((cell) * (levelObj.getHorizontalLines()[i].getPositions()[0].getY()));
                hPane[player][i].setLayoutX(0);
            }else{
                getGrpField(player)[levelObj.getHorizontalLines()[i].getPositions()[1].getX()+ 1]
                        [levelObj.getHorizontalLines()[i].getPositions()[1].getY()].getChildren().add(hPane[player][i]);
                img = new Image(path+"5Left"+ext);
            }
            
            ImageView imgVw = new ImageView(img);
            
            hPane[player][i].getChildren().add(imgVw);

            hPane[player][i].prefWidthProperty().bind(grdPn.widthProperty().divide(col).subtract(grdPn.getHgap()));
            hPane[player][i].prefHeightProperty().bind(grdPn.heightProperty().divide(row).subtract(grdPn.getVgap()));
            
            imgVw.fitWidthProperty().bind(hPane[player][i].widthProperty());
            imgVw.fitHeightProperty().bind(hPane[player][i].heightProperty());
            
        }
         //adding symbols of the vertical lines
         for(int i = 0; i < levelObj.getVerticalLines().length; i++){
            Pane pn = new Pane();
            vPane[player][i] = new Pane();
            getPaneById(player).getChildren().add(vPane[player][i]);
            
            ImageView imgVw = new ImageView();
            imgVw.setImage(new Image(path+"10Up"+ext));
            
            vPane[player][i].getChildren().add(imgVw);
            
            vPane[player][i].setLayoutY(grdPn.getHeight());
            vPane[player][i].setLayoutX(cell + (cell * (levelObj.getVerticalLines()[i].getPositions()[0].getX())));
            
            vPane[player][i].prefWidthProperty().bind(grdPn.widthProperty().divide(col).subtract(grdPn.getHgap()));
            vPane[player][i].prefHeightProperty().bind(grdPn.heightProperty().divide(row).subtract(grdPn.getVgap()));
            
            imgVw.fitWidthProperty().bind(vPane[player][i].widthProperty());
            imgVw.fitHeightProperty().bind(vPane[player][i].heightProperty());
            
        }
         
    }
    /**
     * this method is adding vertical and horizontal line symbols of level 3 to the board
     * @param player int id of player
     * @param pane Pane pane of player
     * @param grdPn GridPane of player  
     * @param levelObj Object of the loaded level
     */
    private void addLineToLevel3(int player, Pane pane, GridPane grdPn, LevelReader levelObj){
        int col = grdPn.getColumnConstraints().size();
        int row = grdPn.getRowConstraints().size();
        
         int cell = (int) grdPn.getHeight() / row;
         //adding symbols of Horizontal line
         for(int i = 0; i < levelObj.getHorizontalLines().length; i++){
            Pane pn = new Pane();
            hPane[player][i] = new Pane();
            Image img = null;
            if(levelObj.getHorizontalLines()[i].getPositions()[0].getX() == 0 &&
                    levelObj.getHorizontalLines()[i].getPositions()[1].getX() == (col - 1)){
                getPaneById(player).getChildren().add(hPane[player][i]);
                img = new Image(path+"15Right"+ext);
                
                hPane[player][i].setLayoutY((cell) * (levelObj.getHorizontalLines()[i].getPositions()[0].getY()));
                hPane[player][i].setLayoutX(0);
            }else{
                if(levelObj.getHorizontalLines()[i].getPositions()[1].getX() != (col - 1)){
                    getGrpField(player)[levelObj.getHorizontalLines()[i].getPositions()[1].getX()+ 1]
                        [levelObj.getHorizontalLines()[i].getPositions()[1].getY()].getChildren().add(hPane[player][i]);
                    if(levelObj.getHorizontalLines()[i].getPositions()[1].getY() == 5){
                        img = new Image(path+"35Right"+ext);
                    }else{
                    img = new Image(path+"5Left"+ext);
                    }
                }
            }
            
            ImageView imgVw = new ImageView(img);
            
            hPane[player][i].getChildren().add(imgVw);

            hPane[player][i].prefWidthProperty().bind(grdPn.widthProperty().divide(col).subtract(grdPn.getHgap()));
            hPane[player][i].prefHeightProperty().bind(grdPn.heightProperty().divide(row).subtract(grdPn.getVgap()));
            
            imgVw.fitWidthProperty().bind(hPane[player][i].widthProperty());
            imgVw.fitHeightProperty().bind(hPane[player][i].heightProperty());
            
        }
         // adding symbols of the vertical lines
         for(int i = 0; i < levelObj.getVerticalLines().length; i++){
            Pane pn = new Pane();
           vPane[player][i] = new Pane();
            getPaneById(player).getChildren().add(vPane[player][i]);
            
            ImageView imgVw = new ImageView();
            imgVw.setImage(new Image(path+"10Up"+ext));
            
            vPane[player][i].getChildren().add(imgVw);
            
            vPane[player][i].setLayoutY(grdPn.getHeight());
            vPane[player][i].setLayoutX(cell + (cell * (levelObj.getVerticalLines()[i].getPositions()[0].getX())));
            
            vPane[player][i].prefWidthProperty().bind(grdPn.widthProperty().divide(col).subtract(grdPn.getHgap()));
            vPane[player][i].prefHeightProperty().bind(grdPn.heightProperty().divide(row).subtract(grdPn.getVgap()));
   
            imgVw.fitWidthProperty().bind(vPane[player][i].widthProperty());
            imgVw.fitHeightProperty().bind(vPane[player][i].heightProperty());
            
        }
        
    }
    /**
     * This method is returning related pane based on given id
     * @param player int id of player
     * @return Pane of player
     */
    private Pane getPaneWithId(int player){
        if(player == 0){
            return pnHuman;
        }else if(player == 1){
            return pnBot1;
        }else if(player == 2){
            return pnBot2;
        }else{
            return pnBot3;
        }
        
    }
    /**
     * This method is clearing the board and other gui components of the game to start
     * new game. 
     */
    @Override
    public void clear(){
        clearPanes();
        
        grdPnDices.getChildren().clear();
        
        //this.imgVwDropOut = null;
        
        grdPnPoints.getChildren().clear();
        updateRoundTurn(0, 0);
        
        logs.clear();
        txtAreaLogs.clear();
        
    }
    /**
     * This method is clearing panes of the players
     */
    private void clearPanes(){
        pnHuman.getChildren().clear();
        pnBot1.getChildren().clear();
        pnBot2.getChildren().clear();
        pnBot3.getChildren().clear();
        
        clearGroups();
        
        pnHuman.getChildren().add(grdPnHuman);
        pnBot1.getChildren().add(grdPnBot1);
        pnBot2.getChildren().add(grdPnBot2);
        pnBot3.getChildren().add(grdPnBot3);
        
    }
    /**
     * this method is clearing arrays of group for each player
     */
    private void clearGroups(){
        for (int i = 0; i < grpHuman.length; i++) {
            for (int j = 0; j < grpHuman[i].length; j++) {
                grpHuman[i][j].getChildren().clear();
                grpBot1[i][j].getChildren().clear();
                grpBot2[i][j].getChildren().clear();
                grpBot3[i][j].getChildren().clear(); 
            }
            
        }
    }
    /**
     * This method is initializing the table of the point based on the level number
     * @param level int level number
     */
    @Override
    public void initPointTable(int level){
        int size;
        if(level == 1){
            size = 5;
        }else if(level == 2){
            size = 7;
        }else{
            size = 8;
        }
        lblPoints = new Label[size];
        lblPoint = new Label[size];
        int idx = 0;
        for (int i = 0; i < lblPoints.length; i++) {
    
           ImageView imgView = new ImageView(new Image(path+getPointTableImage(i, level)+ext));
  
           imgView.setStyle("-fx-border-color: black");
            grdPnPoints.add(imgView, 0, idx);
            
            lblPoints[idx] = new Label("" + 0);
            
            lblPoints[idx].minWidthProperty().bind(grdPnPoints.widthProperty().divide(4.0).multiply(2.0));
            lblPoints[idx].minHeightProperty().bind(grdPnPoints.heightProperty().divide(
            grdPnPoints.getRowConstraints().size()));
            
            lblPoints[idx].setAlignment(Pos.CENTER);
            lblPoints[idx].setStyle("-fx-font-size: 18px");
            lblPoints[idx].setStyle("-fx-border-color: black");
            
            grdPnPoints.add(lblPoints[idx], 2, idx);
            
            //point of each one
            lblPoint[idx] = new Label("" + tmpPoint);
            lblPoint[idx].minWidthProperty().bind(grdPnPoints.widthProperty().divide(4.0));
            lblPoint[idx].minHeightProperty().bind(grdPnPoints.heightProperty().divide(
            grdPnPoints.getRowConstraints().size()));
            
            lblPoint[idx].setAlignment(Pos.CENTER);
            lblPoint[idx].setStyle("-fx-font-size: 18px");
            lblPoint[idx].setStyle("-fx-border-color: black");
            
            grdPnPoints.add(lblPoint[idx], 1, idx);
            
            imgView.fitWidthProperty().bind(grdPnPoints.widthProperty().divide(4.0));
            imgView.fitHeightProperty().bind(grdPnPoints.heightProperty().divide(
                    grdPnPoints.getRowConstraints().size()));
            
            
            
            idx++;
        }
    }
    /**
     * This method is returning name of picture in String format based on index of the
     * picture on point table and level number
     * @param idx int id of row in point table
     * @param level int level id
     * @return String of picture name
     */
     private String getPointTableImage(int idx, int level){
        String s = null;
        if(idx == 0){
            if(level == 1){
                s = "jewelRed";
                tmpPoint = "3";
            }else if(level == 2){
                s = "jewelRed";
                tmpPoint = "3";
            }else{
                s = "jewelBlue";
               tmpPoint ="1";
            }
        }else if(idx == 1){
            if(level == 1){
                s = "horizontalLine";
                tmpPoint ="?";
            }else if(level == 2){
                s = "jewelYellow";
                tmpPoint ="2";
            }else{
                s = "jewelYellow";
                tmpPoint ="2";
            }
        }else if(idx == 2){
            if(level == 1){
                s = "verticalLine";
                tmpPoint ="?";
            }else if(level == 2){
                s = "horizontalLine";
                tmpPoint ="?";
            }else{
                s = "jewelRed";
                tmpPoint ="3";
            }
        }else if(idx == 3){
            if(level == 1){
                s = "puzzleBlue";
                tmpPoint ="10";
            }else if(level == 2){
                s = "verticalLine";
                tmpPoint ="?";
            }else{
                s = "horizontalLine";
                tmpPoint ="?";
            }
        }else if(idx == 4){
            if(level == 1){
                s = "exploded";
                tmpPoint ="-2";
            }else if(level == 2){
                s = "puzzleGreen";
                tmpPoint ="10";
            }else{
                s = "verticalLine";
                tmpPoint ="?";
            }
        }else if(idx == 5){
            if(level == 2){
                s = "puzzleBlue";
                tmpPoint ="10";
            }else{
                s = "puzzleBlue";
                tmpPoint ="15";
            }
        }else if(idx == 6){
            if(level == 2){
                s = "exploded";
                tmpPoint ="-2";
            }else{
                s = "flagBlue";
                tmpPoint ="10";
            }
        }else{
            s = "exploded";
            tmpPoint ="-2";
        }
        return s;
    }
     /**
      * This method is updating the point received by player for a symbol in the given index
      * @param point int, received point
      * @param idx int index of point
      */
    @Override
    public void updateScores( int point, int idx){
       lblPoints[idx].setText("" + point);
    }
    /**
     * This method is putting cross symbol on given position for given player id. 
     * The last children that is a dice on the given position should be deleted before adding new 
     * image of cross on cell. 
     * @param pos Position of the cell on the player's board
     * @param player int player id
     */
    @Override
    public void setCrossedPos(Position pos, int player){
        getGrpField(player)[pos.getX()][pos.getY()].getChildren().remove(getGrpField(player)[pos.getX()][pos.getY()].getChildren().size() -1);
        ImageView imgVw = new ImageView(path+"crossed"+ext);
        getGrpField(player)[pos.getX()][pos.getY()].getChildren().add(imgVw);
        
        imgVw.fitWidthProperty().bind(getGrdPane(player).widthProperty().divide(getGrdPane(player).getColumnConstraints().size()).
                subtract(getGrdPane(player).getHgap()));
        imgVw.fitHeightProperty().bind(getGrdPane(player).heightProperty().divide(getGrdPane(player).getRowConstraints().size()).
                subtract(getGrdPane(player).getVgap()));
        
    }
    /**
     * This method is putting cross symbol on given position for given player id. 
     * @param pos Position of the cell on the player's board
     * @param player int player id
     */
    @Override
    public void setCrossedPosLoad(Position pos, int player){
        ImageView imgVw = new ImageView(path+"crossed"+ext);
        getGrpField(player)[pos.getX()][pos.getY()].getChildren().add(imgVw);
        
        imgVw.fitWidthProperty().bind(getGrdPane(player).widthProperty().divide(getGrdPane(player).getColumnConstraints().size()).
                subtract(getGrdPane(player).getHgap()));
        imgVw.fitHeightProperty().bind(getGrdPane(player).heightProperty().divide(getGrdPane(player).getRowConstraints().size()).
                subtract(getGrdPane(player).getVgap()));
        
    }
    /**
     * this method is removing the symbol of dice from board of the player from given position
     * @param pos position of cell
     * @param player int index of player
     */
    @Override
    public void removeDiceFromBoard(Position pos, int player){
        getGrpField(player)[pos.getX()][pos.getY()].getChildren().remove(getGrpField(player)[pos.getX()][pos.getY()].getChildren().size() -1);
    }
    /**
     * this method is update the labels of turns of user. 
     * @param round int round   
     * @param turn int turn
     */
    @Override
    public void updateRoundTurn(int round, int turn){
        lblRound.setText("" + (round+1));
        lblTurn.setText("" + (turn+1));
    }
    /**
     * set the symbol of exploded on bomb cell of the players
     * @param player int, index of player
     * @param pos Position of the bomb cell
     */
    @Override
    public void setExploded(int player, Position pos){
        
        ImageView imgVw = new ImageView(path+"exploded"+ext);
        getGrpField(player)[pos.getX()][pos.getY()].getChildren().add(imgVw);
        
        imgVw.fitWidthProperty().bind(getGrdPane(player).widthProperty().divide(getGrdPane(player).getColumnConstraints().size()).
                subtract(getGrdPane(player).getHgap()));
        imgVw.fitHeightProperty().bind(getGrdPane(player).heightProperty().divide(getGrdPane(player).getRowConstraints().size()).
                subtract(getGrdPane(player).getVgap()));
    }
    /**
     * This method is to show error messages in a pop-up window based on the int code of
     * the message. 
     * @param msgType int code of error
     */
    public void msgWindow(int msgType){
        
        String s = getErrMsg(msgType);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);    
        alert.setTitle("Error");
        alert.setHeaderText("" + s);
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        alert.showAndWait();
    }
    /**
     * returning proper message based on given message code
     * @param msgType int code of error
     * @return String error
     */
    private String getErrMsg(int msg){

        String s = "";
        if(msg == 1){
            s = "Level file: Board array length is not OK. "
                    + "2-D array should be 9x7 ";
        }else if(msg == 2){
            s = "Level file: Value inside the array is not OK. "
                    + "0  for starting points"
                    +"1-6 for dice values"
                    + "7 for planet symbol";
        }else if(msg == 3){
            s = "Level file: The positions for jewels is not OK.";
        }else if(msg == 4){
            s = "Level file: The position for bomb is not OK";
        }else if(msg == 5){
            s = "Level file: The position for puzzle is not OK";
        }else if(msg == 6){
            s = "Level file: The position for horizontal line is not OK";
        }else if(msg == 7){
            s = "Level file: The position for vertical line is not OK";
        }else if(msg == 8){
            s = "Level file: The position for key is not OK";
        }else if(msg == 9){
            s = "Level file: The position for keyhole is not OK";
        }else if(msg == 10){
            s = "Level file: The lenght of point array for flag is not OK";
        }else if(msg == 11){
            s = "Level file: The position for flag is not OK";
        }else if(msg == 12){
            s = "Level file: The position for rocket is not OK";
        }else if(msg == 13){
            s = "Level file: The position for planet is not OK";
        }else if(msg == 14){
            s = "Loading: the level number is not OK";
        }else if(msg == 15){
            s = "Loading: the round number is not OK";
        }else if(msg == 16){
            s = "Loading: the turn number is not OK";
        }else if(msg == 17){
            s = "Loading: the number of players is not OK";
        }else if(msg == 18){
            s = "Loading: a value in dices is not OK";
        }else if(msg == 19){
            s = "Loading: the flagReachedAs is not OK";
        }else if(msg == 20){
            s = "Loading: a position in checked  is not OK";
        }else if(msg == 21){
            s = "Loading: a position in diceOn  is not OK";
        }else if(msg == 22){
            s = "Loading: a position in exploded is not OK";
        }
        return s;
    }
    /**
     * This method is receiving information from logic part and show the turns a text 
     * area to user. 
     * @param player int id player
     * @param dice int laid dice
     * @param pos Position laid dice
     * @param type ObjectTypes collected Symbol
     * @param dropOut boolean drop out (nonactive player)
     */
    @Override
    public void logger(int player, int dice, Position pos, ObjectTypes type, boolean dropOut, boolean roll) {
        
        if(!roll){
             logs.add(dataToStringLog(player, dice, pos, type, dropOut)); 
        }else{
            String tmp = "";
            if(player == 0){
                tmp += "Human";
            }else{
                tmp += "C"+player;
            }
            tmp+= " removed dice "+ dice +" from position "+pos;
            logs.add(tmp);
        }
       
        
        String s = "";
        
        for (int i = 0; i < logs.size(); i++) {
            s =s + logs.get(i) + "\n";       
        }
        this.txtAreaLogs.setText(s);
        this.txtAreaLogs.selectPositionCaret(this.txtAreaLogs.getLength());
        this.txtAreaLogs.deselect();
    }
    /**
     * This method is used to log the activities of the game in the log area of the game. 
     * 
     * 
     * @param dices List of Integer
     * @param idPlayer int Id of player
     */
    public void loggerRoll(List<Integer> dices, int idPlayer){
        String s = "";
        
        if(idPlayer== 0 ){
            s = s + "Human";
        }else{
            s = s+ "C"+idPlayer;
        }
        s += " rolled, dices [";
        
        for (int i = 0; i < dices.size(); i++) {
            s += " "+dices.get(i);
            if(i < dices.size() -1){
                s +=",";
            } 
        }
        s += "]";
        logs.add(s); 
        
        s = "";
        
        for (int i = 0; i < logs.size(); i++) {
            s =s + logs.get(i) + "\n";       
        }
        this.txtAreaLogs.setText(s);
        this.txtAreaLogs.selectPositionCaret(this.txtAreaLogs.getLength());
        this.txtAreaLogs.deselect();
    }
    /**
     * This method is generating a String value based on information that is passed from 
     * logic part. 
     * @param player int id player
     * @param dice int laid dice
     * @param pos Position of laid dice
     * @param type ObjectTypes collected symbol
     * @param dropOut boolean drop out (nonactive player)
     * @return String generated message
     */
    private String dataToStringLog(int player, int dice, Position pos, ObjectTypes type, boolean dropOut){
        String s = "";
        if(dropOut){
            if(player == 0){
                s = s+ "Human player is dropped out.";
            }else{
                s = s + "C" +player+" is dropped out.";
            }
        }else{
            if(player == 0){
                s = s+ "Human put dice " + dice + " at ["+pos+"], ";
            }else{
                
                s = s + "C" +player+" put dice " + dice + " at ["+pos+"], ";
            }
            if(type != ObjectTypes.noType){
            if(type == ObjectTypes.bomb){
                s = s + type + " is ignited.";
            }else{
                s = s +type+ " is collected.";
            }
            }
        }
    
        return s;
    }
    /**
     * This method is showing the winner or winners of the game based on the calculated 
     * points which are done in logic package
     * @param winners List winner with id and point
     */
    @Override
    public void winnerWindow(List<Winner> winners){
        
        String s = "The winner/s is/are ";
        
        for (int i = 0; i < winners.size(); i++) {
            if(winners.get(i).getWinnerID() == 0){
                s = s + "Human";
            }else{ 
                s = s + " C"+winners.get(i).getWinnerID();
            }
            if(i < winners.size() - 1){
                s = s+ ",";
            }
            
        }
        s = s + " with "+winners.get(0).getWinnerPoint() + " point.";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);    
        alert.setTitle("Winner");
        alert.setHeaderText("" + s);
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        alert.showAndWait();
    }
    
    public void lblDiceBack(boolean roll){
        String s = "";
        if(roll){
            s = "Click on dice to return!";
        }
        
        lblDiceBack.setText(s);
    }
}
