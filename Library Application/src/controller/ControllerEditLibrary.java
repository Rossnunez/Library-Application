package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import model.Book;

public class ControllerEditLibrary implements Initializable {
	@FXML
	public TreeMap<String, Book> bookMapI;
	public TreeMap<String, Book> bookMapT;
	public TreeMap<String, Book> bookMapLN;
	public HashSet<String> dictionary;
	public TabPane tabPane;
	public Tab updateTab;
	//
	// ADD BOOKS TAB VARIABLES//
	public TextField bookTitleField;
	public TextField authorField;
	public TextField noOfCopiesField;
	public TextField bookPriceField;
	public TextField isbnField;
	//
	// SEARCH BOOKS TAB VARIABLES//
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
	//
	// UPDATE BOOK TAB VARIABLES//
	public TextField bookTitleUpdate;
	public TextField authorUpdate;
	public TextField noOfCopiesUpdate;
	public TextField bookPriceUpdate;
	public TextField isbnUpdate;
	/////////////////////////////////////////////

	// Add Book to Library//
	public void addBook(ActionEvent event) {
		String title = bookTitleField.getText();
		String isbn = isbnField.getText();
		String author = authorField.getText();
		String bookNoOfCopies = noOfCopiesField.getText();
		String bookPrice = bookPriceField.getText();
		int price = Integer.parseInt(bookPrice);
		int noOfCopies = Integer.parseInt(bookNoOfCopies);
		Book book = new Book(title, isbn, price, author, noOfCopies);

		bookMapI.put(isbn, book);
		bookMapT.put(title, book);
		bookMapLN.put(author, book);

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Alert");
		alert.setHeaderText("Book added!");
		alert.setContentText("Book was successfully added to Library");
		alert.showAndWait();

		bookTitleField.clear();
		noOfCopiesField.clear();
		bookPriceField.clear();
		isbnField.clear();
		authorField.clear();
	}

	public void backToMenu(ActionEvent event) throws IOException {
		Parent secondRoot = FXMLLoader.load(getClass().getResource("/view/view5.fxml"));
		Scene secondScene = new Scene(secondRoot);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(secondScene);
		window.show();
	}
	//////////////////////////////

	// Search for Books to Update and Remove//
	public void enter(ActionEvent event) {
		book.clear();

		String search = searchBar.getText();
		String type = searchByBox.getValue();
		try {
			if (type.contentEquals("ISBN")) {
				//////////////////////////////////
				Map<String, Book> result = bookMapI.entrySet().stream().filter(map -> map.getKey().startsWith(search))
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
			}
		} catch (NullPointerException e) {
			Alert searchByAlert = new Alert(AlertType.WARNING);
			searchByAlert.setTitle("WARNING ALERT");
			searchByAlert.setHeaderText("Select a search filter first!");
			searchByAlert.setContentText("Please select by isbn, title or author's last name");
			searchByAlert.showAndWait();
		}
	}

	public void checkIn(MouseEvent event) {
		try {
			selectionText.setText(table.getSelectionModel().getSelectedItem().getTitle());
		} catch (NullPointerException e) {
			// allow the user to sort the table columns without getting a null error
		}
	}

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
	
	public void removeBook(ActionEvent event) {
		try {
			bookMapI.remove(table.getSelectionModel().getSelectedItem().getIsbn());
			bookMapT.remove(table.getSelectionModel().getSelectedItem().getTitle());
			bookMapLN.remove(table.getSelectionModel().getSelectedItem().getAuthor());
			table.getItems().remove(table.getSelectionModel().getSelectedItem());
			Alert exportAlert = new Alert(AlertType.INFORMATION);
			exportAlert.setTitle("Information Alert");
			exportAlert.setHeaderText("Book has been removed");
			exportAlert.setContentText("INFORMATION ALERT");
			exportAlert.showAndWait();
		} catch(NullPointerException e) {
			Alert searchByAlert = new Alert(AlertType.WARNING);
			searchByAlert.setTitle("WARNING ALERT");
			searchByAlert.setHeaderText("No book Selected");
			searchByAlert.setContentText("Please select a book first");
			searchByAlert.showAndWait();
		}
	}

