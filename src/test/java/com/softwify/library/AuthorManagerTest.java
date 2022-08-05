package com.softwify.library;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.softwify.library.dao.AuthorDao;
import com.softwify.library.service.AuthorManager;

class AuthorManagerTest {

	AuthorDao authorDAO = Mockito.mock(AuthorDao.class);
	AuthorManager authorManager = new AuthorManager(authorDAO);

	@Test
	public void testDisplayAuthors() {
		authorManager.displayAuthors();
		verify(authorDAO, times(1)).getAuthors();
	}

}