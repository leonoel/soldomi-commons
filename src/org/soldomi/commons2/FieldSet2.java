package org.soldomi.commons2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldSet2<F1, F2> {

    private final FieldParser<F1> f1;
    private final FieldParser<F2> f2;

    public FieldSet2(FieldParser<F1> f1,
		     FieldParser<F2> f2) {
	this.f1 = f1;
	this.f2 = f2;
    }


    public <T> RowParser<T> toRowParser(final Function2<F1, F2, T> constructor) {
	return new RowParser<T>() {
	    @Override public T parseRow(ResultSet resultSet) throws SQLException {
		return constructor.apply(f1.parse(resultSet),
					 f2.parse(resultSet));
	    }
	};
    }

    public FieldSet3<F1, F2, Integer> addInt() {
	return new FieldSet3<F1, F2, Integer>(f1, f2, FieldParser.asInt(3));
    }

    public FieldSet3<F1, F2, Long> addLong() {
	return new FieldSet3<F1, F2, Long>(f1, f2, FieldParser.asLong(3));
    }

    public FieldSet3<F1, F2, String> addString() {
	return new FieldSet3<F1, F2, String>(f1, f2, FieldParser.asString(3));
    }

}
