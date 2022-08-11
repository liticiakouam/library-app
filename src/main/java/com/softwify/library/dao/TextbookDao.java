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
                int id = resultSet.getInt("id");
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
}
