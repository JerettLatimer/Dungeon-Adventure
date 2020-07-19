package dungeon;

/*
 * Jerett Latimer
 * CSCD 349
 * Final Project
 * 6/12/2019
 * 
 */

public class RoomContents {
	
	private HealPotion healPotion;
	private Pit pit;
	private Monster monster;
	private String pillar;
	
	public RoomContents() {
		// Creates empty room with 0 items, contents are generated in Room Class.
	}
	
	public String toString() {
		String result = "";
		if(this.pillar != null) {
			result += "0";
		}
		else if(this.monster != null) {
			result += "X";
		}
		else if(this.pit != null) {
			result += "P";
		}
		else if(this.healPotion != null) {
			result += "H";
		}
		else {
			result += "E";
		}
		
		return result;
	}

	public HealPotion getHealPotion() {
		return healPotion;
	}

	public void setHealPotion(HealPotion healPotion) {
		this.healPotion = healPotion;
	}

	public Pit getPit() {
		return pit;
	}

	public void setPit(Pit pit) {
		this.pit = pit;
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	public String getPillar() {
		return pillar;
	}

	public void setPillar(String pillar) {
		this.pillar = pillar;
	}
}
