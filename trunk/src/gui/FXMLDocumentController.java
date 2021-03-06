
package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.Position;

/**
 * This class is used to initialize the gui and logic based on the information provided by user 
 * in initializer window.
 * 
 * all the containers and components, which are created in FXMLDocument.fxml file, are here to work 
 * with them or pass them to JavaFXGUI for updating the gui part of the game. 
 * 
 * Controlling the actions of the game is also done here, like clicking on buttons or items in menus. 
 * @author Rezaei
 */
public class FXMLDocumentController implements Initializable {
    
  
    private logic.Game game;
    private JavaFXGUI gui;
    private int level; 
    private int noOfPlayers;
    @FXML
    private MenuItem menuSave;
    @FXML
    private MenuItem menuLoad;
    @FXML
    private MenuItem menuClose;
    @FXML
    private MenuItem menuAbout;
    @FXML
    private Label lblRound;
    @FXML
    private Label lblTurn;
    @FXML
    private AnchorPane pnBots;
    @FXML
    private GridPane grdPnBot3;
    @FXML
    private GridPane grdPnBot2;
    @FXML
    private GridPane grdPnBot1;
    @FXML
    private Pane pnHuman;
    @FXML
    private GridPane grdPnHuman;
    @FXML
    private GridPane grdPnDices;
    @FXML
    private Button btnRollAgain;
    @FXML
    private Button btnDropOut;
    @FXML
    private MenuItem menuNewGame;
    @FXML
    private TextArea txtAreaLogs;
    
    private Group[][] grpHuman;
    private Group[][] grpBot1;
    private Group[][] grpBot2;
    private Group[][] grpBot3;
    @FXML
    private Pane pnBot1;
    @FXML
    private Pane pnBot2;
    @FXML
    private Pane pnBot3;
    @FXML
    private GridPane grdPnScores;
    @FXML
    private Label lblDiceBack;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //call the game initializer method to start the game. 
        gameInitializer();
        
        //the listener code to change the main human grid pane based on chagnes of main window
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {

            double diffWidth =  (pnHuman.getWidth() - pnHuman.getMinWidth());
            double diffHeight =  (pnHuman.getHeight() - pnHuman.getMinHeight());
   
            grdPnHuman.setPrefWidth(grdPnHuman.getMinWidth() + Math.min(diffWidth, diffHeight));
            grdPnHuman.setPrefHeight(grdPnHuman.getMinHeight() + Math.min(diffWidth, diffHeight));
        };
        
