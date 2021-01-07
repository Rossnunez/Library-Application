package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Book;
import model.BookInfo;
import model.OverDueUsers;
import model.UserInfo;

public class Controller3 implements Initializable {
	@FXML
	// for initializing data structures
	public HashMap<String, UserInfo> userMap;
	public HashSet<String> dictionary;
	public TreeMap<String, Book> bookMapI;
	public TreeMap<String, Book> bookMapT;
	public TreeMap<String, Book> bookMapLN;
	public TreeSet<OverDueUsers> allOverDueBooks;
	public TreeSet<Book> selectedBooks;
	public UserInfo userInfo;
	public String username;
	public BufferedImage buff;

	// for update tab
	public Text usernameText;
	public TextField firstName;
	public TextField lastName;
	public TextField password;
	public TextField phoneNumber;
	public TextField address;
	public ImageView photo;
	public Image defaultProfile;
	public FileChooser fileChooser;
	public File filePath;

	// for search tab
	public ComboBox<String> searchByBox;
	public ObservableList<String> searchList = FXCollections.observableArrayList("ISBN", "TITLE", "LAST NAME");
	public ObservableList<Book> book;
	public TableView<Book> table = new TableView<Book>();
	public TableColumn<Book, String> price;
	public TableColumn<Book, String> author;
	public TableColumn<Book, String> isbn;
	public TableColumn<Book, String> title;
	public TableColumn<Book, String> numOfCopies;
	public Text selectionText;
	public TextField searchBar;

	// for history tab
	public ComboBox<String> searchByBox1;
	public ObservableList<String> searchList1 = FXCollections.observableArrayList("ALL BOOKS", "OVERDUE BOOKS");
	public ObservableList<BookInfo> book1;
	public TableView<BookInfo> table1 = new TableView<BookInfo>();
	public TableColumn<BookInfo, String> dateBorrowed;
	public TableColumn<BookInfo, String> price1;
	public TableColumn<BookInfo, String> author1;
	public TableColumn<BookInfo, String> isbn1;
	public TableColumn<BookInfo, String> title1;
	public TableColumn<BookInfo, String> dueDate;
	public Text selectionText1;
	public TextField searchBar1;
	///////////////////////////////////////////////////////////////

	// HISTORY TAB//
	// return selected books
	public void returnBook(ActionEvent event) {
		
		bookMapI.get(table1.getSelectionModel().getSelectedItem().getIsbn()).addCopies();
		// bookMapT.get(table1.getSelectionModel().getSelectedItem().getTitle()).addCopies();
		// bookMapLN.get(table1.getSelectionModel().getSelectedItem().getAuthor()).addCopies();

		userInfo.getBrowsingHistoryT().remove(table1.getSelectionModel().getSelectedItem().getIsbn());
		userInfo.getBrowsingHistoryL().remove(table1.getSelectionModel().getSelectedItem());
		table1.getItems().remove(table1.getSelectionModel().getSelectedItem());
		Alert exportAlert = new Alert(AlertType.INFORMATION);
		exportAlert.setTitle("Information Alert");
		exportAlert.setHeaderText("Your Book as been Returned");
		exportAlert.setContentText("INFORMATION ALERT");
		exportAlert.showAndWait();
		
		
	}

