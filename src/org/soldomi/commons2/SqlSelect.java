package org.soldomi.commons2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

public abstract class SqlSelect<I, O> extends SqlRequest<I, List<O>> {

    public SqlSelect(String sql) {
	super(sql);
    }

    public abstract RowParser<O> withRowParser(I i);

    @Override public Result<List<O>> run(Connection connection, I i) {
	try {
	    PreparedStatement statement = connection.prepareStatement(m_sql);
	    withParameterSet(i).feedStatement(statement);
	    System.out.println("Running SQL : " + statement);
	    statement.execute();
	    ResultSet resultSet = statement.getResultSet();
	    List<O> outputs = new ArrayList<O>();
	    RowParser<O> rowParser = withRowParser(i);
	    while(resultSet.next()) {
		outputs.add(rowParser.parseRow(resultSet));
	    }
	    return Result.<List<O>>success(outputs);
	} catch (SQLException e) {
	    return Result.<List<O>>failure(e.toString());
	}
    }

    public DaoAction<I, O> single() {
	return this.chain(new DaoAction<List<O>, O>() {
		@Override public Result<O> run(Connection connection, List<O> values) {
		    if (values.isEmpty()) {
			return Result.failure("Not found.");
		    } else {
			return Result.success(values.get(0));
		    }
		}
	    });
    }
}
