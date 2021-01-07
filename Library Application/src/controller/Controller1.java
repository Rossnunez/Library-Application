package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.TreeSet;

import app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.AccountStatus;
import model.AccountType;
import model.Book;
import model.BookInfo;
import model.OverDueUsers;
import model.StorageBag;
import model.UserInfo;

public class Controller1 implements Initializable{
	@FXML
	public StorageBag storageBag;
	public TreeSet<OverDueUsers> allOverDueBooks;
	public HashSet<String> dictionary;
	public TreeMap<String, Book> bookMapI;
	public TreeMap<String, Book> bookMapT;
	public TreeMap<String, Book> bookMapLN;
	public TextField username;
	public TextField password;
	public HashMap<String, UserInfo> userMap;
	public TreeMap<String, BookInfo> browsingHistoryT;
	public LinkedList<BookInfo> browsingHistoryL;
	public TreeSet<Book> bookSet;
	
	private static UserInfo userInfo;
	private static String userName;
	
	public void exit(ActionEvent event) {
		System.exit(0);
	}
	
	public void backup(ActionEvent event) {
		storageBag = new StorageBag(userMap, bookMapI, bookMapT,
				bookMapLN, allOverDueBooks, dictionary);
		try {
			FileOutputStream fos = new FileOutputStream("RawData/Storage.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(storageBag);
			oos.close();
			Alert exportAlert = new Alert(AlertType.INFORMATION);
			exportAlert.setTitle("Information Alert");
			exportAlert.setHeaderText("Library has been BackedUp!");
			exportAlert.setContentText("INFORMATION ALERT");
			exportAlert.showAndWait();
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
	}
	
	public void restore(ActionEvent event) {
		FileInputStream fis;
		try {
			fis = new FileInputStream("RawData/Storage.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			storageBag = (StorageBag) ois.readObject();
			userMap = storageBag.getUserMap();
			bookMapI = storageBag.getBookMapI();
			bookMapT = storageBag.getBookMapT();
			bookMapLN = storageBag.getBookMapLN();
			allOverDueBooks = storageBag.getAllOverDueBooks();
			dictionary = storageBag.getDictionary();
			Alert exportAlert = new Alert(AlertType.INFORMATION);
			exportAlert.setTitle("Information Alert");
			exportAlert.setHeaderText("Library has been Restored!");
			exportAlert.setContentText("INFORMATION ALERT");
			exportAlert.showAndWait();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public static UserInfo getUserInfo() {
		return userInfo;
	}
	
	public static String getUsername() {
		return userName;
	}
	
	public void LogIn(ActionEvent event) throws IOException {
		String user = username.getText();
		String pass = password.getText();
		
		if(userMap.containsKey(user) && userMap.get(user).getPassword().contentEquals(pass)) {
			if(userMap.get(user).getStatus() == AccountStatus.ACTIVE) {
			if(userMap.get(user).getType() == AccountType.USER) {
				userInfo = userMap.get(user);
				userName = user;
				Parent secondRoot = FXMLLoader.load(getClass().getResource("/view/view3.fxml"));
				Scene secondScene = new Scene(secondRoot);
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
				window.setScene(secondScene);
				window.show(); 
			} else {
				userInfo = userMap.get(user);
				userName = user;
				Parent secondRoot = FXMLLoader.load(getClass().getResource("/view/view5.fxml"));
				Scene secondScene = new Scene(secondRoot);
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
				window.setScene(secondScene);
				window.show(); 
			}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR WARNING");
				alert.setContentText("Your Account is Suspended");
				alert.setHeaderText("Try Another User");
				alert.showAndWait();
			}
		}
		
	}
	
	public void CreateUser(ActionEvent event) throws IOException {
		Parent secondRoot = FXMLLoader.load(getClass().getResource("/view/view2.fxml"));
		Scene secondScene = new Scene(secondRoot);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(secondScene);
		window.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userMap = Main.getHashMap();
		allOverDueBooks = Main.getAllOverDueBooks();
		dictionary = Main.getDictionary();
		bookMapI = Main.getBookMapI();
		bookMapT = Main.getBookMapT();
		bookMapLN = Main.getBookMapLN();
		//browsingHistoryT = Main.getTreeMap();
		//browsingHistoryL = Main.getList();
	}

}
