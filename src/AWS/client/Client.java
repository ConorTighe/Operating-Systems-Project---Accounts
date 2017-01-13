package AWS.client;

import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.util.*;

public class Client {
	
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message;
 	String name;
 	String username;
 	String pass;
 	String address;
 	String login1;
 	String login2;
 	String command;
 	int choice = 0;
 	int sentinel;
 	int min = 100000000;
 	int max = 999999999;
 	String BN;
 	int generateBN;
 	Client(){}
 	// Object for hashing the passwords
 	//SecureRandom random = new SecureRandom();
 	void run() throws ClassNotFoundException
	{
		try{
			//1. creating a socket to connect to the server
			
			requestSocket = new Socket("0.0.0.0", 2004);
			System.out.println("Connected to AWS in port 2004");
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			//3: Communicating with the server
			
			message = (String)in.readObject();
			
			Scanner sc = new Scanner(System.in);
			do{
					
					System.out.println("server>" + message);
					System.out.println("Press 1: Register details of new user ");
					System.out.println("Press 2: To login as existing user ");
					choice = sc.nextInt();
					
					if(choice == 1){
						
						//Register details with system
						
						command = Integer.toString(choice) + "^]";
						sendDetails(command);
						
						System.out.println("Enter Username: ");
						sc.nextLine();
						username = sc.nextLine();
						username = "user:" + username;
						sendDetails(username);
						
						System.out.println("Enter Name: ");
						name = sc.nextLine();
						name = "name:" + name;
						sendDetails(name);
						
						System.out.println("Enter Address: ");
						address = sc.nextLine();
						address = "ad:" + address;
						sendDetails(address);
						
						System.out.println("Enter Password: ");
						pass = sc.nextLine();
						pass = "pass:" + pass;
						sendDetails(pass);
						// hash passwords if enough time left
					 
						generateBN = min + (int)(Math.random() * max);
						BN = "BN:" + Integer.toString(generateBN);
						sendDetails(BN);
						
						
					}else if(choice == 2){
						
						//Login eith user and pass
						
						int option;
						int cash;
						command = Integer.toString(choice) + "^]";
						sendDetails(command);
						System.out.println("Enter username: ");
						sc.nextLine();
						login1 = sc.nextLine();
						login1 = "user:" + login1;
						sendDetails(login1);
						System.out.println("Enter password: ");
						login2 = sc.nextLine();
						login2 = "pass:" + login2;
						sendDetails(login2);
						
						message = (String)in.readObject();
						System.out.println(message);
						if(message.toLowerCase().contains("Login successful".toLowerCase())){
							// Details saved on server side for login
							System.out.println(message);
							Account acc = new Account();
							message = (String)in.readObject();
							acc.setBankNum(message);
							message = (String)in.readObject();
							acc.setName(message);
							message = (String)in.readObject();
							acc.setAddress(message);
							message = (String)in.readObject();
							message = message.replace("user:", "");
							acc.setUsername(message);
						
							Boolean menu = true;
							String n;
							String u;
							String a;
							do{
						
							// Main menu selection
							System.out.println("");
							System.out.println("The balance of " + acc.getUsername() + ": "
							+ acc.getBalance());
							System.out.println("");
							System.out.println("---------------------------------------------");
							System.out.println("1.Display current balance");
							System.out.println("2.Withdraw money");
							System.out.println("3. Log Money");
							System.out.println("4. Transaction history");
							System.out.println("5. User details");
							System.out.println("6. Edit user details");
							System.out.println("0. Log out");
							System.out.println("---------------------------------------------");
							
							option = sc.nextInt();
							
							switch (option) {
				            case 1:  System.out.println("The balance of" + username + ": "
									+ acc.getBalance()); // Current balance 
				                     break;
				            case 2:  System.out.print("Enter amount: "); // Withdraw
				            		 cash = sc.nextInt();
				            		 acc.WithdrawMoney(cash);
				                     break;
				            case 3:  System.out.print("Enter amount: "); // Put money into account
			            			 cash = sc.nextInt();
			            			 acc.LodgeMoney(cash);
				                     break;
				            case 4:  option = 4;
				                     break;
				            case 5:  acc.DisplayDetails(); // Display all details
				            		 break;
				            case 6:  System.out.print("\nEnter new name: "); // Edit current details
				            		 n = sc.nextLine();
				            		 System.out.print("\nEnter new address: ");
				            		 a = sc.nextLine();
				            		 System.out.print("\nEnter new username: ");
				            		 u = sc.nextLine();
				            		 acc.UpdateDetails(n, a, u);
		                     break;
				            default: option = 0; // exit
				            		// sendDetails("bye!");
				            		 menu = false;
				                     break;
							}
						}while(menu == true);
					  }
						
					}else{
						System.out.println("Are you sure you want to exit? 9 to quit. "
								+ "Anything else to return to menu ");
						sentinel = sc.nextInt();
						sc.close();
					}
				
			}while(sentinel != 9);
		}catch(ClassNotFoundException classNot){
			System.err.println("data received in unknown format");
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
 	
 	void sendDetails(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			//System.out.println(msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
 	
	
 	
	public static void main(String[] args){
		
		Client client = new Client();
		try {
			System.out.println("Connecting to Amazon Web Server...");
			client.run();
		} catch (ClassNotFoundException e) {
			System.err.println("Error when running client");
			e.printStackTrace();
		}
	}

}
