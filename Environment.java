import java.util.ArrayList;
import java.util.Scanner;

public class Environment {
    private String[][] grid;
    private int m;
    private int n;
    private int mode;
    private int obstacles;
    private ArrayList<Hunter> hunter;
    private ArrayList<Prey> prey;
    private ArrayList<UltimateHunter> ultimateHunter;

    public Environment() {
        System.out.println("default grid created. \"10x10\"");
        grid = new String[11][11];
    }

    public Environment(int m, int n, int obstacles, int mode) {
        this.m = m;
        this.n = n;
        this.mode = mode;
        this.obstacles = obstacles;

        hunter = new ArrayList<Hunter>();
        prey = new ArrayList<Prey>();
        ultimateHunter = new ArrayList<UltimateHunter>();

        grid = new String[m+1][n+1];

        for(int i = 1; i < grid.length; i++)
            for(int j = 1; j < grid[i].length; j++)
                grid[i][j] = ".";

        while(obstacles > 0) {
            int i = (int)(1 + ((m) * Math.random()));
            int j = (int)(1 + ((n) * Math.random()));
            if(grid[i][j].equals(".")) {
                grid[i][j] = "O";
                obstacles--;
            }
        }
    }
    
    public void putCreature(Creature creature) {
        if(creature instanceof Hunter) {
            hunter.add((Hunter)creature);
            hunter.get(hunter.size()-1).putHunter(grid);
        }
        else if (creature instanceof Prey) {
            prey.add((Prey)creature);
            prey.get(prey.size()-1).putPrey(grid);
        }
        else
            System.out.println("unexpected creature type!");
    }

    
    public Creature get(int y, int x) {
        try {
            for(int i = 0; i < prey.size(); i++)
                if(prey.get(i).getX() == y && prey.get(i).getY() == x)
                    return prey.get(i);
            
            for(int i = 0; i < hunter.size(); i++)
                if(hunter.get(i).getX() == y && hunter.get(i).getY() == x)
                    return hunter.get(i);

            throw new OutOfThisWorldCreatureException("there isn't any creature at (" + y + "," + x + ")");
        } catch (OutOfThisWorldCreatureException e) {
            System.out.print(e.getMessage() + " ");
        }
        return new Creature(); //to avoid "missing return statement" exception :(
    }
    

    public void step() {
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

    public void stepSimulation() {
        if(hunter.size() == 0 || prey.size() == 0)
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
        for(int i = 1; i < grid.length; i++) {
            for(int j = 1; j < grid[i].length; j++)
                System.out.print(grid[i][j] + " ");
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

    public int getM() {
        return m;
    }
    public int getN() {
        return n;
    }
}
