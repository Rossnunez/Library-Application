package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class StorageBag implements Serializable {
	public HashMap<String, UserInfo> userMap;
	public  TreeMap<String, Book> bookMapI;
	public  TreeMap<String, Book> bookMapT;
	public  TreeMap<String, Book> bookMapLN;
	public  TreeSet<OverDueUsers> allOverDueBooks;
	public  HashSet<String> dictionary;
	
	public StorageBag(HashMap<String, UserInfo> userMap, TreeMap<String, Book> bookMapI, TreeMap<String, Book> bookMapT,
			TreeMap<String, Book> bookMapLN, TreeSet<OverDueUsers> allOverDueBooks, HashSet<String> dictionary) {
		super();
		this.userMap = userMap;
		this.bookMapI = bookMapI;
		this.bookMapT = bookMapT;
		this.bookMapLN = bookMapLN;
		this.allOverDueBooks = allOverDueBooks;
		this.dictionary = dictionary;
	}

	public HashMap<String, UserInfo> getUserMap() {
		return userMap;
	}

	public void setUserMap(HashMap<String, UserInfo> userMap) {
		this.userMap = userMap;
	}

	public TreeMap<String, Book> getBookMapI() {
		return bookMapI;
	}

	public void setBookMapI(TreeMap<String, Book> bookMapI) {
		this.bookMapI = bookMapI;
	}

	public TreeMap<String, Book> getBookMapT() {
		return bookMapT;
	}

	public void setBookMapT(TreeMap<String, Book> bookMapT) {
		this.bookMapT = bookMapT;
	}

	public TreeMap<String, Book> getBookMapLN() {
		return bookMapLN;
	}

	public void setBookMapLN(TreeMap<String, Book> bookMapLN) {
		this.bookMapLN = bookMapLN;
	}

	public TreeSet<OverDueUsers> getAllOverDueBooks() {
		return allOverDueBooks;
	}

	public void setAllOverDueBooks(TreeSet<OverDueUsers> allOverDueBooks) {
		this.allOverDueBooks = allOverDueBooks;
	}

	public HashSet<String> getDictionary() {
		return dictionary;
	}

	public void setDictionary(HashSet<String> dictionary) {
		this.dictionary = dictionary;
	}
	
	
	
	
	
}
