public class Hunter extends Creature{
	protected int withoutEating;

	public Hunter() {
		super("Hunter");
		withoutEating = 0;
	}

	public Hunter(Environment grid) {
		super("Hunter" ,grid);
		withoutEating = 0;
	}

	/**
		This constructor invokes if and only if the Hunter is borned
		from another Hunter.
	*/
	public Hunter(Environment grid, int x, int y, boolean newBie) {
		super("Hunter", grid, x, y, newBie);
		withoutEating = 0;
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
		withoutEating++;
		turn++;

		newBie = false;
		one = true; two = true; three = true; four = true;

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
		else if(turn == 8)
			breed();

		return moveDirection;
	}

	/**
		Checks if there is any possible location to breed with "isAvailable" method
		If it is possible to breed, it breeds a new Creature of it's own type.
	*/
	@Override
	protected void breed() {
		one = true; two = true; three = true; four = true;

		if(isAvailable(1,true) || isAvailable(2,true) || isAvailable(3,true) || isAvailable(4,true))
			grid.putCreature(new Hunter(grid, this.x, this.y, true));
		
		turn = 0;
	}

	/**
		Checks if there is any Prey available to eat,
		if it is, then it removes this Prey from grid
		but not remove Prey object and returns true.
	*/
	protected boolean checkForPrey() {
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
}
