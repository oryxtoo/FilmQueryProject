package com.skilldistillery.filmquery.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.test();
		app.launch();
	}

	private void test() {
		Film film = db.getFilmById(1);
		System.out.println(film);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);
		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		String userChoice = "";
		while (!userChoice.equals("3")) {
			System.out.println("1. Look up film by ID");
			System.out.println("2. Look up a film by keyword");
			System.out.println("3. Exit");

			userChoice = input.nextLine();

			if (userChoice.equals("1")) {
				System.out.println("What film ID would you like to search on?");
				String filmId = input.nextLine();
				Film film = db.getFilmById(Integer.parseInt(filmId));
				if (film == null) {
					System.out.println("There were no movies found with that ID.");
					System.out.println("========================================");
					System.out.println("1. Return to main menu?");
					System.out.println("2. Exit");
					userChoice = input.nextLine();
					if (userChoice.equals("2")) {
						userChoice = "3";
					}
				} else {
					System.out.println("Title       :  " + film.getTitle());
					System.out.println("Year        :  " + film.getReleaseYear());
					System.out.println("Rating      :  " + film.getRating());
					System.out.println("Description :  " + film.getDesc());
					System.out.println("Language    :  " + db.getLanguageById(film.getLangId()));
					System.out.println("========================================");
					System.out.println("1. Return to main menu?");
					System.out.print("2. Exit");
					userChoice = input.nextLine();
					if (userChoice.equals("2")) {
						userChoice = "3";
					}
				}

			}
			if (userChoice.equals("2")) {
				System.out.print("What keyword would you like to search on?");
				String filmSearch = input.nextLine();
				List<Film> filmList = new ArrayList<>();
				filmList = db.getFilmsByKeyword(filmSearch);
				if (filmList == null) {
					System.out.println("There were no movies found with that keyword.");
					System.out.println("========================================");
					System.out.println("1. Return to main menu?");
					System.out.print("2. Exit");
					userChoice = input.nextLine();
					if (userChoice.equals("2")) {
						userChoice = "3";
					}
				} else {
					System.out.println("Our list size :  "+filmList.size());
					for (Film film : filmList) {
						System.out.println("Title       :  " + film.getTitle());
						System.out.println("Year        :  " + film.getReleaseYear());
						System.out.println("Rating      :  " + film.getRating());
						System.out.println("Description :  " + film.getDesc());
						System.out.println("Language    :  " + db.getLanguageById(film.getLangId()));
						System.out.println("========================================");
						System.out.println("");
					}
					System.out.println("1. Return to main menu?");
					System.out.print("2. Exit");
					userChoice = input.nextLine();
					if (userChoice.equals("2")) {
						userChoice = "3";
					}
				}
			}
		}
	}
}
