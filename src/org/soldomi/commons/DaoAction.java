package org.soldomi.commons;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DaoAction<I> {

    public DaoAction() {
    }

    public DaoAction<I> chain(final DaoAction<I> other) {
	return new DaoAction<I>() {
	    @Override public Result<Void> run(Connection connection, I i) {
		Result<Void> result = DaoAction.this.run(connection, i);
		if (result.success) 
		    result = other.run(connection, i);
		return result;
	    }
	};
    }

    public abstract Result<Void> run(Connection connection,
				     I i);

    public Result<Void> runInTransaction(Connection connection,
					 I i) {
	Result<Void> result;
	try {
	    connection.setAutoCommit(false);
	    run(connection, i);
	    connection.commit();
	    result = Result.success(null);
	} catch (SQLException e1) {
	    try {
		connection.rollback();
	    } catch (SQLException e2) {
		e2.printStackTrace();
	    }
	    result = Result.<Void>failure(e1.toString());
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e2) {
		e2.printStackTrace();
	    }
	}
	return result;
    }

}
