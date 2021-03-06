/*
 * This application is a game project developed by Alireza Rezaei ITE102772
 * The purpose is for Advanced Programming course.
 */
package logic;

/**
 * ErrorHandling class is to check if there is an error in the read file by levelReader 
 * class or SaveLoadObject class. The structural examinations are done with those classed 
 * to prove if the provided types of objects and structure of the file is as expected or not.
 * This class is checking the logical mistakes that can be provided mistakenly by the user for
 * saved files or during compile for level json files. 
 * 
 * The class has two two main methods, jsonError method to check level object file and loadError 
 * method to check the loaded file. An helper method is also here to calculated total round of game
 * based on number of players. 
 * @author Rezaei
 */
public class ErrorHandling {

    private int errCode = -1;
    /**
     * This method is checking json level objected which is read from json file.
     * If there is an error in the file, the method is returning true value, otherwise
     * false.
     * @param load LevelReader Object
     * @return boolean true if there is an error in file, false otherwise
     */
    protected boolean jsonError(LevelReader obj) {
        boolean error = false;
        // check the length of first dimenstion
        if (obj.getField().length < 7 || obj.getField().length > 7) {
            errCode = 1;
            error = true;
        }
        // check the length of second dimenstion
        for (int i = 0; i < obj.getField().length && !error; i++) {
            if (obj.getField()[i].length > 9 || obj.getField()[i].length < 9) {
                errCode = 1;
                error = true;
            }

        }
        //check values of field
        for (int i = 0; i < obj.getField().length && !error; i++) {
            for (int j = 0; j < obj.getField()[i].length && !error; j++) {
                if (obj.getField()[i][j] != null && (obj.getField()[i][j] < 0 || obj.getField()[i][j] > 7)) {
                    errCode = 2;
                    error = true;
                }
            }
        }
        // check the positions of jewels
        for (int i = 0; i < obj.getJewels().length && !error; i++) {
            for (int j = 0; j < obj.getJewels()[i].getPositions().length && !error; j++) {
                if (obj.getJewels()[i].getPositions()[j].getX() < 0 || obj.getJewels()[i].getPositions()[j].getX() > 8
                        || obj.getJewels()[i].getPositions()[j].getY() < 0 || obj.getJewels()[i].getPositions()[j].getY() > 6) {
                    errCode = 3;
                    error = true;
                }
            }
        }
        // check the positions of bombs
        for (int i = 0; i < obj.getBombs().getPositions().length && !error; i++) {
            if (obj.getBombs().getPositions()[i].getX() < 0 || obj.getBombs().getPositions()[i].getX() > 8
                    || obj.getBombs().getPositions()[i].getY() < 0 || obj.getBombs().getPositions()[i].getY() > 6) {
                errCode = 4;
                error = true;
            }

        }
        // check the positions of puzzles
        for (int i = 0; i < obj.getPuzzles().length && !error; i++) {
            for (int j = 0; j < obj.getPuzzles()[i].getPositions().length && !error; j++) {
                if (obj.getPuzzles()[i].getPositions()[j].getX() < 0 || obj.getPuzzles()[i].getPositions()[j].getX() > 8
                        || obj.getPuzzles()[i].getPositions()[j].getY() < 0 || obj.getPuzzles()[i].getPositions()[j].getY() > 6) {
                    errCode = 5;
                    error = true;
                }
            }
        }
        //// check the positions of horizontal lines
        for (int i = 0; i < obj.getHorizontalLines().length && !error; i++) {
            for (int j = 0; j < obj.getHorizontalLines()[i].getPositions().length && !error; j++) {
                if (obj.getHorizontalLines()[i].getPositions()[j].getX() < 0 || obj.getHorizontalLines()[i].getPositions()[j].getX() > 8
                        || obj.getHorizontalLines()[i].getPositions()[j].getY() < 0 || obj.getHorizontalLines()[i].getPositions()[j].getY() > 6) {
                    errCode = 6;
                    error = true;
                }
            }
        }
        // check the positions of vertical lines
        for (int i = 0; i < obj.getVerticalLines().length && !error; i++) {
            for (int j = 0; j < obj.getVerticalLines()[i].getPositions().length && !error; j++) {
                if (obj.getVerticalLines()[i].getPositions()[j].getX() < 0 || obj.getVerticalLines()[i].getPositions()[j].getX() > 8
                        || obj.getVerticalLines()[i].getPositions()[j].getY() < 0 || obj.getVerticalLines()[i].getPositions()[j].getY() > 6) {
                    errCode = 7;
                    error = true;
                }
            }
        }
        // check the positions of keys and keyholes
        for (int i = 0; i < obj.getKeys().length && !error; i++) {
            if (obj.getKeys()[i].getPosition().getX() < 0 || obj.getKeys()[i].getPosition().getX() > 8
                    || obj.getKeys()[i].getPosition().getY() < 0 || obj.getKeys()[i].getPosition().getY() > 6) {
                error = true;
                errCode = 8;
            }
            //check the positions of keyholes 
            for (int j = 0; j < obj.getKeys()[i].getHoles().length && !error; j++) {
                for (int k = 0; k < obj.getKeys()[i].getHoles().length; k++) {     
                if (obj.getKeys()[i].getHoles()[j].getX() < 0 || obj.getKeys()[i].getHoles()[j].getX() > 8
                        || obj.getKeys()[i].getHoles()[j].getY() < 0 || obj.getKeys()[i].getHoles()[j].getY() > 6) {
                    error = true;
                    errCode = 9;
                }
            }
        }}
        // check the position and points of flag
        if (obj.getFlag() != null) {
            if (obj.getFlag().getPoints().length > 4 && !error) {
                error = true;
                errCode = 10;
            }
            if (!error && (obj.getFlag().getPosition().getX() < 0 || obj.getFlag().getPosition().getX() > 8
                    || obj.getFlag().getPosition().getY() < 0 || obj.getFlag().getPosition().getY() > 6)) {
                error = true;
                errCode = 11;

            }
        }
        // check the position of rocket
        if (obj.getRocket() != null && !error && (obj.getRocket().getX() < 0 || obj.getRocket().getX() > 8
                || obj.getRocket().getY() < 0 || obj.getRocket().getY() > 6)) {
            error = true;
            errCode = 12;
        }
        // check the position of planet
        if (obj.getPlanet() != null && !error && (obj.getPlanet().getX() < 0 || obj.getPlanet().getX() > 8
                || obj.getPlanet().getY() < 0 || obj.getPlanet().getY() > 6)) {
            error = true;
            errCode = 13;
        }

        return error;
    }
    /**
     * This method is checking load objected which is read from saved game file.
     * If there is an error in the file, the method is returning true value, otherwise
     * false.
     * @param load SaveLoadobject
     * @return boolean true if there is an error in file, false otherwise
     */
    protected boolean loadError(SaveLoadObject load) {
        boolean error = false;
        // check if level number is between 1 to 3
        if (load.getLevelNo() < 1 || load.getLevelNo() > 3) {
            error = true;
            errCode = 14;
        }
        // check if number player is 2 to 4
        if(!error && (load.getPlayers().length < 2 || load.getPlayers().length > 4)){
            error = true;
            errCode = 17;
        }
        //check if number of round is ok 
        if(!error && (load.getRound() < 0 || load.getRound() > totalRound(load.getPlayers().length)-1)){
            error = true;
            errCode = 15;
        }
        //check if number of turn is ok
        if(!error && (load.getTurnOf() < 0 || load.getTurnOf() > load.getPlayers().length-1)){
            error = true;
            errCode = 16;
        }
        //check if values of dice on is ok
        for (int i = 0; i < load.getDice().length && !error; i++) {
            if (load.getDice()[i] < 1 || load.getDice()[i] > 6) {
                error = true;
                errCode = 18;
            }
        }
        //checking the players
        for (int i = 0; i < load.getPlayers().length && !error; i++) {
            // check if flag reached as is 0 to 4
            if(load.getPlayers()[i].getFlagReachedAs() < 0 && load.getPlayers()[i].getFlagReachedAs() > 4){
                error = true;
                errCode = 19;
            }
            // check if positions of checked array are ok
            for (int j = 0; j < load.getPlayers()[i].getChecked().length && !error; j++) {
                if(load.getPlayers()[i].getChecked()[j].getX() < 0 || load.getPlayers()[i].getChecked()[j].getX() > 8 ||
                        load.getPlayers()[i].getChecked()[j].getY() < 0 || load.getPlayers()[i].getChecked()[j].getY() > 6){
                    error = true;
                    errCode = 20;
                }
            }
            // check if positions of diceon array are ok
            for (int j = 0; j < load.getPlayers()[i].getDiceOn().length && !error; j++) {
                if(load.getPlayers()[i].getDiceOn()[j].getX() < 0 || load.getPlayers()[i].getDiceOn()[j].getX() > 8 ||
                        load.getPlayers()[i].getDiceOn()[j].getY() < 0 || load.getPlayers()[i].getDiceOn()[j].getY() > 6){
                    error = true;
                    errCode = 21;
                }
            }
            // check if positions of exploded are ok
            for (int j = 0; j < load.getPlayers()[i].getExploded().length && !error; j++) {
                if(load.getPlayers()[i].getExploded()[j].getX() < 0 || load.getPlayers()[i].getExploded()[j].getX() > 8 ||
                        load.getPlayers()[i].getExploded()[j].getY() < 0 || load.getPlayers()[i].getExploded()[j].getY() > 6){
                    error = true;
                    errCode = 22;
                }
            }
        }
        
        return error;
    }
    /**
     * returning the error code
     * @return int error code
     */
    protected int getErrorCode() {
        return errCode;
    }
    /**
     * returning total number of round based on number of players
     * @param players number of players
     * @return int total number of round
     */
    public int totalRound(int players) {
        if (players == 2) {
            return 6;
        } else if (players == 3) {
            return 4;
        } else {
            return 3;
        }
    }
}
