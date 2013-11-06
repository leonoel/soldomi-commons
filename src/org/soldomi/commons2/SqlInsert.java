package org.soldomi.commons2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SqlInsert<I> extends SqlRequest<I, I> {

    public SqlInsert(String sql) {
	super(sql);
    }

    public abstract I withKey(I i, Long key);

    @Override public Result<I> run(Connection connection, I i) {
	try {
	    PreparedStatement statement = connection.prepareStatement(m_sql);
	    withParameterSet(i).feedStatement(statement);
	    System.out.println("Running SQL : " + statement);
	    statement.execute();
	    ResultSet resultSet = statement.getGeneratedKeys();

	    if(resultSet.next()) {
		return Result.<I>success(withKey(i, resultSet.getLong(1)));
	    } else {
		return Result.<I>failure("Could not retrieve key.");
	    }
	} catch (SQLException e) {
	    return Result.<I>failure(e.toString());
	}
    }
}
