package com.softwify.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.softwify.library.configuration.DataBaseConfig;
import com.softwify.library.constants.DBConstants;
import com.softwify.library.model.Author;

public class AuthorDao {
	private static final Logger logger = LogManager.getLogger(AuthorDao.class.getSimpleName());

	private DataBaseConfig dataBaseConfig;

	public AuthorDao(DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}

	public List<Author> getAuthors() {
		Connection connection = null;
		List<Author> authors = new ArrayList<>();

		try {
			connection = dataBaseConfig.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.GET_AUTHORS);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				Author author = new Author(id, firstname, lastname);
				authors.add(author);
			}
			dataBaseConfig.closeResultSet(resultSet);
			dataBaseConfig.closePreparedStatement(preparedStatement);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("An error has occurred", e);
		} finally {
			dataBaseConfig.closeConnection(connection);
		}

		return authors;
	}

	public boolean deleteAuthor(int id) {
		Connection connection = null;
		boolean deleted = false;
		try {
			connection = dataBaseConfig.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DBConstants.DELETE_AUTHOR);
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
}
