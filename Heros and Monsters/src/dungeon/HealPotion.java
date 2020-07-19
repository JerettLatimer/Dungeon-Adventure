package dungeon;

/*
 * Jerett Latimer
 * CSCD 349
 * Final Project
 * 6/12/2019
 * 
 */

import java.util.*;


public class HealPotion extends RoomContents {

	private int healAmount;
	
	public HealPotion() {
		setHealAmount(generateHealAmount());
	}

	private int generateHealAmount() {
		Random rand = new Random();
		return rand.nextInt(10 + 1) + 5;
	}

	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}
	
}
