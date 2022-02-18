public class Creature {
	protected String name;
	protected String[][] gridArray;
	protected int x, y;
	protected int turn;
	protected int moveDirection;
	protected boolean newBie;
	protected boolean one, two, three, four;
	protected Environment grid;

	public Creature() {
		this.name = "no name yet!";
		this.x = 0;
		this.y = 0;
		this.turn = 0;
		this.newBie = false;
	}

	public Creature(String name) {
		this();
		this.name = name;
	}

	public Creature(String name, Environment grid) {
		this();
		this.name = name;
		this.grid = grid;
	}

	/**
		Creates a Creature with it's class name, the grid it belongs to,
		(x,y) location.
		If this creature borned from another creature then newBie will be true.
	*/
	public Creature(String name, Environment grid, int x, int y, boolean newBie) {
		this.name = name;
		this.grid = grid;
		this.x = x;
		this.y = y;
		this.newBie = newBie;
	}

	/**
		Puts a new Hunter to Grid,
		If this method invokes, it always puts a new Hunter
		because we already checked if it is possible to put a Hunter
		in method "breed".
	*/
	public void addCreature(String[][] gridArray) {
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

        if(this instanceof Hunter || this instanceof UltimateHunter)
        	gridArray[x][y] = "H";
		else if(this instanceof Prey)
			gridArray[x][y] = "P";
		}

	/**
		Checks if there is any possible location to move.
		If breeding is false, then it changes Creature's location to an available one
		If breeding is true, then it only checks if there is any possible location to breed
		returns true if there is any location, else returns false.
	*/
	protected boolean isAvailable(int i, boolean breeding) {
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

	public int move() {
		System.out.println("you can't use \"move\" before selecting the creature type");
		return -1;
	}
	protected void breed() {
		System.out.println("you can't use \"breed\" before selecting the creature type");
	}
	protected boolean starve() {
		System.out.println("you can't use \"starve\" before selecting the creature type");
		return false;
	}

	public String getName() { return name; }
	public String[][] getGridArray() { return gridArray; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getTurn() { return turn; }
	public int getMoveDirection() { return moveDirection; }
	public boolean getNewBie() { return newBie; }
	public Environment getGrid() { return grid; }
}
