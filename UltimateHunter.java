import java.util.ArrayList;

public class UltimateHunter extends Hunter{
	private ArrayList<Prey> prey;

	public UltimateHunter() {
		super(); 
		this.name = "UltimateHunter";
	}

	public UltimateHunter(Environment grid) {
		super(grid);
		this.name = "UltimateHunter";
	}

	/**
		This constructor invokes if and only if the UltimateHunter is borned
		from another UltimateHunter.
	*/
	public UltimateHunter(Environment grid, int x, int y, boolean newBie) {
		super(grid, x, y, newBie);
		this.name = "UltimateHunter";
	}

	/**
		First it checks if there is any possible Prey to attack with "checkForPrey"
		method. If there is any possible Prey, then it directly moves this way and
		deletes this Prey object. If there isn't any Prey available, then it computes
		the nearest Prey's location with "specialMove" method and then moves this way.
		If it is possible to move, it moves and returns the direction that it moved
		and it also checks how many times did this UltimateHunter move. 
		
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
		else if(!specialMove()) {
			do {
				moveDirection = (int)(1 + (Math.random()*4));
			} while(!isAvailable(moveDirection, false) && (one || two || three || four));
		}

		gridArray[x][y] = "H";

		if(withoutEating == 3) {
			grid.removeUltimateHunter(x,y);
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
			grid.putCreature(new UltimateHunter(grid, this.x, this.y, true));
		
		turn = 0;
	}

	/**
		Compares X and Y coordinates between UltimateHunter and
		the "nearestPrey", than decides which way to go.
	*/
	private boolean specialMove() {
		if (nearestPrey().getX() < this.getX()) {
			if(isAvailable(1, false)){
				moveDirection = 1;
				return true;
			}
		}
		else if (nearestPrey().getX() > this.getX()) {
			if(isAvailable(2, false)) {
				moveDirection = 2;
				return true;
			}
		}
	
		if (nearestPrey().getY() > this.getY()) {
			if(isAvailable(3,false)) {
				moveDirection = 3;
				return true;
			}
		}
		else if (nearestPrey().getY() < this.getY()) {
			if(isAvailable(4, false)){
				moveDirection = 4;
				return true;
			}
		}
	
		return false;
	}

	/**
		Finds the nearest located Pray to this Ultimate Hunter
		and returns this Prey object.
	*/
	private Prey nearestPrey() {
		Prey nearestPrey = new Prey();
		nearestPrey = prey.get(0);

		double min = Math.pow(this.getX() - prey.get(0).getX(), 2) + Math.pow(this.getY() - prey.get(0).getY(), 2);

		for(int i = 1; i < prey.size(); i++) {
			double temp = Math.pow(this.getX() - prey.get(i).getX(), 2) + Math.pow(this.getY() - prey.get(i).getY(), 2);
			if(temp < min)
				nearestPrey = prey.get(i);
		}
	
		return nearestPrey;
	}

	public void getPreyLocations(ArrayList<Prey> prey) {
		this.prey = prey;
	}
}
