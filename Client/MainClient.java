package Client;

import javax.swing.JFrame;

public class MainClient {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		Client client = new Client(4000,null);
		client.start(false);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
