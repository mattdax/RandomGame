import java.util.Random;
import java.util.Scanner;

public class GameClass {
	//Initiating variables that are used constantly throughout the whole game.
	private int x;
	private int y;
	private int x2;
	private int y2;
	private int hp;
	
	//Constructor
	GameClass() {
		//TODO: Make seperate method for print statements
		//Intro print statements		
		System.out.println("READ BELOW:");
		System.out.println("1. Use the W,A,S,D + Enter to move aroud the map");
		System.out.println("2. Every x is a different location in a room");
		System.out.println("3. P is you player in the room");
		System.out.println("4. M is a monster also located in the room\nmoving your player over the monster will enter a battle. ");
		System.out.println("Press \"ENTER\" to continue...");

		//Waits for user to press a button to continue in the program 
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
	}
	
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
		//Placing the player in the New room
		this.x2 = rand.nextInt(this.x) + 0;
		this.y2 = rand.nextInt(this.y) + 0;
    	n[this.y2][this.x2] = "P".charAt(0);
    	

    	int numt = rand.nextInt(2) + 1;
    	for(int i = 0; i<=numt; i++) {
    		int x3 = rand.nextInt(this.x) + 0;
			 int y3 = rand.nextInt(this.y) + 0; 
    		n[y3][x3] = "T".charAt(0);
    	}


    	int numm = rand.nextInt(4)+1;
    	for(int i = 0; i<=numt; i++) {
    		int x4 = rand.nextInt(this.x) + 0;
			 int y4 = rand.nextInt(this.y) + 0; 
    		//TODO: Add P too
    		if(n[y4][x4] == "T".charAt(0)) {
    		}
    		else {
    			n[y4][x4] = "M".charAt(0);	
    		}	
    	}

    	return n;
    }
	public void displayBoard(char[][] a) {
		
		for (int z = 0; z < a.length; z++) {
			for (int c = 0; c < a[0].length; c++) {
				System.out.print(" ");
				System.out.print(a[z][c]);
			}
			System.out.println("");
		}
		this.hp = 100;
		System.out.println("HP("+this.hp+"/100)");
	}
	public void move(char[][] a) {
		displayBoard(a);
		Scanner input = new Scanner(System.in);
		System.out.println("Enter: W for up, S for down, A for left, D for right");
		String direction = input.nextLine();
		if(direction == "W" || direction == "w") {
			System.out.println("Here");
			a[this.y2][this.x2] = "x".charAt(0);
			this.y2 += 1;
			a[this.y2][this.x2] = "P".charAt(0);
			displayBoard(a);
		}

	}											

}
	