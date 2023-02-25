import java.util.*;

public class TicTacToe {

    public static int scale = 3; //scale * scale

    public static int playerN = 3;
    public static player_information[] players = new player_information[playerN];

    public class returnV {
        int max;
        int index;
        returnV(int max_, int index_) {
            max = max_;
            index = index_;
        }
    }

    public returnV Bot(player_information player, String[][] board, int depth, int index) {

        player_information yourturn;
        int nextindex = index + 1;

        if ( index == playerN - 1 ) {
            yourturn = players[0];
            nextindex = 0;
        }
        else {
            yourturn = players[index + 1];
        }

        int mymax = -1;
        int myindex = 0;
        for ( int i = 0; i < scale; i++ ) {
            for (int j = 0; j < scale; j++ ) {

                if ( '1' <= board[i][j].charAt(0) && board[i][j].charAt(0) <= '9' ) {
                    board[i][j] = player.check;
                    String s = String.valueOf(i * 3 + j + 1);

                    if ( Is_Winner(board, player) || depth == scale * scale - 1 ) {
                        if ( Is_Winner(board, player) ) {
                            mymax = 1;
                        }
                        else {
                            mymax = 0;
                        }
                        myindex = i * 3 + j + 1;
                        board[i][j] = s;
                        return new returnV(mymax, myindex);
                    }

                    returnV your = Bot (yourturn, board, depth+1, nextindex);

                    if ( mymax < -your.max ) {
                        mymax = -your.max;
                        myindex = i * 3 + j + 1;
                    }

                    board[i][j] = s;
                }

            }

        }

        return new returnV(mymax, myindex);
    }



    public static boolean Is_Winner(String[][] board, player_information player) {
        int c = 0;

        for ( int i = 0; i < scale; i++ ) {
            for ( int j = 0; j < scale; j++ ) {
                if ( board[i][j].equals( player.check ) ) {
                    c++;
                }
            }
            if ( c == scale ) {
                return true;
            }
            c = 0;
        }

        for ( int i = 0; i < scale; i++ ) {
            for ( int j = 0; j < scale; j++ ) {
                if ( board[j][i].equals( player.check ) ) {
                    c++;
                }
            }
            if ( c == scale ) {
                return true;
            }
            c = 0;
        }

        for ( int i = 0; i < scale; i++ ) {
            if ( board[i][i].equals( player.check) ) {
                c++;
            }
        }
        if ( c == scale ) {
            return true;
        }

        c = 0;
        for ( int i = 0; i < scale; i++ ) {
            if ( board[i][scale - 1 - i].equals( player.check ) ) {
                c++;
            }
        }
        return c == scale;
    }


    public static void printBoard(String[][] board) {
        for ( int i = 0; i < board.length; i++ ) {
            for ( int j = 0; j < board[0].length; j++ ) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int depth = 0;

    public static Scanner sc = new Scanner(System.in);

    public static void game(String[][] board, int index) {

        player_information player = players[index];
        if ( depth == scale * scale ) {
            printBoard(board);
            System.out.println("Drawn!");
            return;
        }

        printBoard(board);
        if ( player.Is_Bot ) {
            TicTacToe dummy = new TicTacToe();
            int i = dummy.Bot(player, board, depth, index).index - 1;
            board[ i / 3 ][ i % 3 ] = player.check;
            System.out.println();
        }
        else {
            System.out.println(player.player_name + "'s turn!");
            int s = sc.nextInt();
            System.out.println();
            if (  '1' > board[ (s - 1) / 3 ][ (s - 1) % 3 ].charAt(0) ||
                    '9' < board[ (s - 1) / 3 ][ (s - 1) % 3 ].charAt(0) ) {
                System.out.println("The place is already occupied!" + "\n");
                game(board, index);
            }
            else {
                board[(s - 1) / 3][(s - 1) % 3] = player.check;
            }
        }

        if ( Is_Winner(board, player) ) {
            printBoard(board);
            System.out.println( players[index].player_name + "won!" );
            return;
        }

        depth++;
        if ( index == playerN - 1 ) {
            game(board, 0);
        }
        else {
            game(board, index + 1);
        }

    }


    public static void main(String[] args) {
        String[][] board = new String[scale][scale];

        int add = 0;
        for ( int i = 0; i < scale; i++ ) {
            for ( int j = 0; j < scale; j++ ) {
                board[i][j] = String.valueOf( j + add + 1);
            }
            add += 3;
        }

        for ( int i = 0; i < playerN; i++ ) {
            System.out.print("Enter player" + (i + 1) + " name(X): ");
            String name = sc.nextLine();
            char c = (char) ('A' + i);
            String mark = String.valueOf(c);
            players[i] = new player_information(i + 1, name, mark);
        }
        game(board, 0);

    }
}
