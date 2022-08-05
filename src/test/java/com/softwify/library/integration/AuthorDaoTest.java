package com.softwify.library.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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
	DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();

	@BeforeEach
	public void setUp() {
		dataBasePrepareService.clearDataBaseEntries();
	}

	@Test
	public void getAuthorsReturnsExpectedSizeAndAuthorsOrderByFullName() {
		List<Author> authors = authorDao.getAuthors();
		assertEquals("Napoleon Hill", authors.get(0).getFullName());
		assertEquals("Pierre-Yves Mcsween", authors.get(1).getFullName());
		assertEquals("Thione Niang", authors.get(2).getFullName());
		assertEquals("Wilfried Djomo", authors.get(3).getFullName());
		assertEquals(4, authors.size());
	}

	@Test
	public void givenIdDeleteAuthorRemovesCorrespondingAuthor() {
		assertEquals(4, authorDao.getAuthors().size());
		boolean deleted = authorDao.deleteAuthor(3);
		assertTrue(deleted);

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
