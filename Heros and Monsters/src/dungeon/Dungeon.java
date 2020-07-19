package dungeon;
/*

 * Jerett Latimer
 * CSCD 349
 * Final Project
 * 6/12/2019
 * 
 */

import java.util.Random;
import java.util.Scanner;

public class Dungeon
{
	private Room heroLocation;
	private Room entrance, exit;
	private Room[][] map;
	
	public Dungeon() {
		this.map = new Room[5][5];
		this.setHeroLocation(getMap());
		this.setEntrance(getHeroLocation());
		this.setExit(getMap());
		this.setMap(createDungeon(this.map));
	}
	
	static Scanner kb = new Scanner(System.in);
    
	private static Room[][] createDungeon(Room[][] dungeon) {
		
		setPillars(dungeon);

		// sets all empty rooms after assigning the exit, pillars, and the hero's starting point/entrance
		for(int i = 0; i < dungeon.length; i++) {
			for(int j = 0; j < dungeon[i].length; j++) {
				if((i == 0 || i == 4 || j == 0 || j == 4) && dungeon[i][j] == null) {
					// edge room
					dungeon[i][j] = new Room(true, false, false, false);
				}
				else if(dungeon[i][j] == null) {
					// interior room
					dungeon[i][j] = new Room(false, false, false, false);
				}
				setEdges(dungeon, i, j);
				setIndex(dungeon, i, j);
			}
		}
		return dungeon;
	}

	private static void setIndex(Room[][] dungeon, int i, int j) {
		dungeon[i][j].setRow(i);
		dungeon[i][j].setColumn(j);
	}

	// Randomly sets the exit in the dungeon on the exterior
	private void setExit(Room[][] dungeon) {
		boolean loop = true;
		while(loop) {
			Random rand = new Random();
			int row;
			// sets row to either 1 or 4
			boolean rowBoolean = rand.nextBoolean();
			if(rowBoolean) {
				row = 1;
			}
			else {
				row = 4;
			}
			// sets column to a random number 0 through 4
			int column = rand.nextInt(5);
			
			if(column == 0 || column == 4) {
				row = rand.nextInt(5);
			}
				
			if(dungeon[row][column] == null) {
				// set exit
				dungeon[row][column] = new Room(true, true, false, false);
				this.exit = dungeon[row][column];
				loop = false;
			}	
		}
	}


	private static void setPillars(Room[][] dungeon) {
		final String[] pillars = {"Encapsulation", "Polymorphism", "Inheritance", "Abstraction"};
		
		for(int i = 0; i < 4; i++) {
			boolean loop = true;
			while(loop) {
				Random rand = new Random();
				int row = rand.nextInt(5);
				int column = rand.nextInt(5);
				if(dungeon[row][column] == null) {
					if((row == 0 || row == 4 || column == 0 || column == 4)) {
						// edge room
						dungeon[row][column] = new Room(true, false, false, true);
						if(dungeon[row][column].isEmpty()) {
							dungeon[row][column].setEmpty(false);
						}
						dungeon[row][column].getContents().setPillar(pillars[i]);
						dungeon[row][column].setItemCount(dungeon[row][column].getItemCount() + 1);
						loop = false;
					}
					else {
						// interior room
						dungeon[row][column] = new Room(false, false, false, true);
						if(dungeon[row][column].isEmpty()) {
							dungeon[row][column].setEmpty(false);
						}
						dungeon[row][column].getContents().setPillar(pillars[i]);
						dungeon[row][column].setItemCount(dungeon[row][column].getItemCount() + 1);
						loop = false;
					}
				}
			}
		}
	}
	
	
	private static void setEdges(Room[][] dungeon, int i, int j) {
			if(i == 0) {
				dungeon[i][j].getNorth().setWall(true);
			}
			if(i == 4) {
				dungeon[i][j].getSouth().setWall(true);
			}
			if(j == 0) {
				dungeon[i][j].getWest().setWall(true);
			}
			if(j == 4) {
				dungeon[i][j].getEast().setWall(true);
			}
	}

	public Room getHeroLocation() {
		return heroLocation;
	}


	public void setHeroLocation(Room heroLocation) {
		this.heroLocation = heroLocation;
	}

	public Room[][] getMap() {
		return map;
	}

	public void setMap(Room[][] map) {
		this.map = map;
	}

	public Room setHeroLocation(Room[][] map) {
		Room location = null;
		boolean loop = true;
		while(loop) {
			Random rand = new Random();
			int i = rand.nextInt(5);
			int j = rand.nextInt(5);
			if(map[i][j] == null) {
				map[i][j] = new Room(false, false, true, false);
				this.heroLocation = map[i][j];
				loop = false;
			}
		}
		
		return location;
	}

	public String toString() {
		String result = "";
		String northSouthDoor = "*-*";
		String northSouthWall = "***";
		String eastWestDoor =  "|";
		String eastWestWall = "*";
		Room[][] dungeon = this.getMap();
		
		result += "Dungeon Info:\n\n"
				+ "Items:\n"
				+ "M = Multiple Items\n"
				+ "P = Pit\n"
				+ "I = Entrance\n"
				+ "O = Exit\n"
				+ "H = Healing Potion\n"
				+ "E = Empty Room\n"
				+ "X = Monster\n"
				+ "0 = Pillar of OO\n\n"
				+ "Doors/Walls:\n"
				+ "North/South Wall = ***\n"
				+ "North/South Door = *-*\n"
				+ "East/West Wall = *\n"
				+ "East/West Door = |\n\n"
				+ "Current Dungeon:\n\n";
		
		for(int i = 0; i < dungeon.length; i++) {
			int x = 0;
			while(x < 5) {
				if(dungeon[i][x].getNorth().isWall()) {
					result += northSouthWall;
				}
				else {
					result += northSouthDoor;
				}
				result += " ";
				x++;
			}
			result += "\n";
			x = 0;
			while(x < 5) {
				// West wall
				if(dungeon[i][x].getWest().isWall()) {
					result += eastWestWall;
				}
				else {
					result += eastWestDoor;
				}
				
				// Room contents
				if(dungeon[i][x].getItemCount() > 1) {
					result += "M";
				}
				else if(dungeon[i][x].isEntrance()) {
					result += "I";
				}
				else if(dungeon[i][x].isExit()) {
					result += "O";
				}
				else {
					result += dungeon[i][x].getContents().toString();
				}
				
				if(dungeon[i][x].getEast().isWall()) {
					result += eastWestWall;
				}
				else {
					result += eastWestDoor;
				}
					
				result += " ";
				x++;
			}
			result += "\n";
			x = 0;
			while(x < 5) {
				if(dungeon[i][x].getSouth().isWall()) {
					result += northSouthWall;
				}
				else {
					result += northSouthDoor;
				}
				result += " ";
				x++;
			}
			result += "\n";
		}
		return result;
	}

	public Room getExit() {
		return exit;
	}

	public void setExit(Room exit) {
		this.exit = exit;
	}

	public Room getEntrance() {
		return entrance;
	}

	public void setEntrance(Room entrance) {
		this.entrance = entrance;
	}
	
}//end Dungeon class