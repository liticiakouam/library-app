package com.softwify.libraryApp.service;

import com.softwify.libraryApp.dao.TextbookDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.softwify.libraryApp.configuration.DataBaseConfig;
import com.softwify.libraryApp.configuration.DefaultDataBaseConfig;
import com.softwify.libraryApp.dao.AuthorDao;
import com.softwify.libraryApp.util.OptionSelector;

public class LibraryMenu {
	private static final Logger logger = LogManager.getLogger(LibraryMenu.class.getSimpleName());

	private static OptionSelector optionSelector = new OptionSelector();
	private static DataBaseConfig dataBaseConfig = new DefaultDataBaseConfig();
	private static AuthorDao authorDao = new AuthorDao(dataBaseConfig);
	private static TextbookDao textbookDao = new TextbookDao(dataBaseConfig);
	private static AuthorManager authorManager = new AuthorManager(authorDao, optionSelector);
	private static TextbookManager textbookManager = new TextbookManager(textbookDao, optionSelector, authorDao);

	public static void loadApp() {
		System.out.println("Bienvenue a la Bibliotheque");

		boolean continueApp = true;

		while (continueApp) {
			loadMenu();
			int option = optionSelector.readInt();
			switch (option) {
				case 1: {
					authorManager.manage();
					continueApp = false;
					break;
				}
				case 2: {
					textbookManager.manage();
				}
				case 0: {
					System.exit(option);
				}
				default:
					logger.error("S'il vous plait veillez entrer une valeur correcte");
					break;
			}
		}
	}

	private static void loadMenu() {
		System.out.println("Que voulez-vous faire ?\n" + "1 - Gestion des auteurs\n" + "2 - Gestion des livres\n"
				+ "0 - Quitter la bibliothèque \n" + "--------------------------------------------");
	}
}