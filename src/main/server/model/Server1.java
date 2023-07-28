package main.server.model;
import java.io.*;
import java.net.*;

public class Server1 {
    private ServerSocket server;
    private int numClients;
    private int maxClients;

    private Socket client1;
    private Socket client2;
    private DataInputStream in1;
    private DataInputStream in2;
    private DataOutputStream out1;
    private DataOutputStream out2;

    // What the server keeps track of
    
    private int[][] board;

    private static final int NUM_CELLS = 4; // the number of cells in a row/column

    // Instantiates the server
    public void newServer() {
        numClients = 0;
        maxClients = 2;
        board = new int[NUM_CELLS][NUM_CELLS];

        try {
            server = new ServerSocket(7070);
            System.out.println("Listening on port 7070...");
        } catch (IOException e) {
            System.out.println("Could not listen on port 7070");
            System.exit(-1);
        }
    }

    // Loops until at most, 2 clients are connected and attaches them to the server for inputs
    public void connectClients() {
        try {
            while (numClients < maxClients) {
                Socket client = server.accept(); // Blocks the while loop until a client ACTUALLY joins

                numClients++;

                DataInputStream in = new DataInputStream(client.getInputStream());
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                out.writeInt(numClients);
                System.out.println("Client #" + numClients + " has connected to the server!");

                if (numClients == 1) {
                    client1 = client;
                    in1 = in;
                    out1 = out;
                } else {
                    client2 = client;
                    in2 = in;
                    out2 = out;
                }

                new Thread(new SyncClients(numClients, in)).start(); // Attaches the client to its own thread for processing
            }

            /*
            Once the numClients have been connected, this loop stops looking for clients and will not be run again
            That is, if a client loses connection, it cannot reconnect because the server has stopped connecting
            clients and the game is effectively over.
            */
        } catch (IOException e) {
            System.out.println("Accept failed: 7070");
            System.exit(-1);
        }
    }

    // 
    private void broadcastUpdate(int row, int col, int clientID) {
        if (client1 != null && client2 != null) {
            try {
                // board
                board[row][col] = clientID;

                out1.writeInt(row);
                out1.writeInt(col);
                out1.writeInt(clientID);

                out2.writeInt(row);
                out2.writeInt(col);
                out2.writeInt(clientID);

                // Check if there is a winner
                int winner = checkWinner();
                out1.writeInt(winner);
                out2.writeInt(winner);

                out1.flush();
                out2.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int checkWinner() {
        // check if the board is full
        for (int row = 0; row < NUM_CELLS; row++) {
            for (int col = 0; col < NUM_CELLS; col++) {
                if (board[row][col] == 0) { // the board is not full, no winner yet
                    return 0;
                }
            }
        }

        // check if there is a winner
        int client1Count = 0;
        int client2Count = 0;
        for (int row = 0; row < NUM_CELLS; row++) {
            for (int col = 0; col < NUM_CELLS; col++) {
                if (board[row][col] == 1) {
                    client1Count++;
                } else if (board[row][col] == 2) {
                    client2Count++;
                }
            }
        }

        if (client1Count > client2Count) {
            return 1;
        } else if (client1Count < client2Count) {
            return 2;
        } else { // draw
            return -1;
        }
    }

    private class SyncClients implements Runnable {
        private int clientID;
        private DataInputStream in;

        public SyncClients(int clientID, DataInputStream in) {
            this.clientID = clientID;
            this.in = in;
        }

        public void run() {
            try {
                while (true) {
                    int row = in.readInt();
                    int col = in.readInt();

                    broadcastUpdate(row, col, clientID);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.newServer();
        server.connectClients();
    }
}
