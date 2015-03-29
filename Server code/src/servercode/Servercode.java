package servercode;

import java.util.*;
import java.io.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
 
public class Servercode implements MessageListener{
 
    XMPPConnection connection;
 
    public void login(String userName, String password) throws XMPPException
    {
    ConnectionConfiguration config = new ConnectionConfiguration("192.168.110.107");
    connection = new XMPPConnection(config);
 
    connection.connect();
    connection.login(userName, password);
    }

    public void displayBuddyList()
    {
    Roster roster = connection.getRoster();
    Collection<RosterEntry> entries = roster.getEntries();
 
    System.out.println("\n\n" + entries.size() + " buddy(ies):");
    for(RosterEntry r:entries)
    {
    System.out.println(r.getUser());
    }
    }
     
    public void sendMessage(String message, String to) throws XMPPException
    {
    Chat chat = connection.getChatManager().createChat(to, this);
    chat.sendMessage(message);
    try {
    	FileWriter fw = new FileWriter("home.txt");
		PrintWriter writer= new PrintWriter(fw);
		writer.write(" ");
		writer.flush();
		writer.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    public void processMessage(Chat chat, Message message)
    {
     
    	System.out.println(chat.getParticipant() + " says: " + message.getBody());
    	try {
			File file = new File("home.txt");
			FileWriter filewriter = new FileWriter(file.getName(),true);
			BufferedWriter bufferwriter = new BufferedWriter(filewriter);
			bufferwriter.write(message.getBody());
			bufferwriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    
 
    public static void main(String args[]) throws XMPPException, IOException
    {   
    Servercode c = new Servercode();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String msg; 
    c.login("admin", "admin");      
    boolean run = true; 
    while(run)
    {
    System.out.println("-----"); 
    System.out.println("Who do you want to talk to? - Type contacts full email address(Ipaddress@keertipriya):");
    String talkTo = br.readLine(); 
    System.out.println("-----");
    c.sendMessage("hello", talkTo);
    System.out.println("All messages will be sent to " + talkTo);
    System.out.println("Enter your message in the console:");
    System.out.println("-----\n");
 
    while( !(msg=br.readLine()).equals("bye"))
    {
        c.sendMessage(msg, talkTo);
    }
    
   
    }
    }    
}