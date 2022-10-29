WELCOME TO DUNGEONS OF DOOM

OVERVIEW

The aim of the game is to collect enough gold to exit the map. 
The player is placed on a random tile on the map. 
The player is not placed on a wall tile (#) or a gold tile (G). 
Once the player gets enough gold, the player is allowed to exit the game on an exit tile (E) by entering the command QUIT. 
If the player uses the QUIT command outside an exit tile, the player loses the game. 
The player is also allows to use other commands like: 
	HELLO - which displays the the gold required for the player to win the game 
	GOLD - which displays the gold currently owned by the player 
	MOVE followed by either N, S, E, or W - this allows the player to move across the map
	LOOK - which allows the user to view a 5x5 grid around the player 
	PICKUP - which allows the player to pick up gold if the player is on a gold tile

The player also has to clear to dungeon whilst being chased by a bot.
The bot moves around the map toward a destination which is updated to the position of the player
when the player is within the look area of the bot.
The player is allowed to choose a difficulty level which affects the size of the bot's look area.
If the bots catches the player, the player loses the game.

LOADING MAP FILES

1. Firslty, in other for the map to be retreive by the game they must be saved
   in the gamemap folder.
2. When running the game, the game prompts you to enter the name of the map you
   want to load. Please enter the name of your map file without the '.txt'
3. The map file reader is case sensitive so make sure that the name you enter is 
   exactly how the file is named.

RESTARTING THE GAME

In other to restart the game, you will need to rerun the GameLogic class. This would
create a new game instance.

HOPE YOU ENJOY PLAYING...
DUNGEONS OF DOOM