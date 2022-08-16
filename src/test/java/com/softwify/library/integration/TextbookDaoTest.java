package com.softwify.library.integration;

import com.softwify.library.configuration.DataBaseConfig;
import com.softwify.library.dao.TextbookDao;
import com.softwify.library.integration.config.DataBaseConfigTest;
import com.softwify.library.integration.service.DataBasePrepareServiceTextbook;
import com.softwify.library.model.Textbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
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
    public void getTextbookReturnsExpectedSIzeAndTextbookOrderByTitle() {
        List<Textbook> textbooks = textbookDao.getAll();
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
        assertEquals(6, textbookDao.getAll().size());
        boolean deleted = textbookDao.deleteTextbook(3);
        assertTrue(deleted);

        List<Textbook> textbooks = textbookDao.getAll();
        assertEquals(5, textbooks.size());

        for (Textbook textbook : textbooks) {
            assertNotEquals(3, textbook.getId());
        }
    }

    @Test
    public void testShouldReturnNullWhenIdNotExists() {
        Textbook textbook = textbookDao.getTextbookInformation(30);
        assertNull(textbook);
    }

    @Test
    public void testShouldNotReturnNullWhenIdExists() {
        Textbook textbook = textbookDao.getTextbookInformation(5);
        assertNotNull(textbook);

        assertEquals("Demain tu gouvernes le monde", textbook.getTitle());
        assertEquals("Thione Niang", textbook.getFullName());
        assertEquals(1234567890, textbook.getIsbn());
        assertEquals("paris", textbook.getEditor());

        Date publicationDate = textbook.getPublicationDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(publicationDate);
        assertEquals(2022, calendar.get(Calendar.YEAR));
        assertEquals(7, calendar.get(Calendar.MONTH));
        assertEquals(2, calendar.get(Calendar.DAY_OF_MONTH));
    }
}
