//Import Statements
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class GameClass {
	//Initiating variables that are used constantly throughout the whole game.
	private int x;
	private int y;
	private int x2;
	private int y2;
	private int hp = 100;
	private String weaponName = "Arm";
	private int damage = 10;
	private String MonsterName;
	private int MonsterDamage;
	private int MonsterHealth = 1;
	private int NumMonsters = 0;
	//Constructor
	GameClass() {
		//TODO: Make seperate method for print statements
		//Intro print statements		
		System.out.println("READ BELOW:");
		System.out.println("1. Use the W,A,S,D + Enter to move aroud the map");
		System.out.println("2. Every x is a different location in a room");
		System.out.println("3. P is you player in the room");
		System.out.println("4. M is a monster also located in the room\nmoving your player over the monster will enter a battle. ");
		System.out.println("To win the game you must defeat all of the monsters on the board");
		System.out.println("Press \"ENTER\" to continue...");


		//Waits for user to press a button to continue in the program 
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
	}
	//Method randomly creates size of room
	public char[][] CreateRoom() {
		
		//Determines Size of the room by generating random numbers in a range
		Random rand = new Random();
		this.x = rand.nextInt(12) + 5;
		this.y = rand.nextInt(7) + 4;

		//Creates 2D array, that contains the room
		char[][] room = new char[this.y][this.x];

		
		//sets the board full of x's 
      for(int yy = 0; yy< this.y; yy++) {
    	  for(int xx = 0; xx< this.x; xx++) {
      		 room[yy][xx] = "x".charAt(0);	  	 
    	}
      		}
     	//returns the room to the GameMain.java file
     	return room;
	}
    
    public char[][] PlaceStuffFirstRoom(char[][] n) {
    	
    	Random rand = new Random();
		
    	
    	//Randomly chooses where to place the locations of treasure 
    	int numt = rand.nextInt(3) + 1;
    	for(int i = 0; i<=numt; i++) {
    		int x3 = rand.nextInt(this.x) + 0;
			 int y3 = rand.nextInt(this.y) + 0; 
    		n[y3][x3] = "T".charAt(0);
    	}

    	//Again chooses where to place the monsters in the map, max 4, min 1
    	int numm = rand.nextInt(4)+1;
    	for(int i = 0; i<=numt; i++) {
    		int x4 = rand.nextInt(this.x) + 0;
			 int y4 = rand.nextInt(this.y) + 0; 
    		//makes sure there is no treasure placed where the monsters are to be placed, if so, it skips.
			 if(n[y4][x4] == "T".charAt(0)) {
    		}
    		else {
    			n[y4][x4] = "M".charAt(0);	
    			this.NumMonsters+=1;
    		}	
    	}
    	
    	//Placing the player in the New room
    	this.x2 = rand.nextInt(this.x) + 0;
    	this.y2 = rand.nextInt(this.y) + 0;
    	 n[this.y2][this.x2] = "P".charAt(0);
    	return n;
    }
	
    //This is the method that displays the board on the console.
    public void displayBoard(char[][] a) {
		
    	//loops through the 2d array and prints the array out to the console 
		for (int z = 0; z < a.length; z++) {
			for (int c = 0; c < a[0].length; c++) {
				System.out.print(" ");
				System.out.print(a[z][c]);
			}
			System.out.println("");
		}
		//Adds the hp to the board
		System.out.println("HP("+this.hp+"/100)");
	}
	
    //This Method reads the weapon.txt file, containing the python generated weapon name
	public static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	//Determines the weapon found in the treasure
	public void getWeapon() throws IOException {
		//Exectues CMD command that runs the python file.
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec("python WeaponGen.py");
		//Generates a damage variable for the weapon
		Random dmg = new Random();
		int dmg2 = dmg.nextInt(40) + 10;
		//Compares the new weapon to the current weapon if new weapon is stronger it is replaced
		if(dmg2>this.damage)  {
		this.damage = dmg2;
		//Uses the read file method to get the name of the weapon
		this.weaponName = readFile("Weapon.txt", StandardCharsets.UTF_8);
		System.out.println("You have discovered: " + this.weaponName+ " \nThis Weapon deals: " + this.damage + " damage.");
		}
		else{
			System.out.println("The weapn you found is not as possible as your current weapon.");  
		}		
	}

	public void CheckHP() {
		if(this.hp<=0) {
			System.out.println("You have died. Try Again");
			java.lang.System.exit(0);
		}
		
	}

	//Fight method, called when a fight is triggered
	public void fight() {
		//List of basic monster namse
	String[] monsters = {"Wolf","Goblin","Kobald","Vampire","Bandit","Dragon"};
		
	//Chooses the monster, its damage, and health
		Random name = new Random();
		int namenum = name.nextInt(6) + 0;
		this.MonsterDamage = name.nextInt(20) + 3;
		this.MonsterHealth = name.nextInt(100) + 50;
		this.MonsterName = monsters[namenum];
		System.out.println("You have encountered a: " + this.MonsterName);
		
		//Loops that runs while the monster is alive
		while(this.MonsterHealth > 0) {
		
		//Move of the player
		Scanner move =  new Scanner(System.in);
		System.out.println("Choose a number between 1-4:");
		String guess = move.nextLine();
		
		//Chance of the move being a crit or a miss
		int guessInt = Integer.parseInt(guess);
		int chanceCrit = name.nextInt(3) + 1;
		int chanceMiss  = chanceCrit + 1;
		
		//Checks if the move is a crit
		if(guessInt==chanceCrit){
			System.out.println("You have critcally hit the enemy, dealing: " + this.damage*2 + " damage.");
			//Applies damage to player and monster
			this.MonsterHealth-= this.damage *2;
			CheckHP();
			}
		else if(guessInt == chanceMiss){
			this.hp -= MonsterDamage;
			CheckHP();
			System.out.println("You Missed. The enemy hit you. You now have: " + this.hp + " health remaining.");
		}
		else{
			this.MonsterHealth-= this.damage;
			this.hp -= MonsterDamage;
			CheckHP();
			
			System.out.println("You have hit the enemy, dealing: " + this.damage + " damage.");	
			System.out.println("The monster hit back. You now have: " + this.hp + " health left.");
		}
		}
	System.out.println("You Have defeated the Enemy!");
	}	

	//Essentially, this method contains the movement of the player in 4 directions and the check to see whether they are ontop of treasure or monsters.
	//There is a constant copy and paste, so only the W has been commented. 
	public void move(char[][] a) {
		
		//First displays board on screen
		displayBoard(a);
		
		
		//While there are monsters in the room
		while (this.NumMonsters>= 0) {
			//takes input from user, wasd to move around the room
		Scanner input = new Scanner(System.in);
		System.out.println("Enter: W for up, S for down, A for left, D for right");
		String direction = input.nextLine();
		
		
		//If direction is W of up
		if(direction.equals("w") || direction.equals("W")) {
			
			//Checks if user is a top of room to avoid errors
			if(this.y2-1==-1) {
				System.out.println("You are on the top of the room.\n You cannot move up");
			}
			else {
	            //Checks if player is moving ontop of monster
				if(a[this.y2-1][this.x2]=="M".charAt(0)){
				//If so, fight is triggered
				fight();
				//After the fight moves the player to the location where the monster was and replaces its previous location with x
				a[this.y2][this.x2] = "x".charAt(0);
				this.y2 -= 1;
				a[this.y2][this.x2] = "P".charAt(0);
				displayBoard(a);
	            	}
			//If player moves to a treasure
				else if (a[this.y2-1][this.x2]=="T".charAt(0)){
				
				//Try and catch are required when calling upon the command line
					try {
				// triggers get method function 
				getWeapon();
			}
			catch(IOException ex) {
			}
			//Again, moves player to new location
			a[this.y2][this.x2] = "x".charAt(0);
			this.y2 -= 1;
			a[this.y2][this.x2] = "P".charAt(0);
			displayBoard(a);

			}			
			//Else is a player is moving to an x tile.
				else{
			//Moves the player and displays the board
			a[this.y2][this.x2] = "x".charAt(0);
			this.y2 -= 1;
			a[this.y2][this.x2] = "P".charAt(0);
			displayBoard(a);
			}
		}
	}
		 
		//Everything is the exact same as above, just a change in the direction.
		else if(direction.equals("s") || direction.equals("S")) {
			if(this.y2+1==this.y) {
				System.out.println("You are on the bottom of the room.\n You cannot move down");}
			
				else {
		            if(a[this.y2+1][this.x2]=="M".charAt(0)){
					fight();
					a[this.y2][this.x2] = "x".charAt(0);
					this.y2 -= 1;
					a[this.y2][this.x2] = "P".charAt(0);
					displayBoard(a);
		            }
				else if (a[this.y2+1][this.x2]=="T".charAt(0)){
					try {
					System.out.println("Here");
					getWeapon();
				}
				catch(IOException ex) {
				}
				a[this.y2][this.x2] = "x".charAt(0);
				this.y2 += 1;
				a[this.y2][this.x2] = "P".charAt(0);
				displayBoard(a);	
			}
			else{
			a[this.y2][this.x2] = "x".charAt(0);
			this.y2 += 1;
			a[this.y2][this.x2] = "P".charAt(0);
			displayBoard(a);
			}
				}
		}
		
		//Everything is the exact same as above, just a change in the direction.
		else if(direction.equals("a") || direction.equals("A")) {
			if(this.x2-1==-1){
			System.out.println("You at the left side of the room of the room.\n You cannot any further left.");
			}
			
			else {
	            if(a[this.y2][this.x2-1]=="M".charAt(0)){
				fight();
				a[this.y2][this.x2] = "x".charAt(0);
				this.y2 -= 1;
				a[this.y2][this.x2] = "P".charAt(0);
				displayBoard(a);
	            }
			else if (a[this.y2][this.x2-1]=="T".charAt(0)){
				try {
				System.out.println("Here");
				getWeapon();
			}
			catch(IOException ex) {
			}
			a[this.y2][this.x2] = "x".charAt(0);
			this.x2 -= 1;
			a[this.y2][this.x2] = "P".charAt(0);
			displayBoard(a);
			}
			
			else{
			a[this.y2][this.x2] = "x".charAt(0);
			this.x2 -= 1;
			a[this.y2][this.x2] = "P".charAt(0);
			displayBoard(a);
			}
		}
		}
		
		
		//Everything is the exact same as above, just a change in the direction.
		else if(direction.equals("d") || direction.equals("D")) {
			if(this.x2-1==this.x) {
				System.out.println("You at the right side of the room of the room.\n You cannot any further right.");
			}
			
			else {
	            if(a[this.y2][this.x2+1]=="M".charAt(0)){
				fight();
				a[this.y2][this.x2] = "x".charAt(0);
				this.y2 -= 1;
				a[this.y2][this.x2] = "P".charAt(0);
				displayBoard(a);
	            }
			else if (a[this.y2][this.x2+1]=="T".charAt(0)){
				try {
				System.out.println("Here");
				getWeapon();
			}
			catch(IOException ex) {
			}
			a[this.y2][this.x2] = "x".charAt(0);
			this.x2 += 1;
			a[this.y2][this.x2] = "P".charAt(0);
			displayBoard(a);
			}
			
			else {
			a[this.y2][this.x2] = "x".charAt(0);
			this.x2 += 1;
			a[this.y2][this.x2] = "P".charAt(0);
			displayBoard(a);
						}
					}
				}											
			
			}
		//End Game
		System.out.println("Congratulations! You have completeed the game\nCreator: Matt Dax")
		java.lang.System.exit(0);
	}
	
}
	