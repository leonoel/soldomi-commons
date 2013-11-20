package org.soldomi.commons2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldSet1<F1> {
    private final FieldParser<F1> f1;

    public FieldSet1(FieldParser<F1> f1) {
	this.f1 = f1;
    }

    public <T> RowParser<T> toRowParser(final Function1<F1, T> constructor) {
	return new RowParser<T>() {
	    @Override public T parseRow(ResultSet resultSet) throws SQLException {
		return constructor.apply(f1.parse(resultSet));
	    }
	};
    }

    public FieldSet2<F1, Integer> addInt() {
	return new FieldSet2<F1, Integer>(f1, FieldParser.asInt(2));
    }

    public FieldSet2<F1, Long> addLong() {
	return new FieldSet2<F1, Long>(f1, FieldParser.asLong(2));
    }

    public FieldSet2<F1, String> addString() {
	return new FieldSet2<F1, String>(f1, FieldParser.asString(2));
    }
}
