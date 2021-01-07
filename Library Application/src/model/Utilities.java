package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Utilities {
	
	//a.
	@SuppressWarnings("resource")
	public static String emitAuthor(String firstNameFileName) throws FileNotFoundException {
		String[] firstNames = new String[1000];
		int x = 0;
		
		//firstName Array created
		File firstNameFile = new File(firstNameFileName);
		Scanner scanner = new Scanner(firstNameFile);
		int num = 0;
		while(scanner.hasNextLine() && num < 1000) {
			String firstName = scanner.nextLine();
			firstNames[x++] = firstName; 
			num++;
			
		}
		

		//random first and last names are chosen
		int rand = ((int)(Math.random() * x));
		int rand2 = ((int)(Math.random() * x));
		String firstName = firstNames[rand];
		String lastName = firstNames[rand];
		
		//a random Author name is created
		String author = firstName + " " + lastName;
		scanner.close();
		return author;
	}
	
	//b.
	@SuppressWarnings("resource")
	public static String[][] emitTitleandIsbn(String titleFileName, String isbnFileName) throws FileNotFoundException {
		String[][] titleAndIsbn = new String[38639][2];
		
		File titleFile = new File(titleFileName);
		Scanner scanner = new Scanner(titleFile, "UTF-8");
		int x = 0;
		
		while(scanner.hasNextLine()) {
			String title = scanner.nextLine();
			titleAndIsbn[x++][0] = title;
			
		}
		
		File isbnFile = new File(isbnFileName);
		scanner = new Scanner(isbnFile, "UTF-8");
		x = 0;
		while(scanner.hasNextLine()) {
			String isbn = scanner.nextLine();
			titleAndIsbn[x++][1] = isbn;
			
		}
		scanner.close();
		return titleAndIsbn;
	}
	
	//c.
	public static double emitPrice() {
		double rand = ((double) Math.random() * 200);
		rand = Math.round(rand * 100.0) / 100.0;
		return rand;
	}
}
