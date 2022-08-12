package com.softwify.library.model;

import java.util.Date;

public class Textbook {
    private int textbook_id;
    private String title;
    private int author_id;
    private int isbn;
    private String editor;
    private Date publication_date;
    private String firstName;
    private String lastName;

    public Textbook(String title, String firstName, String lastName, int isbn, String editor, Date publication_date) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isbn = isbn;
        this.editor = editor;
        this.publication_date = publication_date;
    }

    public Textbook(int id, String title, String firstName, String lastName) {
        this.textbook_id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getTextbook_id() {
        return textbook_id;
    }

    public void setTextbook_id(int textbook_id) {
        this.textbook_id = textbook_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Date getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(Date publication_date) {
        this.publication_date = publication_date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
