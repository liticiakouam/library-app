package com.softwify.library.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.softwify.library.configuration.DataBaseConfig;
import com.softwify.library.dao.AuthorDao;
import com.softwify.library.integration.config.DataBaseConfigTest;
import com.softwify.library.integration.service.DataBasePrepareService;
import com.softwify.library.model.Author;

public class AuthorDaoTest {

	DataBaseConfig dataBaseConfig = new DataBaseConfigTest();
	AuthorDao authorDao = new AuthorDao(dataBaseConfig);
	DataBasePrepareService dataBasePrepareService;
	
	@BeforeAll
	public void setUp() {
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	public void setUpTest() {
		dataBasePrepareService.clearDataBaseEntries();
	}

	@Test
	public void getAuthorsReturnsExpectedSizeAndAuthorsOrderByFullName() {
		List<Author> authors = authorDao.getAuthors();
		assertEquals("Diva Kouam", authors.get(0).getFullName());
		assertEquals("Liti AKL", authors.get(1).getFullName());
		assertEquals("Liticia Anze", authors.get(2).getFullName());
		assertEquals("Walter Obrian", authors.get(3).getFullName());
		assertEquals(4, authors.size());
	}

	@Test
	public void givenIdDeleteAuthorRemovesCorrespondingAuthor() {
		assertEquals(4, authorDao.getAuthors().size());
		authorDao.deleteAuthor(4);
		List<Author> authors = authorDao.getAuthors();
		assertEquals(3, authors.size());

		for (Author author : authors) {
			assertNotEquals(4, author.getId());
		}

		/*
		 * boolean authorFound = false; for(Author author : authors) { if
		 * (author.getId() == 4) { authorFound = true; } } assertFalse(authorFound);
		 */
	}
}
