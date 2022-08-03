package com.softwify.library.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.softwify.library.dao.AuthorDao;
import com.softwify.library.model.Author;
import com.softwify.library.util.OptionSelector;

public class AuthorManager {

	private static final Logger logger = LogManager.getLogger(AuthorManager.class.getSimpleName());
	private final AuthorDao authorDAO;
	OptionSelector optionSelector = new OptionSelector();

	public AuthorManager(AuthorDao authorDAO) {
		this.authorDAO = authorDAO;
	}

	public void displayAuthors() {
		List<Author> authors = authorDAO.getAuthors();
		System.out.println("Liste des auteurs");
		for (Author author : authors) {
			System.out.println(author.getId() + " - " + author.getFullName());
		}
		System.out.println("\r\n" + "Pour ajouter un nouvel auteur, veuillez saisir : \"add\"\r\n"
				+ "Pour faire une action sur un auteur, veuillez saisir l'action suivit, d'un espace, puis de l'identifiant de l'auteur.\r\n"
				+ "Actions possible : Suppression (delete).\r\n" + "\r\n"
				+ "Ou saisissez \"back\" pour retourner au menu principal \r\n"
				+ "----------------------------------------------");
		selectNextAction();
	}

	private boolean delete(int id) {
		// List<Author> authors = authorDAO.getAuthors();
		boolean result = false;
		result = authorDAO.deleteAuthor(id);

		if (result) {
			System.out.println("L'auteur et ses livres ont ete supprimes avec succes.\r\n" + "\r\n"
					+ "Tapez \"ENTER\" pour retourner\r\n" + "-------------------------");
			nextAction();
		}

		return false;
	}

	private void selectNextAction() {
		boolean continueApp = true;

		while (continueApp) {
			String splitter = optionSelector.readString();
			String substring[] = splitter.split(" ");
			String option = substring[0];

			switch (option) {
			case "back": {
				LibraryMenu.loadApp();
				continueApp = false;
				break;
			}
			case "delete": {
				delete(Integer.parseInt(substring[1]));
				continueApp = false;
				break;
			}
			default:
				logger.error("L'action que vous avez effectuer n'est pas correcte, veuillez entrer la valeur requise");
				break;
			}
		}
	}

	private void nextAction() {
		boolean continueApp = true;

		while (continueApp) {
			String option = optionSelector.readString();
			switch (option) {
			case "enter": {
				displayAuthors();
				break;
			}
			default:
				logger.error("L'action que vous avez effectuer n'est pas correcte, veuillez entrer la valeur requise");
				break;
			}
		}
	}
}
