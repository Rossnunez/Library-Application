package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.AccountStatus;
import model.AccountType;
import model.Book;
import model.BookInfo;
import model.OverDueUsers;
import model.StorageBag;
import model.UserInfo;
import model.Utilities;

public class Main extends Application {
	private static StorageBag storageBag;
	private static HashMap<String, UserInfo> userMap;
	private static TreeMap<String, Book> bookMapI;
	private static TreeMap<String, Book> bookMapT;
	private static TreeMap<String, Book> bookMapLN;
	private static TreeSet<OverDueUsers> allOverDueBooks;
	private static HashSet<String> dictionary;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//USE STREAM.FILTER FOR YOUR SEARCHING LOLSIES
		userMap = new HashMap<String, UserInfo>(50000, (float) 0.5);
		dictionary = new HashSet<String>(99171);
		bookMapI = new TreeMap<String, Book>();
		bookMapT = new TreeMap<String, Book>();
		bookMapLN = new TreeMap<String, Book>();
		allOverDueBooks = new TreeSet<OverDueUsers>();
		String[][] titleAndIsbn = Utilities.emitTitleandIsbn("RawData/textbook_titles.txt", "RawData/textbook_isbns.txt");
		
		insertBooks(titleAndIsbn, bookMapI, bookMapT, bookMapLN);
		insertUsers(userMap);
		
		
		String dictionaryFILEname = "RawData/dictionary.txt";
		File dictionaryFile = new File(dictionaryFILEname);
		Scanner scanner1 = null;
		try {
			scanner1 = new Scanner(dictionaryFile);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR:");
			System.out.println("Could not find file path.");
		}
		while (scanner1.hasNextLine()) {
			dictionary.add(scanner1.nextLine());
		}
		scanner1.close();
		
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/view1.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static HashSet<String> getDictionary(){
		return dictionary;
	}
	
	public static TreeSet<OverDueUsers> getAllOverDueBooks(){
		return allOverDueBooks;
	}
	
	public static TreeMap<String, Book> getBookMapI(){
		return bookMapI;
	}
	
	public static TreeMap<String, Book> getBookMapT(){
		return bookMapT;
	}
	
	public static TreeMap<String, Book> getBookMapLN(){
		return bookMapLN;
	}
	
	public static HashMap<String, UserInfo> getHashMap() {
		return userMap;
	}
	
	public static StorageBag getStorageBag() {
		return storageBag;
	}
	
	public static void insertUsers(HashMap<String, UserInfo> userMap) {
		String image = "RawData/user.png";
		for(int i = 0; i < 100; i++) {
			String username = "username" + i;
			TreeMap<String, BookInfo> bookingHistoryT = new TreeMap<String, BookInfo>();
			LinkedList<BookInfo> browsingHistoryL = new LinkedList<BookInfo>();
			UserInfo userInfo = new UserInfo(username, "firstname", "lastname", "password", AccountStatus.ACTIVE, "address",
					"phonenumber", image, bookingHistoryT, browsingHistoryL, AccountType.USER);
			userMap.put(username, userInfo);
		}
		TreeMap<String, BookInfo> bookingHistoryT = new TreeMap<String, BookInfo>();
		LinkedList<BookInfo> browsingHistoryL = new LinkedList<BookInfo>();
		String username = "John";
		UserInfo userInfo = new UserInfo(username, "John", "Doe", "Doe", AccountStatus.ACTIVE, "43 Lewis Ave",
				"631-234-7654", image, bookingHistoryT, browsingHistoryL, AccountType.USER);
		userMap.put(username, userInfo);
		String usernameA = "Jane";
		UserInfo userInfoA = new UserInfo(username, "Jane", "Doe", "Doe", AccountStatus.ACTIVE, "110th Queens Street",
				"631-234-7654", image, bookingHistoryT, browsingHistoryL, AccountType.ADMIN);
		userMap.put(usernameA, userInfoA);
		
	}
	
	public static void insertBooks(String[][] titleAndIsbn, TreeMap<String, Book> bookMapI, TreeMap<String, Book> bookMapT, TreeMap<String, Book> bookMapLN) throws FileNotFoundException {
		for(int i = 0; i < 5000; i++) {
			int rand = (int) (Math.random() * 38639);
			String title = titleAndIsbn[rand][0];
			String isbn = titleAndIsbn[rand][1];
			double price = Utilities.emitPrice();
			String author = Utilities.emitAuthor("RawData/First_Names.txt");
			int copies = (int)(Math.random() * 10);
			
			Book book = new Book(title, isbn, price, author, copies);
			
			bookMapI.put(isbn, book);
			bookMapT.put(title, book);
			bookMapLN.put(author, book);
		}
	}
	
	
	
	

}
