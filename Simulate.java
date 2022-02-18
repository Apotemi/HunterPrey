import java.util.Scanner;

public class Simulate {
    public static void main(String[] args) {
        int m = 5, n = 5;

        int mode = 0;
        int obstacles = 0;

        Environment grid = new Environment(m,n,obstacles,mode);
        
        int numberOfHunters = 0;
        
        Creature org;
        
        for (int i = 0; i < numberOfHunters; i++) {
            org = new Hunter(grid);

            grid.putCreature(org);
        }

        int numberOfPreys = 1;
        
        for (int i = 0; i < numberOfPreys; i++) {
            org = new Prey(grid);

            grid.putCreature(org);
        }

        int numberOfUltimateHunters = 1   ;
        
        
        for (int i = 0; i < numberOfUltimateHunters; i++) {
            org = new UltimateHunter(grid);

            grid.putCreature(org);
        }

        int simulationSteps = 10000;

        for (int i = 0; i < simulationSteps; i++) {
            try{
                grid.step();
            } catch (OutOfThisWorldCreatureException e) {
                System.out.println(e.getMessage());
            }
            //if (i % 100 == 0)
                //grid.info();
        }

        /**while(true) {
            try{
                grid.step();
            } catch (OutOfThisWorldCreatureException e) {
                System.out.println(e.getMessage());
            }
        }*/
    }
}
