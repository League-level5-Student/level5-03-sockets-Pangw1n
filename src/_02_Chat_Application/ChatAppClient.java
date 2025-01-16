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
	
	private ServerSocket server;
	private Socket connection;

	ObjectOutputStream os;
	ObjectInputStream is;
	
	public ChatAppClient(String ip, int port, ChatApp app) {
		application = app;
		this.port = port;
		this.ip = ip;
	}
	
	public void start(){
		try {
			connection = new Socket(ip, port);

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

			os.flush();
			
			while (connection.isConnected()) {
				try {
					String input = (String) is.readObject();
					System.out.println(input);
					application.addMessage(input);
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

	public void sendMessage(JTextArea input) {
		try {
			if (os != null) {
				os.writeObject(input.getText());
				input.setText("");
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
