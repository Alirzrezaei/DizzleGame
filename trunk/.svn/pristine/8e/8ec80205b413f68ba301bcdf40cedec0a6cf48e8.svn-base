
package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * the class is start of the game and it is making the main window of the gui by adding stage
 * and then set scene into it and setting size and name of window. At the end it shows the stage. 
 * The root which is embedded inside scene is loaded by FXMLDocument.fxml file which has the gui 
 * components defined there. 
 * @author Rezaei
 */
public class GameGUI extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Dizzle Game");
        stage.setMinHeight(800.0);
        stage.setMinWidth(1015.0);
        stage.show();
        
        Thread.currentThread().setUncaughtExceptionHandler((Thread th, Throwable ex) -> {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unexpected Exception");
            alert.setContentText("Sorry, that should not be happening!");
            alert.showAndWait();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
