// A Server that displays in a window (Swing JFrame)

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent; // For events
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ServerSocket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SkServer extends JFrame implements ActionListener{
	private JLabel label = new JLabel("Let's Chat!");
	private JTextField tf = new JTextField(18);
	private JTextArea ta= new JTextArea(); // Shows the chatroom 
	private JPanel pl =new JPanel();
	
	private ServerSocket server=null; // Socket for server
	private ServerSocket client=null; // Socket for client
	
	private InputStream input=null;
	private OutputStream out=null;
	
	public SkServer(){
		this.setSize(280,300);
		ta.setBackground(Color.gray);
		ta.setFont(new Font("Helvitica",Font.BOLD,12));
		ta.setForeground(Color.green);
		
		label.setFont(new Font("Helvitica",Font.BOLD,15));
		pl.add(label); // insert label into the panel
		pl.add(tf); //insert text field into the panel
		
		/* Layouts */
		this.add("North",pl); //upper area in the panel
		this.add("Center",ta); //central area as the text area
		this.setTitle("Server"); // window title
		this.setLocationRelativeTo(null); // center the window
		this.setVisible(true);            // make the window visible
		
		/* Events */
		tf.addActionListener(this); // Press "Enter" after text entry in the text field
		addWindowListener(new WindowAdapter(){ // Exit when clicking "X"
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		/* Create connection between server and client*/
	}
	
	//program entry point
	public static void main(String[] args){
		new SkServer();
	}
	
	public void actionPerformed(ActionEvent e){
		
	}
}
