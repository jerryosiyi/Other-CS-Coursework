public class GameLogic {
    // Creating the three different game objects variables
    public Map map;
    private HumanPlayer player;
    private Bot bot;

    // This variable is used to check if the game is running
    private boolean gameRunning;
    // This variable is used tro check if the player wants to quit the game
    private boolean quitGame;

    public GameLogic() {
        //Creating the three different game objects
        map = new Map();
        player = new HumanPlayer(this);
        bot = new Bot( this);
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public boolean isQuitGame() {
        return quitGame;
    }

    /**
     * This function takes in a parameter and assigns it to the variable quitGame
     * @param quitGame
     */
    public void setQuitGame(boolean quitGame) {
        this.quitGame = quitGame;
    }

    /**
     * This function prints out the gold required to win the game
     * @return Gold required to win the game
     */
    protected String hello() {
        return String.format(Variables.winGold, map.getGoldRequired());
    }

    /**
     * This function prints out the gold the player currently owns
     * @return Gold owned by the player
     */
    protected String gold() {
        return String.format(Variables.goldOwned, player.getGoldOwned());
    }

    /**
     * This function returns the map object currently loaded
     * @return map
     */
    public Map getMap() {
        return map;
    }

    /**
     * This function returns the current player object
     * @return player
     */
    public HumanPlayer getPlayer() {
        return player;
    }

    /**
     * This function takes in the parameter direction which is a string
     * indicating the position the player would like to move to.
     * @param direction
     */
    protected void move(String direction) {
        int x = player.getPlayerPosX();
        int y = player.getPlayerPosY();

        /* This if statement checks to see a a direction has been entered */
        if(direction == null) {
            System.out.println("You have not entered a direction");
            return;
        }

        /*
          This switch statement changes the players position based
          on the direction they wish to move to. If the direction
          the player has enter is invalid, an error message is return
          and the player has to enter a valid direction.
         */
        switch (direction) {
            case "N":
                y--;
                break;
            case "S":
                y++;
                break;
            case "E":
                x++;
                break;
            case "W":
                x--;
                break;
            default:
                System.out.println("Invalid Direction.");
                return;
        }

        /*
          This if statement checks if the position the player is trying
          to move to is a wall. if the position is a wall, a failed move
          statement is outputted otherwise, a success message is returned.
         */
        if (map.mapFloor(x, y) == '#')
            System.out.println(Variables.moveFail);
        else {
            player.setPlayerPosX(x);
            player.setPlayerPosY(y);
            System.out.println(Variables.moveSuccess);
        }

        player.setPlayerTurn(false);
    }

    /*
     * This function displays a 5x5 area of the map with the center being
     * the player's position.
     */
    protected void look() {
        int minRow = player.getPlayerPosX() - 2;
        int minCol = player.getPlayerPosY() - 2;

        int maxRow = player.getPlayerPosX() + 2;
        int maxCol = player.getPlayerPosY() + 2;

        for (int i = minCol; i <= maxCol; i++) {
            for (int j = minRow; j <= maxRow; j++) {
                /*
                  This if statement checks to see if i or j is out of bounds,
                  if they are a wall; # is printed
                 */
                if (i < 0 || j < 0 || j >= map.getMaxX() || i >= map.getMaxY()) {
                    System.out.print("# ");
                    continue;
                }

                /*
                  This if statement check to see if the bot is within the look area
                  if it is, B is printed. Also the if statement prints P in the middle
                  of the are to indicate the player.
                 */
                if (j == player.getPlayerPosX() && i == player.getPlayerPosY())
                    System.out.print("P ");
                else if (j == bot.getBotPosX() && i == bot.getBotPosY())
                    System.out.print("B ");
                else
                    System.out.print(map.mapFloor(j, i) + " ");
            }

            System.out.println();
        }
    }

    /**
     * This function checks to see if the tile at the player's position is a G tile.
     * If it is the G tile is replaced with a "." tile and the gold owned by the player
     * is incremented and a success message is printed out. if it's not a G tile a fail
     * message is outputted.
     * @return Gold currently owned by the player
     */
    protected String pickup() {
        int x = player.getPlayerPosX();
        int y = player.getPlayerPosY();

        if (map.mapFloor(x, y) == 'G') {
            int a = player.getGoldOwned();
            player.setGoldOwned(a + 1);

            map.setMapFloor(x, y, '.');
            return String.format(Variables.pickUpSuccess, player.getGoldOwned());
        } else
            return String.format(Variables.pickUpFail, player.getGoldOwned());

    }

    /*
     * This function allows the player to exit the game through an exit tile
     * or quit the game if the player wishes not to play anymore.
     */
    protected void quitGame() {
        /*
         * This if statement checks to see if quit has been enter before and if
         * it has then the player is sure that he wants to exit the game. if the
         * player has enough gold to win, a win message is outputted as long as
         * the player isd on an exit tile...
         */
        if (isQuitGame()) {
            int x = player.getPlayerPosX();
            int y = player.getPlayerPosY();

            if (map.mapFloor(x, y) == 'E'
                    && player.getGoldOwned() == map.getGoldRequired()) {
                System.out.println(Variables.quitGameWin);
                gameRunning = false;
                return;
            }

            //If they are not on an exit tile a lose message is outputted
            System.out.println(Variables.quitGameLose);
            gameRunning = false;
            return;
        }

        /*
         * Because quitGame is not true, the player to has to confirm that they
         * want to quit. They are prompted to confirm if they want to quit.
         */
        quitGame = true;

        System.out.println("Please enter QUIT again to exit game.");
    }

    /**
     * This function creates the game for the user. it prints out the welcome
     * messages and it calls the mapSelect() function and chooseDifficulty()
     * function which allows the player to load a map and choose a difficulty level.
     * @throws InterruptedException
     */
    public void runGameLoop()throws InterruptedException{
        Thread.sleep(1000);
        System.out.println(Variables.welcomeMessage);//Welcome message
        gameRunning = true;//Start games
        map = Map.mapSelect();
        //This if statement checks to see if a map has been loaded
        if(!map.mapLoad){
            map = new Map();
        }

        System.out.println(Variables.lineBreak);
        System.out.println("Welcome to \n" + map.getMapName());//Prints out map name
        System.out.println(Variables.lineBreak);

        bot.chooseDifficulty();
        System.out.println(Variables.lineBreak);
        Thread.sleep(1000);
        System.out.println(Variables.gameStart);

        player.generatePlayerPosition();//generates players position
        bot.generateBotPosition();// generates the bot's position
        player.setPlayerTurn(true);

        /*
         * This while loop keeps on running till the game has ended. it starts with
         * the player's turn and when the player's turn has ended, the bot's turn begins
         * and when the bot's turn end, it goes back to the player's turn.
         */
        while (isGameRunning()) {
            /*
             * This if statement checks to see if the bot has caught the player and if
             * it has, a game over message is printed out and the game stops running
             */
            if(bot.getBotPosY() == player.getPlayerPosY()
                    && bot.getBotPosX() == player.getPlayerPosX()){
                System.out.println("The bot has caught you");
                System.out.println(Variables.quitGameLose);
                gameRunning = false;
            }

            if (this.player.isPlayerTurn()) {

                player.getInputFromConsole();
            }

            if (!this.player.isPlayerTurn()) {
                System.out.println();

                this.bot.setBotTurn(true);
            }

            if (this.bot.isBotTurn()){
                bot.botDecide();
                bot.setBotTurn(false);
            }

            if (!this.bot.isBotTurn()) {

                this.player.setPlayerTurn(true);
            }

        }
    }


    public static void main(String[] args) {
        GameLogic logic = new GameLogic();

        try {
            logic.runGameLoop();
        } catch (InterruptedException e) {
            //do nothing
        }
    }
}
