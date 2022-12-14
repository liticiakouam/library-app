package com.softwify.libraryApp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.softwify.libraryApp.model.Author;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.softwify.libraryApp.dao.AuthorDao;
import com.softwify.libraryApp.service.AuthorManager;
import com.softwify.libraryApp.util.OptionSelector;

class AuthorManagerTest {

	AuthorDao authorDao = Mockito.mock(AuthorDao.class);
	OptionSelector optionSelector = Mockito.mock(OptionSelector.class);
	AuthorManager authorManager = new AuthorManager(authorDao, optionSelector);

	@Test
	public void testDisplayAuthors() {
		authorManager.displayAuthors();
		verify(authorDao, times(1)).getAll();
	}

	@Test
	public void deleteAuthorShouldReturnFalseWhenDaoReturnFalse() {
		when(authorDao.delete(anyInt())).thenReturn(false);

		boolean deleted = authorManager.delete(10);
		assertFalse(deleted);
		verify(authorDao, times(1)).delete(10);
	}

	@Test
	public void deleteAuthorShouldReturnTrueWhenDaoReturnTrue() {
		when(authorDao.delete(anyInt())).thenReturn(true);
		boolean deleted = authorManager.delete(10);
		assertTrue(deleted);
		verify(authorDao, times(1)).delete(10);
	}

	@Test
	public void insertAuthorSTest() {
		String name = "liticia";
		//Author author = new Author(1, name, name);
		when(optionSelector.readString()).thenReturn(name);
		//when(authorDao.save(any(Author.class))).thenReturn(author);
		authorManager.processSave();
		verify(authorDao, times(1)).save(any(Author.class));
		verify(authorDao, times(1)).checkExistingAuthor(any(Author.class));
	}
}
