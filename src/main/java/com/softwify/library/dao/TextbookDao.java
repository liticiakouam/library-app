package com.softwify.library.dao;

import com.softwify.library.configuration.DataBaseConfig;
import com.softwify.library.constants.DBConstants;
import com.softwify.library.model.Textbook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TextbookDao {
    private static final Logger logger = LogManager.getLogger(TextbookDao.class.getSimpleName());

    private DataBaseConfig dataBaseConfig;

    public TextbookDao(DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }

    public List<Textbook> getTextbooks() {
        Connection connection = null;
        List<Textbook> textbooks = new ArrayList<>();

        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.GET_TEXTBOOKS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("textbook_id");
                String title = resultSet.getString("title");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                Textbook textbook = new Textbook(id, title, firstname, lastname);
                textbooks.add(textbook);
            }
            dataBaseConfig.closeResultSet(resultSet);
            dataBaseConfig.closePreparedStatement(preparedStatement);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("An error has occurred", e);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }

        return textbooks;
    }

    public boolean deleteTextbook (int id) {
        Connection connection = null;
        boolean deleted = false;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.DELETE_TEXTBOOK);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            deleted = affectedRows == 1;
            dataBaseConfig.closePreparedStatement(preparedStatement);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("An error has occurred", e);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }

        return deleted;
    }

    public List<Textbook> readTextbook (int id) {
        Connection connection = null;
        List<Textbook> textbooks = new ArrayList<>();
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.READ_TEXTBOOK);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                int isbn = resultSet.getInt("isbn");
                String editor = resultSet.getString("editor");
                Date publicationDate = resultSet.getDate("publication_date");
                Textbook textbook = new Textbook(title, firstname, lastname, isbn, editor, publicationDate);
                textbooks.add(textbook);
            }
            dataBaseConfig.closeResultSet(resultSet);
            dataBaseConfig.closePreparedStatement(preparedStatement);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("An error has occurred", e);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }

        return textbooks;
    }
}
