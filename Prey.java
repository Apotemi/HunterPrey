public class Prey extends Creature{
	private int x;
	private int y;
	private Environment grid;
	private String[][] gridArray;
	private int turn;
	private int moveDirection;
	public boolean newBie;
	public boolean one,two,three,four;

	public Prey() {
		super("Prey");
		this.x = 0;
		this.y = 0;
		this.turn = 0;
		this.newBie = false;
	}

	public Prey(Environment grid) {
		super("Prey");
		this.grid = grid;
		this.x = 0;
		this.y = 0;
		this.turn = 0;
		this.newBie = false;
	}

	/**
		This constructor invoked if and only if the Prey is borned
		from other Prey.
	*/
	public Prey(Environment grid, int x, int y, boolean newBie) {
		super("Prey");
		this.grid = grid;
		this.x = x;
		this.y = y;
		this.turn = 0;
		this.newBie = newBie;
	}

	/**
		Puts a new Prey to Grid,
		If this method invokes, it always put a new Prey
		because we already checked if it is possible to put a Prey
		in method "breed".
	*/
	public void putPrey(String[][] gridArray) {
		this.gridArray = gridArray;
		one = true; two = true; three = true; four = true;

		if(newBie) {
			do {
				moveDirection = (int)(1 + (Math.random()*4));
			} while(!isAvailable(moveDirection, false) && (one || two || three || four));
		} else {
			do {
        		x = (int) (1 + ((grid.getM()) * Math.random()));
        		y = (int) (1 + ((grid.getN()) * Math.random()));
        	} while(!gridArray[x][y].equals("."));
        }

        gridArray[x][y] = "P";
	}

	/**
		Checks if there is any possible location to move with "isAvailable" method
		If it is possible to move, it moves and returns the direction that it moved
		and it also checks how many times did this Prey move.
	*/
	@Override
	public int move() {
		newBie = false;
		one = true; two = true; three = true; four = true;

		do {
			moveDirection = (int)(1 + (Math.random()*4));
		} while(!isAvailable(moveDirection, false) && (one || two || three || four));

		gridArray[x][y] = "P";
		turn++;

		if(turn == 3)
			breed();

		return moveDirection;
	}
	/**
		Checks if there is any possible location to breed with "isAvailable" method
		If it is possible to breed, it breeds a new Creature of it's own type.
	*/
	@Override
	public int breed() {
		one = true; two = true; three = true; four = true;

		if(isAvailable(1,true) || isAvailable(2,true) || isAvailable(3,true) || isAvailable(4,true))
			grid.putCreature(new Prey(grid, this.x, this.y, true));
		
		turn = 0;
		
		return -1;
	}

	/**
		Checks if there is any possible location to move.
		If breeding is false, then it changes Creature's location to an available one
		If breeding is true, then it only checks if there is any possible location to breed
		return true if there is any location, else return false.
	*/
	private boolean isAvailable(int i, boolean breeding) {
		boolean isAvailable = true;

		if(i == 1) {
			if(x-1 <= 0)
				isAvailable = false;
			else if(!gridArray[x-1][y].equals("."))
				isAvailable = false;
			if(isAvailable && !breeding) {
				if(!newBie)
					gridArray[x][y] = ".";
				x--;
			}
		one = false;
		}
		else if(i == 2) {
			if(x+1 > grid.getM())
				isAvailable = false;
			else if(!gridArray[x+1][y].equals("."))
				isAvailable = false;
			if(isAvailable && !breeding) {
				if(!newBie)
					gridArray[x][y] = ".";
				x++;
			}
		two = false;
		}
		else if(i == 3) {
			if(y+1 > grid.getN()) 
				isAvailable = false;
			else if(!gridArray[x][y+1].equals("."))
				isAvailable = false;
			if(isAvailable && !breeding) {
				if(!newBie)
					gridArray[x][y] = ".";
				y++;
			}
		three = false;
		}
		else if(i == 4) {
			if(y-1 <= 0 )
				isAvailable = false;
			else if(!gridArray[x][y-1].equals("."))
				isAvailable = false;
			if(isAvailable && !breeding) {
				if(!newBie)
					gridArray[x][y] = ".";
				y--;
			}
		four = false;
		}
		
		return isAvailable;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getMoveDirection() {
		return moveDirection;
	}
}

