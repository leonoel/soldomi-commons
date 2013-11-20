package org.soldomi.commons2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldSet {
    public <T> RowParser<T> toRowParser(final Function0<T> constructor) {
	return new RowParser<T>() {
	    @Override public T parseRow(ResultSet resultSet) throws SQLException {
		return constructor.apply();
	    }
	};
    }

    public FieldSet1<Integer> addInt() {
	return new FieldSet1<Integer>(FieldParser.asInt(1));
    }

    public FieldSet1<Long> addLong() {
	return new FieldSet1<Long>(FieldParser.asLong(1));
    }

    public FieldSet1<String> addString() {
	return new FieldSet1<String>(FieldParser.asString(1));
    }
}
