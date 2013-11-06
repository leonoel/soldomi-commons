package org.soldomi.commons2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SqlRequest<I, O> extends DaoAction<I, O> {

    protected final String m_sql;

    public SqlRequest(String sql) {
	m_sql = sql;
    }

    public abstract ParameterSet withParameterSet(I i);
}
