package Server;

//HJChen
//A Server that displays in a window (Swing JFrame)

//import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent; // For events
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SkServer extends JFrame implements ActionListener,Runnable{
	private JLabel label = new JLabel("Let's Chat!");
	private JTextField tf = new JTextField(18);
	private JTextArea ta= new JTextArea(); // Shows the chatroom 
	private JPanel pl =new JPanel();
	private JButton sendButton=new JButton("SEND");
	
	private ServerSocket server=null; // ServerSocket for server
	private Socket client=null; // Socket for client
	final public static int Port = 4000; /*usually set port to >1023 to avoid conflicts with the system.
			no need for address since it's a local simulation*/
	
	private InputStream input=null;
	private OutputStream out=null;
	
	public SkServer() {
		this.setSize(480,640);
		ta.setBackground(Color.gray);
		ta.setFont(new Font("Helvitica",Font.BOLD,12));
		ta.setForeground(Color.green);
		
		label.setFont(new Font("Helvitica",Font.BOLD,15));
		pl.add(label); // insert label into the panel
		pl.add(tf); //insert text field into the panel
		pl.add(sendButton);
		// Adding event to the Send button
		sendButton.addActionListener(
//				new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // send the message 
//            }
//        }
				this
		);
		
		/* Layouts */
		this.add("North",pl); //upper area in the panel
		this.add("Center",ta); //central area as the text area
		this.setTitle("Server"); // window title
		this.setLocationRelativeTo(null); // center the window
		this.setVisible(true);            // make the window visible
		this.setResizable(true);
		
		/* Window closing event */
		tf.addActionListener(this); 
		addWindowListener(new WindowAdapter(){ // Exit when clicking "X"
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		/* Create connection between server and client*/
		
	}
	
	public void run(){
		try { //The method blocks until a connection is made
			server=new ServerSocket(Port);/* server started (port 4000). */
			System.out.println("Waiting for connections ...");
			client=server.accept(); /* waiting for the client to connect
			(if there's no request for connection, it'll keep waiting) */
			ta.append("Client is connected");
			input=client.getInputStream();
			out=client.getOutputStream();
		} catch(Exception e){//handle exception
			System.out.println("Connection is lost");
		}
		
		/* Receive messages from client*/
		while(true){ // infinite loop for messaging
			try{
				byte[] buff=new byte[1024]; // use byte array to increase reading speed, 
				//because i/o streams are read one char at a time, and therefore slower
				input.read(buff); // read certain bytes (1024) of data
				String instr=new String (buff); //message to be shown in the text area
				ta.append("Client:" + instr + "\n"); // show message in the text area
				//ta.append("\n");
			} catch(Exception e){
				System.out.println("Connection is lost");
			} 
		}// end while
	}
	
	public void actionPerformed(ActionEvent e){
		// send messages (back) to client (try-catch)
		try{
			String str=tf.getText(); // get text (message) from the text field
			byte[] putbuff=str.getBytes();
			out.write(putbuff); // Send message to client
			tf.setText("");     // Empty text field (for the next message entry)
			ta.append("Me:" + str + "\n"); //Show message in the text area
		}catch(Exception e2){
			System.out.println("Cannot send message");
		} 
	}
}
