import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SkClient extends JFrame implements ActionListener{
	private JLabel label = new JLabel("Ask me questions!");
	private JTextField tf = new JTextField(18);
	private JTextArea ta= new JTextArea(); 
	private JPanel pl =new JPanel();
	
	private InputStream input=null;
	private OutputStream out=null;
	
	final public static String host = "localhost";
	final public static int Port = 5000;
	private Socket socket=null;
	
	public SkClient() throws UnknownHostException, IOException {
		/* Use the same layout as the server */
		this.setSize(480,640);
		ta.setBackground(Color.gray);
		ta.setFont(new Font("Helvitica",Font.BOLD,12));
		ta.setForeground(Color.green);
		
		label.setFont(new Font("Helvitica",Font.BOLD,15));
		pl.add(label); 
		pl.add(tf); 
		
		this.add("North",pl); 
		this.add("Center",ta); 
		this.setTitle("Server"); 
		this.setLocationRelativeTo(null); 
		this.setVisible(true);
		
		tf.addActionListener(this); // Press "Enter" after text entry in the text field
		addWindowListener(new WindowAdapter(){ // Exit when clicking "X"
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		// connect to server
		socket = new Socket(host , Port);
	    System.out.println("Connection Successful");
	}
	
	public static void SkClient(String[] args) throws UnknownHostException, IOException{
		new SkClient();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try{
			
		}catch(Exception e2){
			System.out.println("Cannot send message");
		}
	}

}
