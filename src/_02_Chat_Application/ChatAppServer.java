package _02_Chat_Application;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ChatAppServer {
	ChatApp application;
	
	private int port;

	private ServerSocket server;
	private Socket connection;

	ObjectOutputStream os;
	ObjectInputStream is;
	
	public ChatAppServer(int port, ChatApp app) {
		application = app;
		this.port = port;
	}
	
	public void start(){
		try {
			server = new ServerSocket(port, 100);

			connection = server.accept();

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

			os.flush();

			while (connection.isConnected()) {
				try {
					String input = (String) is.readObject();
					System.out.println(input);
					application.addMessage(input);
				}catch(EOFException e) {
					JOptionPane.showMessageDialog(null, "Connection Lost");
					System.exit(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getIp()
	{
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return null;
		}
	}

	public int getPort() {
		return port;
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
