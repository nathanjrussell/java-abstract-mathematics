import Mathematics.Groups.Group;
import Mathematics.Groups.GroupException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws GroupException {
        int[][] opTable =
                {
                    {0,1,2},
                    {1,2,0},
                    {2,0,1}
                };
        String[][] strOpTable = {
                {"0","1","2"},
                {"1","2","0"},
                {"1","0","1"}
        };

        Integer[][] intOpTable = {
                {0,1,2},
                {1,2,0},
                {2,0,5}
        };

            Group<Integer> G = new Group<Integer>(intOpTable);
            System.out.println(G.getIdentityElement());
            System.out.println(G.getOrder());


    }
}