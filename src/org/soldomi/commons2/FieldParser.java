package org.soldomi.commons2;

import java.sql.SQLException;
import java.sql.ResultSet;

public abstract class FieldParser<T> {
    private FieldParser() {}

    public abstract T parse(ResultSet resultSet) throws SQLException;

    public static FieldParser<Long> asLong(final Integer index) {
	return new FieldParser<Long>() {
	    @Override public Long parse(ResultSet resultSet) throws SQLException {
		return resultSet.getLong(index);
	    }
	};
    }

    public static FieldParser<String> asString(final Integer index) {
	return new FieldParser<String>() {
	    @Override public String parse(ResultSet resultSet) throws SQLException {
		return resultSet.getString(index);
	    }
	};
    }
}
