package main.shared.model;

import java.io.IOException;
import java.io.Serializable;


// This class is responsible for managing the game state
public class Game implements Serializable{
    private final int MAX_PLAYERS;

    private final Player[] players;
    private GameBoard gameBoard;

    // 0 = no winner
    // 1 = player 1
    // 2 = player 2
    // n = player n
    private int winner;

    // Given a max number of players an grid size, initialize the game
    public Game(int num_cells, int max_players) {
        this.MAX_PLAYERS = max_players;

        this.gameBoard = new GameBoard(num_cells); // Assume this is your game board class
        this.winner = 0;
        this.players = new Player[max_players];
    }
    
    // Accessor Functions
    public Player getPlayer(int playerID) {
        if (playerID < MAX_PLAYERS && playerID >= 0) {
            return players[playerID];
        } else {
            return null;
        }
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

    public int getWinner() {
        return this.winner;
    }

    public int getNumPlayers() {
        return players.length;
    }

    // Setting Functions
    public void setWinner(int winner) {
        this.winner = winner;
    }

    // Game logic
    public boolean isValidMove(int row, int col) {
        return gameBoard.getCell(row, col).isOwned();
    }

    public void addPlayer(Player player) {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
    }

    public int checkWinner() {
        // check if the board is full
        for (int row = 0; row < Settings.NUM_CELLS; row++) {
            for (int col = 0; col < Settings.NUM_CELLS; col++) {
                int currOwnerID = getGameBoard().getCell(row, col).getOwnerID();
                if (currOwnerID == 0 || currOwnerID == -1) { 
                    // the board is not full, no winner yet
                    return -2;
                }
            }
        }

        int winnerID = -1;
        int numWinnerCells = -1;
        // the board is full, see who owns the highest number of cells
        for (int i = 0; i < Settings.MAX_PLAYERS; i++) {
            if (getPlayer(i).getNumFilledCells() > numWinnerCells) {
                winnerID = i;
                numWinnerCells = getPlayer(i).getNumFilledCells();
            }
        }

        return winnerID;
    }

    // Update the game state based on a player's move
    public void makeMove(int row, int col, Player player) throws IOException {
        if (isValidMove(row, col)) {
            gameBoard.setCell(row, col, player.getId());
            
            // Check if the game is over
            if (gameBoard.checkWin(player)) {
                setWinner(player.getId());
            }
        } else {
            throw new IOException(); // Create this exception to handle invalid moves
        }
    }
}
