import java.util.ArrayList;
import java.util.Scanner;

public class Environment {
    private String[][] gridArray;
    private int m;
    private int n;
    private int mode;
    private int obstacles;
    private ArrayList<Hunter> hunter;
    private ArrayList<Prey> prey;
    private ArrayList<UltimateHunter> ultimateHunter;

    public Environment() {
        System.out.println("default grid created. \"10x10\"");
        gridArray = new String[11][11];
    }

    /**
        Creates "m"x"n" sized "gridArray" and add "obstacles" number of obstacle.
        If mode = 0 then it will start an interactive mode,
        else if mode = 1 then it will start a simulation mode.
    */
    public Environment(int m, int n, int obstacles, int mode) {
        this.m = m;
        this.n = n;
        this.mode = mode;
        this.obstacles = obstacles;

        hunter = new ArrayList<Hunter>();
        prey = new ArrayList<Prey>();
        ultimateHunter = new ArrayList<UltimateHunter>();

        gridArray = new String[m+1][n+1];

        for(int i = 1; i < gridArray.length; i++)
            for(int j = 1; j < gridArray[i].length; j++)
                gridArray[i][j] = ".";

        while(obstacles > 0) {
            int i = (int)(1 + ((m) * Math.random()));
            int j = (int)(1 + ((n) * Math.random()));

            if(gridArray[i][j].equals(".")) {
                gridArray[i][j] = "O";
                obstacles--;
            }
        }
    }
    
    /**
        Creates "creature" type of creature and add this
        creature to "gridArray" with "addCreature" method.
    */
    public void putCreature(Creature creature) {
         if (creature instanceof UltimateHunter) {
            ultimateHunter.add((UltimateHunter)creature);
            ultimateHunter.get(ultimateHunter.size()-1).addCreature(gridArray);
            ultimateHunter.get(ultimateHunter.size()-1).getPreyLocations(prey);
        }
        else if(creature instanceof Hunter) {
            hunter.add((Hunter)creature);
            hunter.get(hunter.size()-1).addCreature(gridArray);
        }
        else if (creature instanceof Prey) {
            prey.add((Prey)creature);
            prey.get(prey.size()-1).addCreature(gridArray);
        }
        else
            System.out.println("unexpected creature type!");
    }

    /**
        returns the Creature Object on (x,y) location on gridArray
        throws Exception if there isn't any creature on this location.
    */
    public Creature get(int y, int x) {
        try {
            for(int i = 0; i < prey.size(); i++)
                if(prey.get(i).getX() == y && prey.get(i).getY() == x)
                    return prey.get(i);
            
            for(int i = 0; i < hunter.size(); i++)
                if(hunter.get(i).getX() == y && hunter.get(i).getY() == x)
                    return hunter.get(i);

            for(int i = 0; i < ultimateHunter.size(); i++)
                if(ultimateHunter.get(i).getX() == y && ultimateHunter.get(i).getY() == x)
                    return ultimateHunter.get(i);

            throw new OutOfThisWorldCreatureException("there isn't any creature at (" + y + "," + x + ")");

        } catch (OutOfThisWorldCreatureException e) {
            System.out.print(e.getMessage() + " ");
        }
        return new Creature(); //to avoid "missing return statement" exception :(
    }
    
    public void step() throws OutOfThisWorldCreatureException {
        if(mode == 1)
            stepSimulation();
        else if(mode == 0)
            stepInteractive();
        else{
            System.out.println("unknown game mode, please enter (1) for \"Simulation\"" +
                " or enter (0) for \"Interactive\"");

            Scanner keyboard = new Scanner(System.in);
                this.mode = keyboard.nextInt();
            
            step();
        }
    }

    /**
        First moves all Hunters for one turn and than moves
        all Preys for one turn and also prints "grid" and "info".
    */
    public void stepSimulation() {
        if((hunter.size() == 0 && ultimateHunter.size() == 0) || prey.size() == 0)
          System.exit(0);

        int hunterLength = hunter.size();
        int i = 0;

        while(i < hunterLength) {
            hunter.get(i).move();
            
            if(hunterLength > hunter.size())
                hunterLength = hunter.size();
            else
                i++;
        }

        int ultimateHunterLength = ultimateHunter.size();
        i = 0;

        while(i < ultimateHunterLength) {
            ultimateHunter.get(i).move();
            
            if(ultimateHunterLength > ultimateHunter.size())
                ultimateHunterLength = ultimateHunter.size();
            else
                i++;
        }
        
        int preyLength = prey.size();

        for(i = 0; i < preyLength; i++) {
            prey.get(i).move();
            if(preyLength > prey.size())
                preyLength = prey.size();
        }

        System.out.println();
        this.printGrid();
        this.info();
    }

    /**
        Invokes "stepInteractive" method while user
        give an input "n".
    */
    public void stepInteractive() {
        this.printGrid();

        Scanner keyboard = new Scanner(System.in);
            String repeat = keyboard.next();

        while(repeat.equals("n")) {
            this.stepSimulation();
            repeat = keyboard.next();
        }

        System.exit(0);
    }

    public void info() {
        System.out.print("Prey: " + prey.size() + ", Hunter: " + hunter.size() +
                ", UltimateHunter: " + ultimateHunter.size());
        System.out.println();
    }
    
    public void printGrid() {
        for(int i = 1; i < gridArray.length; i++) {
            for(int j = 1; j < gridArray[i].length; j++)
                System.out.print(gridArray[i][j] + " ");

            System.out.println();
        }
    }

    public void removePrey(int x, int y) {
        for(int i = 0; i < prey.size(); i++)
            if(prey.get(i).getX() == x && prey.get(i).getY() == y)
                prey.remove(i);
    }
    public void removeHunter(int x, int y) {
        for(int i = 0; i < hunter.size(); i++)
            if(hunter.get(i).getX() == x && hunter.get(i).getY() == y)
                hunter.remove(i);

    }
    public void removeUltimateHunter(int x, int y) {
        for(int i = 0; i < ultimateHunter.size(); i++)
            if(ultimateHunter.get(i).getX() == x && ultimateHunter.get(i).getY() == y)
                ultimateHunter.remove(i);

    }

    public int getM() { return m; }
    public int getN() { return n; }
    public String[][] getGridArray() { return gridArray; }
    public int getObstacles() { return obstacles; }
    public int getMode() { return mode; }

}
