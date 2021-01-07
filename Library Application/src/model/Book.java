package model;

import java.io.Serializable;

public class Book implements Serializable, Comparable<Book> {
	private String title;
	private String isbn;
	private String author;
	private double price;
	private int numOfCopies;

	public Book(String title, String isbn, double price, String author, int numOfCopies) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.price = price;
		this.author = author;
		this.numOfCopies = numOfCopies;
	}

	public int getNumOfCopies() {
		return numOfCopies;
	}

	public void subtractCopies() {
		this.numOfCopies -= 1;
	}

	public void setNumOfCopies(int numOfCopies) {
		this.numOfCopies = numOfCopies;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", isbn=" + isbn + ", author=" + author + ", price=" + price
				+ ", no. Of Copies=" + numOfCopies + "]";
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public int compareTo(Book o) {
		if (isbn.compareTo(o.getIsbn()) == 0)
			return 0;
		else if (isbn.compareTo(o.getIsbn()) > 0)
			return 1;
		else
			return -1;
	}

	public void addCopies() {
		numOfCopies += 1;

	}

}
