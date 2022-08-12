package com.softwify.library;


import com.softwify.library.dao.TextbookDao;
import com.softwify.library.service.TextbookManager;
import com.softwify.library.util.OptionSelector;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        textbookManager.displayTextbook();
        verify(textbookDao, times(1)).getTextbooks();
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
}
