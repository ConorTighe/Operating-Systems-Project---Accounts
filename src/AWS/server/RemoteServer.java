package AWS.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class RemoteServer {
  public static void main(String[] args) throws Exception {
    ServerSocket m_ServerSocket = new ServerSocket(2004,10);
    int id = 0;
    while (true) {
      System.out.println("Listening for conenctions...");
      Socket clientSocket = m_ServerSocket.accept();
      System.out.println(clientSocket.getRemoteSocketAddress().toString() + " Connected");
      ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
      cliThread.start();
    }
  }
}

class ClientServiceThread extends Thread {
  Socket clientSocket;
  String message;
  int clientID = -1;
  boolean running = true;
  ObjectOutputStream out;
  ObjectInputStream in;

  ClientServiceThread(Socket s, int i) {
    clientSocket = s;
    clientID = i;
  }

  void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("Server> " + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
  public void run() {
    
    try 
    {
    	//Set up stream
    	out = new ObjectOutputStream(clientSocket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(clientSocket.getInputStream());
		System.out.println("Accepted Client : ID - " + clientID + " : Address - " +
				clientSocket.getRemoteSocketAddress().toString() +
				" : Host - "+ clientSocket.getInetAddress().getHostName());
		
		sendMessage("Connection successful");
		do{
			try
			{
				//if (message.equals("bye"))
				//sendMessage("server got the following: "+message);
				message = (String)in.readObject();
				if(message.toLowerCase().contains("1^]".toLowerCase())){
					PrintWriter out = new PrintWriter("Users.txt");
					
						// Process new details
						message = (String)in.readObject();
						System.out.println(message);
						out.println(message);
						message = (String)in.readObject();
						System.out.println(message);
						out.println(message);
						message = (String)in.readObject();
						System.out.println(message);
						out.println(message);
						message = (String)in.readObject();
						System.out.println(message);
						out.println(message);
						message = (String)in.readObject();
						System.out.println(message);
						out.println(message);
						
					out.close();

				}else if(message.toLowerCase().contains("2^]".toLowerCase())){
					 Scanner lineReader = new Scanner(new File("Users.txt"));
					 String user = null;
					 String pass = null;
					 String vef_user;
					 String vef_pass;
					 String bankNum = "10";
					 String name = "default";
					 String add = "default";
					 
					 
					 	// Check login provided
				        while (lineReader.hasNext())
				        {
				            String line = lineReader.nextLine();
				            if(line.toLowerCase().contains("user:".toLowerCase())){
				            	user = line;
				            	System.out.println(line);
				            	
				            }else if(line.toLowerCase().contains("pass:".toLowerCase())){
				            	pass = line;
				            	 System.out.println(line);
				            }
				          //  System.out.println(line);
				        }
				        
				        System.out.println(user);
				        System.out.println(pass);
				        
						vef_user = (String)in.readObject();
						vef_pass = (String)in.readObject();
						System.out.println(vef_user);
				        System.out.println(vef_pass);
				        
						if (vef_user.equals(user) && vef_pass.equals(pass)) {
							System.out.print("Login successful");
							//Handle login
							sendMessage("Login successful");
							while (lineReader.hasNext())
					        {
					            String line = lineReader.nextLine();
					            if(line.toLowerCase().contains("BN:".toLowerCase())){
					            	 bankNum = line;
					            	 System.out.println(bankNum);
					            }else if(line.toLowerCase().contains("name:".toLowerCase())){
					            	name = line;
					            	System.out.println(name);
					            }else if(line.toLowerCase().contains("ad:".toLowerCase())){
					            	add = line;
					            	System.out.println(add);
					            }
					            
					           
					        }
							//provide corisponding details if login is correct
						        sendMessage(bankNum);
						        sendMessage(name);
						        sendMessage(add);
						        sendMessage(user);
						        
						        
						} else {
							sendMessage("Login failed");
						    }
				      
				        lineReader.close();

				}
			}
			catch(ClassNotFoundException classnot){
				System.err.println("Data received in unknown format");
			}
			
    	}while(!message.equals("bye"));
      
		System.out.println("Ending Client : ID - " + clientID + " : Address - "
		        + clientSocket.getInetAddress().getHostName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
