package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.scene.image.Image;

public class UserInfo implements Serializable {
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private AccountStatus status;
	private String address;
	private String phonenumber;
	private String picture;
	private TreeMap<String, BookInfo> browsingHistoryT;
	private LinkedList<BookInfo> browsingHistoryL;
	private AccountType type;

	public UserInfo(String username, String firstName, String lastName, String password, AccountStatus status, String address,
			String phonenumber, String picture, TreeMap<String, BookInfo> browsingHistoryT,
			LinkedList<BookInfo> browsingHistoryL, AccountType type) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.status = status;
		this.address = address;
		this.phonenumber = phonenumber;
		this.picture = picture;
		this.browsingHistoryT = browsingHistoryT;
		this.browsingHistoryL = browsingHistoryL;
		this.type = type;
	}
	
	
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public TreeMap<String, BookInfo> getBrowsingHistoryT() {
		return browsingHistoryT;
	}

	public void setBrowsingHistoryT(TreeMap<String, BookInfo> browsingHistoryT) {
		this.browsingHistoryT = browsingHistoryT;
	}

	public LinkedList<BookInfo> getBrowsingHistoryL() {
		return browsingHistoryL;
	}

	public void setBrowsingHistoryL(LinkedList<BookInfo> browsingHistoryL) {
		this.browsingHistoryL = browsingHistoryL;
	}

	@Override
	public String toString() {
		return "UserInfo [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", status="
				+ status + ", address=" + address + ", phonenumber=" + phonenumber + ", picture=" + picture
				+ ", browsingHistoryT=" + browsingHistoryT + ", browsingHistoryL=" + browsingHistoryL + ", type=" + type
				+ "]";
	}
}
