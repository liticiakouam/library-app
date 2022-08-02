package com.softwify.library.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.softwify.library.configuration.DataBaseConfig;
import com.softwify.library.dao.AuthorDao;
import com.softwify.library.integration.config.DataBaseConfigTest;
import com.softwify.library.model.Author;

public class AuthorDaoTest {

	DataBaseConfig dataBaseConfig = new DataBaseConfigTest();
	AuthorDao authorDao = new AuthorDao(dataBaseConfig);

	@Test
	public void authorLenght() {
		List<Author> authors = authorDao.getAuthors();
		assertEquals("Diva Kouam", authors.get(0).getFullName());
		assertEquals("Liti AKL", authors.get(1).getFullName());
		assertEquals("Liticia Anze", authors.get(2).getFullName());
		assertEquals("Walter Obrian", authors.get(3).getFullName());
		assertEquals(4, authors.size());
	}
}
