package model;

import java.io.Serializable;

public class BookInfo implements Serializable {
	private String date;
	private Book book;
	private String dueDate;
	private String username;
	
	public BookInfo(String date, Book book, String dueDate, String username) {
		super();
		this.date = date;
		this.book = book;
		this.dueDate = dueDate;
		this.username = username;
	}


	
	
	
	public String getUsername() {
		return username;
	}





	public void setUsername(String username) {
		this.username = username;
	}





	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Book getBook() {
		return book;
	}

	public String getIsbn() {
		return book.getIsbn();
	}

	public String getTitle() {
		return book.getTitle();
	}

	public String getAuthor() {
		return book.getAuthor();
	}

	public double getPrice() {
		return book.getPrice();
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		return "BookInfo [date=" + date + ", Book=" + book + ", dueDate=" + dueDate + ", bookStatus=" + "]";
	}

}
