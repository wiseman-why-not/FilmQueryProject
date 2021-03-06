package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

	private void test() throws SQLException {
		Film film = db.findFilmById(1);
		System.out.println(film);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		String userChoice;
		Film searchedFilm = null;
		int filmIdChoice;
		boolean keepGoing = true;
		do {
			System.out.println("\nPlease select from one of the following: ");
			System.out.println("1: Look up a film by its id. ");
			System.out.println("2: Look up a film by a search keyword. ");
			System.out.println("0: Exit the application ");
			System.out.print("Option: ");
			userChoice = input.next().toLowerCase();

			switch (userChoice) {
			case "1":
				try {
					System.out.print("Please enter Film ID: ");
					userChoice = input.next().toLowerCase();
					filmIdChoice = Integer.parseInt(userChoice);
					searchedFilm = db.findFilmById(filmIdChoice);
					if (searchedFilm == null) {
						System.out.println("film id not found");
					}
					System.out.println(searchedFilm);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// happens when user tries to string after selecting option 1.
					// have the user start over
					System.out.println("please ONLY enter numbers");
				}
				break;
			case "2":
				List<Film> listOfFilmFound = new ArrayList<>();
				System.out.print("Please enter keyword to search by: ");
				userChoice = input.next().toLowerCase();
				try {
					listOfFilmFound = db.findFilmsByKeyword(userChoice);
					if (listOfFilmFound.isEmpty()) {
						System.out.println("No film(s) found with that keyword");
					} else {
						for (Film film : listOfFilmFound) {
							System.out.println(film);
						}
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case "0":
				System.out.println("\nexiting...");
				keepGoing = false;
				break;
			default:
				System.out.println("Please type in 1, 2 or 0 !!!! \n");
				break;

			}

		} while (keepGoing);

	}

}
