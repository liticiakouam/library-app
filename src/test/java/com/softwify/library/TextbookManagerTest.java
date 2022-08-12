package com.softwify.library;


import com.softwify.library.dao.TextbookDao;
import com.softwify.library.model.Textbook;
import com.softwify.library.service.TextbookManager;
import com.softwify.library.util.OptionSelector;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class TextbookManagerTest {
    TextbookDao textbookDao = Mockito.mock(TextbookDao.class);
    OptionSelector optionSelector = Mockito.mock(OptionSelector.class);
    TextbookManager textbookManager = new TextbookManager(textbookDao, optionSelector);

    @Test
    public void testDisplayTextbooks() {
        textbookManager.displayTextbooks();
        verify(textbookDao, times(1)).getAll();
    }

    @Test
    public void deleteTextbookShouldReturnFalseWhenDaoReturnFalse() {
        when(textbookDao.deleteTextbook(anyInt())).thenReturn(false);

        boolean deleted = textbookManager.delete(5);
        assertFalse(deleted);
        verify(textbookDao, times(1)).deleteTextbook(5);
    }

    @Test
    public void deleteTextbookShouldReturnTrueWhenDaoReturnTrue() {
        when(textbookDao.deleteTextbook(anyInt())).thenReturn(true);
        boolean deleted = textbookManager.delete(5);
        assertTrue(deleted);
        verify(textbookDao, times(1)).deleteTextbook(5);
    }

    @Test
    public void testShouldReturnTrueWhenTextbookIsDisplayed() {
        Textbook textbook = new Textbook(5, "super titre", "Liti", "kouam", 5, 1234, "paris", new Date());
        when(textbookDao.get(5)).thenReturn(textbook);
        assertTrue(textbookManager.readTextbook(5));
        verify(textbookDao, times(1)).get(5);
    }

    @Test
    public void testShouldReturnFalseWhenTextbookIsNotDisplayed() {
        when(textbookDao.get(5)).thenReturn(null);
        assertFalse(textbookManager.readTextbook(5));
        verify(textbookDao, times(1)).get(5);
    }
}
