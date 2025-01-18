package _02_Chat_Application;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ChatAppClient {
	ChatApp application;
	
	private int port;
	private String ip;
	
	private Socket connection;

	ObjectOutputStream os;
	ObjectInputStream is;
	
	String username;
	
	public ChatAppClient(String ip, int port, ChatApp app) {
		application = app;
		this.port = port;
		this.ip = ip;
		username = app.username;
	}
	
	public void start(){
		try {
			connection = new Socket(ip, port);

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

			os.flush();
			
			sendMessage(username, "JOINED");
			
			while (connection.isConnected()) {
				try {
					String input = (String) is.readObject();
					int index = input.indexOf("\n");
					String name = input.substring(0, index);
					String text = input.substring(index + 1);
					
					System.out.println(name + ": " + input);
					application.addMessage(name, text);
				} catch(EOFException e) {
					JOptionPane.showMessageDialog(null, "Connection Lost");
					System.exit(0);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(String name, String input) {
		application.addMessage(name, input);
		try {
			if (os != null) {
				os.writeObject(name + "\n" + input);
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
