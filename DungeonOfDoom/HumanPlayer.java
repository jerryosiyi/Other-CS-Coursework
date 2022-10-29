import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class HumanPlayer {

    //The below 2 variables holds the position of the player
    private int playerPosX;
    private int playerPosY;
    //This variable holds the gold currently owned by the player
    private int goldOwned;
    //This string holds the direction the player wishes to move to
    private String direction;
    //This variable is used to indicate when its a player's turn
    private boolean playerTurn;

    private GameLogic logic;

    public HumanPlayer(GameLogic logic) {
        this.logic = logic;
    }

    /*
     * This function keeps on generating random numbers for xAxis and yAxis.
     * The map tile at that position is checked to see if it is a wall or not.
     * If it is a wall another position is generated.
     */
    public void generatePlayerPosition() {
        Random rand = new Random();
        int xAxis;
        int yAxis;

        Map map = logic.getMap();
        do {
            xAxis = rand.nextInt(map.getMaxX());
            yAxis = rand.nextInt(map.getMaxY());

        } while (map.mapFloor(xAxis, yAxis) == '#' && map.mapFloor(xAxis, yAxis) == 'G');

        //The values generate gives a valid position
        setPlayerPosX(xAxis);
        setPlayerPosY(yAxis);
    }

    //This function set playerTurn to the boolean parameter playerTurn
    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    //This function returns playerTurn
    public boolean isPlayerTurn() {
        return playerTurn;
    }

    //This function returns the x coordinates of the player's position
    public int getPlayerPosX() {
        return playerPosX;
    }

    //This function returns the y coordinates of the player's position
    public int getPlayerPosY() {
        return playerPosY;
    }

    //This function set the x coordinates of the player's position to playerPosX
    public void setPlayerPosX(int playerPosX) {
        this.playerPosX = playerPosX;
    }

    //This function set the y coordinates of the player's position to playerPosY
    public void setPlayerPosY(int playerPosY) {
        this.playerPosY = playerPosY;
    }

    //This function returns the gold currently owned by the player
    public int getGoldOwned() {
        return goldOwned;
    }

    //This function sets the gold owned by the player to goldOwned
    public void setGoldOwned(int goldOwned) {
        this.goldOwned = goldOwned;
    }

    /**
     * This function get a input from the user and this is given to the variable command
     * @throws InterruptedException
     */
    public void getInputFromConsole() throws InterruptedException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String command = reader.readLine();
            processInput(command);
        } catch (IOException e) {
            System.exit(1);
        }
    }

    /**
     * This function takes in a parameter; command and checks to see if it has any spaces
     * in the beginning, ending of in between it.
     * @param command
     * @throws InterruptedException
     */
    public void processInput(String command) throws InterruptedException{
        try {
            command = command.toUpperCase().trim();
            /*
             * if the string has spaces between it, the string is split between
             * the spaces
             */
            String[] parts = command.split("\\s+");
            direction = parts[1];// the part after the space is the direction in the case of move
            command = parts[0];
            getNextAction(command);
        } catch (ArrayIndexOutOfBoundsException e) {
            getNextAction(command.trim());
        }
    }

    /**
     * This function takes in the parameter command and passes it
     * through a switch case with perform different operations based
     * what command is.
     * @param command
     * @throws InterruptedException
     */
    public void getNextAction(String command) throws InterruptedException{
        switch (command) {
            /*
             * quitGame is set to false at the start of each case except the
             * quit case because if the player changes their mind on quit they
             * need to be able to continue the game.
             */
            case "HELLO":
                logic.setQuitGame(false);
                Thread.sleep(300);
                System.out.println(logic.hello());
                setPlayerTurn(false);
                break;
            case "GOLD":
                logic.setQuitGame(false);
                Thread.sleep(300);
                System.out.println(logic.gold());
                setPlayerTurn(false);
                break;
            case "MOVE":
                logic.setQuitGame(false);
                Thread.sleep(300);
                logic.move(direction);
                direction = null;
                break;
            case "PICKUP":
                logic.setQuitGame(false);
                Thread.sleep(300);
                System.out.println(logic.pickup());
                setPlayerTurn(false);
                break;
            case "LOOK":
                logic.setQuitGame(false);
                Thread.sleep(200);
                logic.look();
                setPlayerTurn(false);
                break;
            case "QUIT":
                Thread.sleep(300);
                logic.quitGame();
                break;
            default:
                logic.setQuitGame(false);
                Thread.sleep(300);
                System.out.println("Invalid command.");
                setPlayerTurn(true);
                break;
        }
    }
}
