package com.softwify.libraryApp.integration;

import com.softwify.libraryApp.configuration.DataBaseConfig;
import com.softwify.libraryApp.dao.TextbookDao;
import com.softwify.libraryApp.integration.config.DataBaseConfigTest;
import com.softwify.libraryApp.integration.service.DataBasePrepareServiceTextbook;
import com.softwify.libraryApp.model.Textbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

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
        Textbook textbook = textbookDao.get(30);
        assertNull(textbook);
    }

    @Test
    public void testShouldNotReturnNullWhenIdExists() {
        Textbook textbook = textbookDao.get(5);
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

    @Test
    public void AddTexbookInfoSTest() throws ParseException {
        String title = "la maison d'acote";
        int isbn = 123;
        int authorId = 16;
        String editor = "paris";
        String date= "03-10-2002";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date convertedDate = formatter.parse(date);

        Textbook textbook = new Textbook(1, title, authorId, isbn, editor, convertedDate);
        textbookDao.save(textbook);
    }
}