	public void updateBook(ActionEvent event) {
		Book book = table.getSelectionModel().getSelectedItem();
		
		bookTitleUpdate.setText(book.getTitle());
		authorUpdate.setText(book.getAuthor());
		isbnUpdate.setText(book.getIsbn());
		bookPriceUpdate.setText(String.valueOf(book.getPrice()));
		noOfCopiesUpdate.setText(String.valueOf(book.getNumOfCopies()));
		tabPane.getSelectionModel().select(updateTab);
	}
	///////////////////////////////////////////////
	
	// Update Selected Books //
	public void editTitle(ActionEvent event) {
		if (!bookTitleUpdate.getText().isEmpty()) {
			TextInputDialog efn = new TextInputDialog("Enter New Book Title:");
			efn.setHeaderText("Edit Title");
			efn.showAndWait();
			String title = efn.getEditor().getText();
			Book book = bookMapI.get(isbnUpdate.getText());
			bookMapT.remove(book.getTitle());
			bookMapI.get(book.getIsbn()).setTitle(title);
			bookMapT.put(title, book);
			bookTitleUpdate.setText(title);
		}
	}
	
	public void editAuthor(ActionEvent event) {
		TextInputDialog efn = new TextInputDialog("Enter a New Author:");
		efn.setHeaderText("Edit Author");
		efn.showAndWait();
		String author = efn.getEditor().getText();
		Book book = bookMapI.get(isbnUpdate.getText());
		
		bookMapLN.remove(book.getAuthor());
		bookMapI.get(book.getIsbn()).setAuthor(author);
		bookMapLN.put(author, book);
		
		authorUpdate.setText(author);
	}
	
	public void editISBN(ActionEvent event) {
		TextInputDialog efn = new TextInputDialog("Enter a New ISBN:");
		efn.setHeaderText("Edit ISBN");
		efn.showAndWait();
		String isbn = efn.getEditor().getText();
		Book book = bookMapT.get(bookTitleUpdate.getText());
		
		bookMapI.remove(book.getIsbn());
		bookMapLN.get(book.getAuthor()).setIsbn(isbn);
		bookMapI.put(isbn, book);
		
		isbnUpdate.setText(isbn);
	}
	
	public void editPrice(ActionEvent event) {
		TextInputDialog efn = new TextInputDialog("Enter a Price:");
		efn.setHeaderText("Edit Price");
		efn.showAndWait();
		String priceS = efn.getEditor().getText();
		int price = Integer.parseInt(priceS);

		bookMapI.get(isbnUpdate.getText()).setPrice(price);
		
		bookPriceUpdate.setText(priceS);
	}
	
	public void editCopies(ActionEvent event) {
		TextInputDialog efn = new TextInputDialog("Enter the Number of Copies:");
		efn.setHeaderText("Edit No. of Copies");
		efn.showAndWait();
		String noOfCopiesS = efn.getEditor().getText();
		int noOfCopies = Integer.parseInt(noOfCopiesS);

		bookMapI.get(isbnUpdate.getText()).setNumOfCopies(noOfCopies);
		
		noOfCopiesUpdate.setText(noOfCopiesS);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bookMapI = Main.getBookMapI();
		bookMapT = Main.getBookMapT();
		bookMapLN = Main.getBookMapLN();
		dictionary = Main.getDictionary();

		// for book searching//
		price.setCellValueFactory(new PropertyValueFactory<Book, String>("price"));
		title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		isbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
		author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		numOfCopies.setCellValueFactory(new PropertyValueFactory<Book, String>("numOfCopies"));
		searchByBox.setItems(searchList);
		book = FXCollections.observableArrayList();
		//
	}
}
