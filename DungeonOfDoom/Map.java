import java.io.*;
import java.util.*;


public class Map {

    //This variable hold the gold required for the player to win
    private int goldRequired;
    private char[][] map;
    //This variable hold the name of the loaded map
    private String mapName;
    //This variable is used to check if map has loaded
    public boolean mapLoad;

    //This function returns goldRequired
    public int getGoldRequired() {
        return goldRequired;
    }

    /*
     * This function try to return the map tile at a certain position.
     * If the position is out of bounds, a wall tile is returned
     */
    public char mapFloor(int x, int y) {
        try {
            return map[y][x];
        } catch (ArrayIndexOutOfBoundsException e) {
            return '#';
        }
    }

    //This function changes the map tile at a certain position to the tile c
    public void setMapFloor(int x, int y, char c) {
        map[y][x] = c;
    }

    //This function returns the length of the map
    public int getMaxX() {
        return map[0].length;
    }

    //This function returns the width of the map
    public int getMaxY() {
        return map.length;
    }

    //This function returns the map name
    protected String getMapName() {
        return mapName;
    }

    //This constructor contains the default map of the game
    public Map() {
        mapName = "Very small Labyrinth of Doom";
        goldRequired = 2;
        map = new char[][]{
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', 'G', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'E', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', 'E', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'G', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
        };
    }

    /**
     * This file try to find the map file based on its directory. Once the file
     * is found the lines of the file is stored in an array list.
     * @param fileName
     *
     */
    public Map(String fileName){
        List<String> fileLines = new ArrayList<>();

        File file = new File("gamemap/" + fileName + ".txt");//directory
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine())
                fileLines.add(scanner.nextLine());

        } catch (FileNotFoundException e){
            System.out.println("File not found!, using default map instead.");
            mapLoad = false;//Map has not loaded
            return;
        }
        processMap(fileLines);
    }


    /**
     * This function takes in the array list; fileLines and goes through it
     * using a for loop, looking for the map name and the required gold to
     * win. The map is store in rows which is a list of Strings which is then
     * converted into an array of characters.
     * @param fileLines
     * @throws InterruptedException
     */
    public void processMap(List<String> fileLines) {
        List<char[]> rows = new ArrayList<>();

        for (String line : fileLines) {

            if (line.startsWith("name "))
                this.mapName = line.substring(5);
            else if (line.startsWith("win "))
                this.goldRequired = Integer.parseInt(line.substring(4).trim());
            else
                rows.add(line.toCharArray());
        }

        this.map = rows.toArray(new char[rows.size()][]);
        mapLoad = true;
    }

    /**
     * This function allows the user to load their own map by
     * entering the name their map is saved as. The while loop
     * makes sure that the user actually enters something. The
     * user can also choose to load 3 maps that comes with the
     * game or the default map.
     * @return map
     * @throws InterruptedException
     */
    public static Map mapSelect() throws InterruptedException{
        Thread.sleep(700);
        System.out.println("Please enter the name of your map file...");
        Thread.sleep(700);
        System.out.println("if you have no map you would like to load...");
        Thread.sleep(700);
        System.out.println("You can enter mapOne, mapTwo, mapThree, or default.");
        Thread.sleep(700);
        System.out.print("Please enter here: ");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String choice = scanner.nextLine();

            if (choice == null || choice.isEmpty())
                continue;

            switch (choice.trim()) {
                case "mapOne":
                    return new Map("mapOne");
                case "mapTwo":
                    return new Map("mapTwo");
                case "mapThree":
                    return new Map("mapThree");
                case "default":
                    return new Map();
                default:
                    return new Map(choice.trim());
            }
        }
    }
}
