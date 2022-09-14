// Students, replace this comment and implement Lab 1
import java.util.Random;
import java.util.Scanner;

public class CoinStrip {

    /**
     * Function to create the board
     * @param coins holds the set of coins
     */

    public static int[] createBoard(int[] coins, int numCoins) {
        //Update this method to create the initial state of the game
        Random rand = new Random();
        //Initialize array  board
        int[] board = coins;
        //Set every slot to board equal to 0
        for (int i = 0; i < board.length; i++) {
          board[i] = 0;
        }

        //For each coin, randomly select an index in array to place it (change value at index from 0 to 1)
        for (int i = 0; i < numCoins; i++) {
          int index = rand.nextInt(board.length);
          int index2 = 0;
          if (board[index] == 0) {
            board[index] = 1;
          //If the randomly selected index already has a coin in it, keep on randomly selecting indexes until a free one is found
          } else {
            while (board[index] != 0) {
              index = rand.nextInt(board.length);
          }
            board[index] = 1;
        }
        //Return array with coins placed randomly
    }   return board;
  }

    //Prints coinstrip board
    public static void printBoard(int[] coins) {
      System.out.println("Board:");
      //traverses through each index in coins array and prints
      for (int i = 0; i < coins.length; i++) {
        System.out.print(" " + coins[i]);
    }
    System.out.print('\n');
  }

    /**
    * Checks whether the move inputted byt the player is a move within the rules of the game
    * @param coins holds the set of coins
    * @param whichCoin the number of the coin chosen to move
    * @param numSpaces number of spaces to move the coin (left)
    * @return true if the move is valid
    */
    public static boolean isValidMove(int[] coins, int whichCoin, int numSpaces) {
        //the coin position of whichCoin is stored in s (starting position)
        int s = getCoinPosition(coins, whichCoin);
        //the desired coin position of whichCoin after the player moves is stored in e
        int e = s - numSpaces;
        /**
        * checks to make sure two conditions are met: the player is seeking to move the coin, and in a leftward getCoinPosition
        * also checks to make sure e is not off the coinstrip board
        */
        if (numSpaces <= 0 || e < 0) {
          return false;
        }
          /**
          * starting from the starting position s, this for loop traverses from left to right towards the ideal end coin position e
          * this satisfies two conditions:
          * no double occupancy, if any of the indexes to the left of s are not empty, will return true
          * no jumps, it checks every index until e is reached to make sure no coins are jumped over
          */
        for (int i = s - 1; i >= e; i--) {
          if (coins[i] != 0) {
            return false;
          }
    } return true;
  }

    /** Updates the game board to reflect a move.
     * Behavior is undefined if the described move is invalid.
     * @param coins holds the set of coins
     * @param whichCoin the number of the coin
     * @param numSpaces number of spaces to move the coin (left)
     */
    public static void makeMove(int[] coins, int whichCoin, int numSpaces) {
      int temp = 0;
      int j = getCoinPosition(coins, whichCoin);
      int a = coins[getCoinPosition(coins, whichCoin)];
      int b = j - numSpaces;
      if (isValidMove(coins, whichCoin, numSpaces)) {
        temp = coins[j];
        coins[j] = coins[b];
        coins[b] = temp;
      }
    }
    /**
   * Plays the game, iteratively allowing players to move
   * @param coins current game board state
   * @param number of coins randomly selected to be on the board
   * @return the winner (last player who makes a move before game is over)
   */
  public static int playGame(int[] coins, int numCoins) {
    Scanner sc = new Scanner(System.in);
    //number of players in the game
	  int currentPlayer = 2;

	   while (!isGameOver(coins, numCoins)) {
       //take a turn
		   //switch whose turn it is
		   if (currentPlayer == 1) {
			 currentPlayer = 2;
		   } else {
			 currentPlayer = 1;
		   }

		   //get user input
       int spaces;
       int chosenCoin;
		   printBoard(coins);
		   System.out.println("Enter a number coin, player " + currentPlayer + ":");
		   chosenCoin = sc.nextInt();
		   //num of spaces to move
		   System.out.println("Enter a number of spaces to move");
		   spaces = sc.nextInt();
		//loop if move is invalid
		  while (!isValidMove(coins, chosenCoin, spaces)) {
			System.out.println("Incorrect move!");
			printBoard(coins);
			System.out.println("Enter a number coin, player " + currentPlayer + ":");
			chosenCoin = sc.nextInt();
			//num of spaces to move
			System.out.println("Enter a number of spaces to move");
			spaces = sc.nextInt();
		}
		//make the move
    makeMove(coins, chosenCoin, spaces);
	}

	return currentPlayer;
  }

    /**
    * Returns true if the game is over
    * @param coins holds the set of coins
    * @param number of coins randomly selected to be on the board
    * @return True if the game is over, false otherwise.
    */
    public static boolean isGameOver(int[] coins, int numCoins) {
      //initialize counter
      int counter = 0;
      //traverses through the coins array
      for (int i = 0; i < numCoins; i++) {
        //every time there is a coin in a space, one is added to the counter
        if (coins[i] != 0) {
          counter++;
        }
        //if the counter is equal to the number of coins, then that means all the coins in the game are concentrated to
        //the left-most spaces, inidicating the game is over
        if (counter == numCoins) {
          return true;
        }
      }
      return false;
    }

    /**
     * Returns the number of coins on the CoinStrip gameboard.
     * @param coins holds the set of coins
     * @return the number of coints on the CoinStrip gameboard.
     */
    public static int getNumCoins(int[] coins) {
      int numCoins = 0;
      //traverse through the coins array
        for (int c: coins) {
          //if there is a coin in a space, add one to numCoins
          if (c != 0) {
            numCoins++;
          }
        }
          return numCoins;
    }

    /**
     * Returns the 0-indexed location of a specific coin. Coins are
     * not zero-indexed. So, if the CoinStrip had 3 coins, the coins
     * would be indexed 1, 2, 3.
     * @param coins holds the set of coins
     * @param coinNum the coin number
     * @return the 0-indexed location of the coin on the CoinStrip
     */
    public static int getCoinPosition(int[] coins, int coinNum) {
      int counter = 0;
      //traverse through the coins array
      for (int i = 0; i < coins.length; i++) {
        //if there is a coin in a space, add one to counter
        if (coins[i] != 0) {
          counter++;
        }
        //if the number of coins is equal to the coinNum the user selected
        //then it is confirmed that this is the right coin we want the index of
        //return the index of coinNum
        if (counter == coinNum) {
        return i;
      }
      }
      //return a random default number that is easy to see if code produces run-time error
      return -5;
    }

    /**
     * `public static void main(String[] args)` is a program's entry point.
     * This main method implements the functionality to play the Coinstrip
     * game.
     * @param Command-line arguments are ignored.
     */
    public static void main(String[] args) {
        Random rand = new Random();
        //randomly chooses a number of coins between 0 and 7
        int numCoins = 3 + rand.nextInt(5);
        //choose the number of boxes on the coinstrip board by multiplying numCoins by 2
        int numBoxes = numCoins * 2;
        int[] coins = new int[numBoxes];
        System.out.println("Welcome to the Silver Dollar Game!");
        int[] board = createBoard(coins, numCoins);
        int winner = playGame(board, numCoins);

        //output game over message
        System.out.println("Game over!");
        System.out.println("Winner is player " + winner);

        }
    }
