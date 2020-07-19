package dungeon;

/*
 * Jerett Latimer
 * CSCD 349
 * Final Project
 * 6/12/2019
 * 
 */


public class Room {

	private RoomContents contents;
	private Door north, south, east, west;
	private boolean exit, entrance, hasPillar;
	private boolean isEdge; // true = edge room, false = interior room;
	private boolean isEmpty;
	private int itemCount, row, column;
	
	public Room(boolean isEdge, boolean isExit, boolean isEntrance, boolean hasPillar) {
		setContents(new RoomContents());
		setEdge(isEdge);
		setExit(isExit);
		setEntrance(isEntrance);
		setItemCount(0);
		setHasPillar(hasPillar);
		generateContents();
		setNorth(new Door());
		setSouth(new Door());
		setEast(new Door());
		setWest(new Door());
	}

	
	private void generateContents() {
		if(this.isEntrance() || this.isExit() || this.isHasPillar()) {
			return;
		}
		
		double chance = (Math.random());
		
		if(chance > 0.10) {
			int choice = (int) (Math.random() * 4) + 1;
			
			switch(choice) {
				case 1: this.contents.setHealPotion(new HealPotion());
						break;
				
				case 2: this.contents.setPit(new Pit());
						break;
				
				case 3: this.contents.setMonster(MonsterFactory.createMonster(Monster.generateMonsterType()));
						break;
				
						// empty room
				case 4: this.setEmpty(true);
						break;
			}
			if(choice != 4) {
				this.setEmpty(false);
				this.setItemCount(1);
			}
		}
		else {
			this.contents.setPit(new Pit());
			this.contents.setHealPotion(new HealPotion());
			this.setEmpty(false);
			this.setItemCount(2);
		}
	}
	
	public String toString() {
		String result = "";
		String northSouthDoor = "*-*";
		String northSouthWall = "***";
		String eastWestDoor =  "|";
		String eastWestWall = "*";
		
		if(this.north.isWall()) {
			result += northSouthWall + "\n";
		}
		else {
			result += northSouthDoor +"\n";
		}
		
		if(this.west.isWall()) {
			result += eastWestWall;
		}
		else {
			result += eastWestDoor;
		}
		
		if(this.itemCount > 1) {
			result += "M";
		}
		else if(this.isEntrance()) {
			result += "I";
		}
		else if(this.isExit()) {
			result += "O";
		}
		else {
			result += this.getContents().toString();
		}
		
		if(this.east.isWall()) {
			result += eastWestWall + "\n";
		}
		else {
			result += eastWestDoor + "\n";
		}
		
		if(this.south.isWall()) {
			result += northSouthWall;
		}
		else {
			result += northSouthDoor;
		}
		
		return result;
	}

	public RoomContents getContents() {
		return contents;
	}

	public void setContents(RoomContents contents) {
		this.contents = contents;
	}

	public Door getNorth() {
		return north;
	}

	public void setNorth(Door north) {
		this.north = north;
	}

	public Door getSouth() {
		return south;
	}

	public void setSouth(Door south) {
		this.south = south;
	}

	public Door getEast() {
		return east;
	}

	public void setEast(Door east) {
		this.east = east;
	}

	public Door getWest() {
		return west;
	}

	public void setWest(Door west) {
		this.west = west;
	}

	public boolean isEdge() {
		return isEdge;
	}

	public void setEdge(boolean isEdge) {
		this.isEdge = isEdge;
	}
	
	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isEntrance() {
		return entrance;
	}

	public void setEntrance(boolean entrance) {
		this.entrance = entrance;
	}


	public int getItemCount() {
		return itemCount;
	}


	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}


	public boolean isEmpty() {
		return isEmpty;
	}


	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}


	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getColumn() {
		return column;
	}


	public void setColumn(int column) {
		this.column = column;
	}


	public boolean isHasPillar() {
		return hasPillar;
	}


	public void setHasPillar(boolean hasPillar) {
		this.hasPillar = hasPillar;
	}
}
