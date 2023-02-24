import java.util.*;

public class TicTacToe {

    public static int scale = 3; //scale * scale

    public static player_information player1 = new player_information(1, "MS", "X");
    public static player_information player2 = new player_information(2, "", "O");

    public static player_information computer = null;


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

    public static int depth = 0; //how far the game is going. 0 ~ 8. scale^2 - 1

    public static Scanner sc = new Scanner(System.in);
    public static boolean Return = false;

    public static void game(String[][] board, player_information player) {

        if ( depth == scale * scale - 1 ) {
            System.out.println("Drawn!");
        }

        printBoard(board);
        if ( Return ) {
            return;
        }
        if ( Objects.equals(player, computer) ) {
            TicTacToe dummy = new TicTacToe();
            int i = dummy.Bot(computer, board, depth).index - 1; //the index may not be saved properly
            board[ i / 3 ][ i % 3 ] = player.check; //throws ArrayIndexOutOfBounds error(-1)
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

    public class returnV {
        int max;
        int index;
        returnV(int max_, int index_) {
            max = max_;
            index = index_;
        }
    }


    public returnV Bot(player_information player, String[][] board, int depth) { //draw

        player_information yourturn = null;

        if ( Objects.equals(player, player2) ) {
            yourturn = player1;
        }
        else if ( Objects.equals(player, player1) ) {
            yourturn = player2;
        }

        int mymax = 0; //-1 or 0 or 1, who wins/computer
        int myindex = 0; //if you set the your mark, O or X
        for ( int i = 0; i < scale; i++ ) {  //scale = 3
            for (int j = 0; j < scale; j++ ) {

                if ( '1' <= board[i][j].charAt(0) && board[i][j].charAt(0) <= '9' ) {
                    board[i][j] = player.check;
                    String s = String.valueOf(i * 3 + (j % 3) + 1);
                    if ( Is_Winner(board, player)) {
                        int a = -1;
                        if ( Objects.equals(player, computer) ) {
                            a = 1;
                        }
                        board[i][j] = s;
                        return new returnV(a, i * 3 + (j % 3) + 1);
                    }

                    if ( depth == 8 ) {
                        return new returnV(0, i * 3 + (j % 3) + 1);
                    }

                    returnV your = Bot (yourturn, board, depth+1);
                    board[i][j] = s;


                    //player == computer
                    if (player.turn_number == computer.turn_number && mymax <= your.max) {
                        mymax = your.max;
                        myindex = your.index;
                    }
                    else if ( player.turn_number != computer.turn_number && mymax >= your.max ) {
                        mymax = your.max;
                        myindex = your.index;
                    }

                }

            }

        }

        return new returnV(mymax, myindex);
    }


    public static void main(String[] args) {
        String[][] board = new String[scale][scale];
        /*String[][] board = { {player1.check, player2.check, player1.check},
                {player2.check, player2.check, player1.check}, {null, null, null}};*/
        /*String[][] board = { {player2.check, null, player1.check}, {player1.check, null, null},
                {player1.check, player2.check, player2.check}};*/

        int add = 0;
        for ( int i = 0; i < scale; i++ ) {
            for ( int j = 0; j < scale; j++ ) {
                if ( board[i][j] == null) { //won't remove for the other debugging circumstances.
                    board[i][j] = String.valueOf( j + add + 1);
                }
            }
            add += 3;
        }

        /*computer = player1; //will be removed if Bot algorithm has completed.
        TicTacToe dummy = new TicTacToe();
        int debug = dummy.Bot(player1, board, 1).index;
        System.out.println(debug); //has to print 9
        System.out.println(dummy.Bot(player1, board, 1).max); //has to print 9
        printBoard(board);*/

        System.out.print("Enter player1 name(X): ");
        player1.player_name = sc.nextLine();
        System.out.println();

        System.out.print("Enter player2 name(O): ");
        player2.player_name = sc.nextLine();
        System.out.println();
        if ( player2.player_name.equals("") ) {
            player2.player_name = "Bot";
            computer = player2;
            System.out.println("The Bot has replaced player2!" + "\n");
        }

        game(board, player1);
    }
}
