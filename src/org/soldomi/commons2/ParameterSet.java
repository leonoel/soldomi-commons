package org.soldomi.commons2;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ParameterSet {
    private interface Value {
	public void feedStatement(PreparedStatement statement) throws SQLException;
    }

    private final List<Value> m_values = new ArrayList<Value>();

    public ParameterSet add(final String value) {
	final Integer index = m_values.size() + 1;
	m_values.add(new Value() {
		@Override public void feedStatement(PreparedStatement statement) throws SQLException {
		    statement.setString(index, value);
		}
	    });
	return this;
    }

    public ParameterSet add(final Long value) {
	final Integer index = m_values.size() + 1;
	m_values.add(new Value() {
		@Override public void feedStatement(PreparedStatement statement) throws SQLException {
		    statement.setLong(index, value);
		}
	    });
	return this;
    }

    public void feedStatement(PreparedStatement statement) throws SQLException {
	for (Value value : m_values) {
	    value.feedStatement(statement);
	}
    }
}
