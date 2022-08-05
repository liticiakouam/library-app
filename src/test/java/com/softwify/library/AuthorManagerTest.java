package com.softwify.library;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.softwify.library.dao.AuthorDao;
import com.softwify.library.service.AuthorManager;
import com.softwify.library.util.OptionSelector;

class AuthorManagerTest {

	AuthorDao authorDao = Mockito.mock(AuthorDao.class);
	OptionSelector optionSelector = Mockito.mock(OptionSelector.class);
	AuthorManager authorManager = new AuthorManager(authorDao, optionSelector);

	@Test
	public void testDisplayAuthors() {
		authorManager.displayAuthors();
		verify(authorDao, times(1)).getAuthors();
	}

	@Test
	public void deleteAuthorShouldReturnFalseWhenDaoReturnFalse() {
		when(authorDao.deleteAuthor(anyInt())).thenReturn(false);

		boolean deleted = authorManager.delete(10);
		assertFalse(deleted);
		verify(authorDao, times(1)).deleteAuthor(10);
	}

	@Test
	public void deleteAuthorShouldReturnTrueWhenDaoReturnTrue() {
		when(authorDao.deleteAuthor(anyInt())).thenReturn(true);

		boolean deleted = authorManager.delete(10);
		assertTrue(deleted);
		verify(authorDao, times(1)).deleteAuthor(10);
	}
}
