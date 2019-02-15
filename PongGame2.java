import javax.swing.JFrame;

public class PongGame2 {

	public static void main(String[] args) { //main method for pongGame
		JFrame frame = new JFrame("PongGame");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PongGameCanvas canvas = new PongGameCanvas();
		frame.add(canvas);
		frame.setVisible(true);

	}

}
