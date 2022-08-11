package com.softwify.library;


import com.softwify.library.dao.TextbookDao;
import com.softwify.library.service.TextbookManager;
import com.softwify.library.util.OptionSelector;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TextbookManagerTest {
    TextbookDao textbookDao = Mockito.mock(TextbookDao.class);
    OptionSelector optionSelector = Mockito.mock(OptionSelector.class);
    TextbookManager textbookManager = new TextbookManager(textbookDao, optionSelector);

    @Test
    public void testDisplayTextbooks() {
        textbookManager.displayTextbook();
        verify(textbookDao, times(1)).getTextbooks();
    }
}
