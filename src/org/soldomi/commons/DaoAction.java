package org.soldomi.commons;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DaoAction<T> {
    public static class DaoException extends Exception {
	public DaoException(Exception e) {
	    super(e);
	}
    }

    public void doTransaction(Connection connection, T t) throws DaoException {
	try {
	    connection.setAutoCommit(false);
	    query(connection, t);
	    connection.commit();
	} catch (SQLException e1) {
	    e1.printStackTrace();
	    try {
		connection.rollback();
	    } catch (SQLException e2) {
		e2.printStackTrace();
	    }
	    throw new DaoException(e1);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public abstract void query(Connection connection, T t) throws SQLException;
}
