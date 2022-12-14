package com.softwify.libraryApp.service;

import com.softwify.libraryApp.dao.AuthorDao;
import com.softwify.libraryApp.dao.TextbookDao;
import com.softwify.libraryApp.model.Author;
import com.softwify.libraryApp.model.Textbook;
import com.softwify.libraryApp.util.OptionSelector;
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
        private final AuthorDao authorDao;

        public TextbookManager(TextbookDao textbookDao, OptionSelector optionSelector, AuthorDao authorDao) {
            this.textbookDao = textbookDao;
            this.optionSelector = optionSelector;
            this.authorDao = authorDao;
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
                String[]  substring= input.split(" ");
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
            System.out.println("Le livre a ??t?? supprim?? avec succ??s.");
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
        System.out.println("Ann??e de publication : " + textbook.getPublicationDate());
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
                    + "n'est pas un nombre, entrer un nombre repr??sentant l'identifiant du livre !!!");
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
                    + "n'est pas un nombre, entrer un nombre repr??sentant l'identifiant de l'auteur !!!");
        }
    }

    private void returnToList() {
        System.out.println("\nTapez \"ENTER\" pour retourner\r\n" + "-------------------------");
        optionSelector.readString();
        displayTextbooks();
    }

    public Author retrieveAuthorFullName() {
        System.out.print("Veuillez entrer le nom complet de l'auteur : ");
        String firstName = null;
        String lastName = null;
        try {
            String authorFullName = optionSelector.readString();
            authorFullName = authorFullName.trim().replaceAll("\\s+", " ");
            String[] substring = authorFullName.split(" ");
            firstName = substring[0];
            lastName = substring[1];

            while (firstName.isEmpty() || lastName.isEmpty()) {
                authorFullName = optionSelector.readString();
                authorFullName = authorFullName.trim().replaceAll("\\s+", " ");
                substring = authorFullName.split(" ");
                firstName = substring[0];
                lastName = substring[1];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Nom et prenom requis");
        }
        return new Author(firstName, lastName);
    }

    public void processAdd() throws ParseException {
        System.out.println("\nAjout d'un nouveau livre");

        Author author = retrieveAuthorFullName();
        if(author.getFirstName() != null && author.getLastName() != null){
            Author existingAuthor = authorDao.getByFirstNameAndLastName(author.getFirstName(), author.getLastName());
            if(existingAuthor != null) {
                Textbook textbook = retrieveBookInformation(existingAuthor.getId());
                saveBook(textbook);
            } else {
                logger.error("L'auteur " + author.getFullName() + " n'existe pas");
                processAdd();
            }
        } else {
            logger.error("s'il vous plait veuillez entrer le nom et le pronom de l'auteur");
            processAdd();
        }
    }

    public Textbook retrieveBookInformation(int authorId) throws ParseException {
        System.out.print("Quel est le titre ? : ");
        String title = optionSelector.readString();
        while (title.isEmpty()) {
            logger.error("Titre vide, veuillez reessayer");
            System.out.print("Entrez le titre du livre : ");
            title = optionSelector.readString();
        }

        System.out.print("Entrez le num??ro ISBN : ");
        int isbnInt = optionSelector.readInt();
        while (isbnInt == 0) {
            logger.error("ISBN vide, veuillez reessayer");
            System.out.print("Entrez le ISBN du livre : ");
            isbnInt = optionSelector.readInt();
        }

        System.out.print("L'??diteur du livre : ");
        String editor = optionSelector.readString();
        while (editor.isEmpty()) {
            logger.error("Editeur vide veuillez reessayer");
            System.out.print("Entrez l'editeur du livre : ");
            editor = optionSelector.readString();
        }
        System.out.print("Ann??e de publication : ");
        String date = optionSelector.readDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date convertedDate = formatter.parse(date);

        while (date.isEmpty()) {
            logger.error("date vide, veuillez reessayer");
            System.out.print("Ann??e de publication : : ");
            date = optionSelector.readDate();
            formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            convertedDate = formatter.parse(date);
        }

        return new Textbook(-1, title, authorId, isbnInt, editor, convertedDate);
    }

    private void saveBook(Textbook textbook){
        Textbook addedTextbook = textbookDao.save(textbook);
        if (addedTextbook != null){
            System.out.println("\nLe livre " + textbook.getTitle() + " a ??t?? rajout?? avec succ??s.\n");
        } else {
            logger.error("Une erreur est survenue");
        }
        returnToList();
    }
}
