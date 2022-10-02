package battleship;

import java.io.IOException;

enum Messages {
    W("You sank the last ship. You won. Congratulations!\n"),
    S("You sank a ship!\nPress Enter and pass the move to another player\n"),
    H("You hit a ship!\nPress Enter and pass the move to another player\n"),
    M("You missed.\nPress Enter and pass the move to another player\n"),
    E("Error! The shot is not valid. Try again: \n");
    private String message;
    Messages(String message) { this.message = message; }
    String getMessage() { return this.message; }
}

public class Main {

    public final static int size = 10;

    public static void main(String[] args) {
        Player[] players = new Player[2];
        initializePlayers(players);
        int turn = -1;
        Player p;
        while (!players[++turn % 2].isLoser()) {
            p = players[turn % 2];
            p.printBothFields();
            System.out.printf("%s, it's your turn:\n", p.getName());
            int[] shot = new int[2];
            while(takeShot(Player.scanner.next(), shot, p.getOpponent()));
            try {
                System.out.println(p.shoot(shot).getMessage());
            } catch (NullPointerException e) {
            }
            try {
                System.in.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean takeShot(String input, int[] shot, Player opponent) {
        shot[0] = input.charAt(0) - 'A';
        shot[1] = Integer.parseInt(input.substring(1)) - 1;
        if (shot[0] >= size || shot[0] < 0 || shot[1] >= size || shot[1] < 0) {
            System.out.println(Messages.E.getMessage());
            return true;
        } else return false;
    }
    public static void initializePlayers(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(String.format("Player %d", i + 1));
            players[i].placeShips();
        }
        for (int i = 0; i < players.length; i++) {
            players[i].setOpponent(players[(i + 1) % players.length]);
        }
    }
}