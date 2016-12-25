package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import Server.Server;

import javax.swing.*;

public class Client extends JFrame implements ActionListener{
	private int port;
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	String msg="";
	String serverAddress;
	Socket s;
	
	public Client (int port,String host) {
		super("Client");
		this.port=port;
		if(host.isEmpty() || host==null)
			serverAddress = JOptionPane.showInputDialog(
	            "Enter IP Address of a machine that is\n" +
	            "running the date service on port"+port+":");
		else serverAddress = host;
		
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(this);
		
		chatWindow = new JTextArea();
		
		this.add(userText,BorderLayout.NORTH);
		this.add(new JScrollPane(chatWindow),BorderLayout.CENTER);
		this.setSize(300,150);
		this.setVisible(true);
		
	}
	private void setupStreams(){
		try {
			output= new ObjectOutputStream(s.getOutputStream());
			output.flush();
			input=new ObjectInputStream(s.getInputStream());
			showMessage("\n Client stream has been set up");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void chatting(){
		setTypable(true);
		do{
			try {
				msg = (String) input.readObject();
				showMessage("\n"+msg);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(!msg.equals("SERVER - END"));
	}
	
	//gives user permission to type
	private void setTypable(final boolean b) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(
				new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						userText.setEditable(b);
					}
				});
	}
	
	public void start(boolean local){
		try {
			// connect to server
			showMessage("Connecting ...");
			if(!local)
				s = new Socket(InetAddress.getByName(serverAddress), port);
			else s=new Socket(serverAddress,Server.getPortNumber());
			showMessage("connected to"+s.getInetAddress().getHostName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showMessage(e.getMessage());
		}finally{
			close();
		}
	}

	private void close() {
		// TODO Auto-generated method stub
		showMessage("\n Closing");
		setTypable(false);
		
		try {
			output.close();
			input.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void senMessage(String message){
		try {
			output.writeObject("CLIENT - "+message);
			output.flush();
			showMessage("\nCLIENT - "+message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			chatWindow.append("\n cannot send this message");
		}
	}
	
	// update chat
	private void showMessage(final String message) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(message);
					}
				};
		);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==userText){
			sendData(e.getActionCommand());
			userText.setText("");
		}
	}

	private void sendData(String actionCommand) {
		// TODO Auto-generated method stub
		
	}
}
