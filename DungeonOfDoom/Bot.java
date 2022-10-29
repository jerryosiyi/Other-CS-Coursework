import java.util.*;

public class Bot {

    //This variable is used to decide when the bot should look at the map or move
    private int decisionVal;
    //The 2 variables below holds the position of the bot
    private int botPosX;
    private int botPosY;
    //This variable is used to indicate when its the bot's turn
    private boolean botTurn;
    //This array is used to store the bot's look area
    private char[][] botLookArray;
    //This variables are used to hold the position in which the bot is heading to
    int botDestinationX;
    int botDestinationY;

    private HumanPlayer player;
    private Map map;
    Random rand = new Random();

    public Bot(GameLogic logic) {
        //Initializing variables
        decisionVal = 0;
        botDestinationX = 0;
        botDestinationY = 0;
        botTurn = false;
        this.player = logic.getPlayer();
        this.map = logic.getMap();
    }

    //This function returns the x coordinate of the bot's position
    public int getBotPosX() {
        return botPosX;
    }

    ////This function returns the y coordinate of the bot's position
    public int getBotPosY() {
        return botPosY;
    }

    //This function sets the x coordinate of the bot's position to botPosX
    public void setBotPosX(int botPosX) {
        this.botPosX = botPosX;
    }

    //This function sets the y coordinate of the bot's position to botPosY
    public void setBotPosY(int botPosY) {
        this.botPosY = botPosY;
    }


    //This function returns botTurn
    public boolean isBotTurn() {
        return botTurn;
    }

    //This function sets botTurn to a boolean
    public void setBotTurn(boolean botTurn) {
        this.botTurn = botTurn;
    }

    /*
     * This function generates to random numbers and checks to set if the
     * position created by the combination of those numbers is the same as
     * the player's position or if the map tile at that position is a wall.
     * if it  is, another two numbers are generated
     */
    public void generateBotPosition() {
        int xAxis, yAxis, flag;

        do {
            xAxis = rand.nextInt(map.getMaxX());
            yAxis = rand.nextInt(map.getMaxY());

            //This variables holds the player's position
            int x = player.getPlayerPosX();
            int y = player.getPlayerPosY();

            if(map.mapFloor(xAxis, yAxis) == '#') {
                flag = 1;
            }
            else if(xAxis == x && yAxis == y) {
                flag = 1;
            }
            else{
                flag = 0;
            }

        } while (flag == 1);

        setBotPosX(xAxis);
        setBotPosY(yAxis);
    }

    /**
     * This function moves the bot toward the botDestination
     * @throws InterruptedException
     */
    public void botMove()throws InterruptedException{
        //This array holds the possible directions the bot can move to
        char[] directions = {'N', 'S', 'E', 'W'};

        /*
         * This if statement checks to see if the bot is at its destination.
         * If it is the bot looks to see if the player is within its look area
         * else it move towards the middle of the map.
         */
        if(getBotPosX() == botDestinationX && getBotPosY() == botDestinationY){
            botDestinationX = 0;
            botDestinationY = 0;
            botLook();
            return;
        }

        if(botDestinationX == 0 && botDestinationY == 0){
            botDestinationX = map.getMaxX()/2;//middle x coordinate
            botDestinationY = map.getMaxY()/2;//middle y coordinate
        }

        /*
         * This if statement checks to moves the bot toward its destination if
         * if the destination position is not (0, 0).
         */
        if(botDestinationX != 0 && botDestinationY != 0){
            int x = getBotPosX();
            int y = getBotPosY();

            if(x < botDestinationX){
                x++;
            }
            else if(x > botDestinationX) {
                x--;
            }
            else if(y < botDestinationY) {
                y++;
            }
            else if(y > botDestinationY) {
                y--;
            }

            /*
             * This if statement checks to see if the position the bot is trying to move
             * to is a wall. If it is, the bot moves to a position that is not a wall.
             */
            if (map.mapFloor(x, y) == '#'){
                x = getBotPosX();
                y = getBotPosY();
                do{
                    int z = rand.nextInt(4);
                    switch (directions[z]){
                        case 'N':
                            x++;
                        case 'S':
                            x--;
                        case 'E':
                            y++;
                        case 'W':
                            y--;
                    }

                }while(map.mapFloor(x, y) == '#');
            }
            //The position the bot is moving to is valid
            setBotPosX(x);
            setBotPosY(y);
            Thread.sleep(300);
            System.out.println("Bot has moved");
        }
    }


    /**
     * This function stores a square area of the game map with the bot as
     * center to the botLookArray by using nested for loops.
     * @throws InterruptedException
     */
    public void botLook()throws InterruptedException{
        int boundary = (botLookArray.length - 1)/2;
        int minRow = getBotPosX() - boundary;
        int minCol = getBotPosY() - boundary;

        int maxRow = getBotPosX() + boundary;
        int maxCol = getBotPosY() + boundary;

        for (int i = minCol, y = 0; i <= maxCol; i++, y++) {
            for (int j = minRow, x = 0; j <= maxRow; j++, x++) {
                if (i < 0 || j < 0 || j >= map.getMaxX() || i >= map.getMaxY()) {
                    botLookArray[y][x] = '#';
                    continue;
                }

                if (j == player.getPlayerPosX() && i == player.getPlayerPosY()) {
                    botLookArray[y][x] = 'P';
                    botDestinationX = player.getPlayerPosX();
                    botDestinationY = player.getPlayerPosY();
                }
                else if (j == getBotPosX() && i == getBotPosY()) {
                    botLookArray[y][x] = 'B';
                }
                else{
                    botLookArray[y][x] = map.mapFloor(j, i);
                }
            }
        }
        Thread.sleep(300);
        System.out.println("Bot has looked");
    }

    /**
     * This function depending on the value of decisionVal run the function
     * botLook() or botMove(). decisionVal is incremented after either
     * botLook() or botMove() has ran. If decisionVal is 5, the value resets
     * @throws InterruptedException
     */
    public void botDecide()throws InterruptedException{
        if(decisionVal == 5){
            decisionVal = 0;
        }

        if(decisionVal == 0){
            botLook();
            setBotTurn(false);
            decisionVal++;
        }
        else{
            botMove();
            decisionVal++;
        }

    }

    /**
     * This function allows the user to choose a difficulty level.
     * The higher difficulty level the bigger the bot's look area is.
     * @throws InterruptedException
     */
    public void chooseDifficulty()throws InterruptedException{
        Thread.sleep(500);
        System.out.println("choose a difficulty level...");
        Thread.sleep(500);
        System.out.println("NORMAL");
        Thread.sleep(300);
        System.out.println("EXPERT");
        Thread.sleep(300);
        System.out.println("MASTER");
        Thread.sleep(500);
        System.out.print("Enter Here: ");


        Scanner scanner = new Scanner(System.in);

        while (true) {
            String choice = scanner.nextLine();

            //Check to see is the user has inputted a difficulty level
            if (choice == null || choice.isEmpty())
                continue;

            switch (choice.toUpperCase().trim()) {
                case "NORMAL":
                    botLookArray = new char[5][5];// initializing botArray
                    return;
                case "EXPERT":
                    botLookArray = new char[10][10];
                    return;
                case "MASTER":
                    botLookArray = new char [20][20];
                    return;
                default:
                    System.out.println("Not a valid difficulty level. Loading Normal level...");
                    botLookArray = new char [5][5];
                    return;
            }
        }
    }
}
