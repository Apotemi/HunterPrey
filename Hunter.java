public class Hunter extends Creature{
	private int x;
	private int y;
	private Environment grid;
	private String[][] gridArray;
	private int turn;
	private int moveDirection;
	public boolean newBie;
	public int withoutEating;
	public boolean one,two,three,four;

	public Hunter() {
		super("Hunter");
		this.x = 0;
		this.y = 0;
		this.turn = 0;
		this.newBie = false;
		this.withoutEating = 0;
	}

	public Hunter(Environment grid) {
		super("Hunter");
		this.grid = grid;
		this.x = 0;
		this.y = 0;
		this.turn = 0;
		this.newBie = false;
		this.withoutEating = 0;
	}

	/**
		This constructor invokes if and only if the Hunter is borned
		from other Hunter.
	*/
	public Hunter(Environment grid, int x, int y, boolean newBie) {
		super("Hunter");
		this.grid = grid;
		this.x = x;
		this.y = y;
		this.turn = 0;
		this.newBie = newBie;
		this.withoutEating = 0;
	}

	/**
		Puts a new Hunter to Grid,
		If this method invokes, it always puts a new Hunter
		because we already checked if it is possible to put a Hunter
		in method "breed".
	*/
	public void putHunter(String[][] gridArray) {
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
        
        gridArray[x][y] = "H";
	}

	/**
		First it checks if there is any possible Prey to attack with "checkForPrey"
		method. If there is any possible Prey, then it directly moves this way and
		deletes this Prey object. If there isn't any Prey available, then it checks
		if there is any possible location to move with "isAvailable" method.
		If it is possible to move, it moves and returns the direction that it moved
		and it also checks how many times did this Hunter move.
	*/
	@Override
	public int move() {
		newBie = false;
		one = true; two = true; three = true; four = true;

		withoutEating++;
		turn++;

		if(checkForPrey()) {
			grid.removePrey(x,y);
			withoutEating = 0;
		}
		else {
			do {
				moveDirection = (int)(1 + (Math.random()*4));
			} while(!isAvailable(moveDirection, false) && (one || two || three || four));
		}

		gridArray[x][y] = "H";

		if(withoutEating == 3) {
			grid.removeHunter(x,y);
			gridArray[x][y] = ".";
		}

		if(turn == 8)
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
			grid.putCreature(new Hunter(grid, this.x, this.y, true));
		
		turn = 0;

		return -1;
	}

	/**
		Checks if there is any Prey available to eat,
		if it is, then it removes this Prey from grid
		but not remove Prey object and returns true.
	*/
	private boolean checkForPrey() {
		boolean preyAvailable = false;

		if(x-1 > 0 && gridArray[x-1][y].equals("P")) {
			gridArray[x][y] = ".";
			x--;
			preyAvailable = true;
			moveDirection = 1;
		}
		else if(x+1 <= grid.getM() && gridArray[x+1][y].equals("P")) {
			gridArray[x][y] = ".";
			x++;
			preyAvailable = true;
			moveDirection = 2;
		}
		else if(y-1 > 0 && gridArray[x][y-1].equals("P")) {
			gridArray[x][y] = ".";
			y--;
			preyAvailable = true;
			moveDirection = 3;
		}
		else if(y+1 <= grid.getN() && gridArray[x][y+1].equals("P")) {
			gridArray[x][y] = ".";
			y++;
			preyAvailable = true;
			moveDirection = 4;
		}
		
		return preyAvailable;
	}

	/**
		Checks if there is any possible location to move.
		If breeding is false, then it changes Creature's location to an available one
		If breeding is true, then it only checks if there is any possible location to breed
		returns true if there is any location, else returns false.
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
}
