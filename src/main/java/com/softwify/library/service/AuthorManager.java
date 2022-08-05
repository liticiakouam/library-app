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
	private final OptionSelector optionSelector;

	public AuthorManager(AuthorDao authorDAO, OptionSelector optionSelector) {
		this.authorDAO = authorDAO;
		this.optionSelector = optionSelector;
	}

	public void manage() {
		displayAuthors();

		boolean continueSection = true;

		while (continueSection) {
			System.out.println("\r\n" + "Pour ajouter un nouvel auteur, veuillez saisir : \"add\"\r\n"
					+ "Pour faire une action sur un auteur, veuillez saisir l'action suivit, d'un espace, puis de l'identifiant de l'auteur.\r\n"
					+ "Actions possible : Suppression (delete).\r\n" + "\r\n"
					+ "Ou saisissez \"back\" pour retourner au menu principal \r\n"
					+ "----------------------------------------------");

			String input = optionSelector.readString();
			input = input.trim().replaceAll("\\s+", " ");
			String substring[] = input.split(" ");
			String option = substring[0];

			switch (option) {
			case "back": {
				LibraryMenu.loadApp();
				continueSection = false;
				break;
			}
			case "delete": {
				try {
					int id = Integer.parseInt(substring[1]);
					boolean deleted = delete(id);
					if (deleted) {
						returnToList();
					}
				} catch (NumberFormatException e) {
					logger.error(substring[1]
							+ "n'est pas un nombre, entrer un nombre représentant l'identifiant de l'auteur !!!");
				}
				break;
			}
			default:
				logger.error("L'action que vous avez effectuer n'est pas correcte, veuillez entrer la valeur requise");
				break;
			}
		}
	}

	public void displayAuthors() {
		List<Author> authors = authorDAO.getAuthors();

		System.out.println("Liste des auteurs");
		for (Author author : authors) {
			System.out.println(author.getId() + " - " + author.getFullName());
		}
	}

	public boolean delete(int id) {
		boolean result = authorDAO.deleteAuthor(id);

		if (result) {
			System.out.println("L'auteur et ses livres ont ete supprimes avec succes.");
		} else {
			System.out.println("L'auteur que vous avez choisi, n'existe pas.");
		}

		return result;
	}

	private void returnToList() {
		System.out.println("Tapez \"ENTER\" pour retourner\r\n" + "-------------------------");
		optionSelector.readString();
		displayAuthors();
	}
}
