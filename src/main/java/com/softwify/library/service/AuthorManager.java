package com.softwify.library.service;

import java.util.List;

import com.softwify.library.dao.AuthorDao;
import com.softwify.library.model.Author;
import com.softwify.library.util.OptionSelector;

public class AuthorManager {

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

	private void selectNextAction() {
		boolean continueApp = true;
		String option = optionSelector.readString();
		while (continueApp) {
			switch (option) {
			case "back": {
				LibraryMenu.loadApp();
				break;
			}
			case "delete": {
				break;
			}
			default:
				break;
			}
		}

	}

}
