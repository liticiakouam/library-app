package com.softwify.libraryApp.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.softwify.libraryApp.configuration.DataBaseConfig;
import com.softwify.libraryApp.dao.AuthorDao;
import com.softwify.libraryApp.integration.config.DataBaseConfigTest;
import com.softwify.libraryApp.integration.service.DataBasePrepareService;
import com.softwify.libraryApp.model.Author;

public class AuthorDaoTest {

	DataBaseConfig dataBaseConfig = new DataBaseConfigTest();
	AuthorDao authorDao = new AuthorDao(dataBaseConfig);
	DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();

	@BeforeEach
	public void setUp() {
		dataBasePrepareService.clearDataBaseEntries();
	}

	@Test
	public void getAuthorsReturnsExpectedSizeAndAuthorsOrderByFullName() {
		List<Author> authors = authorDao.getAll();
		assertEquals("Napoleon Hill", authors.get(0).getFullName());
		assertEquals("Pierre-Yves Mcsween", authors.get(1).getFullName());
		assertEquals("Thione Niang", authors.get(2).getFullName());
		assertEquals("Wilfried Djomo", authors.get(3).getFullName());
		assertEquals(4, authors.size());
	}

	@Test
	public void givenIdDeleteAuthorRemovesCorrespondingAuthor() {
		assertEquals(4, authorDao.getAll().size());
		boolean deleted = authorDao.delete(3);
		assertTrue(deleted);

		List<Author> authors = authorDao.getAll();
		assertEquals(3, authors.size());

		for (Author author : authors) {
			assertNotEquals(4, author.getId());
		}

		/*
		 * boolean authorFound = false; for(Author author : authors) { if
		 * (author.getId() == 4) { authorFound = true; } } assertFalse(authorFound);
		 */
	}

	@Test
	public void insertAuthor() {
		Author authorAdd = new Author("liti", "kouam");
		authorDao.save(authorAdd);
		assertEquals(5, authorDao.getAll().size());
	}

	@Test
	public void checkAuthorAlreadyExist() {
		Author author = new Author("Thione", "Niang");
		boolean check = authorDao.checkExistingAuthor(author);
		assertTrue(check);
	}

	@Test
	public void checkAuthorIsNotAlreadyExist() {
		Author author = new Author("anze", "Niang");
		boolean check = authorDao.checkExistingAuthor(author);
		assertFalse(check);
	}
}
