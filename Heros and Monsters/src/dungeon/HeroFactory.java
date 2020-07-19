package dungeon;

/*
 * Jerett Latimer
 * CSCD 349
 * Final Project
 * 6/12/2019
 * 
 */

import java.util.*;

public class HeroFactory {
	
	static Scanner kb = new Scanner(System.in);
	
	public static Hero createHero(String type) {
		Hero hero = null;
		
		if(type.equals("Warrior")) {
			hero = new Warrior();
		}
		else if(type.equals("Sorceress")) {
			hero = new Sorceress();
		}
		else if(type.equals("Thief")) {
			hero = new Thief();
		}
		
		hero.setName(getName());
		
		return hero;	
	}
	
	public static String getName() {
		String result = "";
		System.out.print("Enter character name: ");
		result = kb.nextLine();
		return result;
	 }//end getName method
}
