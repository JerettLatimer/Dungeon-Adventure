package dungeon;

/*
 * Jerett Latimer
 * CSCD 349
 * Final Project
 * 6/12/2019
 * 
 * 
 * SECRET MENU OPTION:
 * 		- When prompted for "Hero Options" you will be asked to select 1 or 2. 
 * 		- Selecting 3 will print the entire current dungeon.
 */

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class DungeonAdventure {
	
	static Scanner kb = new Scanner(System.in);
	static boolean moveNorth = false, moveSouth = false, moveEast = false, moveWest = false;

	public static void main(String[] args) {
		printIntro();
		
		do {
			Dungeon dungeon = new Dungeon();
			Hero hero = HeroFactory.createHero(Hero.chooseHero());
			hero.setHeroLocation(dungeon.getHeroLocation());
			do {
				System.out.println(hero.toString());
				// Prints current room
				System.out.println("Current Hero Location: \n" + hero.getHeroLocation().toString());
				play(dungeon, hero);
			}while(gameOver(hero, dungeon) == false);
			// Display entire dungeon
			if(hero.isAlive()) {
				System.out.println(hero.getName() + " has conquered the dungeon!!!"
						+ "\n You are a WINNER!");
			}
			else {
				System.out.println(hero.getName() + " has died. You LOSE!");
			}
		}while(playAgain() == true);
		
	}

	private static void play(Dungeon dungeon, Hero hero) {
		if(!(hero.getHeroLocation().isExit())) {
			System.out.println("\nHero Options:\n"
					+ "1. Move Hero\n"
					+ "2. Use Potion");
			System.out.print("Enter choice -> ");
			int choice = getInput(kb, 3);
			
			switch(choice) {
				case 1: System.out.println("\n\nMove Hero Selected\n");
						boolean move = moveHero(dungeon, hero);
						while(move == false) {
							move = moveHero(dungeon, hero);
						}
						System.out.println("\nCurrent Location After Move:\n");
						System.out.println(hero.getHeroLocation().toString());
						heroInteraction(dungeon, hero);
						break;
				
				case 2: boolean use = usePotion(dungeon, hero);
						if(!(use))
							System.out.println("\nNo Healing Potions in Inventory!");
						break;
					
				case 3: System.out.println("\nSecret Option Chosen\n"
						+ "Current Dungeon:\n");
						System.out.println(dungeon.toString());
						break;
			}
		}
	}

	private static boolean usePotion(Dungeon dungeon, Hero hero) {
		if(hero.getNumHealingPotions() < 1) {
			return false;
		}
		else {
			System.out.println(hero.getName() + " used a Healing Potion and restored"
					+ " <" + hero.getHealingPotion().getHealAmount() + "> HitPoints");
			hero.addHitPoints(hero.getHealingPotion().getHealAmount());
			hero.setNumHealingPotions(hero.getNumHealingPotions() - 1);
			if(hero.getNumHealingPotions() == 0) {
				hero.setHealingPotion(null);
			}
		}
		return false;
	}

	private static void heroInteraction(Dungeon dungeon, Hero hero) {
		if(hero.getHeroLocation().isExit() && hero.getNumPillars() < 4) {
			System.out.println("\nYou don't have all the pillars yet! Move again!");
			return;
		}
		if(hero.getHeroLocation().isEntrance()) {
			System.out.println("\nYou have navigated back to the entrance. This is an empty room. Move again!");
			return;
		}
		if(hero.getHeroLocation().isEmpty()) {
			System.out.println("\nThis room is empty! Move Along!");
			return;
		}
		RoomContents curr = hero.getHeroLocation().getContents();
		if(curr.getPillar() != null) {
			System.out.println("\nYou have found the " + curr.getPillar() + " Pillar of OO! Adding to Inventory...");
			hero.setNumPillars(hero.getNumPillars() + 1);
			curr.setPillar(null);
		}
		if(curr.getPit() != null) {
			System.out.println("\n" + hero.getName() + " fell down a pit and lost " + curr.getPit().getDamageAmount() + " HitPoints!");
			hero.subtractHitPoints(curr.getPit().getDamageAmount());
		}
		if(curr.getMonster() != null) {
			System.out.println("\n" + hero.getName() + " has encountered a Monster! They must battle it to the death!");
			boolean won = battle(hero, curr.getMonster());
			if(won) {
				Random rand = new Random();
				int choice = rand.nextInt(10);
				if(choice < 4) {
					System.out.println("\n" + curr.getMonster().getName() + " dropped a Healing Potion!");
					hero.setHealingPotion(new HealPotion());
					hero.setNumHealingPotions(hero.getNumHealingPotions() + 1);
				}
			}
			curr.setMonster(null);
		}
		if(curr.getHealPotion() != null) {
			System.out.println("\n" + hero.getName() + " found a Healing Potion! Adding to Inventory...");
			hero.setNumHealingPotions(hero.getNumHealingPotions() + 1);
			hero.setHealingPotion(curr.getHealPotion());
			curr.setHealPotion(null);
		}
		
		// If the contents are empty (besides the pit) set the room to empty.
		if(curr.getHealPotion() == null && curr.getMonster() == null && curr.getPillar() ==  null) {
			hero.getHeroLocation().setEmpty(true);
			hero.getHeroLocation().setItemCount(0);
		}
	}

	private static boolean moveHero(Dungeon dungeon, Hero hero) {
		System.out.println("Move " + hero.getName() + " in one of the following directions: ");
		System.out.println("1. Move North\n"
						+ "2. Move South\n"
						+ "3. Move East\n"
						+ "4. Move West\n");
		System.out.print("Enter choice -> ");
		int choice = getInput(kb, 4);
		
		// Checks valid moves for the hero and changes their static fields to true if they're a valid move
		validMove(hero);
		
		switch(choice) {
			case 1: {
					if(moveNorth) {
						int row = hero.getHeroLocation().getRow();
						int column = hero.getHeroLocation().getColumn();
						if(dungeon.getMap()[row - 1][column].isExit() && hero.getNumPillars() < 4) {
							System.out.println("Not a valid move. You must have all the Pillars"
									+ " to visit this room! Choose again!\n");
							return false;
						}
						hero.setHeroLocation(dungeon.getMap()[row - 1][column]);
						hero.getHeroLocation().setRow(row - 1);
						dungeon.setHeroLocation(hero.getHeroLocation());
						return true;
					}
					else {
						System.out.println("Not a valid move. Choose again!\n");
						return false;
					}
			}
			
			case 2: {
					if(moveSouth) {
						int row = hero.getHeroLocation().getRow();
						int column = hero.getHeroLocation().getColumn();
						if(dungeon.getMap()[row + 1][column].isExit() && hero.getNumPillars() < 4) {
							System.out.println("Not a valid move. You must have all the Pillars"
									+ " to visit this room! Choose again!\n");
							return false;
						}
						hero.setHeroLocation(dungeon.getMap()[row + 1][column]);
						hero.getHeroLocation().setRow(row + 1);
						dungeon.setHeroLocation(hero.getHeroLocation());
						return true;
					}
					else {
						System.out.println("Not a valid move. Choose again!\n");
						return false;
					}
			}
				
			case 3: {
					if(moveEast) {
						int row = hero.getHeroLocation().getRow();
						int column = hero.getHeroLocation().getColumn();
						if(dungeon.getMap()[row][column + 1].isExit() && hero.getNumPillars() < 4) {
							System.out.println("Not a valid move. You must have all the Pillars"
									+ " to visit this room! Choose again!\n");
							return false;
						}
						hero.setHeroLocation(dungeon.getMap()[row][column + 1]);
						hero.getHeroLocation().setColumn(column + 1);
						dungeon.setHeroLocation(hero.getHeroLocation());
						return true;
					}
					else {
						System.out.println("Not a valid move. Choose again!\n");
						return false;
					}
			}
			
			case 4: {
					if(moveWest) {
						int row = hero.getHeroLocation().getRow();
						int column = hero.getHeroLocation().getColumn();
						if(dungeon.getMap()[row][column - 1].isExit() && hero.getNumPillars() < 4) {
							System.out.println("Not a valid move. You must have all the Pillars"
									+ " to visit this room! Choose again!\n");
							return false;
						}
						hero.setHeroLocation(dungeon.getMap()[row][column - 1]);
						hero.getHeroLocation().setColumn(column - 1);
						dungeon.setHeroLocation(hero.getHeroLocation());
						return true;
					}
					else {
						System.out.println("Not a valid move. Choose again!\n");
						return false;
					}
			}
		}
		return false;
	
	}

	private static void validMove(Hero hero) {
		if(hero.getHeroLocation().getNorth().isWall()) {
			moveNorth = false;
		}
		else {
			moveNorth = true;
		}
		
		if(hero.getHeroLocation().getSouth().isWall()) {
			moveSouth = false;
		}
		else {
			moveSouth = true;
		}
		
		if(hero.getHeroLocation().getEast().isWall()) {
			moveEast = false;
		}
		else {
			moveEast = true;
		}
		
		if(hero.getHeroLocation().getWest().isWall()) {
			moveWest = false;
		}
		else {
			moveWest = true;
		}
	}

	private static boolean gameOver(Hero hero, Dungeon dungeon) {
		if(hero.isAlive() && hero.getNumPillars() == 4 && hero.getHeroLocation().equals(dungeon.getExit())
				|| (!(hero.isAlive()))) {
			return true;
		}
		return false;
	}

	private static void printIntro() {
		boolean playNow = false;
		System.out.println("Welcome to Dungeon Adventure!\n");
		do {
			System.out.println("Choose of the following: \n"
					+ "1. Game Info\n"
					+ "2. How to Play\n"
					+ "3. Play Now!");
			System.out.print("Select an option -> ");
			int choice = getInput(kb, 3);
			System.out.println();
			
			switch(choice) {
				case 1: System.out.println("Game Info Selected\n\n"
						+ "In this Dungeon Adventure game, you pick one of three Heros: A Warrior, Sorcerress"
						+ ", and a Thief.\n"
						+ "The chosen Hero will be thrown into a dangerous dungeon containing Monsters and Pits.\n"
						+ "The Hero is tasked with locating, retrieving, and safely extracting the Four Pillars of OO.\n");
						break;
				
				case 2: System.out.println("How To Play Selected\n\n"
						+ "Your selected hero will be dropped into the dungeon. The room your hero is currently in\n"
						+ "will be displayed. You can either move your Hero, or use a potion. When you enter a room you will\n"
						+ "automatically iteract with whatever is located within the room. Your goal is to visit the 4 rooms\n"
						+ "containing the Pillars of OO and safely make it to the exit.\n");
						break;
				
				case 3: System.out.println("Play Now Selected\n\n");
						playNow = true;
						break;
			}
			if(choice != 3) {
				pressEnter();
			}
		}while(playNow == false);
	}

	private static void pressEnter() {
		System.out.println("Press Enter to display menu");
		Scanner enter = new Scanner(System.in);
		enter.nextLine();
	}

	private static int getInput(Scanner kb, int upperBound) {
		int choice;
		try {
		    choice = kb.nextInt();
		    if(choice < 1 || choice > upperBound) {
		    	throw new IllegalArgumentException();
		    }
	    }
	    catch(InputMismatchException e) {
	    	System.out.println("Invalid input. Choose again.\n");
	    	kb.next();
	    	choice = getInput(kb, upperBound);
	    }
	    catch(IllegalArgumentException ex) {
	    	System.out.println("Invalid input. Choose again.\n");
	    	choice = getInput(kb, upperBound);
	    }
		return choice;
	}
	
	/*-------------------------------------------------------------------
	playAgain allows gets choice from user to play another game.  It returns
	true if the user chooses to continue, false otherwise.
	---------------------------------------------------------------------*/
		private static boolean playAgain()
		{
			char again;

			System.out.println("Play again (y/n)?");
			again = kb.next().charAt(0);

			return (again == 'Y' || again == 'y');
		}//end playAgain method

		/*-------------------------------------------------------------------
		battle is the actual combat portion of the game.  It requires a Hero
		and a Monster to be passed in.  Battle occurs in rounds.  The Hero
		goes first, then the Monster.  At the conclusion of each round, the
		user has the option of quitting.
		---------------------------------------------------------------------*/
			private static boolean battle(Hero theHero, Monster theMonster)
			{
				System.out.println(theHero.getName() + " battles " +
									theMonster.getName());
				System.out.println("---------------------------------------------");

				//do battle
				while (theHero.isAlive() && theMonster.isAlive())
				{
				    //hero goes first
					theHero.battleChoices(theMonster);

					//monster's turn (provided it's still alive!)
					if (theMonster.isAlive())
					    theMonster.attack(theHero);

					System.out.println();

				}//end battle loop

				if (!theMonster.isAlive()) {
				    System.out.println(theHero.getName() + " was victorious!");
				    return true;
				}
				else if (!theHero.isAlive()) {
					System.out.println(theHero.getName() + " was defeated :-(");
					return false;
				}
				return false;
			}//end battle method

}
