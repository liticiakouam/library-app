package com.softwify.library.service;

import com.softwify.library.dao.TextbookDao;
import com.softwify.library.model.Author;
import com.softwify.library.model.Textbook;
import com.softwify.library.util.OptionSelector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TextbookManager {

        private static final Logger logger = LogManager.getLogger(TextbookManager.class.getSimpleName());
        private final TextbookDao textbookDao;
        private final OptionSelector optionSelector;

        public TextbookManager(TextbookDao textbookDao, OptionSelector optionSelector) {
            this.textbookDao = textbookDao;
            this.optionSelector = optionSelector;
        }

        public void manage(){
            displayTextbooks();

            boolean continueSection = true;
            while (continueSection){

                System.out.println("\nPour ajouter un nouveau livre, veuillez saisir : \"add\"\n" +
                        "Pour faire une action sur un livre, veuillez saisir l'action suivit, de l'espace, puis de l'identifiant du livre.\n" +
                        "Actions possible : Affichage (read) et Suppression (delete) \n" +
                        "\n" +
                        "Ou saisissez \"back\" pour retourner au menu principal \n" +
                        "----------------------------------------------");
                String input = optionSelector.readString();
                input = input.trim().replaceAll("\\s+", " ");
                String substring[] = input.split(" ");
                String option = substring[0];
                switch (option){
                    case "back": {
                        LibraryMenu.loadApp();
                        continueSection = false;
                        break;
                    }
                    case "delete": {
                        try {
                            processDelete(substring[1]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            logger.error("Veuillez entrer un  identifiant s'il vous plait");
                        }
                        break;
                    }
                    case "read": {
                        try {
                            processReading(substring[1]);
                        } catch (ArrayIndexOutOfBoundsException e){
                            logger.error("Veuillez entrer un  identifiant s'il vous plait");
                        }
                        break;
                    }
                    case "add": {
                        try {
                            processAdd();
                        } catch (ParseException e) {
                            logger.error("Erreur de conversion");
                        }

                        break;
                    }
                    default:
                        logger.error("L'action que vous avez effectuer n'est pas correcte, veuillez entrer la valeur requise");
                        break;
                     }

                }
             }

        public void displayTextbooks() {
            List<Textbook> textbooks = textbookDao.getAll();

            System.out.println("Liste des livres");
            for (Textbook textbook : textbooks) {
                System.out.println(textbook.getId() + " - " + textbook.getTitle() + " - " + textbook.getFullName());
            }
        }

    public boolean delete(int id) {
        boolean result = textbookDao.deleteTextbook(id);
        if (result) {
            System.out.println("Le livre a été supprimé avec succès.");
        } else {
            System.out.println("Le livre que vous avez choisi, n'existe pas.");
        }

        return result;
    }

    public boolean readTextbook(int id) {
        Textbook textbook = textbookDao.get(id);
        if (textbook == null) {
            return false;
        }
        System.out.println("Titre : " + textbook.getTitle());
        System.out.println("Auteur : " + textbook.getFullName());
        System.out.println("ISBN : " + textbook.getIsbn());
        System.out.println("Editeur : " + textbook.getEditor());
        System.out.println("Année de publication : " + textbook.getPublicationDate());
        return true;
    }

    public void processReading(String idInString){

        try {
            int id = Integer.parseInt(idInString);
            boolean read = readTextbook(id);
            if (read) {
                returnToList();
            }
        } catch (NumberFormatException e) {
            logger.error(idInString
                    + "n'est pas un nombre, entrer un nombre représentant l'identifiant du livre !!!");
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

    private void returnToList() {
        System.out.println("\nTapez \"ENTER\" pour retourner\r\n" + "-------------------------");
        optionSelector.readString();
        displayTextbooks();
    }

    public void processAdd() throws ParseException {
        System.out.println("\nAjout d'un nouveau livre");
        System.out.print("Veuillez entrer le nom complet de l'auteur : ");
        String authorFullName = null;
        String firstName = null;
        String lastName = null;
        try {
            authorFullName = optionSelector.readString();
            authorFullName = authorFullName.trim().replaceAll("\\s+", " ");
            String substring[] = authorFullName.split(" ");
            firstName = substring[0];
            lastName = substring[1];

        } catch (ArrayIndexOutOfBoundsException e){
            logger.error("Nom et prenom requis");
        }

        Author author = new Author(firstName, lastName);
        if (textbookDao.checkExistingAuthor(author)) {

            System.out.print("Quel est le titre ? : ");
            String titre = optionSelector.readString();
            while (titre.isEmpty()) {
                logger.error("Titre vide, veuillez reessayer");
                System.out.print("Entrez le titre du livre : ");
                titre = optionSelector.readString();
            }

            System.out.print("Entrez le numéro ISBN : ");
            String isbn = optionSelector.readString();
            int  isbnInt = Integer.parseInt(isbn);
            while (titre.isEmpty()) {
                logger.error("ISBN vide, veuillez reessayer");
                System.out.print("Entrez le ISBN du livre : ");
                isbn = optionSelector.readString();
                isbnInt = Integer.parseInt(isbn);
            }

            System.out.print("L'éditeur du livre : ");
            String editeur = optionSelector.readString();
            while (editeur.isEmpty()) {
                logger.error("Editeur vide veuillez reessayer");
                System.out.print("Entrez l'editeur du livre : ");
                editeur = optionSelector.readString();
            }
            System.out.print("Année de publication : ");
            String date = optionSelector.readString();
            Date convertedDate = null;
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            convertedDate = formatter.parse(date);

            while (date.isEmpty()) {
                    logger.error("date vide, veuillez reessayer");
                    System.out.print("Année de publication : : ");
                    date = optionSelector.readString();
                    formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    convertedDate = formatter.parse(date);
            }
            Textbook validTextbook = textbookDao.getValidTextbook(firstName, lastName);
            int authorId = validTextbook.getId();
            Textbook textbook =  new Textbook(1, titre, authorId, isbnInt, editeur, convertedDate);
            Textbook addedTextbook = textbookDao.save(textbook);
            if (addedTextbook != null){
                System.out.println("\nLe livre " + textbook.getTitle() + " a été rajouté avec succès.\n");
                displayTextbooks();
            }
        } else {
            logger.error("L'auteur " +author.getFullName() + " n'existe pas");
            processAdd();
        }
    }
}
