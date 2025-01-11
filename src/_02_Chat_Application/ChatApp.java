package _02_Chat_Application;

import javax.swing.JOptionPane;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp {
	public static void main(String[] args) {
		new ChatApp();
	}
	
	public ChatApp()
	{
		String[] options = {"Join", "Host"};
		int option = JOptionPane.showOptionDialog(null, "Would you like to host a room or join one", null, 0, 0, null, options, null);
		System.out.println(option);
		if (option == 1)
		{
			//host
		}
		else if (option == 0)
		{
			//join
		}
	}
}
