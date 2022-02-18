public class Prey extends Creature{

	public Prey() {
		super("Prey");
	}

	public Prey(Environment grid) {
		super("Prey", grid);
	}

	/**
		This constructor invoked if and only if the Prey is borned
		from another Prey.
	*/
	public Prey(Environment grid, int x, int y, boolean newBie) {
		super("Prey", grid, x, y, newBie);
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
	protected void breed() {
		one = true; two = true; three = true; four = true;

		if(isAvailable(1,true) || isAvailable(2,true) || isAvailable(3,true) || isAvailable(4,true))
			grid.putCreature(new Prey(grid, this.x, this.y, true));
		
		turn = 0;
		}
}

