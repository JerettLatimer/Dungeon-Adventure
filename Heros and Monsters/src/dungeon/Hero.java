package dungeon;

/*
 * Jerett Latimer
 * CSCD 349
 * Final Project
 * 6/12/2019
 * 
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Hero extends DungeonCharacter
{
	static Scanner kb = new Scanner(System.in);
	private double chanceToBlock;
	private int numTurns, numHealingPotions, numPillars;
	private HealPotion healingPotion;
	private String specialMove;
	private Room heroLocation;

//-----------------------------------------------------------------
//calls base constructor and gets name of hero from user
  public Hero(String name, int hitPoints, int attackSpeed,
				     double chanceToHit, int damageMin, int damageMax,
					 double chanceToBlock, String specialMove, int numHealingPotions,
					 int numPillars, Room heroLocation, HealPotion healPotion)
  {
	super(name, hitPoints, attackSpeed, chanceToHit, damageMin, damageMax);
	setChanceToBlock(chanceToBlock);
	setSpecialMove(specialMove);
	setNumHealingPotions(numHealingPotions);
	setNumPillars(numPillars);
	setHealingPotion(healPotion);
	setHeroLocation(heroLocation);
  }
  
  protected static String chooseHero()
	{
		int choice;
		String type = "";

		System.out.println("Choose a hero:\n" +
					       "1. Warrior\n" +
						   "2. Sorceress\n" +
						   "3. Thief");
		System.out.print("Enter choice -> ");
		choice = kb.nextInt();

		switch(choice)
		{
			case 1: return type = "Warrior";

			case 2: return type = "Sorceress";

			case 3: return type = "Thief";

			default: System.out.println("invalid choice, returning Thief");
				     return type = "Thief";
		}//end switch
	}//end chooseHero method

  public double getChanceToBlock() {
	  return chanceToBlock;
  }

  public void setChanceToBlock(double chanceToBlock) {
	  this.chanceToBlock = chanceToBlock;
  }

  public int getNumTurns() {
		return numTurns;
	}

	public void setNumTurns(int numTurns) {
		this.numTurns = numTurns;
	}
	
	public String getSpecialMove() {
		return specialMove;
	}

	public void setSpecialMove(String specialMove) {
		this.specialMove = specialMove;
	}
/*-------------------------------------------------------
defend determines if hero blocks attack

Receives: nothing
Returns: true if attack is blocked, false otherwise

This method calls: Math.random()
This method is called by: subtractHitPoints()
---------------------------------------------------------*/
  private boolean defend()
  {
		return Math.random() <= chanceToBlock;

  }//end defend method

/*-------------------------------------------------------
subtractHitPoints checks to see if hero blocked attack, if so a message
is displayed, otherwise base version of this method is invoked to
perform the subtraction operation.  This method overrides the method
inherited from DungeonCharacter promoting polymorphic behavior

Receives: hit points to subtract
Returns: nothing

This method calls: defend() or base version of method
This method is called by: attack() from base class
---------------------------------------------------------*/
protected void subtractHitPoints(int hitPoints)
	{
		if (defend())
		{
			System.out.println(this.getName() + " BLOCKED the attack!");
		}
		else
		{
			super.subtractHitPoints(hitPoints);
		}


	}//end method

/*-------------------------------------------------------
battleChoices will be overridden in derived classes.  It computes the
number of turns a hero will get per round based on the opponent that is
being fought.  The number of turns is reported to the user.  This stuff might
go better in another method that is invoked from this one...

Receives: opponent
Returns: nothing

This method calls: getAttackSpeed()
This method is called by: external sources
---------------------------------------------------------*/
	protected void battleChoices(DungeonCharacter opponent)
	{
	    setNumTurns(this.getAttackSpeed()/opponent.getAttackSpeed());

		if (getNumTurns() == 0)
			setNumTurns(getNumTurns() + 1);

		System.out.println("Number of turns this round is: " + getNumTurns());

	}//end battleChoices
	
	protected int getInput(Scanner kb) {
		int choice = 0;
		System.out.println("1. Attack Opponent");
	    System.out.println("2. "+ this.getSpecialMove());
	    System.out.print("Choose an option: ");
	    
	    try {
		    choice = kb.nextInt();
		    if(choice < 1 || choice > 2) {
		    	throw new IllegalArgumentException();
		    }
	    }
	    catch(InputMismatchException e) {
	    	System.out.println("Invalid input. Choose again.\n");
	    	kb.next();
	    	choice = getInput(kb);
	    }
	    catch(IllegalArgumentException ex) {
	    	System.out.println("Invalid input. Choose again.\n");
	    	choice = getInput(kb);
	    }
		return choice;
	}
	
	public String toString() {
		String result = "";
		result += "\n\nHero Info: \n\n"
				+ "Name: " + this.getName() + "\n"
				+ "HitPoints: " + this.getHitPoints() + "\n"
				+ "Healing Potions: " + this.getNumHealingPotions() + "\n"
				+ "Pillars of OO Found: " + this.getNumPillars() + "\n";
		return result;
				
	}

	public int getNumHealingPotions() {
		return numHealingPotions;
	}

	public void setNumHealingPotions(int numHealingPotions) {
		this.numHealingPotions = numHealingPotions;
	}

	public int getNumPillars() {
		return numPillars;
	}

	public void setNumPillars(int numPillars) {
		this.numPillars = numPillars;
	}

	public Room getHeroLocation() {
		return heroLocation;
	}

	public void setHeroLocation(Room heroLocation) {
		this.heroLocation = heroLocation;
	}

	public HealPotion getHealingPotion() {
		return healingPotion;
	}

	public void setHealingPotion(HealPotion healingPotion) {
		this.healingPotion = healingPotion;
	}

}//end Hero class