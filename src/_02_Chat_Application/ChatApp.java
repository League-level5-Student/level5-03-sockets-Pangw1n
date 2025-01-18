package _02_Chat_Application;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp {
	JFrame frame;
	JPanel panel;
	JPanel inputPanel;
	JTextArea text;
	JTextArea textInput;
	JScrollPane chatArea;
	JScrollPane messageArea;
	JButton send;
	
	String ip;
	int port;
	
	String username;
	
	public static void main(String[] args) {
		new ChatApp();
	}
	
	public ChatApp()
	{
		username = JOptionPane.showInputDialog("Enter a username");
		
		String[] options = {"Join", "Host"};
		int option = JOptionPane.showOptionDialog(null, "Would you like to host a room or join one", null, 0, 0, null, options, null);
		System.out.println(option);
		if (option == 1)
		{
			//host
			ChatAppServer server = new ChatAppServer(8080, this);
			ip = server.getIp();
			port = server.getPort();
			start();
			send.addActionListener((e) -> {server.sendMessage(username, textInput.getText()); textInput.setText("");});
			server.start();
		}
		else if (option == 0)
		{
			//join
			ip = JOptionPane.showInputDialog("Server IP: ");
			port = Integer.parseInt(JOptionPane.showInputDialog("Server Port: "));
			
			ChatAppClient client = new ChatAppClient(ip, port, this);
			start();
			send.addActionListener((e) -> {client.sendMessage(username, textInput.getText()); textInput.setText("");});
			client.start();
		}
	}
	
	public void start()
	{
		frame = new JFrame();
		panel = new JPanel();
		inputPanel = new JPanel();
		text = new JTextArea();
		textInput = new JTextArea();
		send = new JButton();
		chatArea = new JScrollPane(text);
		messageArea = new JScrollPane(textInput);
		
		frame.add(panel);
		panel.add(chatArea);
		panel.add(inputPanel);
		inputPanel.add(messageArea);
		inputPanel.add(send);
		
		chatArea.setPreferredSize(new Dimension(500, 500));
		messageArea.setPreferredSize(new Dimension(400, 50));
		send.setPreferredSize(new Dimension(75,25));
		send.setText("Send");
		
		panel.setPreferredSize(new Dimension(500, 600));
		inputPanel.setPreferredSize(new Dimension(500, 50));
		
		text.setEditable(false);
		text.setText("IP: " + ip + " Port: " + port);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
	
	public void addMessage(String name, String inputText)
	{
		text.setText(text.getText() + "\n\n" + name + ": \n" + inputText);
	}
}

//192.168.1.103
