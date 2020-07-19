package dungeon;

/*
 * Jerett Latimer
 * CSCD 349
 * Final Project
 * 6/12/2019
 * 
 */

import java.util.Random;

public class Pit extends RoomContents {
	
	private int damageAmount;
	
	public Pit() {
		setDamageAmount(generateDamageAmount());
	}

	private int generateDamageAmount() {
		Random rand = new Random();
		return rand.nextInt(20 + 1) + 1;
	}

	public int getDamageAmount() {
		return damageAmount;
	}

	public void setDamageAmount(int damageAmount) {
		this.damageAmount = damageAmount;
	}
}
