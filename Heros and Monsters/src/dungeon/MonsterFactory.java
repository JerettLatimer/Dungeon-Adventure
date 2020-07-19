package dungeon;

/*
 * Jerett Latimer
 * CSCD 349
 * Final Project
 * 6/12/2019
 * 
 */


public class MonsterFactory {
	
	public static Monster createMonster(String type) {
		Monster monster = null;
		
		if(type.equals("Ogre")) {
			monster = new Ogre();
		}
		else if(type.equals("Gremlin")) {
			monster = new Gremlin();
		}
		else if(type.equals("Skeleton")) {
			monster = new Skeleton();
		}
		
		return monster;	
	}
	
}
