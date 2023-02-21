public class NewClass {

    public static void main(String[] args) {

        System.out.println(sigma(0, 100, 1));
    }

    public static int sigma(int index, int goal, int group) {
        for ( int i = 0; i < group; i++ ) {
            index++;
            if ( index == goal + 1 ) {
                return i;
            }
        }
        return sigma(index, goal, group + 1);
    }
}
