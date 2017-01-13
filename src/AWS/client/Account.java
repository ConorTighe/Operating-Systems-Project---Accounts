package AWS.client;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Account {

	    private String bankNum;
	    private String name;
	    private String address;
	    private String username;
	    private int balance = 0;
	  
	    
	    
		public Account() {
			super();
		}

		public Account(String bankNum, String name, String address, String username, int balance) {
			super();
			this.bankNum = bankNum;
			this.name = name;
			this.address = address;
			this.username = username;
			this.balance = balance;
		}
		
		//Getters and Setters for controlling account

		public String getBankNum() {
			return bankNum;
		}

		public void setBankNum(String bankNum) {
			this.bankNum = bankNum;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public int getBalance() {
			return balance;
		}
		
		public void setBalance(int balance) {
			this.balance = balance;
		}

		void LodgeMoney(int money) throws FileNotFoundException{ // Put money in account
			
			PrintWriter out = new PrintWriter("History.txt");
			if(money < 0){
				System.out.print("credit limit of €1000");
				money = 1000;
			}
			System.out.println("Lodge: " + money );
			out.println("Lodge: " + money );
			balance = money + balance;
			System.out.println("Balance: " + balance );
			out.println("Balance: " + balance );
			out.close();
		}
	    
		void WithdrawMoney(int money) throws FileNotFoundException{ // Take money out
			
			PrintWriter out = new PrintWriter("History.txt");
			if(money > 1000){
				System.out.print("credit limit of €1000");
				money = 1000;
			}
			System.out.println("Withdraw: " + money );
			out.println("Withdraw: " + money );
			balance = balance - money;
			System.out.println("Balance: " + balance );
			out.println("Balance: " + balance );
			out.close();
		}
		
		void DisplayDetails(){ // Display details
			
			System.out.println("Username: " + getUsername());
			System.out.println("Name: " + getName());
			System.out.println("Address: " + getAddress());
			System.out.println("Bank number: " + getBankNum());
			
		}
		
		void UpdateDetails(String n, String a, String u){ // update details
			
			setName(n);
			setAddress(a);
			setUsername(u);
			
			System.out.println("Username: " + getUsername());
			System.out.println("Name: " + getName());
			System.out.println("Address: " + getAddress());
		}
		
		void DisplayHistory() throws FileNotFoundException{ // show transaction hiatory
			
			Scanner lineReader = new Scanner(new File("History.txt"));
			String transaction;
			for(int i=0;i<9;i++){
				transaction = lineReader.nextLine();
				System.out.println(transaction);
			}
			lineReader.close();
		}
		
	   
	}