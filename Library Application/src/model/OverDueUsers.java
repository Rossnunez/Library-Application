package model;

import java.io.Serializable;

public class OverDueUsers implements Serializable, Comparable<OverDueUsers>  {
	public UserInfo userInfo;
	public BookInfo bookInfo;
	
	
	public OverDueUsers(UserInfo userInfo, BookInfo bookInfo) {
		super();
		this.userInfo = userInfo;
		this.bookInfo = bookInfo;
	}
	
	public String getUsername() {
		return userInfo.getUsername();
	}
	
	public AccountStatus getStatus() {
		return userInfo.getStatus();
	}
	
	public void setAccountStatus(AccountStatus accountStatus) {
		userInfo.setStatus(accountStatus);
	}
	
	public String getTitle() {
		return bookInfo.getTitle();
	}
	
	public String getAuthor() {
		return bookInfo.getAuthor();
	}
	
	public double getPrice() {
		return bookInfo.getPrice();
	}
	
	public String getIsbn() {
		return bookInfo.getIsbn();
	}
	
	public String getDueDate() {
		return bookInfo.getDueDate();
	}
	
	
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public BookInfo getBookInfo() {
		return bookInfo;
	}
	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}
	@Override
	public String toString() {
		return "OverDueUsers [userInfo=" + userInfo + ", bookInfo=" + bookInfo + "]";
	}

	@Override
	public int compareTo(OverDueUsers o) {
	    if(o.getIsbn().compareTo(getIsbn()) < 0){  
	        return -1;  
	    }else if(o.getIsbn().compareTo(getIsbn()) > 0){  
	        return 1;  
	    }else{  
	    return 0;  
	    }  
	}  
	
	
	
}