        pnHuman.widthProperty().addListener(stageSizeListener);
        pnHuman.heightProperty().addListener(stageSizeListener);  
    } 
    
    private Group[][] setGroupToGrdPane(GridPane grdPn){
        int colcount = grdPn.getColumnConstraints().size();
        int rowcount = grdPn.getRowConstraints().size();

        Group[][] grpGame = new Group[colcount][rowcount];

        for (int x = 0; x < colcount; x++) {
            for (int y = 0; y < rowcount; y++) {
                //creates an Group with the empty constructor 
                grpGame[x][y] = new Group();

                //add the group to the cell and
                //assign the correct indicees for this group, so you later can use GridPane.getColumnIndex(Node)
                grdPn.add(grpGame[x][y], x, y);

               
            }
        }
        return grpGame;
    }
    /**
     * This method is opening a small window and receives information from user like level number 
     * and number of players to create the gui and game based on the provided data. 
     */
    private void gameInitializer()  {
        
        //clearing the old game if it is not null. 
        if (game != null) {
            
            game.clear();
            
            for (int i = 0; i < grpHuman.length; i++) {
                for (int j = 0; j < grpHuman[i].length; j++) {       
                    grpHuman[i][j].getChildren().clear();
                    grpBot1[i][j].getChildren().clear();
                    grpBot2[i][j].getChildren().clear();
                    grpBot3[i][j].getChildren().clear();
                    
                }
                
                
            }

        }
        //setting array of groups into players Grid Pane
        grpHuman = setGroupToGrdPane(grdPnHuman);
        grpBot1 = setGroupToGrdPane(grdPnBot1);
        grpBot2 = setGroupToGrdPane(grdPnBot2);
        grpBot3 = setGroupToGrdPane(grdPnBot3);

        
        
        //label for levels 
        Label lblLevels = new Label("Levels");
        
        //combo box for levels
        ComboBox levels = new ComboBox();
        levels.getItems().addAll("1", "2", "3");
        levels.setValue("1");

        //label for players
         Label lblPlayers = new Label("players");
         
         
         // combo box for players
        ComboBox players = new ComboBox();
        players.getItems().addAll("2", "3", "4");
        players.setValue("2");
        
        // button to press OK for confirmation of settings
        Button btnOk = new Button("OK");
        
        // make a container for components
        StackPane secondaryLayout = new StackPane();

        
       //add all components to the container of second window
        secondaryLayout.getChildren().add(btnOk);
        secondaryLayout.getChildren().add(levels);
        secondaryLayout.getChildren().add(lblLevels);
        secondaryLayout.getChildren().add(lblPlayers);
        secondaryLayout.getChildren().add(players);
        
        // positioning of components
        btnOk.setPrefSize(75, 25);
        
        lblLevels.setTranslateX(-65);
        lblLevels.setTranslateY(-50);
        
        lblPlayers.setTranslateX(-65);
        lblPlayers.setTranslateY(0);
        
        players.setTranslateX(30);
        players.setTranslateY(0);
        
        levels.setTranslateX(30);
        levels.setTranslateY(-50);
 
  
        btnOk.setTranslateX(0);
        btnOk.setTranslateY(50);

        //add the layout to Scene
        Scene secondScene = new Scene(secondaryLayout, 250, 150);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Setting Initializer");
        newWindow.setMinHeight(200);
        newWindow.setMaxWidth(250);
        newWindow.setMaxHeight(200);
        newWindow.setMaxWidth(250);
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(500);
        newWindow.setY(500);
        newWindow.setAlwaysOnTop(true);

        newWindow.show();
        // setting level number based on level drop-down list
        btnOk.setOnAction((ActionEvent e) -> {
            if (levels.getValue().equals("1")) {
                level = 1;
            } else if (levels.getValue().equals("2")) {
                level = 2;
            } else if (levels.getValue().equals("3")) {
                level = 3;
            } else {
                level = 1;
            }
            //setting number of players based on noOfPlayers drop-down list
             if (players.getValue().equals("2")) {
                noOfPlayers = 2;
            } else if (players.getValue().equals("3")) {
                noOfPlayers = 3;
            } else if (players.getValue().equals("4")) {
                noOfPlayers = 4;
            } else {
                noOfPlayers = 2;
            }
            //closing initializer window
            newWindow.close();
            
            //setting background color to players fields
            setBackgroudColorToField();
            
            //make text aread of logs noeditable
            txtAreaLogs.setEditable(false);
            
            //disabling drop out and roll again buttons
            btnDropOut.setDisable(true);
            btnRollAgain.setDisable(true);
            
            //create and initialize instance of gui
            this.gui = new JavaFXGUI(grdPnHuman, grdPnBot1, grdPnBot2, grdPnBot3, grdPnDices, 
                    lblRound, lblTurn, btnRollAgain, btnDropOut, grpHuman, grpBot1, grpBot2, grpBot3, 
                    pnHuman, pnBot1, pnBot2, pnBot3, grdPnScores, txtAreaLogs, lblDiceBack);
            //create and initialize instance of logic game
           this.game = new logic.Game(gui, level, noOfPlayers);

        });

    }
    /**
     * This method is setting background color to pane of human and bots. 
     */
    private void setBackgroudColorToField(){
        if(level == 1){
            pnHuman.setStyle("-fx-background-color:lightblue;");
            pnBots.setStyle("-fx-background-color:lightblue;");
        }else if(level == 2){
            pnHuman.setStyle("-fx-background-color:THISTLE;");
            pnBots.setStyle("-fx-background-color:THISTLE;");
    
        }else if(level == 3){
            pnHuman.setStyle("-fx-background-color:MEDIUMAQUAMARINE;");
            pnBots.setStyle("-fx-background-color:MEDIUMAQUAMARINE;");
           
        } 
    }
    /**
     * get click on new game item in file menu and call game initializer to create a new game. 
     * The old game will be cleared and new one is starting based on user's information. 
     * @param event action event
     */
    @FXML
    private void onClickMenuNewGame(ActionEvent event) {
        gameInitializer();
    }
    /**
     * get click on save item of file menu and sent it to logic game to save the current game
     * @param event action event
     */
    @FXML
    private void onCllickMenuSave(ActionEvent event) {  
       this.game.save();
    }
    /**
     * get click on load item in file menu and send it to logic game to load the game
     * @param event Action event
     * @throws FileNotFoundException 
     */
    @FXML
    private void onClickMenuLoad(ActionEvent event) throws FileNotFoundException {
        
        this.game = this.game.load(this.game);
    }
    /**
     * get click on close item in file menu and close the game
     * @param event action event
     */
    @FXML
    private void onClickMenuClose(ActionEvent event) {
        System.exit(0);
    }
    
    /**
     * get the click on about item of the edit menu and take a proper action
     * @param event action event
     */
    @FXML
    private void onClickBtnAbout(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(" About Me");
            alert.setContentText("This game is a Dizzle game.\n"
                    + "To get more information how to play, please read the documentation!");
            alert.showAndWait();
    }
    /**
     * get click on roll again button and send it to logic game for further action
     * @param event MouseEvent
     */
    @FXML
    private void onClickBtnRoll(MouseEvent event) {
        this.game.rollAgainClicked();
    }
    /**
     * get click on drop out button and sent it to logic game to take an action
     * @param event MouseEvent
     */
    @FXML
    private void onClickBtnDropOut(MouseEvent event) {
        this.game.DropOutClicked();
    }
    /**
     * This method is getting click on Grid Pane of human player and gives the coordinates of 
     * x and y as position and pass it to logic game to check if dice can be put on the board or not. 
     * @param event Mouse Event
     */
    @FXML
    private void onClickGrdPane(MouseEvent event) {
        
        int x = -1;
        int y = -1;
        boolean leftClicked = event.getButton() == MouseButton.PRIMARY;
        boolean rightClicked = event.getButton() == MouseButton.SECONDARY;

        for (Node node : grdPnHuman.getChildren()) {
            if (node instanceof Group) {
                if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                    //to use following methods the columnIndex and rowIndex
                    //must have been set when adding the imageview to the grid
                    x = GridPane.getColumnIndex(node);
                    y = GridPane.getRowIndex(node);
                }
            }
        }

        if (x >= 0 && y >= 0 && leftClicked) {     
            this.game.setDiceToBoard(new Position(x, y));
        }
    }
    
}
