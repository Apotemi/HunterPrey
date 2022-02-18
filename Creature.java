public class Creature {
	private String name;

	public Creature() {
		this.name = "no name yet!";
	}

	public Creature(String name) {
		this.name = name;
	}

	public int move() {
		System.out.println("you can't use \"move\" before selecting the creature type");
		return -1;
	}
	public int breed() {
		System.out.println("you can't use \"breed\" before selecting the creature type");
		return -1;
	}
	public boolean starve() {
		System.out.println("you can't use \"starve\" before selecting the creature type");
		return false;
	}

	public String getName() {
		return name;
	}
}
