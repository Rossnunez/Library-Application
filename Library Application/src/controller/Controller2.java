package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.AccountStatus;
import model.AccountType;
import model.Book;
import model.BookInfo;
import model.UserInfo;

public class Controller2 implements Initializable {
	@FXML
	public FileChooser fileChooser;
	public File filePath;
	//public UserInfo userInfo;
	public ComboBox<String> accountTypeBox;
	public HashMap<String, UserInfo> userMap;
	public HashSet<String> dictionary;
	public TreeSet<Book> bookSet;
	public String buff;
	//private static UserInfo userInfo;

	
	public TextField firstName;
	public TextField lastName;
	public TextField username;
	public TextField password;
	public TextField address;
	public TextField phonenumber;
	public ImageView photo;
	public Image defaultProfile;
	
	public ObservableList<String> at = FXCollections.observableArrayList("User", "Admin");
	
	public void goBack(ActionEvent event) throws IOException {
		Parent secondRoot = FXMLLoader.load(getClass().getResource("/view/view1.fxml"));
		Scene secondScene = new Scene(secondRoot);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(secondScene);
		window.show();
	}
	
	
	public void CreateUser(ActionEvent event) {
		
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
			String phone = phonenumber.getText();
			Image pfp = photo.getImage();
			
			LinkedList<BookInfo> browsingHistoryL = new LinkedList<BookInfo>();
			TreeMap<String, BookInfo> browsingHistoryT = new TreeMap<String, BookInfo>();
		
			String type = accountTypeBox.getValue();
			AccountType at = null;
			if(type.contentEquals("User")) {
				at = AccountType.USER;
			} else {
				at = AccountType.ADMIN;
			}
			UserInfo ui = new UserInfo(user, fn, ln, pass, AccountStatus.ACTIVE, add, phone, buff, browsingHistoryT, browsingHistoryL, at);
			userMap.put(user, ui);
			firstName.clear();
			lastName.clear();
			username.clear();
			password.clear();
			phonenumber.clear();
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
	
	public void chooseImageButton(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		fileChooser = new FileChooser();
		//fileChooser.setTitle("Open Image");
		this.setFilePath(fileChooser.showOpenDialog(stage));
		
		try {
			buff = filePath.getPath();
			BufferedImage bufferedImage = ImageIO.read(filePath);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			photo.setImage(image);
		} catch(IOException e ) {
			System.out.println(e.getMessage());
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		accountTypeBox.setValue("User");
		accountTypeBox.setItems(at);
		userMap = Main.getHashMap();
		dictionary = Main.getDictionary();
		defaultProfile = photo.getImage();
		buff = "\\RawData\\user.png";
		
	
		
		
	}


	public File getFilePath() {
		return filePath;
	}


	public void setFilePath(File filePath) {
		this.filePath = filePath;
	}
	
}
