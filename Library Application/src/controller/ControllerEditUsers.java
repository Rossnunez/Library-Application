package controller;

import java.awt.image.BufferedImage;
import java.io.File;
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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.AccountStatus;
import model.AccountType;
import model.Book;
import model.BookInfo;
import model.OverDueUsers;
import model.StorageBag;
import model.UserInfo;

public class ControllerEditUsers implements Initializable {
	@FXML
	public StorageBag storageBag;
	public HashMap<String, UserInfo> userMap;
	public TreeSet<OverDueUsers> allOverDueBooks;
	public HashSet<String> dictionary;
	public TreeMap<String, Book> bookMapI;
	public TreeMap<String, Book> bookMapT;
	public TreeMap<String, Book> bookMapLN;
	public FileChooser fileChooser;
	public File filePath;
	public String buff;
	
	//add users//
	public TextField firstName;
	public TextField lastName;
	public TextField username;
	public TextField password;
	public TextField address;
	public TextField phoneNumber;
	public ImageView photo;
	public Image defaultProfile;
	public ObservableList<String> accountTypeList = FXCollections.observableArrayList("User", "Admin");
	public ComboBox<String> accountTypeBox;
	public ObservableList<String> accountStatusList = FXCollections.observableArrayList("Active", "Suspended");
	public ComboBox<String> accountStatusBox;
	
	//search for ALL users//
	public ObservableList<UserInfo> allUsersList;
	public TableView<UserInfo> table = new TableView<UserInfo>();
	public TableColumn<BookInfo, String> usernameColumn;
	public TableColumn<BookInfo, String> userStatus;
	public Text selectionText;
	public TextField searchBar;
	
	//search for users with OVERDUE BOOKS//
	public ObservableList<OverDueUsers> overDueUsersList;
	public ComboBox<String> searchByBox;
	public ObservableList<String> searchList = FXCollections.observableArrayList("USERNAME", "ISBN");
	public Text selectionText1;
	public TextField searchBar1;
	public TableView<OverDueUsers> table1 = new TableView<OverDueUsers>();
	public TableColumn<OverDueUsers, String> price;
	public TableColumn<OverDueUsers, String> author;
	public TableColumn<OverDueUsers, String> isbn;
	public TableColumn<OverDueUsers, String> title;
	public TableColumn<OverDueUsers, String> dueDate;
	public TableColumn<OverDueUsers, String> usernameColumn1;
	public TableColumn<OverDueUsers, String> userStatus1;
	/////////////////////////////////////////////////////
	
	//searching for users by OVERDUE BOOKS//
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
	
	public void displayAll(ActionEvent event) {
		overDueUsersList.clear();
		overDueUsersList.addAll(allOverDueBooks);
		table1.setItems(overDueUsersList);
	}
	
	public void enter1(ActionEvent event) {
		overDueUsersList.clear();
		String search = searchBar1.getText();
		String type = searchByBox.getValue();
		
			if (type.contentEquals("USERNAME")) {
				List<OverDueUsers> result = allOverDueBooks.stream()
						.filter(book -> book.getUsername().startsWith(search)).collect(Collectors.toList());
				overDueUsersList.addAll(result);
				table1.setItems(overDueUsersList);
			} else if(type.contentEquals("ISBN")) {
				List<OverDueUsers> result = allOverDueBooks.stream()
						.filter(book -> book.getIsbn().startsWith(search)).collect(Collectors.toList());
				overDueUsersList.addAll(result);
				table1.setItems(overDueUsersList);
			}
	}
	
	public void checkIn1(MouseEvent event) {
		try {
			selectionText1.setText(table1.getSelectionModel().getSelectedItem().getUsername());
		} catch (NullPointerException e) {
			// allow the user to sort the table columns without getting a null error
		}
	}
	
	public void removeUser1(ActionEvent event) {
		userMap.remove(table1.getSelectionModel().getSelectedItem().getUsername());
		String username = table1.getSelectionModel().getSelectedItem().getUsername();
		
		List<OverDueUsers> result = allOverDueBooks.stream()
				.filter(book -> book.getUsername().contentEquals(username)).collect(Collectors.toList());
		
		allOverDueBooks.removeAll(result);
		overDueUsersList.removeAll(result);
		table1.setItems(overDueUsersList);
	}
	
	public void changeUserStatus1(ActionEvent event) {
		if(table1.getSelectionModel().getSelectedItem().getStatus() == AccountStatus.ACTIVE) {
				table1.getSelectionModel().getSelectedItem().setAccountStatus(AccountStatus.SUSPENDED);
		} else {
			table1.getSelectionModel().getSelectedItem().setAccountStatus(AccountStatus.ACTIVE);
		}
		table1.refresh();
	}
	
	
	//adding users methods//
	public void checkIn(MouseEvent event) {
		try {
			selectionText.setText(table.getSelectionModel().getSelectedItem().getUsername());
		} catch (NullPointerException e) {
			// allow the user to sort the table columns without getting a null error
		}
	}
	
