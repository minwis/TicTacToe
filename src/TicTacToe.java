import java.util.*;

public class TicTacToe {

    public static int scale = 3; //scale * scale

    public static player_information player1;
    public static player_information player2;

    public class returnV {
        int max;
        int index;
        returnV(int max_, int index_) {
            max = max_;
            index = index_;
        }
    }

    public static boolean Drawn(String[][] board ) {
        int c = 0;
        int max = scale * scale;
        for ( int i = 0; i < scale; i++ ) {
            for ( int j = 0; j < scale; j++ ) {
                if ( '1' <= board[i][j].charAt(0) && board[i][j].charAt(0) <= '9' ) {
                }
                else {
                    c++;
                }
            }
        }
        return c == max;
    }

    public returnV Bot(player_information player, String[][] board, int depth) { //draw

        player_information yourturn = null;

        if ( Objects.equals(player, player2) ) {
            yourturn = player1;
        }
        else if ( Objects.equals(player, player1) ) {
            yourturn = player2;
        }

        int mymax = -1;
        int myindex = 0;
        for ( int i = 0; i < scale; i++ ) {
            for (int j = 0; j < scale; j++ ) {

                if ( '1' <= board[i][j].charAt(0) && board[i][j].charAt(0) <= '9' ) {
                    board[i][j] = player.check;
                    String s = String.valueOf(i * 3 + j + 1);

                    if ( Is_Winner(board, player) ) {
                        mymax = 1;
                        myindex = i * 3 + j + 1;
                        board[i][j] = s;
                        return new returnV(mymax, myindex);
                    }

                    if ( depth == scale * scale - 1 ) {
                        mymax = 0;
                        myindex = i * 3 + j + 1;
                        board[i][j] = s;
                        return new returnV(mymax, myindex);
                    }

                    returnV your = Bot (yourturn, board, depth+1);

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

        //vertical
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

        c = 0;
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
        if ( c == scale ) {
            return true;
        }

        return false;
    }


    public static void printBoard(String[][] board) {
        for ( int i = 0; i < board.length; i++ ) {
            for ( int j = 0; j < board[0].length; j++ ) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int depth = 0; //how far the game is going.

    public static Scanner sc = new Scanner(System.in);
    public static boolean Return = false;

    public static void game(String[][] board, player_information player) {

        if ( depth == scale * scale ) {
            printBoard(board);
            System.out.println("Drawn!");
            return;
        }

        printBoard(board);
        if ( Return ) {
            return;
        }
        if ( player.Is_Bot ) {
            TicTacToe dummy = new TicTacToe();
            int i = dummy.Bot(player, board, depth).index - 1;
            board[ i / 3 ][ i % 3 ] = player.check;
            System.out.println();
        }
        else {
            System.out.println(player.player_name + "'s turn!");
            int s = sc.nextInt();
            System.out.println();
            String check1 = player1.check;
            String check2 = player2.check;
            if ( board[ (s - 1) / 3 ][ (s - 1) % 3 ].equals(check1) ||
                    board[ (s - 1) / 3 ][ (s - 1) % 3 ].equals(check2) ) {
                System.out.println("The place is already occupied!" + "\n");
                game(board, player);
            }
            else {
                board[(s - 1) / 3][(s - 1) % 3] = player.check;
            }
        }

        if ( Is_Winner(board, player) ) {
            printBoard(board);
            if ( player.turn_number == 1 ) {
                System.out.println(player1.player_name + " won!");
            }
            else if ( player.turn_number == 2 ) {
                System.out.println(player2.player_name + " won!");
            }
            return;
        }

        depth++;
        if ( player.turn_number == 1 ) {
            game(board, player2);
        }
        else if ( player.turn_number == 2 ) {
            game(board, player1);
        }

    }


    public static void main(String[] args) {
        String[][] board = new String[scale][scale];

        int add = 0;
        for ( int i = 0; i < scale; i++ ) {
            for ( int j = 0; j < scale; j++ ) {
                if ( board[i][j] == null) { //won't remove for the other debugging circumstances.
                    board[i][j] = String.valueOf( j + add + 1);
                }
            }
            add += 3;
        }

        System.out.print("Enter player1 name(X): ");
        String name1 = sc.nextLine();
        player1 = new player_information(1, name1, "X");
        System.out.println();

        System.out.print("Enter player2 name(O): ");
        String name2 = sc.nextLine();
        player2 = new player_information(2, name2, "O");
        System.out.println();

        game(board, player1);

    }
}
