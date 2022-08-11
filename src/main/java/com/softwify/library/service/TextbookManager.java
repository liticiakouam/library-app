package com.softwify.library.service;

import com.softwify.library.dao.TextbookDao;
import com.softwify.library.model.Author;
import com.softwify.library.model.Textbook;
import com.softwify.library.util.OptionSelector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TextbookManager {

        private static final Logger logger = LogManager.getLogger(TextbookManager.class.getSimpleName());
        private final TextbookDao textbookDao;
        private final OptionSelector optionSelector;

        public TextbookManager(TextbookDao textbookDao, OptionSelector optionSelector) {
            this.textbookDao = textbookDao;
            this.optionSelector = optionSelector;
        }

        public void manage(){
            displayTextbook();

            System.out.println("Pour ajouter un nouveau livre, veuillez saisir : \"add\"\n" +
                    "Pour faire une action sur un livre, veuillez saisir l'action suivit, de l'espace, puis de l'identifiant du livre.\n" +
                    "Actions possible : Affichage (read) et Suppression (delete) \n" +
                    "\n" +
                    "Ou saisissez \"back\" pour retourner au menu principal \n" +
                    "----------------------------------------------");
        }

        public void displayTextbook() {
            List<Textbook> textbooks = textbookDao.getTextbooks();

            System.out.println("Liste des livres");
            for (Textbook textbook : textbooks) {
                System.out.println(textbook.getId() + " - " + textbook.getTitle() + " - " + textbook.getFullName());
            }
        }

}
