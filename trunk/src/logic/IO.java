/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;

/**
 * IO class is to read and write from input file or output file. The type of
 * file that this class is dealing with is json file for save, load and parsing
 * of level files; and txt file with .log extension to log the actions of all
 * players. The jsonParser method is to parse the json file of the given level
 * and it store the information into LevelReader class object that can be used
 * to loaded informations on the player's boards. The load method is for loading
 * the game from a save game and SaveLoadObject class object is used to store the
 * data in it,and the save method is to save the current game.
 *
 * The logger method is to show the each players action on main window and also
 * log it into a file to be checked later for errors.
 *
 * @author Rezaei
 */
public class IO {

    private final String path = "logic/levels/Level";
    private final String ext = ".json";
    private final String logName = "dizzle.log";
    private Game game;

    FileOutputStream f;
    OutputStreamWriter o;

    List<String> logs = new LinkedList<>();

    public IO() {
        try {
            URL url = IO.class.getProtectionDomain().getCodeSource().getLocation();
            String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
            String parentPath = new File(jarPath).getParentFile().getPath();
            this.f = new FileOutputStream(parentPath + "/" + logName);
            this.o = new OutputStreamWriter(this.f);

        } catch (IOException ex) {
            System.out.println("Error " + ex);
        }
    }

    protected LevelReader jsonParser(int level, GUIConnector gui) {

        Gson gson = new Gson();
        JsonArray jsonArray = null;
     
        InputStreamReader isr;
        LevelReader levelObj = null;
        ErrorHandling errorHandling = new ErrorHandling();
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(path + "" + level + "" + ext);
            isr = new InputStreamReader(is);
            //creating an object of level reader from json file
            levelObj = gson.fromJson(isr, LevelReader.class);
            if (errorHandling.jsonError(levelObj)) {
                gui.msgWindow(errorHandling.getErrorCode());
                System.exit(0);
            }
        } catch (com.google.gson.JsonParseException e) {
            
        }

        return levelObj;
    }

    protected Game load(GUIConnector gui, Game game) throws FileNotFoundException {
        this.game = game;
        ErrorHandling er = new ErrorHandling();
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.setTitle("Choose location To Save Report");
        File selectedFile = null;
        if (selectedFile == null) {
            selectedFile = chooser.showOpenDialog(null);
        }

        Gson gson = new Gson();
        //JSONArray jsonArray = null;
        if (selectedFile != null) {
            FileReader reader = new FileReader(selectedFile.getPath());

            SaveLoadObject load = gson.fromJson(reader, SaveLoadObject.class);
            if(!er.loadError(load)){
                this.game = new Game(gui, load);
            }else{
                gui.msgWindow(er.getErrorCode()); 
            }
        }

        return this.game;

    }

    protected void save(int round, int levelNo, int turnOf, int[] dice, int flagReachedAs, Player[] players) {

        Gson gson = new Gson();
        SaveLoadObject saveObject = new SaveLoadObject();

        saveObject.setDice(dice);
        saveObject.setLevelNo(levelNo);
        saveObject.setRound(round);
        saveObject.setTurnOf(turnOf);

        SaveLoadPlayer[] savePlayer = new SaveLoadPlayer[players.length];

        for (int i = 0; i < players.length; i++) {
            savePlayer[i] = new SaveLoadPlayer();
            savePlayer[i].setActive(!players[i].isDropOut());
            savePlayer[i].setFlagReachedAs(players[i].getFlagReachedAs());

            Position[] temp = new Position[players[i].getBoard().getAllCrossed().size()];
            players[i].getBoard().getAllCrossed().toArray(temp);

            savePlayer[i].setChecked(temp);

            temp = new Position[players[i].getBoard().getAllOccupied().size()];
            players[i].getBoard().getAllOccupied().toArray(temp);

            savePlayer[i].setDiceOn(temp);

            temp = new Position[players[i].getBoard().getAllExploded().size()];
            players[i].getBoard().getAllExploded().toArray(temp);

            savePlayer[i].setExploded(temp);

        }

        saveObject.setPlayers(savePlayer);

        jsonToFile(saveObject);

    }

    private void jsonToFile(SaveLoadObject obj) {
        assert (obj != null);

        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.setTitle("Choose location To Save the Game");
        File selectedFile = null;
        if (selectedFile == null) {
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".json", ".json"),
                    new FileChooser.ExtensionFilter(".txt", ".txt"));
            selectedFile = chooser.showSaveDialog(null);
        }
        if (selectedFile != null) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter writer = new FileWriter(selectedFile.getPath())) {
                gson.toJson(obj, writer);
                writer.flush();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Logger method is logging the turn of each player to see what was
     * happening in the game. It shows which player laid what dice on which
     * position or if the player is dropped out, it say that the player can not
     * player.
     *
     * Then it calls a method in gui part to update gui and also write the log
     * into a file.
     *
     * @param player int id player
     * @param dice int dice value
     * @param pos Position of laid dice
     * @param type ObjectTypes type symbols that is collected
     * @param dropOut boolean drop out (nonactive player)
     * @param gui GUIConnector gui of game
     */
    public void logger(int player, int dice, Position pos, ObjectTypes type, boolean dropOut, GUIConnector gui, boolean roll) {
        try {
            String s = "";
            if(!roll){
            if (dropOut) {
                if (player == 0) {
                    s = s + "Human player is dropped out.";
                } else {
                    s = s + "C" + player + " is dropped out.";
                }
            } else {
                if (player == 0) {
                    s = s + "Human put dice " + dice + " at [" + pos + "], ";
                } else {

                    s = s + "C" + player + " put dice " + dice + " at [" + pos + "], ";
                }
                if (type != ObjectTypes.noType) {
                    if (type == ObjectTypes.bomb) {
                        s = s + type + " is ignited.";
                    } else {
                        s = s + type + " is collected.";
                    }
                }
            }
            
            }else{

            if(player == 0){
                s += "Human";
            }else{
                s += "C"+player;
            }
            s += " removed dice "+ dice +" from position "+pos;
            }
            s += "\n";
            gui.logger(player, dice, pos, type, dropOut, roll);
            o.append(s);
            o.flush();
        } catch (IOException e) {

        }

    }

    /**
     * this method is closing the FileOutput String and OutputStreamWriter
     */
    public void closeLog() {
        try {
            if (f != null) {
                f.close();
            }
            if (o != null) {
                o.close();
            }
        } catch (IOException ex) {

        }
    }
    protected void rollLogger(List<Integer> dices, int idPlayer){
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
        s += "]\n";
        try {
            o.append(s);
            o.flush();
        } catch (IOException ex) {
            System.out.println("error "+ ex);
        }
         
    }

}
