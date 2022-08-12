package com.softwify.library.integration;

import com.softwify.library.configuration.DataBaseConfig;
import com.softwify.library.dao.AuthorDao;
import com.softwify.library.dao.TextbookDao;
import com.softwify.library.integration.config.DataBaseConfigTest;
import com.softwify.library.integration.service.DataBasePrepareService;
import com.softwify.library.integration.service.DataBasePrepareServiceTextbook;
import com.softwify.library.model.Author;
import com.softwify.library.model.Textbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextbookDaoTest {
    DataBaseConfig dataBaseConfig = new DataBaseConfigTest();
    TextbookDao textbookDao = new TextbookDao(dataBaseConfig);
    DataBasePrepareServiceTextbook dataBasePrepareServiceTextbook = new DataBasePrepareServiceTextbook();

    @BeforeEach
    public void setUp() {
        dataBasePrepareServiceTextbook.clearDataBaseEntries();
    }

    @Test
    public void getTextbookReturnsExpectedSizeAndTextbookOrderByTitle() {
        List<Textbook> textbooks = textbookDao.getTextbooks();
        assertEquals("Demain tu gouvernes le monde", textbooks.get(0).getTitle());
        assertEquals("En as-tu vraiment besoin ?", textbooks.get(1).getTitle());
        assertEquals("L'effet papillon", textbooks.get(2).getTitle());
        assertEquals("Liberté 45", textbooks.get(3).getTitle());
        assertEquals("Social Entrepreneurship", textbooks.get(4).getTitle());
        assertEquals("Think and grow rich", textbooks.get(5).getTitle());
        assertEquals(6, textbooks.size());
    }

    @Test
    public void givenIdDeleteTextbookRemovesCorrespondingTextbook() {
        assertEquals(6, textbookDao.getTextbooks().size());
        boolean deleted = textbookDao.deleteTextbook(3);
        assertTrue(deleted);

        List<Textbook> textbooks = textbookDao.getTextbooks();
        assertEquals(5, textbooks.size());

        for (Textbook textbook : textbooks) {
            assertNotEquals(3, textbook.getTextbook_id());
        }
    }
}