	// returns current time
	public String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY hh:mm");
		// current date
		Date dateC = new Date();
		String current = sdf.format(dateC);
		return current;
	}

	// displays all books the moment you select History Tab
	public void displayListedBooks(Event event) {
		book1.clear();
		book1.addAll(userInfo.getBrowsingHistoryL());
		table1.setItems(book1);

	}

	// searches for overdue or all books by isbn
	public void enter1(ActionEvent event) {
		book1.clear();
		String search = searchBar1.getText();
		String type = searchByBox1.getValue();
		//BookInfo bookInfo = userInfo.getBrowsingHistoryT().get(search);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY hh:mm:ss");
		Date currentTime = new Date(System.currentTimeMillis());
		String current = sdf.format(currentTime);
		
		Map<String, BookInfo> result = userInfo.getBrowsingHistoryT().entrySet()
				.stream()
				.filter(map -> map.getValue().getIsbn().startsWith(search))
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

		if (type.contentEquals("OVERDUE BOOKS")) {
			for (java.util.Map.Entry<String, BookInfo> entry : result.entrySet()) {
				if (entry.getValue().getDueDate().compareTo(current) < 0) {
					book1.add(entry.getValue());
				}
			} 
		} else {
			for (java.util.Map.Entry<String, BookInfo> entry : result.entrySet()) {
				book1.add(entry.getValue());
			} 
		}

		table1.setItems(book1);
	}

	// displays all books either by overdue or all books
	public void displayAll(ActionEvent event) {
		book1.clear();
		String type = searchByBox1.getValue();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY hh:mm:ss");
		Date currentTime = new Date(System.currentTimeMillis());
		String current = sdf.format(currentTime);
		
		if (type.contentEquals("OVERDUE BOOKS")) {
			Map<String, BookInfo> result = userInfo.getBrowsingHistoryT().entrySet().stream()
					.filter(map -> map.getKey().compareTo(current) < 0)
					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

			for (java.util.Map.Entry<String, BookInfo> entry : result.entrySet()) {
				book1.add(entry.getValue());
			}
			table1.setItems(book1);
		} else {
			book1.addAll(userInfo.getBrowsingHistoryL());
			table1.setItems(book1);
		}
	}

	// book title shows up in return cart
	public void checkIn1(MouseEvent event) {
		try {
			selectionText1.setText(table1.getSelectionModel().getSelectedItem().getTitle());
		} catch (NullPointerException e) {
			//
		}
	}

	// SEARCH TAB////////////////////////////////////////////
	public void spellCheck(KeyEvent event) {
		String type = searchByBox.getValue();
		try {
			if (type.contentEquals("TITLE")) {
				if (event.getCode() == KeyCode.SPACE) {
					String word = searchBar.getText();
					String[] words = word.split(" ");
					if (!dictionary.contains(words[words.length - 1])) {
						Alert creditsAlert = new Alert(AlertType.ERROR);
						creditsAlert.setTitle("WARNING ALERT");
						creditsAlert.setHeaderText(
								"The Word " + words[words.length - 1].toString() + " is spelled incorrectly.");
						creditsAlert.setContentText("This word is not in the dictionary.");
						creditsAlert.show();
						searchBar.setText(searchBar.getText() + " ");
					}
				}
			}
		} catch (NullPointerException e) {
			// allow the user to continue to search if the filter isnt selected
		}
	}

	public void checkOutBook(ActionEvent event) {
		if ((table.getSelectionModel().getSelectedItem().getNumOfCopies()) > 0) {

			bookMapI.get(table.getSelectionModel().getSelectedItem().getIsbn()).subtractCopies();
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY hh:mm:ss");
			Date currentTime = new Date(System.currentTimeMillis());
			String c = sdf.format(currentTime);
			Date dateF = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));
			String f = sdf.format(dateF);
			BookInfo bookInfo = new BookInfo(c, table.getSelectionModel().getSelectedItem(), f, username);
			
			//String key = 
			userInfo.getBrowsingHistoryL().add(bookInfo);
			userInfo.getBrowsingHistoryT().put(bookInfo.getDueDate(), bookInfo);

			Alert exportAlert = new Alert(AlertType.INFORMATION);
			exportAlert.setTitle("Information Alert");
			exportAlert.setHeaderText("Your Book as been Checked Out!");
			exportAlert.setContentText("INFORMATION ALERT");
			exportAlert.showAndWait();
			table.refresh();

		} else {
			Alert exportAlert = new Alert(AlertType.WARNING);
			exportAlert.setTitle("Information Alert");
			exportAlert.setHeaderText("No more copies avaliable");
			exportAlert.setContentText("INFORMATION ALERT");
			exportAlert.showAndWait();
		}
	}

	// select books
	public void checkIn(MouseEvent event) {
		try {
			selectionText.setText(table.getSelectionModel().getSelectedItem().getTitle());
		} catch (NullPointerException e) {
			// allow the user to sort the table columns without getting a null error
		}
	}

	// search for books by isbn, last name, or title
	public void enter(ActionEvent event) {
		book.clear();

		String search = searchBar.getText();
		String type = searchByBox.getValue();
		try {
			if (type.contentEquals("ISBN")) {
				//////////////////////////////////
				Map<String, Book> result = bookMapI.entrySet()
						.stream()
						.filter(map -> map.getKey().startsWith(search))
						.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

				for (java.util.Map.Entry<String, Book> entry : result.entrySet()) {
					book.add(entry.getValue());
				}
				table.setItems(book);
				/////////////////////////////////////
			} else if (type.contentEquals("LAST NAME")) {
				//////////////////////////////////
				Map<String, Book> result = bookMapLN.entrySet().stream().filter(map -> map.getKey().startsWith(search))
						.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

				for (java.util.Map.Entry<String, Book> entry : result.entrySet()) {
					book.add(entry.getValue());
				}
				table.setItems(book);
				/////////////////////////
			} else {
				//////////////////////////
				Map<String, Book> result = bookMapT.entrySet().stream().filter(map -> map.getKey().startsWith(search))
						.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

				for (java.util.Map.Entry<String, Book> entry : result.entrySet()) {
					book.add(entry.getValue());
				}
				table.setItems(book);
				/////////////////////
			}
		} catch (NullPointerException e) {
			Alert searchByAlert = new Alert(AlertType.WARNING);
			searchByAlert.setTitle("WARNING ALERT");
			searchByAlert.setHeaderText("Select a search filter first!");
			searchByAlert.setContentText("Please select by isbn, title or author's last name");
			searchByAlert.showAndWait();
		}
	}
	//////////// UPDATE//////////////////

	// UPDATE
	// TAB////////////////////////////////////////////////////////////////////////
	public void logOut(ActionEvent event) throws IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY hh:mm:ss");
		Date currentTime = new Date(System.currentTimeMillis());
		String current = sdf.format(currentTime);
		
		Map<String, BookInfo> result = userInfo.getBrowsingHistoryT().entrySet().stream()
				.filter(map -> map.getValue().getDueDate().compareTo(current) < 0)
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		
		for (java.util.Map.Entry<String, BookInfo> entry : result.entrySet()) {
			OverDueUsers overDueUsers = new OverDueUsers(userInfo, entry.getValue());
			allOverDueBooks.add(overDueUsers);
		}

		Parent secondRoot = FXMLLoader.load(getClass().getResource("/view/view1.fxml"));
		Scene secondScene = new Scene(secondRoot);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(secondScene);
		window.show();
	}

	public void editFN(ActionEvent event) {
		TextInputDialog efn = new TextInputDialog("Enter your First Name:");
		efn.setHeaderText("Edit First Name");
		efn.showAndWait();
		String fn = efn.getEditor().getText();
		userInfo.setFirstName(fn);
		firstName.setText(fn);

	}

	public void editLN(ActionEvent event) {
		TextInputDialog efn = new TextInputDialog("Enter your Last Name:");
		efn.setHeaderText("Edit Last Name");
		efn.showAndWait();
		String ln = efn.getEditor().getText();
		userInfo.setLastName(ln);
		lastName.setText(ln);
	}

	public void editPass(ActionEvent event) {
		TextInputDialog efn = new TextInputDialog("Enter your Password:");
		efn.setHeaderText("Edit Password");
		efn.showAndWait();
		String pass = efn.getEditor().getText();
		userInfo.setPassword(pass);
		password.setText(pass);
	}

	public void editAddress(ActionEvent event) {
		TextInputDialog efn = new TextInputDialog("Enter your Address:");
		efn.setHeaderText("Edit Address");
		efn.showAndWait();
		String addressS = efn.getEditor().getText();
		userInfo.setAddress(addressS);
		address.setText(addressS);
	}

	public void editPN(ActionEvent event) {
		TextInputDialog efn = new TextInputDialog("Enter your Phonenumber:");
		efn.setHeaderText("Edit Phonenumber");
		efn.showAndWait();
		String pn = efn.getEditor().getText();
		userInfo.setPhonenumber(pn);
		phoneNumber.setText(pn);
	}

	public void editImage(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		fileChooser = new FileChooser();
		// fileChooser.setTitle("Open Image");
		this.setFilePath(fileChooser.showOpenDialog(stage));

		try {
			buff = ImageIO.read(filePath);
			Image image = SwingFXUtils.toFXImage(buff, null);
			photo.setImage(image);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		userMap.get(username).setPicture(filePath.getPath());
	}

	public File getFilePath() {
		return filePath;
	}

	public void setFilePath(File filePath) {
		this.filePath = filePath;
	}
	///////////////////////////////////////////

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// initializing variables
		userMap = Main.getHashMap();
		dictionary = Main.getDictionary();
		bookMapI = Main.getBookMapI();
		bookMapT = Main.getBookMapT();
		bookMapLN = Main.getBookMapLN();
		userInfo = Controller1.getUserInfo();
		
		File file = new File(Controller1.getUserInfo().getPicture());
		setFilePath(file);
		try {
			buff = ImageIO.read(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = SwingFXUtils.toFXImage(buff, null);
		username = Controller1.getUsername();
		allOverDueBooks = Main.getAllOverDueBooks();
		
		photo.setImage(image);

		// for update tab
		firstName.setText(userInfo.getFirstName());
		lastName.setText(userInfo.getLastName());
		password.setText(userInfo.getPassword());
		address.setText(userInfo.getAddress());
		phoneNumber.setText(userInfo.getPhonenumber());
		usernameText.setText(username);

		// for search tab
		price.setCellValueFactory(new PropertyValueFactory<Book, String>("price"));
		title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		isbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
		author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		numOfCopies.setCellValueFactory(new PropertyValueFactory<Book, String>("numOfCopies"));
		searchByBox.setItems(searchList);
		book = FXCollections.observableArrayList();

		// for history tab
		dateBorrowed.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("date"));
		price1.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("price"));
		title1.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("title"));
		isbn1.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("isbn"));
		author1.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("author"));
		dueDate.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("dueDate"));
		searchByBox1.setItems(searchList1);
		book1 = FXCollections.observableArrayList();

	}

}
