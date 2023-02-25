import java.util.*;

public class player_information {

    int turn_number;
    String player_name;
    String check;
    boolean Is_Bot = false;

    player_information ( int turn_number, String player_name, String check ) {
        this.turn_number = turn_number;
        this.player_name = player_name;
        if ( this.player_name.equals("") ) {
            this.player_name = "Bot";
            Is_Bot = true;
            System.out.println( "\n" + "The player has replaced to Bot!");
        }
        this.check = check;
        System.out.println();
    }

}