	public void createUser(ActionEvent event) {
		
		if(userMap.size() > 50000) {
			System.out.println("TOO MANY USERS");
			return;
		}
		String user = username.getText();	
		if(!userMap.containsKey(user)) {
			String fn = firstName.getText();
			String ln = lastName.getText();
			String pass = password.getText();
			String add = address.getText();
			String phone = phoneNumber.getText();
			//Image pfp = photo.getImage();
			LinkedList<BookInfo> browsingHistoryL = new LinkedList<BookInfo>();
			TreeMap<String, BookInfo> browsingHistoryT = new TreeMap<String, BookInfo>();
		
			String stringType = accountTypeBox.getValue();
			String stringStatus = accountStatusBox.getValue();
			
			AccountType type = null;
			AccountStatus status = null;
			if(stringType.contentEquals("User")) {
				type = AccountType.USER;
			} else {
				type = AccountType.ADMIN;
			}
			if(stringStatus.contentEquals("Active")) {
				status = AccountStatus.ACTIVE;
			} else {
				status = AccountStatus.SUSPENDED;
			}
			
			UserInfo userinfo = new UserInfo(user, fn, ln, pass, status, add, phone, buff, browsingHistoryT, browsingHistoryL, type);
			userMap.put(user, userinfo);
			firstName.clear();
			lastName.clear();
			username.clear();
			password.clear();
			phoneNumber.clear();
			address.clear();
			photo.setImage(defaultProfile);
			System.out.println(userMap.toString());
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ACCOUNT CREATION ERROR");
			alert.setContentText("Username already exist");
			alert.setHeaderText("Enter a different username");
			alert.showAndWait();
		}
		
	}
	
	//searching and removing user//
	public void removeUser(ActionEvent event) {
		userMap.remove(table.getSelectionModel().getSelectedItem().getUsername());
		table.getItems().remove(table.getSelectionModel().getSelectedItem());
	}
	
	public void changeUserStatus(ActionEvent event) {
		if(table.getSelectionModel().getSelectedItem().getStatus() == AccountStatus.ACTIVE) {
				table.getSelectionModel().getSelectedItem().setStatus(AccountStatus.SUSPENDED);
		} else {
			table.getSelectionModel().getSelectedItem().setStatus(AccountStatus.ACTIVE);
		}
		table.refresh();
	}
	
	public void enter(ActionEvent event) {
		allUsersList.clear();
		String search = searchBar.getText();
		
			Map<String, UserInfo> result = userMap.entrySet()
					.stream()
					.filter(map -> map.getKey().startsWith(search))
					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

			for (java.util.Map.Entry<String, UserInfo> entry : result.entrySet()) {
				allUsersList.add(entry.getValue());
			}
			table.setItems(allUsersList);
	}
	
	public void changeImage(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		fileChooser = new FileChooser();
		//fileChooser.setTitle("Open Image");
		this.setFilePath(fileChooser.showOpenDialog(stage));
		
		try {
			BufferedImage bufferedImage = ImageIO.read(filePath);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			photo.setImage(image);
		} catch(IOException e ) {
			System.out.println(e.getMessage());
		}
	}
	
	public File getFilePath() {
		return filePath;
	}

	public void setFilePath(File filePath) {
		this.filePath = filePath;
	}
	
	public void backToMenu(ActionEvent event) throws IOException {
		Parent secondRoot = FXMLLoader.load(getClass().getResource("/view/view5.fxml"));
		Scene secondScene = new Scene(secondRoot);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
		accountTypeBox.setItems(accountTypeList);
		accountStatusBox.setItems(accountStatusList);
		defaultProfile = photo.getImage();
		storageBag = Main.getStorageBag();
		buff = "C:\\Users\\nross\\eclipse-workspace\\cse218\\Library Application\\RawData\\user.png";
		
		
		//for searching by ALL USERS//
		usernameColumn.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("username"));
		userStatus.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("status"));
		allUsersList = FXCollections.observableArrayList();
		
		//for searching by users with OVERDUE BOOKS//
		overDueUsersList = FXCollections.observableArrayList();
		price.setCellValueFactory(new PropertyValueFactory<OverDueUsers, String>("price"));
		title.setCellValueFactory(new PropertyValueFactory<OverDueUsers, String>("title"));
		isbn.setCellValueFactory(new PropertyValueFactory<OverDueUsers, String>("isbn"));
		author.setCellValueFactory(new PropertyValueFactory<OverDueUsers, String>("author"));
		dueDate.setCellValueFactory(new PropertyValueFactory<OverDueUsers, String>("dueDate"));
		usernameColumn1.setCellValueFactory(new PropertyValueFactory<OverDueUsers, String>("username"));
		userStatus1.setCellValueFactory(new PropertyValueFactory<OverDueUsers, String>("status"));
		searchByBox.setItems(searchList);
		
		

	}

}
