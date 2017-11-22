
public class GameMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameClass Game = new GameClass();
		char[][] room = Game.CreateRoom();
		room = Game.PlaceStuffFirstRoom(room);
		Game.move(room);
	}

}
