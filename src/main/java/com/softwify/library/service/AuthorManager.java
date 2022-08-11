package com.softwify.library.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.softwify.library.dao.AuthorDao;
import com.softwify.library.model.Author;
import com.softwify.library.util.OptionSelector;

public class AuthorManager {

	private static final Logger logger = LogManager.getLogger(AuthorManager.class.getSimpleName());
	private final AuthorDao authorDao;
	private final OptionSelector optionSelector;

	public AuthorManager(AuthorDao authorDao, OptionSelector optionSelector) {
		this.authorDao = authorDao;
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
						processDelete(substring[1]);
					} catch (Exception e) {
						logger.error("Veuillez saisir un identifiant apres \"delete.\"");
					}
					break;
				}
				case "add": {
					processSave();
					break;
				}
				default:
					logger.error("L'action que vous avez effectuer n'est pas correcte, veuillez entrer la valeur requise");
					break;
			}
		}
	}

	public void displayAuthors() {
		List<Author> authors = authorDao.getAuthors();

		System.out.println("Liste des auteurs");
		for (Author author : authors) {
			System.out.println(author.getId() + " - " + author.getFullName());
		}
	}

	public void processDelete(String idInString){

		try {
			int id = Integer.parseInt(idInString);
			boolean deleted = delete(id);
			if (deleted) {
				returnToList();
			}
		} catch (NumberFormatException e) {
			logger.error(idInString
					+ "n'est pas un nombre, entrer un nombre représentant l'identifiant de l'auteur !!!");
		}
	}

	public boolean delete(int id) {
		boolean result = authorDao.deleteAuthor(id);

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

	public void processSave() {
		System.out.println("Ajout d'un nouvel auteur");
		System.out.print("Entrez le prénom de l'auteur : ");
		String firstName = optionSelector.readString();
		System.out.print("Entrez le nom de l'auteur : ");
		String lastName = optionSelector.readString();

		Author author = new Author(firstName, lastName);

		if (lastName.isEmpty() && firstName.isEmpty()) {
			logger.error("Veillez entrer un prenom et un nom\n");
			processSave();
		} if (firstName.isEmpty()) {
			logger.error("Veillez entrer le prenom\n");
			processSave();
		} if (lastName.isEmpty()) {
			logger.error("Veillez entrer un nom\n");
			processSave();
		} else if (authorDao.checkExistingAuthor(author)) {
			logger.error("\nL'auteur " + author.getFullName() +" existe déjà.\nVeuillez reprendre s'il vous plaît.\n");
			processSave();
		} else {
			Author addedAuthor = authorDao.save(author);
			if (addedAuthor != null){
				System.out.println("\nL'auteur " + author.getFullName() +" a été rajouté avec succès.\n");
				returnToList();
			} else {
				logger.error("Une erreur est survenue lors de l'insertion");
				processSave();
			}
		}
	}
}